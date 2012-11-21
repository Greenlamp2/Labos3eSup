/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import CDB.Backdrops;
import CDB.Cast;
import CDB.Country;
import CDB.Genres;
import CDB.Image;
import CDB.Language;
import CDB.Movies;
import CDB.Studios;
import Helpers.EasyFile;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;


public class OracleAccess {
    private Connection con = null;
    private CallableStatement csmt;
    private Statement smt;
    private String urlWebfs = null;

    public OracleAccess(){
        String driver = EasyFile.getConfig("Configs", "oracle_driver");
        String host = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_host_cb"));
        String port = EasyFile.getConfig("Configs", "oracle_port");
        String user = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_user_cb"));
        String pass = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_pass_cb"));
        String sid = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_sid_cb"));

        String hostWebfs = EasyFile.getConfig("Configs", "webfs_host");
        String portWebFs = EasyFile.getConfig("Configs", "webfs_port");
        urlWebfs = "http://" + hostWebfs + ":" + portWebFs + "/";

        //jdbc:oracle:thin:@172.16.40.10:1521:alpha
        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            //con.setAutoCommit(false);
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCall(String namePackage, String nameProcedure, int nbParam){
        String request = "{ call " + namePackage + "." + nameProcedure + "(";
        for(int i=0; i<nbParam; i++){
            request += (i<(nbParam-1) ? "?," : "?)}");
        }
        try {
            csmt = con.prepareCall(request);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String quotes(String message){
        String temp = message;
        temp = temp.replace("'", "''");
        return temp;
    }

    public int insertMovie(Movies movie, int physique, int digitale) {
        try {

            ArrayDescriptor tabVarchar = ArrayDescriptor.createDescriptor("VARCHAR2_T", con);
            ArrayDescriptor tabBlob = ArrayDescriptor.createDescriptor("BLOB_T", con);

            /**********************************************/
            /*Identifiant du film*/
            /**********************************************/
            csmt.setInt(1, movie.getId());

            /**********************************************/
            /*Imdb id du film*/
            /**********************************************/
            csmt.setString(2, movie.getImdb_id());

            /**********************************************/
            /*Nom du film*/
            /**********************************************/
            csmt.setString(3, movie.getName());

            /**********************************************/
            /*Résumé du film*/
            /**********************************************/
            csmt.setString(4, movie.getOverview());

            /**********************************************/
            /*Note du film*/
            /**********************************************/
            csmt.setFloat(5, (movie.getRating() == -1 ? -1 : movie.getRating()));

            /**********************************************/
            /*Date de sortie du film*/
            /**********************************************/
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date dateSql = null;
            java.util.Date dateJava = null;
            try {
                dateJava = sdf.parse(movie.getReleased());
                dateSql = new java.sql.Date(dateJava.getTime());

            } catch (ParseException ex) {
                Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
            csmt.setDate(6, dateSql);

            /**********************************************/
            /*Adresse de la bande annonce du film*/
            /**********************************************/
            csmt.setString(7, movie.getTrailer());

            /**********************************************/
            /*Est-ce que le film est traduit ?*/
            /**********************************************/
            csmt.setBoolean(8, movie.isTranslated());

            /**********************************************/
            /*Nombre de vote pour la popularité du film*/
            /**********************************************/
            csmt.setInt(9, (movie.getVotes() == -1 ? null : movie.getVotes()));

            /**********************************************/
            /*Liste des acteurs du film*/
            /**********************************************/
            String[] nomActeurs = getTabCast(movie.getCast(), "Actor");
            if(nomActeurs == null){
                csmt.setNull(10, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomActeurs);
                csmt.setArray(10, sqlArray);
                sqlArray.free();
                sqlArray = null;
            }

            /**********************************************/
            /*Liste des personnages joué par les acteurs*/
            /**********************************************/
            LinkedList<String> characters = null;
            if(nomActeurs != null){
                for(int i=0; i<nomActeurs.length; i++){
                    String name = getCharacter(nomActeurs[i], movie);
                    if(characters == null) characters = new LinkedList<>();
                    characters.add(name);
                }
            }
            String[] nomCharacters = null;
            if(characters != null){
                int cpt = 0;
                nomCharacters = new String[characters.size()];
                for(String name : characters){
                    nomCharacters[cpt] = name;
                    cpt++;
                }
                Array sqlArray = new ARRAY(tabVarchar, con, nomCharacters);
                csmt.setArray(11, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }else{
                csmt.setNull(11, OracleTypes.ARRAY, "VARCHAR2_T");
            }

            /**********************************************/
            /*Liste des réalisateurs du film*/
            /**********************************************/
            String[] nomRealisateur = getTabCast(movie.getCast(), "Director");
            if(nomRealisateur == null){
                csmt.setNull(12, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomRealisateur);
                csmt.setArray(12, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            /**********************************************/
            /*Liste des pays qui ont produit le film*/
            /**********************************************/
            String[] nomCountries = getTabCountries(movie.getCountries());
            if(nomCountries == null){
                csmt.setNull(13, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomCountries);
                csmt.setArray(13, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            /**********************************************/
            /*Copie physique et digitale.*/
            /**********************************************/
            csmt.setInt(14, physique);
            csmt.setInt(15, digitale);

            /**********************************************/
            /*Liste des studios qui ont produit le film*/
            /**********************************************/
            String[] nomStudios = getTabStudios(movie.getStudios());
            if(nomStudios == null){
                csmt.setNull(16, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomStudios);
                csmt.setArray(16, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            /**********************************************/
            /*Liste des genres du film*/
            /**********************************************/
            String[] nomGenres = getTabGenres(movie.getGenres());
            if(nomGenres == null){
                csmt.setNull(17, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomGenres);
                csmt.setArray(17, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            /**********************************************/
            /*Certification du film*/
            /**********************************************/
            if(movie.getCertification() == null){
                csmt.setNull(18, OracleTypes.VARCHAR);
            }else{
                String certification = getCertification(movie.getCertification());
                csmt.setString(18, certification);
            }

            /**********************************************/
            /*Liste des langues du film*/
            /**********************************************/
            String[] nomLangues = getTabLangues(movie.getLanguages_spoken());
            if(nomLangues == null){
                csmt.setNull(19, OracleTypes.ARRAY, "VARCHAR2_T");
            }else{
                Array sqlArray = new ARRAY(tabVarchar, con, nomLangues);
                csmt.setArray(19, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            BLOB[] tabBlobs = getTabImage(movie.getId());
            if(tabBlobs == null){
                csmt.setNull(20, OracleTypes.ARRAY, "BLOB_T");
            }else{
                Array sqlArray = new ARRAY(tabBlob, con, tabBlobs);
                csmt.setArray(20, sqlArray);
                sqlArray.free();
                sqlArray = null;
                nomActeurs = null;
            }

            csmt.setInt(21, movie.getRuntime());

        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            /**********************************************/
            /*Execution de la procédure*/
            /**********************************************/
            csmt.execute();
            //getImage(movie.getId(), movie.getBackdrops());

            /**********************************************/
            /*On ferme la transaction*/
            /**********************************************/
            csmt.close();
            finish("commit");

            /**********************************************/
            /*On retourne 1 pour le compteur des insertions correctes*/
            /**********************************************/
            return 1;
        } catch (SQLException ex) {
            finish("rollback");
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }


    }

    private int getNbCast(LinkedList<Cast> casts, String job) {
        int count = 0;
        for(Cast cast : casts){
            if(cast.getJob().equals(job)) count++;
        }
        return count;
    }

    private String[] getTabCast(LinkedList<Cast> casts, String job) {
        String[] nomActeurs = null;
        int nbCast = getNbCast(casts, job);
        if(getNbCast(casts, job) > 0){
            nomActeurs = new String[nbCast];
            int cpt = 0;
            for(Cast cast : casts){
                if(cast.getJob().equals(job)){
                    nomActeurs[cpt] = quotes(cast.getName());
                    cpt++;
                }
            }
        }
        return nomActeurs;
    }

    private String getCharacter(String acteur, Movies movie) {
        String name = null;
        for(Cast cast : movie.getCast()){
            if(cast.getName().equals(acteur)){
                name = quotes(cast.getCharacter());
                if(name.isEmpty()) return null;
                else return name;
            }
        }
        return name;
    }

    private String[] getTabCountries(LinkedList<Country> countries) {
        String[] nameCountries = null;
        if(countries.size() > 0){
            nameCountries = new String[countries.size()];
            int cpt = 0;
            for(Country country : countries){
                nameCountries[cpt] = country.getName();
                cpt++;
            }
        }
        return nameCountries;
    }

    private String[] getTabStudios(LinkedList<Studios> studios) {
        String[] nameStudios = null;
        if(studios.size() > 0){
            nameStudios = new String[studios.size()];
            int cpt = 0;
            for(Studios studio : studios){
                nameStudios[cpt] = studio.getName();
                cpt++;
            }
        }
        return nameStudios;
    }

    private String[] getTabGenres(LinkedList<Genres> genres) {
        String[] nameGenres = null;
        if(genres.size() > 0){
            nameGenres = new String[genres.size()];
            int cpt = 0;
            for(Genres genre : genres){
                nameGenres[cpt] = genre.getName();
                cpt++;
            }
        }
        return nameGenres;
    }

    private String[] getTabLangues(LinkedList<Language> languages_spoken) {
        String[] nameLangues = null;
        if(languages_spoken.size() > 0){
            nameLangues = new String[languages_spoken.size()];
            int cpt = 0;
            for(Language language : languages_spoken){
                nameLangues[cpt] = language.getName();
                cpt++;
            }
        }
        return nameLangues;
    }

    private void getImage(int idMovie, LinkedList<Backdrops> backdrops) {
        int nbPoster = getNPoster(backdrops);
        int cpt = 0;
        int num = 0;
        if(nbPoster > 0){
            for(Backdrops backdrop : backdrops){
                if(num >= 4) return;
                if(backdrop.getImage().getSize().equals("poster")){
                    insertBlob(idMovie, backdrop.getImage());
                    num++;
                }
            }
        }
    }

    private String getCertification(String certification) {
        if(certification == null) return "NR";
        else if(certification.equals("")) return "NR";
        else if(certification.equals(" ")) return "NR";
        else if(certification.equals("-")) return "NR";
        else if(certification.equals("unrated")) return "NR";
        else if(certification.equals("Unrated")) return "NR";
        else if(certification.equals("12")) return "NR";
        else if(certification.equals("N/A")) return "NR";
        else if(certification.equals("Not Rated")) return "NR";
        else if(certification.equals("Not Yet Rated")) return "NR";
        else if(certification.equals("None")) return "NR";
        else if(certification.equals("UR")) return "NR";
        else if(certification.equals("NR")) return "NR";
        else if(certification.equals("G")) return "G";
        else if(certification.equals("MA")) return "MA";
        else if(certification.equals("NC-17")) return "NC-17";
        else if(certification.equals("PG")) return "PG";
        else if(certification.equals("NC-17")) return "NC-17";
        else if(certification.equals("PG")) return "PG";
        else if(certification.equals("pg-13")) return "PG-13";
        else if(certification.equals("PG13")) return "PG-13";
        else if(certification.equals("PG-13")) return "PG-13";
        else if(certification.equals("R")) return "R";
        else if(certification.equals("TV-14")) return "TV-14";
        else if(certification.equals("TV14")) return "TV-14";
        else if(certification.equals("X")) return "XXX";
        else if(certification.equals("XXX")) return "XXX";
        else return null;
    }

    private int getNPoster(LinkedList<Backdrops> backdrops) {
        int cpt = 0;
        for(Backdrops backdrop : backdrops){
            if(backdrop.getImage().getSize().equals("poster")){
                cpt++;
            }
        }
        return cpt;
    }

    private byte[] getImage(String urlCover) {
        byte[] image = null;
        try {
            URL urlImage = new URL(urlCover);
            BufferedImage bi = ImageIO.read(urlImage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);

            image = baos.toByteArray();
            baos.close();
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    private void insertBlob(int id, Image image) {
        String urlImage = image.getUrl();
        InputStream is = null;
        try {
            URL url = new URL(urlImage);
            BufferedImage img = ImageIO.read(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);

            byte[] imgByte = baos.toByteArray();
            baos.close();

            is = new ByteArrayInputStream(imgByte);
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        String request = "INSERT INTO POSTERS(idPoster, sizePoster, height, width, image_blob, idMovie) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(request);

            ps.setString(1, image.getId());
            ps.setString(2, image.getSize());
            ps.setInt(3, image.getHeight());
            ps.setInt(4, image.getWidth());
            ps.setBinaryStream(5, is);
            ps.setInt(6, id);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            if(ex.getErrorCode() != 1){
                Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void finish(String type) {
        try {
            this.smt = this.con.createStatement();
            this.smt.executeQuery(type);
            this.smt.close();
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BLOB[] getTabImage(int idMovie) {
        BLOB[] img = null;
        String urlImage = urlWebfs + idMovie + ".jpg";
        URL url = null;
        try{
            url = new URL(urlImage);
        }catch(Exception ex){
            return null;
        }
        System.out.println("idMovie: " + idMovie);
        try {
            BufferedImage image = ImageIO.read(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            img = new BLOB[1];
            //Faire une boucle ici si on aura plus que une seule image.
            img[0] = BLOB.createTemporary(con, false, BLOB.DURATION_SESSION);
            OutputStream outputStream = img[0].setBinaryStream(0L);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageInByte);
            byte[] buffer = new byte[img[0].getBufferSize()];
            int bytesRead = 0;
            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,bytesRead);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception ex) {
            return null;
        }
        return img;
    }
}

