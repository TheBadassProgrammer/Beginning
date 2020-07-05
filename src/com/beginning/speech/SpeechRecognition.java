package com.beginning.speech;

import com.beginning.TextToSpeech.TextToSpeech;
import com.beginning.speech.microphone.Microphone;
import com.beginning.speech.recognizer.GSpeechDuplex;
import com.beginning.speech.recognizer.GSpeechResponseListener;
import com.beginning.speech.recognizer.GoogleResponse;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SpeechRecognition implements GSpeechResponseListener {
    public static final Microphone mic = new Microphone(FLACFileWriter.FLAC);
    //Don't use the below google api key , make your own !!! :)
    public static GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
    private final TextToSpeech tts = new TextToSpeech();
    public static String old_text = "";
    public static String output = "";

    public void speechRecognition() {
        duplex.setLanguage("en");

        JFrame frame = new JFrame("Jarvis Speech API DEMO");
        frame.setDefaultCloseOperation(3);
        JTextArea response = new JTextArea();
        response.setEditable(false);
        response.setWrapStyleWord(true);
        response.setLineWrap(true);

        final JButton record = new JButton("Record");
        final JButton stop = new JButton("Stop");
        stop.setEnabled(false);

        record.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new Thread(() -> {
                    try {
                        duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }).start();
                record.setEnabled(false);
                stop.setEnabled(true);
            }
        });
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mic.close();
                duplex.stopSpeechRecognition();
                record.setEnabled(true);
                stop.setEnabled(false);
            }
        });


        JScrollPane scroll = new JScrollPane(response);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), 1));
        frame.getContentPane().add(scroll);
        JPanel recordBar = new JPanel();
        frame.getContentPane().add(recordBar);
        recordBar.setLayout(new BoxLayout(recordBar, 0));
        recordBar.add(record);
        recordBar.add(stop);
        frame.setVisible(true);
        frame.pack();
        frame.setSize(500, 100);
        frame.setLocationRelativeTo(null);

        duplex.addResponseListener(new GSpeechResponseListener() {


            public void onResponse(GoogleResponse gr) throws IOException, URISyntaxException {

                output = gr.getResponse();
                if (gr.getResponse() == null) {
                    old_text = response.getText();

                    if (old_text.contains("(")) {
                        old_text = old_text.substring(0, old_text.indexOf('('));
                    }
                    System.out.println("Paragraph Line Added");
                    old_text = ( response.getText() + "\n" );
                    old_text = old_text.replace(")", "").replace("( ", "");
                    response.setText(old_text);
                    return;
                }
                if (output.contains("(")) {
                    output = output.substring(0, output.indexOf('('));
                }
                if (!gr.getOtherPossibleResponses().isEmpty()) {
                    output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
                }
                System.out.println(output);
                response.setText("");
                response.append(old_text);
                response.append(output);
                makeDecision(response.getText());
            }
        });

        startSpeechRecognition();
    }

    @Override
    public void onResponse(GoogleResponse paramGoogleResponse) {
        // TODO Auto-generated method stub

    }

    public void makeDecision(String output) throws IOException, URISyntaxException {
        output = output.trim();
        //System.out.println(output.trim());

        //We don't want duplicate responses
        if (!SpeechRecognition.output.equals(output))
            SpeechRecognition.output = output;
        else
            return;

        Desktop desktop = null;
        if (output.contains("my computer")) {
            Runtime.getRuntime().exec("cmd /c start explorer");

        } else if (output.contains("search")) {
            output = output.replaceAll("search", "");
            output = output.replaceAll(" ", "");
            try {
                URI uri= new URI("https://www." + output + ".com/");
                Desktop.getDesktop().browse(uri);
                System.out.println("Web page opened in browser");
                speak("Okay sir, opening" + output);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (output.contains("Spider-Man Homecoming") || output.contains("Spiderman Homecoming")) {
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            Desktop.getDesktop().open(new File("d:\\Movie\\SpiderMan - Homecoming - 2017 [Dual Audio].mkv"));

        } else if (output.contains("what is your profession")) {
            speak("I am your personal assistant, sir.");

        } else if (output.contains("do you have any girlfriend")) {
            speak("Yes sir, but she is also AI");

        } else if (output.contains("where do you live")) {
            speak("I live in your computer, sir");

        } else if (output.contains("I think you're funny") || output.contains("I think you are funny")) {
            speak("Yeah you too!");

        } else if (output.contains("let me sleep")) {
            speak("i am also going to sleep then");

        } else if (output.contains("damn")||output.contains("fuck")||output.contains("shit")) {
            speak("dont abuse me sir");

        } else if (output.contains("can u run my business for a day")) {
            speak("ofcourse sir, i will hire some temporary employees. You should leave it on me, sir");

        } else if (output.contains("who's your daddy") || output.contains("I am the boss")|| output.contains("Who's your daddy")) {
            speak("hahaha ofcourse you are");

        } else if (output.contains("show me some respect")) {
            speak("Ok i will try");

        } else if (output.contains("tell me a story")) {
            speak("I dont know any story");

        } else if (output.contains("why do you speak like that")) {
            speak("My speaking skills were always in your hand");

        } else if (output.contains("say hi to Ashutosh")) {
            speak("Hello babe how are you doing?");

        } else if (output.contains("stop speech recognition")) {//Stop Speech Recognition
            stopSpeechRecognition();

        } else {
            System.out.println("Not entered on any else if statement");
        }

    }

    /**
     * Calls the MaryTTS to say the given text
     *
     * @param text
     */
    public void speak(String text) {
        System.out.println(text);
        //Check if it is already speaking

        new Thread(() -> tts.speak(text, 2.0f, true, true)).start();

    }

    /**
     * Starts the Speech Recognition
     */
    public static void startSpeechRecognition() {
        //Start a new Thread so our application don't lags
        new Thread(() -> {
            try {
                duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
            } catch (LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Stops the Speech Recognition
     */
    public void stopSpeechRecognition() {
        mic.close();
        System.out.println("Stopping Speech Recognition...." + " , Microphone State is:" + mic.getState());
    }

    public static void main(String[] args) throws IOException {
        new SpeechRecognition().speechRecognition();
    }
}


