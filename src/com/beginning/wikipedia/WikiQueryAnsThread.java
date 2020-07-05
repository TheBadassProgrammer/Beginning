package com.beginning.wikipedia;

import java.io.IOException;

import static com.beginning.wikipedia.WikiMainThread.query;

public class WikiQueryAnsThread extends Thread{
    @Override
    public void run(){
        try {
            ReadFromWikipedia.search(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
