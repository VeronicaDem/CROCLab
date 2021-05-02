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
    PROCESSING_DAYS_OF_WEEK("Обработка дней недели"),
    REMOVE_INITIALS("Удаление инициалов"),
    FIND_ABBREVIATIONS("Поиск сокращений в тексте"),
    PROCESS_NUMBERS("Раскрытие цифр в текст"),
    PROCESS_LINKS("Удаление ссылок из текста"),
    PROCESS_PHONE_NUMBERS("Раскрытие номеров телефонов"),
    PROCESS_PUNCTUATIONS("Обработка знаков препинания"),
    SEPARATE_ON_SENTENCES("Разделение файла на предложения"),
    REMOVE_EXTRAS_WHITESPACE("Удаление лишних пробелов"),
    REMOVE_ACRONYMS("Помещение акронимов в карантин"),
    REMOVE_CAMEL_CASE("Удаление CamelCase"),
    CREATE_REPLACEMENT_FILE("Создание файла с заменами"),
    PROCESSING_DATES("Обработка дат"),
    PROCESSING_TIME("Обработака времени"),
    PROCESSING_MONEY("Обработка денег"),
    CAMELCASE_SEPARATOR("Разделение camelCase слов"),
    PROCESSING_FRACTIONS("Обработка дробей"),
    CREATE_PROCESSED_FILES("Создание обработанных файлов");

    private String operationName;

    LogOperation(String operationName){
        this.operationName = operationName;
    }

    public String toString(){
        return operationName;
    }
}
