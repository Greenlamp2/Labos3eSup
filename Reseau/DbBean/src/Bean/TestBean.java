package Bean;

import java.beans.Beans;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBean {
    public static void main(String[] args){


        //=====================[MySQL]======================
        try {
            System.out.println("=====================[MySQL]======================");
            Jdbc_MySQL dbSql = null;
            dbSql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbSql.init();

            System.out.println("Count: " + dbSql.count("voyageurs"));
            System.out.println("==Select==");
            String request = "Select * from voyageurs";
            Object tuples = dbSql.select(request);
            ResultSet rs = (ResultSet) tuples;
            while(rs.next()){
                String test = rs.getString("nom");
                System.out.println("Nom:" + test);
            }
            /*
            System.out.println("nom 1: " + dbSql.extract(tuples, 1, "nom"));
            System.out.println("nom 2: " + dbSql.extract(tuples, 2, "nom"));
            System.out.println("nom 3: " + dbSql.extract(tuples, 3, "nom"));
            */
            dbSql.closeStatement();

            System.out.println("==Update==");
            request = "Update voyageurs SET nom = 'BLABLA' Where nom = 'albert'";
            dbSql.update(request);
            request = "Select * from voyageurs";
            tuples = dbSql.select(request);
            rs = (ResultSet) tuples;
            while(rs.next()){
                String test = rs.getString("nom");
                System.out.println("Nom:" + test);
            }
            dbSql.closeStatement();

            System.out.println("==Update==");
            request = "Update voyageurs SET nom = 'albert' Where nom = 'BLABLA'";
            dbSql.update(request);
            request = "Select * from voyageurs";
            tuples = dbSql.select(request);
            rs = (ResultSet) tuples;
            while(rs.next()){
                String test = rs.getString("nom");
                System.out.println("Nom:" + test);
            }
            /*
            System.out.println("nom 1: " + dbSql.extract(tuples, 1, "nom"));
            System.out.println("nom 2: " + dbSql.extract(tuples, 2, "nom"));
            System.out.println("nom 3: " + dbSql.extract(tuples, 3, "nom"));
            */
            dbSql.closeStatement();
            //reset
            request = "Update voyageurs SET nom = 'wirth' Where nom = 'BLABLA'";
            dbSql.update(request);

            dbSql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(TestBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            System.out.println("=====================[Csv]======================");
            Jdbc_CSV dbCsv = null;
            dbCsv = (Jdbc_CSV) Beans.instantiate(null, "Bean.Jdbc_CSV");
            dbCsv.init();

            int csvCount = dbCsv.count("F_AGENTS");
            System.out.println("Count: " + csvCount);
            System.out.println("==Select==");
            String request = "Select nom from F_AGENTS";
            Object tuples = dbCsv.select(request);
            for(int i = 1; i<=csvCount; i++){
                System.out.println("nom 1: " + dbCsv.extract(tuples, i, "nom"));
            }
            dbCsv.closeStatement();

            //dbCsv.insert("INSERT INTO F_AGENTS VALUES ('coco', 'vil', 0, 'SECRET')");
            dbCsv.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(TestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
