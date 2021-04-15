package Statistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class GeneralQuarantineSentencesStatistic {


        ArrayList<QuarantineStatisticFile>quarantineStatisticFiles = new ArrayList() ;

    public GeneralQuarantineSentencesStatistic(ArrayList<QuarantineStatisticFile>quarantineStatisticFiles){
            for (QuarantineStatisticFile quarantineStatisticFile : quarantineStatisticFiles){
                if (!quarantineStatisticFile.fileIsEmpty()){
                    this.quarantineStatisticFiles.add(quarantineStatisticFile);
                }
            }
    }



    public void createFile(String outDir){
        if (!isFileEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/QuarantineSentencesStatistic.txt"))) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private boolean isFileEmpty(){
        return quarantineStatisticFiles.size() == 0;
    }
}


class QuarantineSentenceStatistic{

    private String fileName;
    private String unreadableSentence;
    private Integer countSentences;


    public QuarantineSentenceStatistic(String fileName, String unreadableSentence){
        this.fileName = fileName;
        this.unreadableSentence = unreadableSentence;
    }

    public String getUnreadableSentence() {
        return unreadableSentence;
    }

    public void setCountSentences(Integer countSentences){
        this.countSentences = countSentences;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + fileName.hashCode();
        result = 31 * result + unreadableSentence.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        QuarantineSentenceStatistic objQuarSentStat = (QuarantineSentenceStatistic)obj;
        return fileName.equals(objQuarSentStat.fileName) && unreadableSentence.equals(objQuarSentStat.unreadableSentence);
    }

    public Integer getCountSentences() {
        return countSentences;
    }
}
