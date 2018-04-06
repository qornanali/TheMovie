package id.aliqornan.seefavoritemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by qornanali on 21/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void openActivity(Class c, Bundle bundle, boolean isFinish) {
        Intent i = new Intent(this, c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        this.startActivity(i);
        if (isFinish) {
            this.finish();
        }
    }
}
