package id.dev.ngantri;

/**
 * Created by Yudis on 12/13/2017.
 */

public class DataModel {
    String Nomer;
    String Nama;
    String Keperluan;
    String Tanggal;
    boolean Status;

    public String getNomer() {
        return Nomer;
    }

    public void setNomer(String nomer) {
        Nomer = nomer;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getKeperluan() {
        return Keperluan;
    }

    public void setKeperluan(String keperluan) {
        Keperluan = keperluan;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
