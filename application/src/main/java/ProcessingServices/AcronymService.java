package ProcessingServices;

import ProtectWords.ProtectedWordsStorage;
import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AcronymService {

    public static void acronymsInQuarantine(ArrayList<InputFile> inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.REMOVE_ACRONYMS, inputFile.getFileName());
            handleAcronyms(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Acronyms ");
    }

    private static void handleAcronyms(InputFile inputFile){
        ArrayList<String>fileSentences = new ArrayList<>(inputFile.getSentences());
        for (String sentence : fileSentences){
            Pattern acronymPattern = Pattern.compile("(?<=^|[\\W&&[^А-Яа-я]])[А-ЯЁ]{2,5}(?=$|[\\W&&[^А-Яа-я]])");
            Matcher matcher = acronymPattern.matcher(sentence);
            if (matcher.find()){
                String foundAcronym = sentence.substring(matcher.start(), matcher.end());
                if (!ProtectedWordsStorage.isWordProtected(foundAcronym)) {//Если найденного слова нет в списке исключений,
                    //помещаем в карантин предложение, в котором оно находится.
                    inputFile.moveToQuarantine(sentence);
                    inputFile.getQuarantineWordsFile().addQuarantineWord(foundAcronym);
                }
            }
        }

    }
}
