/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import CDB.Movies;
import Helpers.EasyFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;


public class CouchDbAccess {
    HttpClient httpClient;
    CouchDbConnector db;
    CouchDbInstance dbInstance;

    public CouchDbAccess(){
        String host = EasyFile.getConfig("Configs", "couchdb_host");
        int port = Integer.parseInt(EasyFile.getConfig("Configs", "couchdb_port"));
        String name = EasyFile.getConfig("Configs", "couchdb_name");


        httpClient = new StdHttpClient.Builder().host(host).port(port).build();
        dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector(name, dbInstance);
    }

    public Set<String> getListeId(LinkedList<String[]> listeCriteres) {
        Set<String> listeId = new HashSet<String>();
        reOrderListe(listeCriteres);
        String populariteMin = null;
        String nbVoteMin = null;
        String dateSortieMin = null;
        for(String[] ligne : listeCriteres){
            String critere = ligne[0];
            String value = ligne[1];

            if(critere.equals("id")){
                getById(value, listeId);
            }else if(critere.equals("acteur")){
                //Matti Pellonpää 3, 339
                //Kari Väänänen 339
                getByActor(value, listeId);
            }else if(critere.equals("realisateur")){
                //Aki Kaurismäki 3 1379 73532
                //Quentin Tarantino 393 1991 184 5 16869 ...
                getByDirector(value, listeId);
            }else if(critere.equals("titre")){
                //Shadows in Paradise 3
                //¡Copiad, Malditos! 74174
                getByTitle(value, listeId);
            }else if(critere.equals("populariteMin")){
                populariteMin = value;
            }else if(critere.equals("populariteMax")){
                //1, 2 => 284
                getByPopularite(populariteMin, value, listeId);
                populariteMin = null;
            }else if(critere.equals("nbVoteMin")){
                nbVoteMin = value;
            }else if(critere.equals("nbVoteMax")){
                //11, 12 => 222
                getByNbVote(nbVoteMin, value, listeId);
                nbVoteMin = null;
            }else if(critere.equals("classification")){
                getByClassification(value, listeId);
            }else if(critere.equals("dateSortieMin")){
                dateSortieMin = value;
            }else if(critere.equals("dateSortieMax")){
                getByDateSortie(dateSortieMin, value, listeId);
                dateSortieMin = null;
            }else if(critere.equals("genre")){
                getByGenre(value, listeId);
            }

        }

        return listeId;
    }

    /*********************************************************************/
    /*Interrogation des vues*/
    /*********************************************************************/
    private void getById(String id, Set<String> listeId) {
        int key = Integer.parseInt(id);
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("id").key(key);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        if(rows.size() == 1){
            listeId.add(rows.get(0).getKey());
            rows.get(0).getKey();
        }
    }

    private void getByActor(String acteur, Set<String> listeId) {
        SortedMap<String, Set<String>> actors = null;
        actors = getCache("actors.dump");
        if(actors == null){
            actors = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("movie_actors");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String id = row.getKey();
                String value = row.getValue();
                List<String> names = getNameCast(value);
                for(String name: names){
                    Set<String> ids = actors.get(name);
                    if(ids == null){
                        ids = new HashSet<>();
                    }
                    ids.add(id);
                    actors.put(name, ids);
                }
            }
            setCache("actors.dump", actors);
        }
        filterResult(listeId, actors, acteur);
    }

    private void getByDirector(String realisateur, Set<String> listeId) {
        SortedMap<String, Set<String>> directors = null;
        directors = getCache("directors.dump");
        if(directors == null){
            directors = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("movie_directors");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String id = row.getKey();
                String value = row.getValue();
                List<String> names = getNameCast(value);
                for(String name: names){
                    Set<String> ids = directors.get(name);
                    if(ids == null){
                        ids = new HashSet<>();
                    }
                    ids.add(id);
                    directors.put(name, ids);
                }
            }
            setCache("directors.dump", directors);
        }
        filterResult(listeId, directors, realisateur);
    }

    private void getByTitle(String titre, Set<String> listeId) {
        String key = '"' + titre + '"';
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("name").key(key);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        Set<String> temp = new HashSet<>();
        for(ViewResult.Row row : rows){
            String title = row.getKey();
            String id = row.getValue();
            if(title.contains(titre)){
                temp.add(id);
            }
        }
        if(listeId.isEmpty()){
            listeId.addAll(temp);
        }else{
            listeId.retainAll(temp);
        }
    }

    private void getByPopularite(String populariteMin, String populariteMax, Set<String> listeId) {
        SortedMap<String, Set<String>> popularities = null;
        popularities = getCache("popularities.dump");
        if(popularities == null){
            popularities = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("popularity");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String value = row.getKey();
                String id = row.getValue();
                Set<String> ids = popularities.get(value);
                if(ids == null){
                    ids = new HashSet<>();
                }
                ids.add(id);
                popularities.put(value, ids);
            }
            setCache("popularities.dump", popularities);
        }
        filterResultBetween(listeId, popularities, populariteMin, populariteMax);
    }

    private void getByNbVote(String nbVoteMin, String nbVoteMax, Set<String> listeId) {
        SortedMap<String, Set<String>> nbVotes = null;
        nbVotes = getCache("nbVotes.dump");
        if(nbVotes == null){
            nbVotes = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("votes");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String value = row.getKey();
                String id = row.getValue();
                Set<String> ids = nbVotes.get(value);
                if(ids == null){
                    ids = new HashSet<>();
                }
                ids.add(id);
                nbVotes.put(value, ids);
            }
            setCache("nbVotes.dump", nbVotes);
        }
        filterResultBetween(listeId, nbVotes, nbVoteMin, nbVoteMax);
    }

    private void getByClassification(String classification, Set<String> listeId) {
        LinkedList<String> keys = new LinkedList<>();
        getKeysClassification(keys, classification);
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("certification").keys(keys);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        Set<String> temp = new HashSet<>();
        for(ViewResult.Row row : rows){
            String id = row.getValue();
            String value = row.getKey();
            temp.add(id);
        }
        if(listeId.isEmpty()){
            listeId.addAll(temp);
        }else{
            listeId.retainAll(temp);
        }
    }

    private void getByGenre(String genre, Set<String> listeId) {
        SortedMap<String, Set<String>> genders = null;
        genders = getCache("genders.dump");
        if(genders == null){
            genders = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("genres_name");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String value = row.getKey();
                String id = row.getValue();
                Set<String> ids = genders.get(value);
                if(ids == null){
                    ids = new HashSet<>();
                }
                ids.add(id);
                genders.put(value, ids);
            }
            setCache("genders.dump", genders);
        }
        filterResult(listeId, genders, genre);
    }

    public void getGenres(LinkedList<String> listeGenre) {
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("genres_name_unique").group(true);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        for(ViewResult.Row row : rows){
            listeGenre.add(row.getKey());
        }
    }

    private void getByDateSortie(String dateSortieMin, String dateSortieMax, Set<String> listeId) {
        String[] datesMin = dateSortieMin.split("/");
        String dateMin = datesMin[2] + "-" + datesMin[1] + "-" + datesMin[0];
        String[] datesMax = dateSortieMax.split("/");
        String dateMax = datesMax[2] + "-" + datesMax[1] + "-" + datesMax[0];
        SortedMap<String, Set<String>> dates = null;
        dates = getCache("released.dump");
        if(dates == null){
            dates = new TreeMap<>();
            ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("released");
            ViewResult result = db.queryView(query);
            List<ViewResult.Row> rows = result.getRows();
            for(ViewResult.Row row : rows){
                String value = row.getKey();
                String id = row.getValue();
                Set<String> ids = dates.get(value);
                if(ids == null){
                    ids = new HashSet<>();
                }
                ids.add(id);
                dates.put(value, ids);
            }
            setCache("released.dump", dates);
        }
        filterResultBetweenDate(listeId, dates, dateMin, dateMax);
    }

    public SortedMap<String, Map<String, String>> getAllMovies() {
        SortedMap<String, Map<String, String>> liste = new TreeMap<>();
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("id_name_certif");
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        for(ViewResult.Row row : rows){
            String key = row.getKey();
            String value = row.getValue();
            String[] tab = getNameCertifAllMovies(value);
            System.out.println("Key: " + key);
            Map<String, String> ensemble = new TreeMap<>();
            ensemble.put("name", tab[0]);
            ensemble.put("certification", tab[1]);
            liste.put(key, ensemble);
        }
        return liste;
    }

    /*********************************************************************/
    /*Metiers*/
    /*********************************************************************/
    private List<String> getNameCast(String value) {
        value = value.replaceAll("\"", "_").replaceAll("_", "");
        String left = "name:";
        String right = ",job";
        Pattern p = Pattern.compile("(?<=\\b"+left+"\\b).*?(?=\\b"+right+"\\b)");
        Matcher m = p.matcher(value);
        List<String> matches = new ArrayList<String>();
        while (m.find()) {
            matches.add(m.group());
        }

        return matches;
    }

    private SortedMap<String, Set<String>> getCache(String nameFile) {
        SortedMap<String, Set<String>> liste = null;
        File f = new File(nameFile);
        if(f.exists()){
            liste = new TreeMap<>();
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                liste = (SortedMap<String, Set<String>>)ois.readObject();
                ois.close();
            } catch (Exception ex) {
                Logger.getLogger(CouchDbAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return liste;
    }

    private void setCache(String nameFile, SortedMap<String, Set<String>> liste) {
        File f = new File(nameFile);
            try {
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutput out = new ObjectOutputStream(fos);
                out.writeObject(liste);
                out.close();
            } catch (Exception ex) {
                Logger.getLogger(CouchDbAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void reOrderListe(LinkedList<String[]> listeCriteres) {
        LinkedList<String[]> temp = new LinkedList<>();
        for(String[] critere : listeCriteres){
            String key = critere[0];
            String value = critere[1];
            if(key.equals("id")){
                temp.add(critere);
            }
        }
        for(String[] critere : listeCriteres){
            String key = critere[0];
            if(!key.equals("id")){
                temp.add(critere);
            }
        }
        listeCriteres.clear();
        listeCriteres.addAll(temp);
    }

    public Movies getMovie(String id) {
        Movies movie = db.get(Movies.class, id);
        return movie;
    }

    private void filterResult(Set<String> listeId, SortedMap<String, Set<String>> liste, String key) {
        Set<String> temp = new HashSet<>();
        for(String name : liste.keySet()){
            if(name.contains(key)){
                Set<String> ids = liste.get(name);
                for(String id : ids){
                    temp.add(id);
                }
            }
        }
        if(listeId.isEmpty()){
            listeId.addAll(temp);
        }else{
            listeId.retainAll(temp);
        }
    }

    private void filterResultBetween(Set<String> listeId, SortedMap<String, Set<String>> liste, String keyMin, String keyMax) {
        Set<String> temp = new HashSet<>();
        for(String name : liste.keySet()){
            float value = Float.parseFloat(name);
            float min = Float.parseFloat(keyMin);
            float max = Float.parseFloat(keyMax);
            if((value >= min) && (value <= max)){
                Set<String> ids = liste.get(name);
                temp.addAll(ids);
            }
        }
        if(listeId.isEmpty()){
            listeId.addAll(temp);
        }else{
            listeId.retainAll(temp);
        }
    }

    private void filterResultBetweenDate(Set<String> listeId, SortedMap<String, Set<String>> liste, String keyMin, String keyMax) {
        Set<String> temp = new HashSet<>();
        for(String name : liste.keySet()){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateMin = sdf.parse(keyMin);
                Date dateMax = sdf.parse(keyMax);
                Date value = sdf.parse(name);
                if((value.compareTo(dateMin) == 1 || value.compareTo(dateMin) == 0) && (value.compareTo(dateMax) == -1 || value.compareTo(dateMax) == 0)){
                    Set<String> ids = liste.get(name);
                    temp.addAll(ids);
                }
            } catch (ParseException ex) {
                Logger.getLogger(CouchDbAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(listeId.isEmpty()){
            listeId.addAll(temp);
        }else{
            listeId.retainAll(temp);
        }
    }

    private void getKeysClassification(LinkedList<String> keys, String classification) {
        Map<String, String[]> listeCertification = new TreeMap<>();
        getListeCertification(listeCertification);

        String[] certif = listeCertification.get(classification);
        for(String name: certif){
            keys.add(name);
        }
    }

    private void getListeCertification(Map<String, String[]> listeCertification) {
        String[] nr = {null, "", " ", "-", "unrated", "Unrated", "12", "N/A", "None", "Not Rated", "Not Yet Rated", "UR", "NR"};
        listeCertification.put("NR", nr);
        String[] g = {"G"};
        listeCertification.put("G", g);
        String[] ma = {"MA"};
        listeCertification.put("MA", ma);
        String[] nc17 = {"NC-17"};
        listeCertification.put("NC-17", nc17);
        String[] pg = {"PG"};
        listeCertification.put("PG", pg);
        String[] pg13 = {"pg-13", "PG-13", "PG13"};
        listeCertification.put("PG-13", pg13);
        String[] r = {"R"};
        listeCertification.put("R", r);
        String[] tv14 = {"TV-14", "TV14"};
        listeCertification.put("TV-14", tv14);
        String[] xxx = {"X", "XXX"};
        listeCertification.put("XXX", xxx);
    }

    private String[] getNameCertifAllMovies(String value) {
        String name = null;
        String certif = null;
        value = value.replace("}", "").replace("{", "");
        String[] split = value.split("\"");
        name = split[3];
        if(split.length >= 8){
            certif = split[7];
        }
        String[] retour = {name, certif};
        return retour;
    }
}
