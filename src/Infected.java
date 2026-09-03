public class Infected extends Person{
    private Virus virus;
    private int infectionDuration;

    public Infected(String name, int age, Virus virus){
        super(name, age);
        this.virus = virus;
        this.infectionDuration = 0;
    }
    
    public Virus getVirus(){
        return virus;
    }

    public void increaseInfectionDuration(){
        infectionDuration++;
    }

    public int getInfectionDuration(){
        return infectionDuration;
    }

    // public Person checkCondition(){
    //     int percentage = virus.getSpreadPercentage();

    //     if(percentage <= 20){
    //         return new Recovered(getName(), getAge(), virus);
    //     }
    //     else if (percentage > 70) {
    //         return new Dead(getName(), getAge(), virus);
    //     } else {
    //         return this;
    //     }
    // }

    @Override
    public String getStatus() {
        return "Infected";
    }

    @Override
    public String toString() {
        return super.toString() + ", Virus: " + virus;
    }
}