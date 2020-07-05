package com.beginning.GUI;

import com.beginning.TextToSpeech.TextToSpeech;
import com.beginning.reports.CurrentTemperature;

import javax.swing.*;
import java.io.IOException;

import static com.beginning.GUI.MainGUI.mainFrame;
import static com.beginning.GUI.MainGUI.mainPanel;
import static com.beginning.reports.CurrentTemperature.weatherReport;

public class CurrentDateTimeThread extends Thread{
    @Override
    public void run(){
        try {
            TextToSpeech tts = new TextToSpeech();
            tts.speak("Welcome Sir.", 2.0f, true, true);

            CurrentDateTime.run();
            CurrentTemperature.search();

            JLabel temperatureLabel = new JLabel("Temperature: " + weatherReport + "°C");
            temperatureLabel.setBounds(208, 20, 200, 25);
            mainPanel.add(temperatureLabel);
            mainFrame.pack();
            mainFrame.setSize(850, 300);
            mainFrame.setVisible(true);

            tts.speak("Temperature is " + weatherReport + "degree celcius", 2.0f, true, true);


            tts.speak("Enter you query.", 2.0f, true, true);

        } catch (InterruptedException | IOException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}