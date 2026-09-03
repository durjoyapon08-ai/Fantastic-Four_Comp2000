public class Dead extends Infected{
    public Dead(String name, int age, Virus virus){
        super(name, age, virus);
    }

    @Override
    public String getStatus() {
        return "Dead";
    }
}