public abstract class Person {

    static final int DIAMETER = 15;
    private static final int PLAY_AREA_TOP = 150;

    // Used to assign an ID to each new person.
    private static int nextId = 1;

    private int id;
    private String name;
    private int age;

    private float x;
    private float y;
    private float speedX;
    private float speedY;

    // Creates a new person and automatically assigns an ID.
    public Person(
            String name,
            int age,
            float x,
            float y,
            float speedX,
            float speedY) {

        validateDetails(name, age);

        this.id = nextId++;
        this.name = name.trim();
        this.age = age;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Preserves the person's ID when their health status changes.
    public Person(
            int id,
            String name,
            int age,
            float x,
            float y,
            float speedX,
            float speedY) {

        validateDetails(name, age);

        this.id = id;
        this.name = name.trim();
        this.age = age;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Both constructors use the same validation rules.
    private static void validateDetails(String name, int age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Person name cannot be empty."
            );
        }

        if (age < 0 || age > 120) {
            throw new IllegalArgumentException(
                    "Age must be between 0 and 120."
            );
        }
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

    // Keeps the person inside the play area and turns them back at the edges.
    public void move(int width, int height) {
        int right = Math.max(0, width - DIAMETER);
        int bottom = Math.max(0, height - DIAMETER);
        int top = Math.min(PLAY_AREA_TOP, bottom);

        float nextX = x + speedX;
        float nextY = y + speedY;

        // Clamp the position, including when the window is resized.
        x = Math.max(0, Math.min(nextX, right));
        y = Math.max(top, Math.min(nextY, bottom));

        if (right > 0) {
            if (nextX <= 0) {
                speedX = Math.abs(speedX);
            } else if (nextX >= right) {
                speedX = -Math.abs(speedX);
            }
        }

        if (bottom > top) {
            if (nextY <= top) {
                speedY = Math.abs(speedY);
            } else if (nextY >= bottom) {
                speedY = -Math.abs(speedY);
            }
        }
    }

    public abstract String getStatus();

    @Override
    public String toString() {
        return "ID: " + id
                + ", Name: " + name
                + ", Age: " + age
                + ", Status: " + getStatus();
    }
}