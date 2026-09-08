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

        // Random starting position
        x = (float)(Math.random() * 750);
        y = (float)(Math.random() * 500);

        // Random movement speed
        speedX = (float)(Math.random() * 4 - 2);
        speedY = (float)(Math.random() * 4 - 2);
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

        // Bounce from left/right wall
        if (x <= 0 || x >= width - 15) {
            speedX *= -1;
        }

        // Bounce from top/bottom wall
        if (y <= 0 || y >= height - 15) {
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
