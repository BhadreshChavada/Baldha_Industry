package prerak.com.baldha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import prerak.com.baldha.util.SharedPreferences_baldaha;

/**
 * Created by Bhadresh Chavada on 16-04-2017.
 */

public class SplushScreenActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splushscreen);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!SharedPreferences_baldaha.GetValue(SplushScreenActivity.this, SharedPreferences_baldaha.USERID).equals("")) {
                    Intent intent = new Intent(SplushScreenActivity.this, MenuActivity.class);
                    startActivity(intent);
                    SplushScreenActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplushScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplushScreenActivity.this.finish();
                }

            }
        }, 3000);

    }
}
