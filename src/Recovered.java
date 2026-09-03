public class Recovered extends Person{
    public Recovered(String name, int age){
        super(name, age);
    }

    @Override
    public String getStatus(){
        return "Recovered";
    }
}