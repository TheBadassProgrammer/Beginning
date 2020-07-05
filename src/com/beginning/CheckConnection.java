package com.beginning;
import com.beginning.TextToSpeech.marytts.TextToSpeech;

public class CheckConnection {

    static TextToSpeech tts = new TextToSpeech();
    public static void check() throws Exception {
        tts.speak("Checking Internet Connection...", 2.0f, true, true);
        Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
        int x = process.waitFor();
        if (x == 0) {
            System.out.println("Connected to Internet!");
            tts.speak("Connected to Internet!", 2.0f, true, true);
        }
        else{
            System.out.println("Could not connect to Internet!");
            tts.speak("Could not connect to Internet!", 2.0f, true, true);
            System.exit(0);
        }
    }
}