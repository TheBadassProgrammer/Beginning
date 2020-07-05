package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class VisibilityReport {
    public static String visibility;
    public static void main(String[] args) throws IOException {
        VisibilityReport.search();
    }
    public static String search() throws IOException {
        String page = "https://weather.com/en-IN/weather/today/l/c9263fe384e8cea7e1a998c529285698db393647a9d4291124e1825e11e7976d";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        visibility = doc.body().text();

        visibility = StringUtils.substringAfter(visibility, " Visibility ");
        visibility = visibility.substring(0, Math.min(visibility.length(), 4));
        visibility = "Visibility is " + visibility + "kilometer";

        return (visibility);
    }
}