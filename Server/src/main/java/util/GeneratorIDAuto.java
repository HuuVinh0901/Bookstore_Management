package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GeneratorIDAuto {
    public GeneratorIDAuto(){

    }
    public String autoID(String keyword){
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String newID;
        newID =keyword + formattedDateTime ;
        return newID;
    }
}
