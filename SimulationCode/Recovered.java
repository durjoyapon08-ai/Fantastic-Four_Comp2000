public class Recovered extends Infected{
    public Recovered(String name, int age, Virus virus, float x, float y, float speedX, float speedY){
        super(name, age, virus, x, y, speedX, speedY);
    }

    @Override
    public String getStatus(){
        return "Recovered";
    }
}