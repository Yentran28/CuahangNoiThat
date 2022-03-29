package sample.Model;

public class PHIEUBAOTRI {
    String MABT,MAHD,MANV,TONGPHIBT,NGAYBT;

    public PHIEUBAOTRI() {

    }


    public String getMABT() {
        return MABT;
    }

    public void setMABT(String MABT) {
        this.MABT = MABT;
    }

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

    public String getTONGPHIBT() {
        return TONGPHIBT;
    }

    public void setTONGPHIBT(String TONGPHIBT) {
        this.TONGPHIBT = TONGPHIBT;
    }

    public String getNGAYBT() {
        return NGAYBT;
    }

    public void setNGAYBT(String NGAYBT) {
        this.NGAYBT = NGAYBT;
    }

    public PHIEUBAOTRI(String mabt, String mahd, String manv, String tpbt,String ngaybt){
        MABT=mabt;
        MAHD=mahd;
        MANV=manv;
        TONGPHIBT=tpbt;
        NGAYBT=ngaybt;
    }
}
