/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.Blob;
import java.util.Date;
import java.util.LinkedList;


public class Detail {
    private int idMovie;
    private String name;
    private String imdbId;
    private float rating;
    private Date dateSortie;
    private String trailer;
    private int votes;
    private boolean traduit;
    private Blob affiche;
    private LinkedList<String> acteurs;
    private LinkedList<String> realisateurs;
    private String resume;
    private LinkedList<String> studios;
    private LinkedList<String> langues;
    private LinkedList<String> genres;
    private LinkedList<String> certifications;
    private LinkedList<Integer> copies;
    private LinkedList<String> projections;
    private int runtime;

    public Detail(){
        idMovie = 0;
        name = null;
        imdbId = null;
        rating = 0;
        dateSortie = null;
        trailer = null;
        votes = 0;
        traduit = false;
        affiche = null;
        acteurs = new LinkedList<>();
        realisateurs = new LinkedList<>();
        resume = null;
        studios = new LinkedList<>();
        langues = new LinkedList<>();
        genres = new LinkedList<>();
        certifications = new LinkedList<>();
        copies = new LinkedList<>();
        projections = new LinkedList<>();
        runtime = 0;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isTraduit() {
        return traduit;
    }

    public void setTraduit(boolean traduit) {
        this.traduit = traduit;
    }

    public Blob getAffiche() {
        return affiche;
    }

    public void setAffiche(Blob affiche) {
        this.affiche = affiche;
    }

    public LinkedList<String> getActeurs() {
        return acteurs;
    }

    public void setActeurs(LinkedList<String> acteurs) {
        this.acteurs = acteurs;
    }

    public LinkedList<String> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(LinkedList<String> realisateurs) {
        this.realisateurs = realisateurs;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public LinkedList<String> getStudios() {
        return studios;
    }

    public void setStudios(LinkedList<String> studios) {
        this.studios = studios;
    }

    public LinkedList<String> getLangues() {
        return langues;
    }

    public void setLangues(LinkedList<String> langues) {
        this.langues = langues;
    }

    public LinkedList<String> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<String> genres) {
        this.genres = genres;
    }

    public LinkedList<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(LinkedList<String> certifications) {
        this.certifications = certifications;
    }

    public LinkedList<Integer> getCopies() {
        return copies;
    }

    public void setCopies(LinkedList<Integer> copies) {
        this.copies = copies;
    }

    public LinkedList<String> getProjections() {
        return projections;
    }

    public void setProjections(LinkedList<String> projections) {
        this.projections = projections;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
