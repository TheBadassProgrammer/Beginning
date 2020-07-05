package com.beginning;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Search {
    public static String result;
    public static void main(String[] args) throws IOException {
        Search.search("Steven grant Rogers");
    }

        public static String search(String query) throws IOException {
            String searchQuery = query.replaceAll(" ", "+");

            String page = "https://in.search.yahoo.com/search?p=" + searchQuery + "&fr=yfp-t&fp=1&toggle=1&cop=mss&ei=UTF-8";
            //Connecting to the web page
            Connection conn = Jsoup.connect(page);
            //executing the get request
            Document doc = conn.get();
            //Retrieving the contents (body) of the web page
            result = doc.body().text();

            String cacheQuery = WordUtils.capitalizeFully(query);
            cacheQuery = cacheQuery.replaceAll(" ", "_");
            System.out.println(cacheQuery);

            result = StringUtils.substringAfter(result, "Wikipedia en.wikipedia.org/wiki/" + cacheQuery + " Cached ");
            result = result.substring(0, Math.min(result.length(), 300));
            return(result);
    }
}
