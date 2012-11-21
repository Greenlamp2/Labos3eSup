/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import Utils.GestionProgressBar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;


public class CouchDB {
    HttpClient httpClient;
    CouchDbConnector db;
    CouchDbInstance dbInstance;
    GestionProgressBar barSearch;

    public CouchDB(GestionProgressBar gpb){
        httpClient = new StdHttpClient.Builder().host("127.0.0.1").port(5984).build();
        dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector("movies_20122013", dbInstance);
        barSearch = gpb;
    }

    public Set<String> getById(int id, Set<String> listeMovies){
        int key = id;
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("id").key(key);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();

        System.out.println("Nb results: " + rows.size());
        if(rows.isEmpty()){
            barSearch.setMax(100);
            barSearch.setValue(100);
        }else{
            barSearch.setMax(rows.size());
        }
        int cpt = 0;
        for(Row ligne: rows){
            if(listeMovies == null){
                listeMovies = new HashSet<>();
                listeMovies.add(ligne.getKey());
            }
            Set<String> temp = new HashSet<>();
            temp.add(ligne.getKey());
            listeMovies.retainAll(temp);
            barSearch.setValue(cpt);
        }
        return listeMovies;
    }

    public Set<String> getByName(String titre, Set<String> listeMovies) {
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("name").key(titre);
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        for(Row ligne: rows){
            if(listeMovies == null){
                listeMovies = new HashSet<>();
                listeMovies.add(ligne.getValue());
            }
            Set<String> temp = new HashSet<>();
            temp.add(ligne.getValue());
            listeMovies.retainAll(temp);
        }
        return listeMovies;
    }

    public Set<String> getByActor(String actor, Set<String> listeMovies) {
        ViewQuery query = new ViewQuery().designDocId("_design/main").viewName("movie_actors");
        ViewResult result = db.queryView(query);
        List<ViewResult.Row> rows = result.getRows();
        for(Row ligne: rows){
            if(listeMovies == null){
                listeMovies = new HashSet<>();
                String value = ligne.getValue();
                listeMovies.add(ligne.getValue());
            }
            Set<String> temp = new HashSet<>();
            temp.add(ligne.getValue());
            listeMovies.retainAll(temp);
        }
        return listeMovies;
    }
}
