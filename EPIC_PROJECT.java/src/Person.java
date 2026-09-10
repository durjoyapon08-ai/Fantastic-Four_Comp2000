public class Person{
    private static int nextId =1;

    private int id;
    private String name;
    private int age;
    private int xPos;
    private int yPos;

    // Current direction of travel. Kept between ticks -- this is what makes
    // the dot actually go somewhere instead of shaking on the spot.
    private int dx;
    private int dy;

    // The speed used on the previous tick, so a change can be spotted.
    private int currentSpeed;

    public Person(String name, int age, int xPos, int yPos){
        this.id = nextId;
        nextId++;

        this.name = name;
        this.age = age;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Person(int id, String name, int age, int xPos, int yPos){
        this.id = id;
        this.name = name;
        this.age = age;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
    public String getStatus(){
        return "Person";
    }

    // Travel in one direction, wobbling a bit as it goes.
    // On hitting the edge it picks a brand new random direction.
    public void moveRandom(int speed, int maxX, int maxY){

        if (dx == 0 && dy == 0){
            pickDirection(speed);
        }
        else if (speed != currentSpeed){
            // The caller asked for a different speed than last tick, which
            // happens the moment this person is infected. Resize the step
            // straight away instead of waiting for the next wall bounce --
            // dx and dy are what actually move the dot, and pickDirection is
            // the only other thing that ever writes them.
            resizeStep(speed);
        }

        currentSpeed = speed;

        // The vibrate part: a small random nudge on top of the steady heading.
        int wobbleX = (int)(Math.random() * 3) - 1;
        int wobbleY = (int)(Math.random() * 3) - 1;

        xPos = xPos + dx + wobbleX;
        yPos = yPos + dy + wobbleY;

        boolean hitWall = false;

        if (xPos < 0){
            xPos = 0;
            hitWall = true;
        }
        if (xPos > maxX){
            xPos = maxX;
            hitWall = true;
        }
        if (yPos < 0){
            yPos = 0;
            hitWall = true;
        }
        if (yPos > maxY){
            yPos = maxY;
            hitWall = true;
        }

        if (hitWall){
            pickDirection(speed);

            // The new direction is random, so it might point straight back into
            // the wall. Flip it if so, otherwise the dot gets stuck on the edge.
            if (xPos <= 0 && dx < 0){ dx = -dx; }
            if (xPos >= maxX && dx > 0){ dx = -dx; }
            if (yPos <= 0 && dy < 0){ dy = -dy; }
            if (yPos >= maxY && dy > 0){ dy = -dy; }
        }
    }

    // Keep the direction we are already travelling, change only how far each
    // step goes. atan2 recovers the current heading from dx and dy, so an
    // infected person slows down without suddenly veering off somewhere new.
    private void resizeStep(int speed){

        double angle = Math.atan2(dy, dx);

        dx = (int) Math.round(Math.cos(angle) * speed);
        dy = (int) Math.round(Math.sin(angle) * speed);

        if (dx == 0 && dy == 0){
            dx = speed;
        }
    }

    // Random angle turned into a dx/dy step of the given length.
    private void pickDirection(int speed){

        double angle = Math.random() * Math.PI * 2;

        dx = (int) Math.round(Math.cos(angle) * speed);
        dy = (int) Math.round(Math.sin(angle) * speed);

        if (dx == 0 && dy == 0){
            dx = speed;
        }
    }

    @Override
    public String toString(){
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Status: " + getStatus();
    }
}