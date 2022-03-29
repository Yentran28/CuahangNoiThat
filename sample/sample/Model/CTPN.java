package sample.Model;

public class CTPN {
    String MAPN,MASP,SLNHAP;

    public String getMAPN() {
        return MAPN;
    }

    public void setMAPN(String MAPN) {
        this.MAPN = MAPN;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getSLNHAP() {
        return SLNHAP;
    }

    public void setSLNHAP(String SLNHAP) {
        this.SLNHAP = SLNHAP;
    }

    public CTPN(String mapn, String masp, String slnhap){
        MAPN=mapn;
        MASP=masp;
        SLNHAP=slnhap;
    }
}
