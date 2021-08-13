package pg_info_re;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author lix
 */
public class PgDb {
//    private final String DB_URI = "jdbc:postgresql://194.87.111.126/home2?user=postgres&password=mae2Itha&ssl=0";
    private final String DB_URI = "jdbc:postgresql://localhost/home2?user=postgres&password=mae2Itha&ssl=0";
    private Connection db = null; 
    
    public boolean connect(){
        boolean res = false;
        try {
            db = DriverManager.getConnection(DB_URI);
            db.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
            res = true;
        } catch(java.lang.Throwable t){
            System.out.println("ERR on db-connect: " + t.getMessage());
        }
        return res;
    }
    
    public void close(){
        if (db != null){
            try {
                db.close();
            } catch(java.lang.Throwable t){}
            finally {
                db = null;
            }
        }
    }
    
    public boolean setTabFile(String tabName, String fileId){
        boolean res = false;
        try {
            if (db == null){
                connect();
            }
            final PreparedStatement stm = db.prepareStatement("update pg_class set relfilenode=" + fileId + " where relname='" + tabName + "'");
            stm.executeUpdate();
            res = true;
        } catch(java.lang.Throwable t){
            System.out.println("ERR on update: \"" + tabName + "\"->" + fileId + ": " + t.getMessage());
        } finally {
            close();
        }
        return res;
    }
    
    public int test(String tabName){
        int res = -1;
        ResultSet rs = null;
        try {
            if (db == null){
                connect();
            }
            Statement stm = db.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery("select * from " + tabName);
            res = 0;
            while ( rs.next() ){
                String id = rs.getString("id");
                if ((id==null)||id.isEmpty()){
                    throw new java.lang.Throwable("bad row id");
                }
                res++;
            }
        } catch(java.lang.Throwable t){
            System.out.println("ERR on db-connect: " + t.getMessage());
        } finally {
            if (rs != null){
                try {
                    rs.close();
                } catch(java.lang.Throwable t){}
            }
            if (res < 0){
                close();
            }
        }
        return res;
    }
 
}
