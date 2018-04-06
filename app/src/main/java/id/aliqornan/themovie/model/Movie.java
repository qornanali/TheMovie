package id.aliqornan.themovie.model;

/**
 * Created by qornanali on 21/03/18.
 */

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    private final static long serialVersionUID = -1776050687088968714L;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Float popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    /**
     * @param id
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */
    public Movie(Integer voteCount, Integer id, Boolean video, Float voteAverage, String title, Float popularity, String posterPath, String originalLanguage, String originalTitle, String backdropPath, Boolean adult, String overview, String releaseDate) {
        super();
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry._ID)));
        setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_BACKDROP_PATH)));
        setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_ORIGINAL_LANGUAGE)));
        setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_OVERVIEW)));
        setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_ORIGINAL_TITLE)));
        setPopularity(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_POPULARITY)));
        setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_POSTER_PATH)));
        setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_RELEASE_DATE)));
        setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_TITLE)));
        setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_VOTE_AVERAGE)));
        setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_VOTE_COUNT)));
        setVideo(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_VIDEO)) == 1 ? true : false);
        setAdult(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSQLiteHelper.MovieEntry.COL_NAME_ADULT)) == 1 ? true : false);
    }

    public ContentValues toContentValues(){
        ContentValues args = new ContentValues();
        args.put(MovieSQLiteHelper.MovieEntry._ID, getId());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_BACKDROP_PATH, getBackdropPath());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_ORIGINAL_LANGUAGE, getOriginalLanguage());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_OVERVIEW, getOverview());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_ORIGINAL_TITLE, getOriginalTitle());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_POPULARITY, getPopularity());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_POSTER_PATH, getPosterPath());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_RELEASE_DATE, getReleaseDate());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_TITLE, getTitle());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_VOTE_AVERAGE, getVoteAverage());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_VOTE_COUNT, getVoteCount());
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_VIDEO, getVideo() == true ? 1 : 0);
        args.put(MovieSQLiteHelper.MovieEntry.COL_NAME_ADULT, getAdult() == true ? 1 : 0);
        return args;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "voteCount=" + voteCount +
                ", id=" + id +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", posterPath=" + posterPath +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath=" + backdropPath +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}


