package com.beginning.reports;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsReport {
    public static String newsReport;
    public static String TimesOfIndia() throws IOException {
        String page = "https://timesofindia.indiatimes.com/briefs";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        newsReport = doc.body().text();

        newsReport = StringUtils.substringAfter(newsReport, "NEWS IN BRIEF ");
        newsReport = newsReport.substring(0, Math.min(newsReport.length(), 800));
        //newsReport = newsReport.replaceAll("ampvideo_youtubeTimes", "\n");
        return (newsReport);
    }

    public static void main(String[] args) throws IOException {
        NewsReport.TimesOfIndia();
    }
}
