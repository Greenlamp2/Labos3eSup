/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;

import java.util.LinkedList;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({ "_id", "_rev"})
public class Movies{
    private boolean adult;
    private String alternative_name;
    private float budget;
    private LinkedList<Cast> cast;
    private String certification;
    private LinkedList<String> countries;
    private String homepage;
    private int id;
    private String imdb_id;
    private LinkedList<String> keywords;
    private LinkedList<String> languages_spoken;
    private String name;
    private String original_name;
    private String overview;
    private float popularity;
    private float rating;
    private String released;
    private float revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String trailer;
    private boolean translated;
    private int votes;
    private LinkedList<Genres> genres;
    private LinkedList<Posters> posters;
    private LinkedList<Studios> studios;
    private LinkedList<Backdrops> backdrops;

    public Movies(){
        adult = false;
        alternative_name = null;
        budget = -1;
        certification = null;
        countries = null;
        homepage = null;
        imdb_id = null;
        keywords = null;
        languages_spoken = null;
        name = null;
        overview = null;
        popularity = -1;
        rating = -1;
        released = null;
        revenue = -1;
        runtime = -1;
        status = null;
        tagline = null;
        trailer = null;
        translated = false;
        votes = -1;
        genres = null;
        cast = null;
        studios = null;
        backdrops = null;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getAlternative_name() {
        return alternative_name;
    }

    public void setAlternative_name(String alternative_name) {
        this.alternative_name = alternative_name;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public LinkedList<String> getCountries() {
        return countries;
    }

    public void setCountries(LinkedList<String> countries) {
        this.countries = countries;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public LinkedList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(LinkedList<String> keywords) {
        this.keywords = keywords;
    }

    public LinkedList<String> getLanguages_spoken() {
        return languages_spoken;
    }

    public void setLanguages_spoken(LinkedList<String> languages_spoken) {
        this.languages_spoken = languages_spoken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public boolean isTranslated() {
        return translated;
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Cast> getCast() {
        return cast;
    }

    public void setCast(LinkedList<Cast> cast) {
        this.cast = cast;
    }

    public LinkedList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<Genres> genres) {
        this.genres = genres;
    }

    public LinkedList<Posters> getPosters() {
        return posters;
    }

    public void setPosters(LinkedList<Posters> posters) {
        this.posters = posters;
    }

    public LinkedList<Studios> getStudios() {
        return studios;
    }

    public void setStudios(LinkedList<Studios> studios) {
        this.studios = studios;
    }

    public LinkedList<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(LinkedList<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }
}
