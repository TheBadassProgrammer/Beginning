package com.beginning.GUI;

import com.beginning.TextToSpeech.TextToSpeech;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;

public class CurrentDateTime {
    public static LocalDate fullDate;
    public static int date;
    public static Month month;
    public static int year;
    public static DayOfWeek day;
    public static LocalTime time;
    public static int hour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    public static int minute = (Calendar.getInstance().get(Calendar.MINUTE));
    public static int sec = (Calendar.getInstance().get(Calendar.SECOND));

    public static void run() throws InterruptedException {

        TextToSpeech tts = new TextToSpeech();
        fullDate = LocalDate.now();
        date = LocalDate.now().getDayOfMonth();
        month = LocalDate.now().getMonth();
        year = LocalDate.now().getYear();
        day = fullDate.getDayOfWeek();
        time = LocalTime.now();

        //System.out.println(fullDate);
        //System.out.println(day);
        //System.out.println(time + "\n");

        //tts.speak("Today's date is: " + date + month + year, 2.0f, true, true);
        //tts.speak("and its" + day, 2.0f, true, true);
        //tts.speak("and time is" + hour + "hours" + minute + "minutes" + sec + "seconds", 2.0f, true, true);
    }
}
