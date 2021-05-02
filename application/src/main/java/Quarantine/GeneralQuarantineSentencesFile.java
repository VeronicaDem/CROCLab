//package Quarantine;
//
//import Handler.Handler;
//import ReportLog.LogOperation;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.ArrayList;
//
//public class GeneralQuarantineSentencesFile {
//
//    private ArrayList<QuarantineSentence>generalQuarantineSentences = new ArrayList<>();
//    private transient ArrayList<QuarantineSentencesFile> quarantineSentencesFiles;
//
//    public GeneralQuarantineSentencesFile( ArrayList<QuarantineSentencesFile> quarantineSentencesFiles){
//        this.quarantineSentencesFiles = quarantineSentencesFiles;
//        for (QuarantineSentencesFile quarantineSentencesFile : quarantineSentencesFiles){
//            handleFile(quarantineSentencesFile);
//        }
//    }
//
//    private void handleFile(QuarantineSentencesFile quarantineSentencesFile){
//        ArrayList<String>quarantineSentences = quarantineSentencesFile.getQuarantineSentences();
//        String fileName = quarantineSentencesFile.getFileName();
//        for (String quarantineSentence : quarantineSentences){
//            generalQuarantineSentences.add(new QuarantineSentence(fileName, quarantineSentence));
//        }
//    }
//
//    public void create(String outDir){
//        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/QuarantineSentences.txt"))){
//            os.write(getJsonFormat());
//        }catch(IOException ex){
//            ex.printStackTrace();
//        }
//    }
//
//    private String getJsonFormat(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(this);
//    }
//
//    private boolean isFileEmpty(){
//        return generalQuarantineSentences.size() == 0;
//    }
//}
//
//class QuarantineSentence{
//
//    private String fileName;
//    private String unreadableSentence;
//
//
//    public QuarantineSentence(String fileName, String unreadableSentence){
//        this.fileName = fileName;
//        this.unreadableSentence = unreadableSentence;
//    }
//
//    public String getUnreadableSentence() {
//        return unreadableSentence;
//    }
//
//}
