package id.aliqornan.themovie.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import id.aliqornan.themovie.R;

/**
 * Created by qornanali on 21/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    public void initView() {
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if(toolbar != null){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    public void displayHome() {
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
