package sample.Model;

public class PHIEUNHAPKHO {
    String MAPN,MANV,NGAYNHAP;

    public String getMAPN() {
        return MAPN;
    }

    public void setMAPN(String MAPN) {
        this.MAPN = MAPN;
    }

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public String getNGAYNHAP() {
        return NGAYNHAP;
    }

    public void setNGAYNHAP(String NGAYNHAP) {
        this.NGAYNHAP = NGAYNHAP;
    }

    public  PHIEUNHAPKHO(String mapn, String manv, String ngaynhap){
        MAPN=mapn;
        MANV=manv;
        NGAYNHAP=ngaynhap;
    }
}
