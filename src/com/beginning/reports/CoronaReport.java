package com.beginning.reports;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CoronaReport {
    public static void main(String[] args) throws IOException, URISyntaxException {
        CoronaReport.provide();
    }

    public static void provide() throws IOException, URISyntaxException {
        URI uri = new URI("https://www.bing.com/covid/local/varanasi_uttarpradesh_india");
        Desktop.getDesktop().browse(uri);
    }

}
