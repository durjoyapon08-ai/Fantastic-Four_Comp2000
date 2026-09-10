public abstract class Person {

    private static int nextId = 1;

    private int id;
    private String name;
    private int age;

    private float x;
    private float y;
    private float speedX;
    private float speedY;

    public Person(
            String name,
            int age,
            float x,
            float y,
            float speedX,
            float speedY) {

        this.id = nextId++;
        this.name = name;
        this.age = age;

        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public Person(
            int id,
            String name,
            int age,
            float x,
            float y,
            float speedX,
            float speedY) {

        this.id = id;
        this.name = name;
        this.age = age;
         this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void move(int width, int height) {

        x += speedX;
        y += speedY;

        if (x < 0 || x > width - 15) {
            speedX *= -1;
        }

        if (y < 150 || y > height - 15) {
            speedY *= -1;
        }
    }

    public abstract String getStatus();

    @Override
    public String toString() {
    return "ID: " + id + ", Name: " + name+ ", Age: " + age + ", Status: " + getStatus();
    }
}