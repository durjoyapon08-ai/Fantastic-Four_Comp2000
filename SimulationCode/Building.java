public class Building {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected boolean active;

    public Building(
        int x,
        int y,
        int width,
        int height
    ) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPersonInside(Person person) {

        return person.getX() >= x
            && person.getX() <= x + width
            && person.getY() >= y
            && person.getY() <= y + height;
    }
}