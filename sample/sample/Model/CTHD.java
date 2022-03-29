package sample.Model;

public class CTHD {
    String MAHD,MASP,SL,DONGIA;

    public String getMAHD() {
        return MAHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public String getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(String DONGIA) {
        this.DONGIA = DONGIA;
    }

    public CTHD(String mahd, String masp, String sl, String dongia){
        MAHD=mahd;
        MASP=masp;
        SL=sl;
        DONGIA=dongia;

    }
}
