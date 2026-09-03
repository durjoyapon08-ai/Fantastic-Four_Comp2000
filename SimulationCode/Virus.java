public class Virus {

    private String virusName;
    private int spreadPercentage;


    public Virus(String virusName, int spreadPercentage){
        this.virusName = virusName;
        this.spreadPercentage = spreadPercentage;
    }

    public String getVirusName(){
        return virusName;
    }
    public int getSpreadPercentage() {
        return spreadPercentage;
    }

    public void setSpreadPercentage(int spreadPercentage) {
        this.spreadPercentage = spreadPercentage;
    }

    @Override
    public String toString(){
        return virusName + "-" + spreadPercentage + "%";
    }
    
}
