

public class Launch {


    public static void main(String[] args){

//        String[] myArgs = new String[8];
//        myArgs[0] = "1";
//        myArgs[1] = "C:/Users/ivan/Desktop/CROCLab/Files/InputFile_abbrev.txt";
//        myArgs[2] = "C:/Users/ivan/Desktop/CROCLab/Files";

//        PropertyLoader pl = new PropertyLoader("C:/Users/ivan/Desktop/CROCLab/Files/Properties.txt");


        Handler analyzer = new Handler("C:/Users/ivan/Desktop/CROCLab/Files/Properties.txt");
        analyzer.createOutputFiles();
    }

}
