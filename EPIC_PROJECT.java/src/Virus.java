public class Virus {

    private String virusName;
    private int spreadPercentage;
    private int xPos;
    private int yPos;

    private int dx;
    private int dy;

    // The speed used on the previous tick, so a change can be spotted.
    private int currentSpeed;

    // Old constructor kept so Client.java still compiles.
    public Virus(String virusName, int spreadPercentage) {
        this(virusName, spreadPercentage, 0, 0);
    }

    public Virus(String virusName, int spreadPercentage, int xPos, int yPos) {
        this.virusName = virusName;
        this.spreadPercentage = spreadPercentage;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getVirusName() {
        return virusName;
    }

    public int getSpreadPercentage() {
        return spreadPercentage;
    }

    public void setSpreadPercentage(int spreadPercentage) {
        this.spreadPercentage = spreadPercentage;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    // Same rule as Person: travel one way, wobble, new random direction at the wall.
    public void moveRandom(int speed, int maxX, int maxY) {

        if (dx == 0 && dy == 0){
            pickDirection(speed);
        }
        else if (speed != currentSpeed){
            // Same fix as in Person. A virus never changes speed today, so this
            // branch never runs -- it is here so the two moveRandom methods stay
            // identical and do not quietly drift apart.
            resizeStep(speed);
        }

        currentSpeed = speed;

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

            if (xPos <= 0 && dx < 0){ dx = -dx; }
            if (xPos >= maxX && dx > 0){ dx = -dx; }
            if (yPos <= 0 && dy < 0){ dy = -dy; }
            if (yPos >= maxY && dy > 0){ dy = -dy; }
        }
    }

    // Keep the direction we are already travelling, change only how far each
    // step goes. atan2 recovers the current heading from dx and dy.
    private void resizeStep(int speed){

        double angle = Math.atan2(dy, dx);

        dx = (int) Math.round(Math.cos(angle) * speed);
        dy = (int) Math.round(Math.sin(angle) * speed);

        if (dx == 0 && dy == 0){
            dx = speed;
        }
    }

    private void pickDirection(int speed){

        double angle = Math.random() * Math.PI * 2;

        dx = (int) Math.round(Math.cos(angle) * speed);
        dy = (int) Math.round(Math.sin(angle) * speed);

        if (dx == 0 && dy == 0){
            dx = speed;
        }
    }

    @Override
    public String toString() {
        return virusName + " - " + spreadPercentage + "%";
    }
}