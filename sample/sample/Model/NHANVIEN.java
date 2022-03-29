package sample.Model;

public class NHANVIEN {
    String MANV,TENNV,TAIKHOAN,MATKHAU,SĐT,DIACHI,EMAIL;

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public String getTENNV() {
        return TENNV;
    }

    public void setTENNV(String TENNV) {
        this.TENNV = TENNV;
    }

    public String getTAIKHOAN() {
        return TAIKHOAN;
    }

    public void setTAIKHOAN(String TAIKHOAN) {
        this.TAIKHOAN = TAIKHOAN;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
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

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public NHANVIEN(String manv, String tennv, String taikhoan, String matkhau, String sdt, String diachi, String email) {
                MANV=manv;
                TENNV=tennv;
                TAIKHOAN=taikhoan;
                MATKHAU=matkhau;
                SĐT=sdt;
                DIACHI=diachi;
                EMAIL=email;
    }
}
