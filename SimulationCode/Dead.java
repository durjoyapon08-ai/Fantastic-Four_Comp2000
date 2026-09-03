public class Dead extends Person{
    public Dead(String name, int age){
        super(name, age);
    }

    @Override
    public String getStatus() {
        return "Dead";
    }
}