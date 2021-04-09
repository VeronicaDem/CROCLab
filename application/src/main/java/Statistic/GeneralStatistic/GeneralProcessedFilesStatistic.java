package Statistic.GeneralStatistic;

import Statistic.ProcessedFileStatistic;
import java.util.ArrayList;


public class GeneralProcessedFilesStatistic {

    private ArrayList<ProcessedFileStatistic>processedFileStatistics;


    public GeneralProcessedFilesStatistic(ArrayList<ProcessedFileStatistic>processedFiles){
        this.processedFileStatistics = processedFiles;
    }

    public void createGeneralStatisticFile(String outDir){
        GeneralWordsStatistic generalWordsStatistic = new GeneralWordsStatistic(processedFileStatistics);
        GeneralSentencesStatistic generalSentencesStatistic = new GeneralSentencesStatistic(processedFileStatistics);
        generalWordsStatistic.createFile(outDir);
        generalSentencesStatistic.createFile(outDir);
    }


}

