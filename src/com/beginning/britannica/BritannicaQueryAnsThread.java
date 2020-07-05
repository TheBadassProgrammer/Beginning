package com.beginning.britannica;

import java.io.IOException;

import static com.beginning.britannica.BritannicaMainThread.query;

public class BritannicaQueryAnsThread extends Thread{
    @Override
    public void run(){
        try {
            ReadFromBritannica.search(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
