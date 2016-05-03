package mmi.com.movieproject.pojo.top_rated_movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ce on 5/3/2016.
 */
public class TopRatedMoviesData {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<ResultTopRated> results = new ArrayList<ResultTopRated>();
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
     * The results
     */
    public List<ResultTopRated> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ResultTopRated> results) {
        this.results = results;
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

}
