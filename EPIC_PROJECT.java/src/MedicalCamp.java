import java.awt.*;

/** The small recovery centre: 20x20, cures a patient in 10 seconds. */
public class MedicalCamp extends Facility {

    private static final int SIZE = 20;
    private static final int RECOVERY_SECONDS = 10;

    public MedicalCamp(int xPos, int yPos){
        super(xPos, yPos, SIZE);
    }

    @Override
    public int getRecoverySeconds(){
        return RECOVERY_SECONDS;
    }

    @Override
    public String getName(){
        return "Medical camp";
    }

    @Override
    public Color getColor(){
        return new Color(160, 80, 200);    // purple
    }
}