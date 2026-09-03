public class Dead extends Person{
    public Dead( int id, String name,int age, int x, int y){
        super(id, name, age, x, y);
    }

    @Override
    public String getStatus(){
        return "Dead";
    }
}