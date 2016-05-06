package mmi.com.movieproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.skyfishjy.library.RippleBackground;

public class AboutActivity extends AppCompatActivity {

    RippleBackground rippleBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rippleBackground = (RippleBackground) findViewById(R.id.rippleContent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        rippleBackground.startRippleAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rippleBackground.stopRippleAnimation();
    }
}
