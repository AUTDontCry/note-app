package at.wifi.swdev.notizapp.persistence;


//Umwandlung zwischen Java und SQL Lite Datentypen


import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Converter {

    // LocalDateTime -> Long
    @TypeConverter
    public static long datetimeToTimestamp(LocalDateTime dateTime){
// TODO: Null check
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()/1000;

    }

    // Long -> LocalDateTime
    @TypeConverter
    public static LocalDateTime timestampToDatetime(long timestamp){
// TODO: Null check
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);

    }



}

