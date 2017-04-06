package prerak.com.baldha;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class MenuActivity extends Activity implements View.OnClickListener{
    Button btn_add_order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        init();
    }
    void init(){
        btn_add_order = (Button)findViewById(R.id.btn_neworder);

        btn_add_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_neworder){
            Intent intent = new Intent(MenuActivity.this,ShopListActivity.class);
            startActivity(intent);
        }
    }
}
