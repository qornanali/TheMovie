package id.aliqornan.themovie.feature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import id.aliqornan.themovie.R;

/**
 * Created by qornanali on 21/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    public void initView(){
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    public void openActivity(Class c, Bundle bundle, boolean isFinish){
        Intent i = new Intent(this, c);
        if(bundle != null){
            i.putExtras(bundle);
        }
        this.startActivity(i);
        if(isFinish){
            this.finish();
        }
    }
}
