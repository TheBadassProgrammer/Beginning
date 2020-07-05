package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CurrentTemperature {
    public static String weatherReport;

    public static String search() throws IOException {
        String page = "https://www.accuweather.com/en/in/varanasi/206681/current-weather/206681";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        weatherReport = doc.body().text();

        weatherReport = weatherReport.replaceAll("Chevron", "");
        weatherReport = StringUtils.substringAfter(weatherReport, "Uttar Pradesh ");//if website changes its text/settings, then change this.
        weatherReport = weatherReport.substring(0, Math.min(weatherReport.length(), 2));
        return (weatherReport);
        //System.out.println("Current Temperature: " + weatherReport + "°C \n");
    }
}