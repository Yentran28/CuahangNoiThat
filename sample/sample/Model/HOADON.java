package sample.Model;

import java.time.LocalDate;

public class HOADON {
    String MAHD, MANV, MAKH,NGAYLAP, TONGHD;

    public String getMAHD() {
        return MAHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public String getMAKH() {
        return MAKH;
    }

    public void setMAKH(String MAKH) {
        this.MAKH = MAKH;
    }

    public String getNGAYLAP() { return NGAYLAP; }

    public void setNGAYLAP(String NGAYLAP) {
        this.NGAYLAP = NGAYLAP;
    }

    public String getTONGHD() {
        return TONGHD;
    }

    public void setTONGHD(String TONGHD) {
        this.TONGHD = TONGHD;
    }

    public HOADON(String mahd, String manv, String makh, String ngaylap, String tonghd){
        MAHD=mahd;
        MANV=manv;
        MAKH=makh;
        NGAYLAP=ngaylap;
        TONGHD=tonghd;
    }
}
