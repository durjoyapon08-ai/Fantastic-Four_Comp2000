import java.awt.*;
import java.awt.event.*;


public class Main {


    public static void main(String[] args) {


        Frame f =
            new Frame("Epidemic Simulation");


        SimulationPanel panel =
            new SimulationPanel();


        panel.setBounds(
            0,
            20,
            800,
            580
        );


        f.add(panel);


        f.setSize(
            800,
            600
        );


        f.setLayout(null);


        f.setVisible(true);


        f.addWindowListener(

            new WindowAdapter() {

                public void windowClosing(
                    WindowEvent we
                ) {

                    System.exit(0);

                }
            }
        );


        panel.startSimulation();
    }
}