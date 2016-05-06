package mmi.com.movieproject.pojo.popular_movies.movie_video_url;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ce on 5/6/2016.
 */
public class MovieVideoUrl implements Parcelable {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("results")
    @Expose
    private List<MovieUrlResult> results = new ArrayList<MovieUrlResult>();

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MovieUrlResult> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<MovieUrlResult> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeList(this.results);
    }

    public MovieVideoUrl() {
    }

    protected MovieVideoUrl(Parcel in) {
        this.id = in.readLong();
        this.results = new ArrayList<MovieUrlResult>();
        in.readList(this.results, MovieUrlResult.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieVideoUrl> CREATOR = new Parcelable.Creator<MovieVideoUrl>() {
        @Override
        public MovieVideoUrl createFromParcel(Parcel source) {
            return new MovieVideoUrl(source);
        }

        @Override
        public MovieVideoUrl[] newArray(int size) {
            return new MovieVideoUrl[size];
        }
    };
}
