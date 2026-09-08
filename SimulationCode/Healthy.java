public class Healthy extends Person {
    
    public Healthy(String name, int age, float x, float y, float speedX, float speedY){
        super(name, age, x, y, speedX, speedY);
    }

    @Override
    public String getStatus(){
        return "Healthy";
    }
}
