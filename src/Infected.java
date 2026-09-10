public class Infected extends Person {

    private Virus virus;
     private long infectedSince;
    private long tentEntryTime = -1;
    private long hospitalEntryTime = -1;

    public Infected(
            String name,
            int age,
            Virus virus,
            float x,
            float y,
            float speedX,
            float speedY) {

        super(name, age, x, y, speedX, speedY);
    this.virus = virus;
        infectedSince = System.currentTimeMillis();
    }

    public Infected(
            int id,
            String name,
            int age,
            Virus virus,
            float x,
            float y,
            float speedX,
            float speedY) {

        super(id, name, age, x, y, speedX, speedY);
     this.virus = virus;
        infectedSince = System.currentTimeMillis();
    }

    public Virus getVirus() {
        return virus;
    }

    public long getInfectedSince() {
        return infectedSince;
    }

    public long getTentEntryTime() {
        return tentEntryTime;
    }

    public void setTentEntryTime(long time) {
        tentEntryTime = time;
    }

    public long getHospitalEntryTime() {
        return hospitalEntryTime;
    }

    public void setHospitalEntryTime(long time) {
        hospitalEntryTime = time;
    }

    @Override
    public String getStatus() {
        return "Infected";
    }
}