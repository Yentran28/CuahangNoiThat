package sample.Model;

public class KHACHHANG {
    String MAKH,TENKH,SĐT,DIACHI;

    public String getMAKH() {
        return MAKH;
    }

    public void setMAKH(String MAKH) {
        this.MAKH = MAKH;
    }

    public String getTENKH() {
        return TENKH;
    }

    public void setTENKH(String TENKH) {
        this.TENKH = TENKH;
    }

    public String getSĐT() {
        return SĐT;
    }

    public void setSĐT(String SĐT) {
        this.SĐT = SĐT;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public KHACHHANG(String makh, String tenkh, String sdt, String diachi){
        MAKH=makh;
        TENKH=tenkh;
        SĐT=sdt;
        DIACHI=diachi;
    }
}
