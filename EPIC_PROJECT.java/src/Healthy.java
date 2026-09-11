public class Healthy extends Person{

    // Speeds live here because the object itself decides how fast it moves.
    private static final int NORMAL_SPEED   = 6;
    private static final int INFECTED_SPEED = 2;

    // The person's state is held in these flags. No new class, no swapping the
    // object out of the array -- the same Healthy object just starts behaving
    // differently as they change.
    private boolean infected = false;
    private boolean recovered = false;

    // Treatment time still to go, in milliseconds. 0 means not being treated.
    private int treatmentTimeLeft = 0;

    public Healthy(String name, int age, int xPos, int yPos){
        super(name, age, xPos, yPos);
    }

    public Healthy(int id, String name, int age, int xPos, int yPos){
        super(id, name, age, xPos, yPos);
    }

    /** Called when this person touches a virus. Recovered people are immune. */
    public void infect(){
        if (canBeInfected()){
            infected = true;
        }
    }

    public boolean isInfected(){
        return infected;
    }

    public boolean isRecovered(){
        return recovered;
    }

    public boolean isInTreatment(){
        return treatmentTimeLeft > 0;
    }

    /** Only someone who has never had the virus can catch it. */
    public boolean canBeInfected(){
        return !infected && !recovered;
    }

    /** Infected, and not already being looked after at a recovery centre. */
    public boolean needsTreatment(){
        return infected && !isInTreatment();
    }

    /** Called by a recovery centre when this person is admitted. */
    public void startTreatment(int milliseconds){
        treatmentTimeLeft = milliseconds;
    }

    /**
     * Counts treatment down by the time since the last tick. When it runs out,
     * the person is cured and stays immune from then on.
     */
    public void updateTreatment(int elapsedMilliseconds){

        if (!isInTreatment()){
            return;
        }

        treatmentTimeLeft = treatmentTimeLeft - elapsedMilliseconds;

        if (treatmentTimeLeft <= 0){
            treatmentTimeLeft = 0;
            infected = false;
            recovered = true;
        }
    }

    /**
     * The object reports its own speed, so PanelDemo does not need to know the
     * rule. Change the two numbers above and nothing else has to move.
     * A recovered person is no longer infected, so they go back to normal speed.
     */
    public int getSpeed(){
        if (infected){
            return INFECTED_SPEED;
        }
        return NORMAL_SPEED;
    }

    @Override
    public String getStatus(){
        if (recovered){
            return "Recovered";
        }
        if (isInTreatment()){
            return "In treatment";
        }
        if (infected){
            return "Infected";
        }
        return "Healthy";
    }
}