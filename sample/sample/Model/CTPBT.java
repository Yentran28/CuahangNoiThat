package sample.Model;

public class CTPBT {
    String MASP,MABT,DONGIABT,SLBT;

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getMABT() {
        return MABT;
    }

    public void setMABT(String MABT) {
        this.MABT = MABT;
    }

    public String getDONGIABT() {
        return DONGIABT;
    }

    public void setDONGIABT(String DONGIABT) {
        this.DONGIABT = DONGIABT;
    }

    public String getSLBT() {
        return SLBT;
    }

    public void setSLBT(String SLBT) {
        this.SLBT = SLBT;
    }

    public CTPBT(String masp, String mabt, String dongiabt, String slbt){
        MASP=masp;
        MABT=mabt;
        DONGIABT=dongiabt;
        SLBT=slbt;
    }
}
