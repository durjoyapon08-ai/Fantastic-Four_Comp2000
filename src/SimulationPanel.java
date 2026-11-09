import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.Timer;

public class SimulationPanel extends Panel {

    private static final int TOTAL_PEOPLE = 100;
    private static final int START_INFECTED = 5;
    private static final double CONTACT_DISTANCE = 18;

    private static final long DEATH_TIME = 50000;
    private static final long RECOVER_TO_HEALTHY_TIME = 10000;

    private static final double VACCINATION_RATE = 0.05;
    private static final double VACCINE_EFFECTIVENESS = 0.2;

    private final ArrayList<Person> people = new ArrayList<>();
    private final ArrayList<RecoveryTent> tents = new ArrayList<>();

    private final Virus virus = new Virus("Zombie Virus", 45);
    private final InfectionStrategy infectionStrategy;
    private final boolean parallelStatistics;
    private final Timer simulationTimer;

    private Hospital hospital;
    private boolean tentsActive;
    private boolean hospitalActive;

    private long lastTent;
    private long tentStart;
    private long lastHospital;
    private long hospitalStart;
    private long lastHealthCheck;

    private Map<String, Long> statusCounts = Map.of();

    public SimulationPanel() {
        this(new AgeBasedInfectionStrategy(), false);
    }

    public SimulationPanel(
            InfectionStrategy infectionStrategy,
            boolean parallelStatistics) {

        if (infectionStrategy == null) {
            throw new IllegalArgumentException(
                    "Infection strategy cannot be null."
            );
        }

        this.infectionStrategy = infectionStrategy;
        this.parallelStatistics = parallelStatistics;

        // Timer callbacks and painting run on the same UI thread.
        simulationTimer = new Timer(30, event -> {
            updateSimulation();
            repaint();
        });

        setBackground(Color.LIGHT_GRAY);
        createPeople();
        updateStatusCounts();
    }

    private void createPeople() {
        for (int i = 0; i < TOTAL_PEOPLE; i++) {
            String name = "Person " + (i + 1);
            int age = 18 + (int) (Math.random() * 63);

            if (i < START_INFECTED) {
                people.add(new Infected(
                        name, age, virus,
                        randomX(), randomY(),
                        randomSpeed(), randomSpeed()
                ));
            } else {
                boolean vaccinated = Math.random() < VACCINATION_RATE;

                people.add(new Healthy(
                        name, age,
                        randomX(), randomY(),
                        randomSpeed(), randomSpeed(),
                        vaccinated
                ));
            }
        }
    }

    private float randomX() {
        return (float) (Math.random() * 850);
    }

    private float randomY() {
        return 160 + (float) (Math.random() * 400);
    }

    private float randomSpeed() {
        float speed = (float) (Math.random() * 4 - 2);

        if (Math.abs(speed) < 0.4f) {
            speed = 0.8f;
        }

        return speed;
    }

    private int randomPosition(int size, int objectSize) {
        return (int) (Math.random() * Math.max(1, size - objectSize));
    }

    private int randomBuildingY(int objectHeight) {
        return 150 + randomPosition(
                Math.max(100, getHeight() - 150),
                objectHeight
        );
    }

    public void startSimulation() {
        if (simulationTimer.isRunning()) {
            return;
        }

        spawnTents();
        spawnHospital();

        long now = System.currentTimeMillis();

        tentsActive = true;
        hospitalActive = true;

        tentStart = now;
        lastTent = now;
        hospitalStart = now;
        lastHospital = now;
        lastHealthCheck = now;

        simulationTimer.start();
    }

    public void stopSimulation() {
        simulationTimer.stop();
    }

    private void updateSimulation() {
        long now = System.currentTimeMillis();

        updateBuildings(now);

        for (Person person : people) {
            person.move(getWidth(), getHeight());
        }

        // Check infection and natural health transitions once per second.
        if (now - lastHealthCheck >= 1000) {
            spreadInfection();
            naturalRecovery(now);
            checkDeaths(now);
            recoveredToHealthy(now);
            lastHealthCheck = now;
        }

        buildingTreatment(now);
        vaccinateAtHospital(now);
        updateStatusCounts();
    }

    private void updateStatusCounts() {
        // Parallel workers receive status strings, not live Person objects.
        List<String> statuses = people.stream()
                .map(Person::getStatus)
                .collect(Collectors.toUnmodifiableList());

        statusCounts = StatusStatistics.count(statuses, parallelStatistics);
    }

    private void spawnTents() {
        tents.clear();

        for (int i = 0; i < 3; i++) {
            tents.add(new RecoveryTent(
                    randomPosition(getWidth(), 100),
                    randomBuildingY(100),
                    100, 100
            ));
        }
    }

    private void spawnHospital() {
        hospital = new Hospital(
                randomPosition(getWidth(), 120),
                randomBuildingY(100),
                120, 100
        );
    }

    private void updateBuildings(long now) {
        if (tentsActive && now - tentStart >= 5000) {
            tentsActive = false;
            tents.clear();
        }

        if (!tentsActive && now - lastTent >= 10000) {
            spawnTents();
            tentsActive = true;
            tentStart = now;
            lastTent = now;
        }

        if (hospitalActive && now - hospitalStart >= 3000) {
            hospitalActive = false;
        }

        if (!hospitalActive && now - lastHospital >= 15000) {
            spawnHospital();
            hospitalActive = true;
            hospitalStart = now;
            lastHospital = now;
        }
    }

    private void spreadInfection() {
        // Newly infected people cannot spread the virus in this same check.
        ArrayList<Person> snapshot = new ArrayList<>(people);

        for (Person infected : snapshot) {
            if (!(infected instanceof Infected)) {
                continue;
            }

            for (int i = 0; i < people.size(); i++) {
                Person target = people.get(i);

                if (!(target instanceof Healthy)) {
                    continue;
                }

                Healthy healthy = (Healthy) target;

                if (healthy.isImmune()
                        && Math.random() < VACCINE_EFFECTIVENESS) {
                    continue;
                }

                if (distance(infected, healthy) <= CONTACT_DISTANCE
                        && shouldInfect(healthy)) {
                    infect(i, healthy);
                }
            }
        }
    }

    private double distance(Person first, Person second) {
        float dx = first.getX() - second.getX();
        float dy = first.getY() - second.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // The selected strategy decides whether contact causes infection.
    private boolean shouldInfect(Healthy person) {
        return infectionStrategy.shouldInfect(person, virus);
    }

    private void infect(int index, Healthy person) {
        people.set(index, new Infected(
                person.getId(), person.getName(), person.getAge(), virus,
                person.getX(), person.getY(),
                person.getSpeedX(), person.getSpeedY()
        ));
    }

    private void naturalRecovery(long now) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Infected)) {
                continue;
            }

            Infected infected = (Infected) people.get(i);

            if (now - infected.getInfectedSince() < 15000) {
                continue;
            }

            int chance;
            if (infected.getAge() < 30) {
                chance = 18;
            } else if (infected.getAge() > 50) {
                chance = 6;
            } else {
                chance = 10;
            }

            if (Math.random() * 100 < chance) {
                recover(i, infected);
            }
        }
    }

    private void vaccinateAtHospital(long now) {
        for (Person person : people) {
            if (!(person instanceof Healthy)) {
                continue;
            }

            Healthy healthy = (Healthy) person;

            if (healthy.isImmune()) {
                continue;
            }

            if (hospitalActive && hospital != null
                    && hospital.isPersonInside(healthy)) {

                if (healthy.getHospitalEntryTime() == -1) {
                    healthy.setHospitalEntryTime(now);
                }

                if (now - healthy.getHospitalEntryTime() >= 2000) {
                    healthy.setImmune(true);
                }
            } else {
                // Time outside the hospital does not count toward vaccination.
                healthy.setHospitalEntryTime(-1);
            }
        }
    }

    private void buildingTreatment(long now) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Infected)) {
                continue;
            }

            Infected infected = (Infected) people.get(i);
            boolean insideTent = false;

            if (tentsActive) {
                for (RecoveryTent tent : tents) {
                    if (tent.isPersonInside(infected)) {
                        insideTent = true;
                        break;
                    }
                }
            }

            if (insideTent) {
                if (infected.getTentEntryTime() == -1) {
                    infected.setTentEntryTime(now);
                }

                if (now - infected.getTentEntryTime() >= 2000) {
                    recover(i, infected);
                    continue;
                }
            } else {
                infected.setTentEntryTime(-1);
            }

            if (hospitalActive && hospital != null
                    && hospital.isPersonInside(infected)) {

                if (infected.getHospitalEntryTime() == -1) {
                    infected.setHospitalEntryTime(now);
                }

                if (now - infected.getHospitalEntryTime() >= 1000) {
                    recover(i, infected);
                }
            } else {
                infected.setHospitalEntryTime(-1);
            }
        }
    }

    private void recover(int index, Infected infected) {
        people.set(index, new Recovered(
                infected.getId(), infected.getName(), infected.getAge(),
                infected.getX(), infected.getY(),
                infected.getSpeedX(), infected.getSpeedY()
        ));
    }

    private void recoveredToHealthy(long now) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Recovered)) {
                continue;
            }

            Recovered recovered = (Recovered) people.get(i);

            if (now - recovered.getRecoveredSince() >= RECOVER_TO_HEALTHY_TIME) {
                people.set(i, new Healthy(
                        recovered.getId(), recovered.getName(), recovered.getAge(),
                        recovered.getX(), recovered.getY(),
                        recovered.getSpeedX(), recovered.getSpeedY(), true
                ));
            }
        }
    }

    private void checkDeaths(long now) {
        for (int i = 0; i < people.size(); i++) {
            if (!(people.get(i) instanceof Infected)) {
                continue;
            }

            Infected infected = (Infected) people.get(i);

            if (now - infected.getInfectedSince() >= DEATH_TIME) {
                people.set(i, new Dead(
                        infected.getId(), infected.getName(), infected.getAge(),
                        infected.getX(), infected.getY()
                ));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBuildings(g);
        drawPeople(g);
        drawLegend(g);
        drawStats(g);
    }

    private void drawBuildings(Graphics g) {
        if (tentsActive) {
            for (RecoveryTent tent : tents) {
                g.setColor(Color.ORANGE);
                g.fillRect(
                        tent.getX(), tent.getY(),
                        tent.getWidth(), tent.getHeight()
                );
                g.setColor(Color.BLACK);
                g.drawString("Recovery Tent", tent.getX() + 5, tent.getY() + 20);
            }
        }

        if (hospitalActive && hospital != null) {
            g.setColor(Color.WHITE);
            g.fillRect(
                    hospital.getX(), hospital.getY(),
                    hospital.getWidth(), hospital.getHeight()
            );
            g.setColor(Color.BLACK);
            g.drawRect(
                    hospital.getX(), hospital.getY(),
                    hospital.getWidth(), hospital.getHeight()
            );
            g.drawString("Hospital", hospital.getX() + 30, hospital.getY() + 20);
        }
    }

    private void drawPeople(Graphics g) {
        for (Person person : people) {
            if (person instanceof Healthy && ((Healthy) person).isImmune()) {
                g.setColor(Color.CYAN);
            } else {
                switch (person.getStatus()) {
                    case "Healthy":
                        g.setColor(Color.GREEN);
                        break;
                    case "Infected":
                        g.setColor(Color.RED);
                        break;
                    case "Recovered":
                        g.setColor(Color.BLUE);
                        break;
                    default:
                        g.setColor(Color.BLACK);
                        break;
                }
            }

            g.fillOval(
                    (int) person.getX(), (int) person.getY(),
                    Person.DIAMETER, Person.DIAMETER
            );
        }
    }

    private void drawLegend(Graphics g) {
        int x = 15;
        int y = 15;

        g.setColor(Color.WHITE);
        g.fillRect(x, y, 180, 145);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 180, 145);
        g.drawString("COLOR GUIDE", x + 15, y + 20);

        drawLegendItem(g, Color.GREEN, "Healthy", x, y + 40);
        drawLegendItem(g, Color.RED, "Infected", x, y + 60);
        drawLegendItem(g, Color.BLUE, "Recovered", x, y + 80);
        drawLegendItem(g, Color.BLACK, "Dead", x, y + 100);
        drawLegendItem(g, Color.CYAN, "Vaccinated", x, y + 120);
    }

    private void drawLegendItem(Graphics g, Color color, String text, int x, int y) {
        g.setColor(color);
        g.fillOval(x + 15, y - 10, 12, 12);
        g.setColor(Color.BLACK);
        g.drawString(text, x + 35, y);
    }

    private void drawStats(Graphics g) {
        int healthy = statusCounts.getOrDefault("Healthy", 0L).intValue();
        int infected = statusCounts.getOrDefault("Infected", 0L).intValue();
        int recovered = statusCounts.getOrDefault("Recovered", 0L).intValue();
        int dead = statusCounts.getOrDefault("Dead", 0L).intValue();
        int total = healthy + infected + recovered + dead;

        int x = Math.max(10, getWidth() - 245);

        g.setColor(Color.WHITE);
        g.fillRect(x, 10, 230, 125);
        g.setColor(Color.BLACK);
        g.drawRect(x, 10, 230, 125);

        g.drawString("EPIDEMIC STATUS", x + 15, 30);
        g.drawString("Total People: " + total, x + 15, 50);

        drawStatusLine(g, "Healthy", healthy, total, x + 15, 70);
        drawStatusLine(g, "Infected", infected, total, x + 15, 88);
        drawStatusLine(g, "Recovered", recovered, total, x + 15, 106);
        drawStatusLine(g, "Dead", dead, total, x + 15, 124);
    }

    private void drawStatusLine(
            Graphics g, String label, int count, int total, int x, int y) {
        double percentage = total == 0 ? 0 : count * 100.0 / total;
        g.drawString(
                String.format("%s: %d (%.1f%%)", label, count, percentage),
                x, y
        );
    }
}