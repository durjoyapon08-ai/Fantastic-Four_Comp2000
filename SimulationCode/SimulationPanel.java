import java.awt.*;
import java.util.ArrayList;

public class SimulationPanel extends Panel implements Runnable {

    private ArrayList<Person> people;
    private Thread simulationThread;
    private Virus virus;

    // 3 Recovery Tents
    private ArrayList<RecoveryTent> recoveryTents;

    // 1 Hospital
    private Hospital hospital;

    // Tent timing
    private long lastTentSpawn;
    private long tentSpawnTime;
    private boolean tentsActive;

    // Hospital timing
    private long lastHospitalSpawn;
    private long hospitalSpawnTime;
    private boolean hospitalActive;


    public SimulationPanel() {

        setBackground(Color.LIGHT_GRAY);

        people = new ArrayList<>();
        recoveryTents = new ArrayList<>();

        virus = new Virus(
            "Zombie Virus",
            40
        );


        // Create 50 healthy people
        for (int i = 0; i < 50; i++) {

            Healthy healthyPerson =
                new Healthy(
                    "Person " + i,
                    20,

                    (float)(Math.random() * 750),
                    (float)(Math.random() * 500),

                    (float)(Math.random() * 4 - 2),
                    (float)(Math.random() * 4 - 2)
                );

            people.add(healthyPerson);
        }


        // Create 2 infected people
        people.add(
            new Infected(
                "Infected 1",
                25,
                virus,

                (float)(Math.random() * 750),
                (float)(Math.random() * 500),

                (float)(Math.random() * 4 - 2),
                (float)(Math.random() * 4 - 2)
            )
        );


        people.add(
            new Infected(
                "Infected 2",
                30,
                virus,

                (float)(Math.random() * 750),
                (float)(Math.random() * 500),

                (float)(Math.random() * 4 - 2),
                (float)(Math.random() * 4 - 2)
            )
        );


        lastTentSpawn = System.currentTimeMillis();
        lastHospitalSpawn = System.currentTimeMillis();

        tentsActive = false;
        hospitalActive = false;
    }


    public void startSimulation() {

        simulationThread = new Thread(this);
        simulationThread.start();
    }


    @Override
    public void run() {

        while (true) {

            updateSimulation();
            repaint();

            try {
                Thread.sleep(30);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // Create 3 recovery tents at random locations
    private void spawnRecoveryTents() {

        recoveryTents.clear();

        for (int i = 0; i < 3; i++) {

            int tentWidth = 100;
            int tentHeight = 100;

            int x =
                (int)(Math.random()
                * (getWidth() - tentWidth));

            int y =
                (int)(Math.random()
                * (getHeight() - tentHeight));

            RecoveryTent tent =
                new RecoveryTent(
                    x,
                    y,
                    tentWidth,
                    tentHeight
                );

            recoveryTents.add(tent);
        }
    }


    // Create one hospital at a random location
    private void spawnHospital() {

        int hospitalWidth = 120;
        int hospitalHeight = 100;

        int x =
            (int)(Math.random()
            * (getWidth() - hospitalWidth));

        int y =
            (int)(Math.random()
            * (getHeight() - hospitalHeight));

        hospital =
            new Hospital(
                x,
                y,
                hospitalWidth,
                hospitalHeight
            );
    }


    public void updateSimulation() {

        long currentTime =
            System.currentTimeMillis();


        // ============================================
        // RECOVERY TENTS
        // Spawn every 5 seconds
        // Stay for 5 seconds
        // ============================================

        if (
            !tentsActive
            && currentTime - lastTentSpawn >= 5000
        ) {

            spawnRecoveryTents();

            tentsActive = true;
            tentSpawnTime = currentTime;
            lastTentSpawn = currentTime;
        }


        if (
            tentsActive
            && currentTime - tentSpawnTime >= 5000
        ) {

            tentsActive = false;
            recoveryTents.clear();
        }


        // ============================================
        // HOSPITAL
        // Spawn every 15 seconds
        // Stay for 3 seconds
        // ============================================

        if (
            !hospitalActive
            && currentTime - lastHospitalSpawn >= 15000
        ) {

            spawnHospital();

            hospitalActive = true;
            hospitalSpawnTime = currentTime;
            lastHospitalSpawn = currentTime;
        }


        if (
            hospitalActive
            && currentTime - hospitalSpawnTime >= 3000
        ) {

            hospitalActive = false;
        }


        // ============================================
        // MOVE PEOPLE
        // ============================================

        for (Person person : people) {

            person.move(
                getWidth(),
                getHeight()
            );
        }


        // ============================================
        // SPREAD INFECTION
        // ============================================

        for (int i = 0; i < people.size(); i++) {

            Person person1 = people.get(i);


            // Only real infected people can spread infection
            if (
                person1 instanceof Infected
                && !(person1 instanceof Recovered)
                && !(person1 instanceof Dead)
            ) {

                for (int j = 0; j < people.size(); j++) {

                    Person person2 = people.get(j);


                    // Only healthy people can become infected
                    if (person2 instanceof Healthy) {

                        float dx =
                            person1.getX()
                            - person2.getX();

                        float dy =
                            person1.getY()
                            - person2.getY();


                        double distance =
                            Math.sqrt(
                                dx * dx
                                + dy * dy
                            );


                        if (distance < 20) {

                            int chance =
                                (int)(Math.random() * 100);


                            if (
                                chance
                                < virus.getSpreadPercentage()
                            ) {

                                Infected newInfected =
                                    new Infected(
                                        person2.getName(),
                                        person2.getAge(),
                                        virus,

                                        person2.getX(),
                                        person2.getY(),

                                        person2.getSpeedX(),
                                        person2.getSpeedY()
                                    );


                                people.set(
                                    j,
                                    newInfected
                                );
                            }
                        }
                    }
                }
            }
        }


        // ============================================
        // INFECTED BECOMES DEAD AFTER 50 SECONDS
        // ============================================

        for (int i = 0; i < people.size(); i++) {

            Person person = people.get(i);


            if (
                person instanceof Infected
                && !(person instanceof Dead)
                && !(person instanceof Recovered)
            ) {

                Infected infectedPerson =
                    (Infected) person;


                infectedPerson
                    .increaseInfectionDuration();


                // 50 seconds ≈ 1667 loops
                if (
                    infectedPerson
                    .getInfectionDuration()
                    >= 1667
                ) {

                    Dead deadPerson =
                        new Dead(
                            infectedPerson.getName(),
                            infectedPerson.getAge(),

                            infectedPerson.getVirus(),

                            infectedPerson.getX(),
                            infectedPerson.getY(),

                            0,
                            0
                        );


                    people.set(
                        i,
                        deadPerson
                    );
                }
            }
        }


        // ============================================
        // RECOVERY TENT TREATMENT
        // Stay inside for 2 seconds
        // ============================================

        for (int i = 0; i < people.size(); i++) {

            Person person = people.get(i);


            if (
                person instanceof Infected
                && !(person instanceof Dead)
                && !(person instanceof Recovered)
            ) {

                Infected infected =
                    (Infected) person;


                boolean insideTent = false;


                if (tentsActive) {

                    for (RecoveryTent tent : recoveryTents) {

                        if (
                            tent.isPersonInside(infected)
                        ) {

                            insideTent = true;
                            break;
                        }
                    }
                }


                if (insideTent) {

                    if (
                        infected.getTentEntryTime()
                        == -1
                    ) {

                        infected.setTentEntryTime(
                            currentTime
                        );
                    }


                    if (
                        currentTime
                        - infected.getTentEntryTime()
                        >= 2000
                    ) {

                        Recovered recovered =
                            new Recovered(
                                infected.getName(),
                                infected.getAge(),

                                infected.getVirus(),

                                infected.getX(),
                                infected.getY(),

                                infected.getSpeedX(),
                                infected.getSpeedY()
                            );


                        people.set(
                            i,
                            recovered
                        );
                    }
                }

                else {

                    infected.setTentEntryTime(-1);
                }
            }
        }


        // ============================================
        // HOSPITAL TREATMENT
        // Stay inside for 1 second
        // ============================================

        for (int i = 0; i < people.size(); i++) {

            Person person = people.get(i);


            if (
                person instanceof Infected
                && !(person instanceof Dead)
                && !(person instanceof Recovered)
            ) {

                Infected infected =
                    (Infected) person;


                if (
                    hospitalActive
                    && hospital != null
                    && hospital.isPersonInside(infected)
                ) {

                    if (
                        infected.getHospitalEntryTime()
                        == -1
                    ) {

                        infected
                            .setHospitalEntryTime(
                                currentTime
                            );
                    }


                    if (
                        currentTime
                        - infected.getHospitalEntryTime()
                        >= 1000
                    ) {

                        Recovered recovered =
                            new Recovered(
                                infected.getName(),
                                infected.getAge(),

                                infected.getVirus(),

                                infected.getX(),
                                infected.getY(),

                                infected.getSpeedX(),
                                infected.getSpeedY()
                            );


                        people.set(
                            i,
                            recovered
                        );
                    }
                }

                else {

                    infected
                        .setHospitalEntryTime(-1);
                }
            }
        }


        // NO DEAD REMOVAL
        // Dead people stay permanently.
    }


    @Override
    public void paint(Graphics g) {


        // ============================================
        // DRAW RECOVERY TENTS
        // ============================================

        if (tentsActive) {

            for (RecoveryTent tent : recoveryTents) {

                g.setColor(Color.ORANGE);


                g.fillRect(
                    tent.x,
                    tent.y,
                    tent.width,
                    tent.height
                );


                g.setColor(Color.BLACK);


                g.drawString(
                    "Recovery Tent",
                    tent.x + 5,
                    tent.y + 20
                );
            }
        }


        // ============================================
        // DRAW HOSPITAL
        // ============================================

        if (
            hospitalActive
            && hospital != null
        ) {

            g.setColor(Color.WHITE);


            g.fillRect(
                hospital.x,
                hospital.y,
                hospital.width,
                hospital.height
            );


            g.setColor(Color.BLACK);


            g.drawString(
                "Hospital",
                hospital.x + 25,
                hospital.y + 20
            );
        }


        // ============================================
        // DRAW PEOPLE
        // ============================================

        for (Person person : people) {


            if (person instanceof Healthy) {

                g.setColor(Color.GREEN);
            }


            else if (person instanceof Dead) {

                g.setColor(Color.BLACK);
            }


            else if (person instanceof Recovered) {

                g.setColor(Color.BLUE);
            }


            else if (person instanceof Infected) {

                g.setColor(Color.RED);
            }


            g.fillOval(
                (int) person.getX(),
                (int) person.getY(),
                15,
                15
            );
        }
    }
}