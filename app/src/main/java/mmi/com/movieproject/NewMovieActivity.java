package mmi.com.movieproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;

import mmi.com.movieproject.adapter.PopularMovieGridAdapter;
import mmi.com.movieproject.pojo.popular_movies.MovieData;
import mmi.com.movieproject.pojo.popular_movies.MovieResult;
import mmi.com.movieproject.pojo.popular_movies.movie_video_url.MovieUrlResult;
import mmi.com.movieproject.pojo.popular_movies.movie_video_url.MovieVideoUrl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ce on 5/6/2016.
 */
public class NewMovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = "New Movie Acticity";
    ImageView imageView;
    TextView releaseDateTextview;
    TextView movieDurationTimeTextView;
    TextView movieRatingTextview;
    TextView movieOverview;
    MovieVideoUrl movieVideoUrl;
    MovieResult movieResult;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_new_page);
        imageView = (ImageView) findViewById(R.id.imageviewnew);
        releaseDateTextview = (TextView) findViewById(R.id.movie_release_date);
        movieDurationTimeTextView= (TextView) findViewById(R.id.movie_duration_time);
        movieRatingTextview= (TextView) findViewById(R.id.movie_rating);
        movieOverview= (TextView) findViewById(R.id.movie_overview);
        linearLayout1 =(LinearLayout)findViewById(R.id.linearlayout1);
        linearLayout2 =(LinearLayout)findViewById(R.id.linearlayout2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieResult = getIntent().getParcelableExtra("Movie_Obj");
        releaseDateTextview.setText(movieResult.getReleaseDate());
        movieDurationTimeTextView.setText("3 hours 29 mins");
        movieRatingTextview.setText(movieResult.getVoteAverage() + "/" + 10);
        movieOverview.setText(movieResult.getOverview());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + movieResult.getPosterPath()).resize(200,300).placeholder(R.drawable.popcorn).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Palette palette = Palette.from(((BitmapDrawable) imageView.getDrawable()).getBitmap()).generate();
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//                collapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(Color.BLACK));

            }

            @Override
            public void onError() {

            }
        });

        getVideoUrl();

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMovieActivity.this,PlayVideo.class);
                intent.putExtra("VideoObject",movieVideoUrl);
                startActivity(intent);

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


    public void getVideoUrl() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/movie/" + movieResult.getId() +"/videos?api_key=587b915f4d1e2c7af0caf242bb2f7953")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(LOG_TAG, response.toString());
                String jsonData = response.body().string();
                Log.d("new_movie_activity_data", jsonData);
                Type type = new TypeToken<MovieVideoUrl>() {
                }.getType();
                movieVideoUrl = new Gson().fromJson(jsonData, type);
                Log.d("movie_url_result",movieVideoUrl.getResults().get(0).getKey());

            }
        });


    }
}