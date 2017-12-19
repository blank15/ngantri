package id.dev.ngantri.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kikiosha on 12/19/17.
 */

public class Notifikasi {
    @SerializedName("notification")
    @Expose
    private DataNotifikasi notification;
    @SerializedName("to")
    @Expose
    private String to;

    public Notifikasi(DataNotifikasi notification, String to) {
        this.notification = notification;
        this.to = to;
    }

    public DataNotifikasi getNotification() {
        return notification;
    }

    public void setNotification(DataNotifikasi notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
