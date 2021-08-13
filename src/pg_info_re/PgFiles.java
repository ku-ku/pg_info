package pg_info_re;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author lix
 */
public class PgFiles {
    private final List<File> files = new ArrayList();
    private ListIterator<File> li = null;
    
    void scan(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                scan(fileEntry);
            } else {
                if (fileEntry.length() > 0){
                    files.add(fileEntry);
                }
            }
        }
    }
    
    PgFiles(String dir){
        final File folder = new File(dir);
        scan(folder);
    }
    
    public int count(){
        return files.size();
    }
    
    public File next(){
        if ( li == null ){
            li = files.listIterator();
        }
        if ( li.hasNext() ){
            return li.next();
        }
        return null;
    }
}
