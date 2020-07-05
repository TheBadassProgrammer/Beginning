package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HumidityReport {
    public static String humidity;
    public static void main(String[] args) throws IOException {
        HumidityReport.search();
    }
    public static String search() throws IOException {
        String page = "https://weather.com/en-IN/weather/today/l/c9263fe384e8cea7e1a998c529285698db393647a9d4291124e1825e11e7976d";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        humidity = doc.body().text();

        humidity = StringUtils.substringAfter(humidity, " Humidity ");
        //result = StringUtils.substringBetween(result, " Air Quality Index", ".");
        humidity = humidity.substring(0, Math.min(humidity.length(), 3));
        humidity = "Humidity is " + humidity;
        return (humidity);
    }
}
