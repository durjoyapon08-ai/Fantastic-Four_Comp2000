public class Healthy extends Person {
    
    public Healthy(String name, int age){
        super(name, age);
    }

    @Override
    public String getStatus(){
        return "Healthy";
    }
}
