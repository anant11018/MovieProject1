package mmi.com.movieproject;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import mmi.com.movieproject.pojo.popular_movies.MovieResult;

public class SpecificMovie extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_movie);
        imageView = (ImageView) findViewById(R.id.imageviewHeader);
        textView = (TextView) findViewById(R.id.info_textview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MovieResult movieResult = getIntent().getParcelableExtra("Movie_Obj");
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + movieResult.getPosterPath()).noFade().fit().placeholder(R.drawable.popcorn).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Palette palette = Palette.from(((BitmapDrawable) imageView.getDrawable()).getBitmap()).generate();
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
                collapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(Color.BLACK));

            }

            @Override
            public void onError() {

            }
        });
        //toolbar.setTitle(movieResult.getTitle());
        textView.setText(movieResult.getOverview());
        getSupportActionBar().setTitle(movieResult.getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return false;
    }
}
