package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RainReport {
    public static String rainPercentage;
    public static void main(String[] args) throws IOException {
        RainReport.search();
    }

    public static String search() throws IOException {
        String page = "https://weather.com/en-IN/weather/today/l/c9263fe384e8cea7e1a998c529285698db393647a9d4291124e1825e11e7976d";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        rainPercentage = doc.body().text();

        rainPercentage = StringUtils.substringBetween(rainPercentage, " Varanasi, Uttar Pradesh Weather", " Now");
        rainPercentage = StringUtils.substringAfter(rainPercentage, "/");
        rainPercentage = StringUtils.substringAfter(rainPercentage, "° ");
        return (rainPercentage);
    }
}
