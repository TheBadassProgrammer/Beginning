package com.beginning.GUI;

import com.beginning.TextToSpeech.TextToSpeech;

public class LoginThread extends Thread{
    @Override
    public void run(){
        TextToSpeech tts = new TextToSpeech();
        tts.speak("Please enter login and password.", 2.0f, true, true);
    }
}
