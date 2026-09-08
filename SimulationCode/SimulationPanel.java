import java.awt.*;
import java.util.ArrayList;


public class SimulationPanel extends Panel implements Runnable {


    private ArrayList<Person> people;

    private Thread simulationThread;

    private Virus virus;


    public SimulationPanel() {

        setBackground(Color.GRAY);


        people = new ArrayList<>();


        virus = new Virus(
            "Zombie Virus",
            40
        );


        // Create 30 healthy people

        for (int i = 0; i < 30; i++) {

            Healthy healthyPerson =
                new Healthy(
                    "Person " + i,
                    20,
                    100,
                    100,
                    2,
                    2
                );

            people.add(healthyPerson);
        }


        // Create 3 infected people

        people.add(
            new Infected(
                "Infected 1",
                25,
                virus,
                100,
                100,
                2,
                2
            )
        );


        people.add(
            new Infected(
                "Infected 2",
                30,
                virus,
                150,
                150,
                2,
                2
            )
        );


        people.add(
            new Infected(
                "Infected 3",
                22,
                virus,
                200,
                200,
                2,
                2
            )
        );
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


 public void updateSimulation() {

    // Move everyone first
    for (Person person : people) {
        person.move(
            getWidth(),
            getHeight()
        );
    }


    // Check infection
    for (int i = 0; i < people.size(); i++) {

        Person person1 = people.get(i);

        if (person1 instanceof Infected) {

            for (int j = 0; j < people.size(); j++) {

                Person person2 = people.get(j);

                if (person2 instanceof Healthy) {

                    float dx =
                        person1.getX() - person2.getX();

                    float dy =
                        person1.getY() - person2.getY();


                    double distance =
                        Math.sqrt(
                            dx * dx + dy * dy
                        );


                    // If they are close enough
                    if (distance < 20) {

                        int chance =
                            (int)(Math.random() * 100);


                        if (
                            chance <
                            virus.getSpreadPercentage()
                        ) {

                            Infected newInfected =
                                new Infected(
                                    person2.getName(),
                                    person2.getAge(),
                                    virus,
                                    person2.getX(),
                                    person2.getY(),
                                    2,
                                    2
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
}


    @Override
    public void paint(Graphics g) {


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