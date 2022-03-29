package sample.Model;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

public class NHACUNGCAP {
    String MANCC,TENNCC,SĐT,DIACHI;

    public String getMANCC() {
        return MANCC;
    }

    public void setMANCC(String MANCC) {
        this.MANCC = MANCC;
    }

    public String getTENNCC() {
        return TENNCC;
    }

    public void setTENNCC(String TENNCC) {
        this.TENNCC = TENNCC;
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

    public NHACUNGCAP(String mancc, String tenncc, String sdt, String diachi){
        MANCC=mancc;
        TENNCC=tenncc;
        SĐT=sdt;
        DIACHI=diachi;
    }
}
