package ReportLog;

public enum LogOperation {

    DICTIONARIES_LOAD("Загрузка словарей"),
    WHITESPACE_DICTIONARY("Удаление словарных слов которые содержат пробелы"),
    SINGLE_DICTIONARY("Удаление словарных слов не содержащих пробелы"),
    FIND_ENGLISH("Поиск английского текста"),
    REMOVE_ENGLISH("Удаление английского текста"),
    CREATE_QUARANTINE_FILES("Создание карантинных файлов"),
    LOAD_WORDS_TO_DELETE("Загрузка файла со словами для удаления"),
    REMOVE_WORDS_TO_DELETE("Удаление заданных слов"),
    CREATE_PROCESSED_FILES("Создание обработанных файлов");

    private String operationName;

    LogOperation(String operationName){
        this.operationName = operationName;
    }

    public String toString(){
        return operationName;
    }
}
