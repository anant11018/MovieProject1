package mmi.com.movieproject.pojo.popular_movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ce on 5/3/2016.
 */

public class MovieData implements Parcelable {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<MovieResult> movieResults = new ArrayList<MovieResult>();
    @SerializedName("total_results")
    @Expose
    private long totalResults;
    @SerializedName("total_pages")
    @Expose
    private long totalPages;

    /**
     *
     * @return
     * The page
     */
    public long getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(long page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The movieResults
     */
    public List<MovieResult> getMovieResults() {
        return movieResults;
    }

    /**
     *
     * @param movieResults
     * The movieResults
     */
    public void setMovieResults(List<MovieResult> movieResults) {
        this.movieResults = movieResults;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public long getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.page);
        dest.writeList(this.movieResults);
        dest.writeLong(this.totalResults);
        dest.writeLong(this.totalPages);
    }

    public MovieData() {
    }

    protected MovieData(Parcel in) {
        this.page = in.readLong();
        this.movieResults = new ArrayList<MovieResult>();
        in.readList(this.movieResults, MovieResult.class.getClassLoader());
        this.totalResults = in.readLong();
        this.totalPages = in.readLong();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
