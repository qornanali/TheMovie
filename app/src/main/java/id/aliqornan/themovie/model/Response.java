package id.aliqornan.themovie.model;

/**
 * Created by qornanali on 21/03/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Response<M> implements Serializable {

    private final static long serialVersionUID = -3619448127704526379L;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private M results = null;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public Response(Integer page, Integer totalResults, Integer totalPages, M results) {
        super();
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public M getResults() {
        return results;
    }

    public void setResults(M results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "Response{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results.toString() +
                '}';
    }
}