package mmi.com.movieproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import mmi.com.movieproject.pojo.popular_movies.movie_video_url.MovieVideoUrl;

/**
 * Created by Ce on 5/6/2016.
 */
public class PlayVideo extends AppCompatActivity{

    MovieVideoUrl  movieVideoUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playvideourl);

        movieVideoUrl = getIntent().getParcelableExtra("VideoObject");
        VideoView v = (VideoView) findViewById(R.id.YoutubeVideoView);
        String url = "https://www.youtube.com/watch?v=" + movieVideoUrl.getResults().get(0).getKey();
        String url1="rtsp://v4.cache3.c.youtube.comwatch?v="+ movieVideoUrl.getResults().get(0).getKey()+"=/0/0/0/video.3gp";
       // v.setVideoURI(Uri.parse(“rtsp://v4.cache3.c.youtube.com/CjYLENy73wIaLQlW_ji2apr6AxMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYOr_86Xm06e5UAw=/0/0/0/video.3gp”));
        v.setVideoURI(Uri.parse(url1));
        v.setMediaController(new MediaController(this)); //sets MediaController in the video view

// MediaController containing controls for a MediaPlayer

        v.requestFocus();//give focus to a specific view

        v.start();//starts the video
    }
}
