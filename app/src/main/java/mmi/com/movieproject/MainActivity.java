package mmi.com.movieproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import mmi.com.movieproject.adapter.PopularMovieGridAdapter;
import mmi.com.movieproject.pojo.popular_movies.MovieData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static String SHARED_PREF;
    public static String LOG_TAG = "Main_Activity";
    static int count = 0;
    MovieData movieData;
    MovieData topRatedMoviesData;
    SharedPreferences sharedPreferences;
    GridView gridView;
    PopularMovieGridAdapter popularMovieGridAdapter;
    PopularMovieGridAdapter topRatedGridAdapter;
    String API_KEY = "587b915f4d1e2c7af0caf242bb2f7953";
private final int MODE_POPULAR =1;
private final int MODE_TOP_RATED =2;
private  int currentMode=0;
    DrawerLayout drawer;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);
        gridView = (GridView) findViewById(R.id.grid_layout);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,SpecificMovie.class);
                if(currentMode==MODE_POPULAR)
                {
                    intent.putExtra("Movie_Obj", movieData.getMovieResults().get(position));
                }
                else
                {
                    intent.putExtra("Movie_Obj", topRatedMoviesData.getMovieResults().get(position));
                }

                startActivity(intent);
            }
        });

        callApi();
        getPopularDataApi();
        getTopRatedDataApi();

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        toolbar =(Toolbar)findViewById(R.id.toolbarPlus);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Intent intent = new Intent(getApplicationContext(),TweakActivity.class);
        switch (item.getItemId())
        {
            case R.id.most_popular:
                gridView.setAdapter(popularMovieGridAdapter);
                currentMode=MODE_POPULAR;
                return true;

            case R.id.top_rated:
                gridView.setAdapter(topRatedGridAdapter);
                currentMode=MODE_TOP_RATED;
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_specific_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.most_popular:
                gridView.setAdapter(popularMovieGridAdapter);
                currentMode=MODE_POPULAR;
                return true;

            case R.id.top_rated:
                gridView.setAdapter(topRatedGridAdapter);
                currentMode=MODE_TOP_RATED;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/authentication/guest_session/new?api_key=587b915f4d1e2c7af0caf242bb2f7953")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.w(LOG_TAG, response.body().string());
                Log.i(LOG_TAG, response.toString());
                try {
                    String jsonData = response.body().string();
                    Log.d("jsonData", jsonData);
                    JSONObject jsonObject = new JSONObject(jsonData);
                    Log.d(LOG_TAG, jsonObject.getString("guest_session_id"));
                    sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("session_id", jsonObject.getString("guest_session_id"));
                    count++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPopularDataApi() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/movie/popular?api_key=587b915f4d1e2c7af0caf242bb2f7953")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(LOG_TAG, response.toString());
                count = 2;
                String jsonData = response.body().string();
                Log.d("jsonData_popular", jsonData);
                Type type = new TypeToken<MovieData>() {
                }.getType();
                movieData = new Gson().fromJson(jsonData, type);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popularMovieGridAdapter = new PopularMovieGridAdapter(getApplicationContext(), movieData);
                        gridView.setAdapter(popularMovieGridAdapter);
                        currentMode=MODE_POPULAR;
                    }
                });

//                Log.d("popluar_movie_data", movieData.getMovieResults().get(0).getTitle());
                count++;
            }
        });


    }

    public void getTopRatedDataApi() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/movie/top_rated?api_key=587b915f4d1e2c7af0caf242bb2f7953")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(LOG_TAG, response.toString());
                count = 1;
                String jsonData = response.body().string();
                Log.d("jsonData_top_rated", jsonData);
                Type type = new TypeToken<MovieData>() {
                }.getType();
                topRatedMoviesData = new Gson().fromJson(jsonData, type);
                topRatedGridAdapter =  new PopularMovieGridAdapter(getApplicationContext(), topRatedMoviesData);
                //Log.d("toprated_movies_data", topRatedMoviesData.getMovieResults().get(0).getTitle());
                count++;
            }
        });
    }


}
