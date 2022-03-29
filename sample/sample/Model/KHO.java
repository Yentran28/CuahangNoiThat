package sample.Model;

public class KHO {
    String MAKHO,TENKHO;

    public String getMAKHO() {
        return MAKHO;
    }

    public void setMAKHO(String MAKHO) {
        this.MAKHO = MAKHO;
    }

    public String getTENKHO() {
        return TENKHO;
    }

    public void setTENKHO(String TENKHO) {
        this.TENKHO = TENKHO;
    }

    public KHO(String makho, String tenkho){
        MAKHO=makho;
        TENKHO=  tenkho;
    }
}
