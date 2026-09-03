public class Recovered extends Infected{
    public Recovered(String name, int age, Virus virus){
        super(name, age, virus);
    }

    @Override
    public String getStatus(){
        return "Recovered";
    }
}