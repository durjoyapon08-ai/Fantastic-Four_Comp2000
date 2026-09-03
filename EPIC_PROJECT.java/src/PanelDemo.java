import java.awt.*;
import java.awt.event.*;
public class PanelDemo {
    public static void main(String[] args) {
        Frame f = new Frame("Example");
        Panel panel = new Panel();
        panel.setBounds(0, 20, 800, 600);
        panel.setBackground(Color.gray);
        
        f.add(panel);
        f.setSize(800, 600);
        f.setLayout(null);
        f.setVisible(true);


        
        f.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            }
        );
    }
}

