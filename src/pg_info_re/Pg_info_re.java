package pg_info_re;

import java.io.File;
import java.nio.file.FileSystems;

/**
 *
 * @author lix
 */
public class Pg_info_re {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dir = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
        String tab = "";
        System.out.println("Starting at \"" + dir + "\"");
        
        for (String arg : args){
            if (arg.contains("table")){
                String[] parts = arg.split("=");
                tab = parts[1];
            }
        }
        
        if (tab.isEmpty()){
            System.out.println("usage java -jar pg_info_re.jar --table=<db-table name>");
            return;
        }
        
        PgFiles files = new PgFiles(dir);
        
        System.out.println("Starting at \"" + dir + "\" - found " + files.count() + " files");
        
        
        PgDb db = new PgDb();
        if ( db.connect() ){
            System.out.println("\t database opened");
            File f = files.next();
            while ( f != null ){
                String fileId = f.getName().replaceAll("\\D", "");
                if (fileId.isEmpty()){
                    System.out.println("\t file \"" + f.getName() + "\" is`t ID - skiped");
                } else {
                    System.out.println("\t at file \"" + fileId + "\"");
                    if (db.setTabFile(tab, fileId)){
                       try {Thread.sleep(3000);}catch(java.lang.Throwable t){} 
                       int rows = db.test(tab);
                       if (rows > 1){
                            System.out.println(tab + " SUCCESSED found " + rows + " rows at file " + fileId);
                            break;
                       } else if (rows < 0){
                           try {Thread.sleep(2000);}catch(java.lang.Throwable t){}
                       }
                    } else {
                        try {Thread.sleep(2000);}catch(java.lang.Throwable t){}
                    }
                }
                f = files.next();
            }
            db.close();
        }
    }
}
