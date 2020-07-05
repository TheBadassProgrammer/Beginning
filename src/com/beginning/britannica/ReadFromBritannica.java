package com.beginning.britannica;

import com.beginning.TextToSpeech.TextToSpeech;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ReadFromBritannica {
    public static String result;
    public static String search(String words) throws IOException {
        String page = "https://www.britannica.com/search?query=" + words;
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        result = doc.body().text();

        //filtering data
        result = result.replaceAll("\\.", ".\n");
        result = result.replaceAll(",", ",\n");
        result = StringUtils.substringAfter(result, "1-10");
        result = result.replaceAll("Next results Inspire your inbox – Sign up for daily fun facts about this day in history.\n" +
                " updates.\n" +
                " and special offers.\n" +
                " Enter your email Subscribe By signing up for this email.\n" +
                " you are agreeing to news.\n" +
                " offers.\n" +
                " and information from Encyclopaedia Britannica.\n" +
                " Click here to view our Privacy Notice.\n" +
                " Easy unsubscribe links are provided in every email.\n" +
                " Thank you for subscribing! Be on the lookout for your Britannica newsletter to get trusted stories delivered right to your inbox.\n" +
                " Stay Connected Facebook Twitter YouTube Instagram Pinterest Newsletters About Us & Legal Info Partner Program Contact Us Privacy Notice Terms of Use ©2020 Encyclopædia Britannica.\n" +
                " Inc.\n" +
                " Your preference has been recorded close Check out Britannica's new site for parents!", "");

        result = result.substring(0, Math.min(result.length(), 300));
        TextToSpeech tts = new TextToSpeech();
        tts.speak(result, 2.0f, true, true);
        return result;

    }
}
