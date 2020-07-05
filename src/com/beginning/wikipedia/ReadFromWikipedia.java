package com.beginning.wikipedia;

import com.beginning.TextToSpeech.TextToSpeech;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ReadFromWikipedia {
    public static void main(String[] args) throws IOException {
        //ReadFromWikipedia.search("tony stark");
    }
    public static String result;
    public static String search(String words) throws IOException {

        String page = "https://en.wikipedia.org/wiki/" + words;
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        result = doc.body().text();

        //filtering data
        result = result.replaceAll("\\.", ".\n");
        result = result.replaceAll(",", ",\n");
        result = StringUtils.substringAfter(result, words + " is ");
        result = result.substring(0, Math.min(result.length(), 300));
        //result = result.replaceAll();

        TextToSpeech tts = new TextToSpeech();
        tts.speak(result, 2.0f, true, true);
        System.out.println(result);
        return result;
    }
}
