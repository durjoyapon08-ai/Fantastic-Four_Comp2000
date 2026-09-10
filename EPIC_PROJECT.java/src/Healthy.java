public class Healthy extends Person{

    // Speeds live here because the object itself decides how fast it moves.
    private static final int NORMAL_SPEED   = 6;
    private static final int INFECTED_SPEED = 2;

    // The whole state change is this one flag. No new class, no swapping the
    // object out of the array -- the same Healthy object just starts behaving
    // differently once it is set.
    private boolean infected = false;

    public Healthy(String name, int age, int xPos, int yPos){
        super(name, age, xPos, yPos);
    }

    public Healthy(int id, String name, int age, int xPos, int yPos){
        super(id, name, age, xPos, yPos);
    }

    /** Called when this person touches a virus. One way only -- no recovery yet. */
    public void infect(){
        infected = true;
    }

    public boolean isInfected(){
        return infected;
    }

    /**
     * The object reports its own speed, so PanelDemo does not need to know the
     * rule. Change the two numbers above and nothing else has to move.
     */
    public int getSpeed(){
        if (infected){
            return INFECTED_SPEED;
        }
        return NORMAL_SPEED;
    }

    @Override
    public String getStatus(){
        if (infected){
            return "Infected";
        }
        return "Healthy";
    }
}