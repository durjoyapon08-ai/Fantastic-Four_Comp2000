public abstract class Building {

    private int x;
    private int y;
    private int width;
    private int height;

    public Building(int x,int y,int width,int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPersonInside(Person person) {

        return person.getX() >= x && person.getX() <= x + width && person.getY() >= y && person.getY() <= y + height;
    }
}