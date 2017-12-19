package id.dev.ngantri.model;

/**
 * Created by kikiosha on 12/13/17.
 */

public class Antrian {
    private String key;
    private String Keperluan;
    private String Nama;
    private String No;
    private Boolean Status;
    private String Tanggal;
    private String Token;

    public Antrian() {
    }

    public Antrian(String key, String keperluan, String nama, String no, Boolean status, String tanggal, String token) {
        this.key = key;
        Keperluan = keperluan;
        Nama = nama;
        No = no;
        Status = status;
        Tanggal = tanggal;
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeperluan() {
        return Keperluan;
    }

    public void setKeperluan(String keperluan) {
        Keperluan = keperluan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }
}
