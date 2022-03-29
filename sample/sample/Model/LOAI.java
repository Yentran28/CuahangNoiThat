package sample.Model;

public class LOAI {
    String MATL,MANCC,TENTL;

    public String getMATL() {
        return MATL;
    }

    public void setMATL(String MATL) {
        this.MATL = MATL;
    }

    public String getMANCC() {
        return MANCC;
    }

    public void setMANCC(String MANCC) {
        this.MANCC = MANCC;
    }

    public String getTENTL() {
        return TENTL;
    }

    public void setTENTL(String TENTL) {
        this.TENTL = TENTL;
    }

    public LOAI(String matl, String mancc, String tentl){
        MATL=matl;
        MANCC=mancc;
        TENTL=tentl;
    }
}
