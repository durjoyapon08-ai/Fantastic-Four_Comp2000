public class Healthy extends Person{

    public Healthy(String name, int age, int x, int y){
        super(name, age, x, y);
    }

    public Healthy(int id, String name, int age, int x, int y){
        super(id, name, age, x, y);
    }

    @Override
    public String getStatus(){
        return "Healthy";
    }
}