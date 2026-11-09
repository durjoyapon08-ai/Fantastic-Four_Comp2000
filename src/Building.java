public abstract class Building {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    // Validates the position and size before creating a building.
    public Building(int x, int y, int width, int height) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(
                    "Building coordinates cannot be negative."
            );
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Building width and height must be positive."
            );
        }

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

    // A person on the edge of the building also counts as inside.
    public boolean isPersonInside(Person person) {
        return person.getX() >= x
                && person.getX() <= x + width
                && person.getY() >= y
                && person.getY() <= y + height;
    }
}