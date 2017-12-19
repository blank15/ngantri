package id.dev.ngantri.RestAPI;

import id.dev.ngantri.model.Notifikasi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by kikiosha on 12/19/17.
 */

public interface PushAPI {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAApc_DsCI:APA91bGUX7bIJrPiGM3QSfBtM-5KV10w7gbwGxhIm-TBJos0D0dMdQdtItG1o5bNgQOeyfyRHMwzVysfhPjPhmdJia4G3USdyRtaOKHCO2UVnfXLPNzXKZYyigLZ4Luh-D4groIweDJo"
    })
    @POST("fcm/send")
    Call<Notifikasi> setNotif(@Body Notifikasi notifikasi);
}
