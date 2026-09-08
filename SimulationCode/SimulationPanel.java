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
                    20
                );

            people.add(healthyPerson);
        }


        // Create 3 infected people

        people.add(
            new Infected(
                "Infected 1",
                25,
                virus
            )
        );


        people.add(
            new Infected(
                "Infected 2",
                30,
                virus
            )
        );


        people.add(
            new Infected(
                "Infected 3",
                22,
                virus
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

        for (Person person : people) {

            person.move(
                getWidth(),
                getHeight()
            );
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