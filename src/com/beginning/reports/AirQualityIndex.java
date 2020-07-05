package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class AirQualityIndex {
    public static String airQuality;
    public static void main(String[] args) throws IOException {
        AirQualityIndex.search();
    }

        public static String search() throws IOException {
            String page = "https://weather.com/en-IN/weather/today/l/c9263fe384e8cea7e1a998c529285698db393647a9d4291124e1825e11e7976d";
            //Connecting to the web page
            Connection conn = Jsoup.connect(page);
            //executing the get request
            Document doc = conn.get();
            //Retrieving the contents (body) of the web page
            airQuality = doc.body().text();

            //result = result.replaceAll("\\.", ".\n");
            //result = result.replaceAll(",", ",\n");
            //result = StringUtils.substringAfter(result, " Air Quality Index");
            airQuality = StringUtils.substringBetween(airQuality, " Air Quality Index", ".");
            //result = result.substring(0, Math.min(result.length(), 300));
            return (airQuality);
    }

}
