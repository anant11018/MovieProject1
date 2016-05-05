package mmi.com.movieproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import mmi.com.movieproject.adapter.PopularMovieGridAdapter;
import mmi.com.movieproject.pojo.popular_movies.PopularMovieData;
import mmi.com.movieproject.pojo.top_rated_movie.TopRatedMoviesData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static String SHARED_PREF;
    public static String LOG_TAG = "Main_Activity";
    static int count = 0;
    PopularMovieData popularMovieData;
    TopRatedMoviesData topRatedMoviesData;
    SharedPreferences sharedPreferences;
    GridView gridView;
    PopularMovieGridAdapter popularMovieGridAdapter;
    String API_KEY = "587b915f4d1e2c7af0caf242bb2f7953";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid_layout);

        callApi();
        getPopularDataApi();
        getTopRatedDataApi();

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

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
                Type type = new TypeToken<PopularMovieData>() {
                }.getType();
                popularMovieData = new Gson().fromJson(jsonData, type);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popularMovieGridAdapter = new PopularMovieGridAdapter(getApplicationContext(), popularMovieData);
                        gridView.setAdapter(popularMovieGridAdapter);
                    }
                });

                Log.d("popluar_movie_data", popularMovieData.getResults().get(0).getTitle());
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
                Type type = new TypeToken<TopRatedMoviesData>() {
                }.getType();
                topRatedMoviesData = new Gson().fromJson(jsonData, type);
                Log.d("toprated_movies_data", topRatedMoviesData.getResults().get(0).getTitle());
                count++;
            }
        });
    }


}
