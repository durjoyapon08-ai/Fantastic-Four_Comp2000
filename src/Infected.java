public class Infected extends Person{
    private Virus virus;

    public Infected(
        String name, 
        int age, 
        int x,
        int y,
        Virus virus){
            super(name, age, x, y);
            this.virus = virus;
        }

        public Infected( int id, String name, int age, int x, int y, Virus virus){
            super(id, name, age, x, y);
            this.virus = virus;
        }
        public Virus getVirus(){
            return virus;
        }

public Person checkCondition() {

        int percentage = virus.getSpreadPercentage();

        if (percentage <= 20) {

            return new Recovered(getId(),getName(),getAge(),getX(),getY());
        }

        if (percentage > 70) {
            return new Dead(getId(),getName(),getAge(),getX(),getY());
        }

        return this;
    }

    @Override
    public String getStatus() {
        return "Infected";
    }

    @Override
    public String toString() {
        return super.toString() + ", Virus: " + virus;
    }
}
        