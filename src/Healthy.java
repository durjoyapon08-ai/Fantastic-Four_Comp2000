public class Healthy extends Person {

    private int exposureCount = 0;
    private boolean immune = false;

    public Healthy(String name,int age,float x,float y,float speedX,float speedY) {
     super(name, age, x, y, speedX, speedY);
    }

    public Healthy(int id,String name,int age,float x,float y,float speedX,float speedY,boolean immune) {

        super(id, name, age, x, y, speedX, speedY);
        this.immune = immune;
    }

    public void addExposure() {
        exposureCount++;
    }

    public int getExposureCount() {
        return exposureCount;
    }

    public boolean isImmune() {
        return immune;
    }

    @Override
    public String getStatus() {
        return "Healthy";
    }
}