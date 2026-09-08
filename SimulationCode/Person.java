public class Person {

    private String name;
    private int age;

    private float x;
    private float y;
    private float speedX;
    private float speedY;

    public Person(String name, int age, float x, float y, 
        float speedX, float speedY) {

        this.name = name;
        this.age = age;

        this.x = x;
        this.y = y;

        this.speedX = speedX;
        this.speedY = speedY;
    
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

    public void move(int width, int height) {
        
        x += speedX;
        y += speedY;

        if(x < 0 || x > width - 15){
            speedX *= -1;
        }
        if(y < 0 || y > height - 15){
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
