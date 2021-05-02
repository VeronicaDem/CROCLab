package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationFinder {

    public static void processAbbreviations(ArrayList<InputFile> inputFiles) {
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.FIND_ABBREVIATIONS, inputFile.getFileName());
            findAbbreviationsInInputFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Abbreviations ");
    }

    private static void findAbbreviationsInInputFile(InputFile inputFile) {
        String fileText = inputFile.getFileText();
        Pattern abbreviationPattern = Pattern.compile("\\s([А-Яа-яёЁA-Za-z]){1,3}\\.[\\s[A-Za-zА-Яа-яёЁ]]");
        Matcher matcher = abbreviationPattern.matcher(fileText);
        while (matcher.find()) {
            String foundAbbreviation = fileText.substring(matcher.start(), matcher.end());
            String sentenceWithAbbreviation = getSentenceWithAbbreviation(fileText, matcher.start(), matcher.end());
            inputFile.getFileWithAbbreviations().addAbbreviationSentences(sentenceWithAbbreviation + "(" + foundAbbreviation + ")");
        }
    }

    private static String getSentenceWithAbbreviation(String fileText, int startAbbreviationIndex, int endAbbreviationIndex) {
        int startSentenceIndex = 0;
        int endSentenceIndex = fileText.length();
        for (int i = startAbbreviationIndex; i > 0; i--) {
            char currentCharacter = fileText.charAt(i);
            if (currentCharacter == '.') {
                startSentenceIndex = i + 1;
                break;
            }
        }
        for (int i = endAbbreviationIndex; i < fileText.length(); i++) {
            char currentCharacter = fileText.charAt(i);
            if (currentCharacter == '.') {
                endSentenceIndex = i;
                break;
            }
        }
        return fileText.substring(startSentenceIndex, endSentenceIndex);
    }


}
