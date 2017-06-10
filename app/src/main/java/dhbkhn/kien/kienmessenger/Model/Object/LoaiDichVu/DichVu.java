package dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu;

/**
 * Created by kiend on 10/9/2016.
 */
public class DichVu {
    private int MADV, MALOAIDV, LUOTKHACH;
    private String TENDV,DIACHIDV,HINHLONDV,HINHNHODV, ICONDV;
    private double LAT,LON;

    public String getICONDV() {
        return ICONDV;
    }

    public void setICONDV(String ICONDV) {
        this.ICONDV = ICONDV;
    }

    public int getMADV() {
        return MADV;
    }

    public void setMADV(int MADV) {
        this.MADV = MADV;
    }

    public int getMALOAIDV() {
        return MALOAIDV;
    }

    public void setMALOAIDV(int MALOAIDV) {
        this.MALOAIDV = MALOAIDV;
    }

    public int getLUOTKHACH() {
        return LUOTKHACH;
    }

    public void setLUOTKHACH(int LUOTKHACH) {
        this.LUOTKHACH = LUOTKHACH;
    }

    public String getTENDV() {
        return TENDV;
    }

    public void setTENDV(String TENDV) {
        this.TENDV = TENDV;
    }

    public String getDIACHIDV() {
        return DIACHIDV;
    }

    public void setDIACHIDV(String DIACHIDV) {
        this.DIACHIDV = DIACHIDV;
    }

    public String getHINHLONDV() {
        return HINHLONDV;
    }

    public void setHINHLONDV(String HINHLONDV) {
        this.HINHLONDV = HINHLONDV;
    }

    public String getHINHNHODV() {
        return HINHNHODV;
    }

    public void setHINHNHODV(String HINHNHODV) {
        this.HINHNHODV = HINHNHODV;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLON() {
        return LON;
    }

    public void setLON(double LON) {
        this.LON = LON;
    }

    public DichVu() {
    }
}
