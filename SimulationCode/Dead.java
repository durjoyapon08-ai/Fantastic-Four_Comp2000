public class Dead extends Infected{
    public Dead(String name, int age, Virus virus, float x, float y, float speedX, float speedY){
        super(name, age, virus, x, y, speedX, speedY);
    }

    @Override
    public String getStatus() {
        return "Dead";
    }
}