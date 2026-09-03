public class Person {
    private String name;
    private int age;

    private float x;
    private float y;
    private float speedX;
    private float speedY;

    public Person(String name, int age){
        this.name = name;
        this.age = age;

        x = 100;
        y = 100;
        speedX = 1;
        speedY = 1;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;   
    }    

    public void move(){
        x += speedX;
        y += speedY;

        if(x < 0 || x > 500){
            speedX *= -1;
        }
        if(y < 0 || y > 500){
            speedY *= -1;
        }
    }

    public String getStatus() {
        return "Person";
    }

    @Override
    public String toString(){
        return "Name: " + name + ", Age: " + age + ", Status: " + getStatus();
    }
    
}
