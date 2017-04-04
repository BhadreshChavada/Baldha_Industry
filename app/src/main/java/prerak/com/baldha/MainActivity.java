package prerak.com.baldha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import prerak.com.baldha.fragment.AddNewShopFragment;
import prerak.com.baldha.fragment.AddOrderFragment;

public class MainActivity extends AppCompatActivity {

    private CardView mAddShop,mAddOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddShop= (CardView) findViewById(R.id.cv_add_new_shop);
        mAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new AddNewShopFragment()).addToBackStack(null).commit();
            }
        });
        mAddOrder= (CardView) findViewById(R.id.cv_add_order);
        mAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new AddOrderFragment()).addToBackStack(null).commit();
            }
        });
    }
}
