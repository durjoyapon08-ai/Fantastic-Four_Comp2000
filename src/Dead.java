public class Dead extends Person {

    public Dead(int id,String name,int age,float x,float y) {
    super(id, name, age, x, y, 0, 0);
    }

    @Override
    public String getStatus() {
        return "Dead";
    }
}