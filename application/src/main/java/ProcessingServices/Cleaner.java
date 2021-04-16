package ProcessingServices;

import Properties.PropertyLoader;

import java.io.File;

public class Cleaner {

    public static void deleteOldOutDirectory(PropertyLoader property){
        deleteDir(property.getOutDirectory());
    }

    private static void deleteDir(String dirPath){
        File oldOutDir = new File(dirPath);
        if (!oldOutDir.delete()){
            for (File fileInDir : oldOutDir.listFiles()){
                deleteDir(fileInDir.getPath());
            }
        }
    }
}
