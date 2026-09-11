import java.awt.*;

/** The big recovery centre: 40x40, cures a patient in 5 seconds. */
public class Hospital extends Facility {

    private static final int SIZE = 40;
    private static final int RECOVERY_SECONDS = 5;

    public Hospital(int xPos, int yPos){
        super(xPos, yPos, SIZE);
    }

    @Override
    public int getRecoverySeconds(){
        return RECOVERY_SECONDS;
    }

    @Override
    public String getName(){
        return "Hospital";
    }

    @Override
    public Color getColor(){
        return new Color(60, 110, 220);    // blue
    }
}