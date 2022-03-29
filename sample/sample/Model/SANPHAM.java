package sample.Model;

public class SANPHAM {
    String MASP,TENSP,VATLIEU,THOIHANBAOHANH,SLTON,GIA,MATL,MAKHO;

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public String getVATLIEU() {
        return VATLIEU;
    }

    public void setVATLIEU(String VATLIEU) {
        this.VATLIEU = VATLIEU;
    }

    public String getTHOIHANBAOHANH() {
        return THOIHANBAOHANH;
    }

    public void setTHOIHANBAOHANH(String THOIHANBAOHANH) {
        this.THOIHANBAOHANH = THOIHANBAOHANH;
    }

    public String getSLTON() {
        return SLTON;
    }

    public void setSLTON(String SLTON) {
        this.SLTON = SLTON;
    }

    public String getGIA() {
        return GIA;
    }

    public void setGIA(String GIA) {
        this.GIA = GIA;
    }

    public String getMATL() {
        return MATL;
    }

    public void setMATL(String MATL) {
        this.MATL = MATL;
    }

    public String getMAKHO() {
        return MAKHO;
    }

    public void setMAKHO(String MAKHO) {
        this.MAKHO = MAKHO;
    }

    public SANPHAM(String masp, String tensp, String vatlieu, String thbh, String slton, String gia, String matl, String makho){
        MASP=masp;
        TENSP=tensp;
        VATLIEU=vatlieu;
        THOIHANBAOHANH=thbh;
        SLTON=slton;
        GIA=gia;
        MATL=matl;
        MAKHO=makho;
    }
}
