package id.dev.ngantri;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Yudis on 12/13/2017.
 */

public class Sharepreferense {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Sharepreferense(Context context){
        sharedPreferences = context.getSharedPreferences("PREFERENSE", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public String getNomer(){
        return sharedPreferences.getString("Nomer","1");
    }
    public String getLogin(){
        return sharedPreferences.getString("Login","tidak");
    }
}
