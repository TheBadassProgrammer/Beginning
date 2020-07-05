package com.beginning;

import com.beginning.GUI.CurrentDateTime;
import com.beginning.TextToSpeech.marytts.TextToSpeech;
import com.beginning.reports.*;
import com.beginning.speech.microphone.Microphone;
import com.beginning.speech.recognizer.GSpeechDuplex;
import com.beginning.speech.recognizer.GSpeechResponseListener;
import com.beginning.speech.recognizer.GoogleResponse;
import com.beginning.wikipedia.ReadFromWikipedia;
import net.sourceforge.javaflacencoder.FLACFileWriter;
import org.apache.commons.lang.StringUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;

/**
 * This is where all begins .
 *
 * @author GOXR3PLUS
 *
 */
public class Application {
    public static String verification = "assistant";
    public static String developerVerification = "";
    public static String developerOutput = "";
    public static String developerInput = "";

    private final TextToSpeech tts = new TextToSpeech();
    private final Random random = new Random();
    public static int hour = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    private final Microphone mic = new Microphone(FLACFileWriter.FLAC);
    private final GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

    String oldText = "";

    /**
     * Constructor
     */
    public Application() throws Exception {

        //Duplex Configuration
        duplex.setLanguage("en");

        CheckConnection.check();//Checking Internet

        speak("Gathering Data...");
        JarvisDB.createConnection();


        duplex.addResponseListener(new GSpeechResponseListener() {
            String old_text = "";
            public void onResponse(GoogleResponse googleResponse) throws IOException, URISyntaxException, SQLException, ClassNotFoundException {
                String output = "";

                //Get the response from Google Cloud
                output = googleResponse.getResponse();
                System.out.println(output);


                if (output != null && verification.equals("Chatting mode on")) {
                    try {
                        makeDecisionForChat(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (output != null && verification.equals("Developer mode on")) {
                    try {
                        makeDecisionForDeveloper(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (output != null) {
                    try {
                        makeDecision(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    System.out.println("Output was null");
            }
        });

        startSpeechRecognition();

    }


    public void makeDecision(String output) throws IOException, URISyntaxException, AWTException, InterruptedException, ClassNotFoundException, SQLException {


            output = output.trim();
            //System.out.println(output.trim());

            //We don't want duplicate responses
            if (!oldText.equals(output))
                oldText = output;
            else
                return;

//            if (JarvisDB.inputList.contains(output)) {
//                int i = JarvisDB.inputList.indexOf(output);
//                String out1 = JarvisDB.output1List.get(i);
//                String out2 = JarvisDB.output2List.get(i);
//                String out3 = JarvisDB.output3List.get(i);
//                String[] outList = {out1, out2, out3};
//                String randomOut = outList[random.nextInt(outList.length)];
//                if (!tts.isSpeaking())
//                    speak(randomOut);
//            }

            if (output.contains("open")&& output.contains("Chrome"))  {
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    output = output.replaceAll("open", "");
                    output = output.replaceAll("Chrome", "");
                    output = output.replaceAll("Google", "");
                    output = output.replaceAll(" on ", "");
                    output = output.replaceAll(" in ", "");
                    output = output.replaceAll(" ", "");
                    try {
                        URI uri = new URI("www." + output );
                        Desktop.getDesktop().browse(uri);
                        System.out.println("Web page opened in browser");
                        speak("Okay sir, opening " + output);
                    } catch (Exception e) {
                     e.printStackTrace();
                    }
                }
            }
            else if (output.contains("shopping") || output.contains("buy")){
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("launching amazon, sir.");
                    URI uri = new URI("www.amazon.in");
                    Desktop.getDesktop().browse(uri);
                }
            }
            else if (output.contains("mail") || output.contains("e-mail")){
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("launching gmail, sir.");
                    URI uri = new URI("www.gmail.com");
                    Desktop.getDesktop().browse(uri);
                }
            }
            else if (output.contains("map") || output.contains("my location")){
             if (!tts.isSpeaking() && verification.equals("assistant")) {
                 speak("launching google maps, sir.");
                 URI uri = new URI("www.maps.google.com");
                 Desktop.getDesktop().browse(uri);
             }

            }
            else if (output.contains("open") && output.contains("WhatsApp")){
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("launching whatsapp, sir.");
                    URI uri = new URI("https://web.whatsapp.com/");
                    Desktop.getDesktop().browse(uri);
                }
            }
            else if (output.contains("YouTube")){
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("launching youtube, sir.");
                    URI uri = new URI("www.youtube.com");
                    Desktop.getDesktop().browse(uri);
                }
            }
            //opening common apps
            else if (output.contains("my computer")) {
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    Runtime.getRuntime().exec("cmd /c start explorer");
                    speak("opening my computer, sir.");
                }
            }
            else if (output.contains("calculator")) {
                speak("opening calculator, sir.");
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("opening calculator, sir.");
                    Runtime.getRuntime().exec("cmd /c start calc");
                }
            }
            else if (output.contains("Notepad")) {
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("opening notepad, sir.");
                    Runtime.getRuntime().exec("cmd /c start notepad");
                }
            }
            else if (output.contains("Windows Media Player")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening windows media player, sir.");
                     Runtime.getRuntime().exec("cmd /c start wmplayer");
                 }
            }
            else if (output.contains("task manager")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening task manager, sir.");
                     Runtime.getRuntime().exec("cmd /c start taskmgr");
                 }
            }
            else if (output.contains("command prompt") || output.contains("command line")) {
                speak("opening command prompt, sir.");
                 if (!tts.isSpeaking() && verification.equals("assistant"))
                     Runtime.getRuntime().exec("cmd /c start cmd");

            }
            else if (output.contains("Google Chrome")) {
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    speak("opening Google Chrome, sir.");
                    Runtime.getRuntime().exec("cmd /c start chrome");
                }
            }
            else if (output.contains("Excel") || output.contains("spreadsheet")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening Microsoft excel, sir.");
                     Runtime.getRuntime().exec("cmd /c start excel");
                 }
            }
            else if (output.contains("Word")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening Microsoft word, sir.");
                     Runtime.getRuntime().exec("cmd /c start winword");
                 }
            }
            else if (output.contains("PowerPoint") || output.contains("Microsoft PowerPoint")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening Microsoft powerpoint, sir.");
                     Runtime.getRuntime().exec("cmd /c start powerpnt");
                 }
            }
            else if (output.contains("WordPad")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening wordpad, sir.");
                     Runtime.getRuntime().exec("cmd /c start wordpad");
                 }
            }
             else if (output.contains("open CD") || output.contains("eject CD")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening CD ROM, sir.");
                     Runtime.getRuntime().exec("cmd /c powershell (New-Object -com \"WMPlayer.OCX.7\").cdromcollection.item(0).eject()");
                 }
             }
             else if (output.contains("sleep")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("i am also going to sleep then, sir");
                     Runtime.getRuntime().exec("rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                 }
             }
            //opening complex apps/folders/files using their path
            else if (output.contains("Spider-Man Homecoming") || output.contains("Spiderman Homecoming")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("opening Spiderman Homecoming, sir");
                     Desktop.getDesktop().open(new File("d:\\Movie\\SpiderMan - Homecoming - 2017 [Dual Audio].mkv"));
                 }
            }
            else if (output.contains("program") || output.contains("code")) {
                if (!tts.isSpeaking() && verification.equals("assistant")) {
                    Desktop.getDesktop().open(new File("C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2020.1.1\\bin\\idea64.exe"));
                    speak("opening IntelliJ IDEA, sir");
                }
            }
             else if (output.contains("my folder")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     Desktop.getDesktop().open(new File("C:\\Users\\MINAKSHI\\Desktop\\Nitin"));
                     speak("opening Nitin's folder, sir");
                 }
             }
            //songs
             else if (output.contains("song list") || output.contains("list of songs")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     Desktop.getDesktop().open(new File("C:\\Users\\MINAKSHI\\Desktop\\Nitin\\SongNames.txt"));
                     speak("opening song list, sir");
                 }
             }
             else if (output.contains("play") && output.contains("song")) {
                 output = StringUtils.substringAfter(output, "play ");
                 output = output.replaceAll(" song", "");
                 output = output.toLowerCase();
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     Desktop.getDesktop().open(new File("C:\\Users\\MINAKSHI\\Desktop\\Nitin\\Songs\\" + output + ".mp3"));
                     speak("playing, sir");
                 }
             }
             //using shortcut keys
             else if (output.contains("close") && output.contains("window") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_ALT);
                     robot.keyPress(KeyEvent.VK_F4);

                     robot.keyRelease(KeyEvent.VK_ALT);
                     robot.keyRelease(KeyEvent.VK_F4);
                     speak("window has been closed, sir");
                 }
             }
             else if (output.contains("close") && output.contains("tab") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_F4);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_F4);
                     speak("tab has been closed, sir");
                 }
             }
             else if (output.contains("multitask") && output.contains("multitasking") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_LEFT);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_LEFT);
                     speak("Now you are in  multitasking mode!");
                 }
             }
             else if (output.contains("switch") && output.contains("window") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_ALT);
                     robot.keyPress(KeyEvent.VK_TAB);

                     robot.keyRelease(KeyEvent.VK_ALT);
                     robot.keyRelease(KeyEvent.VK_TAB);
                 }
             }
             else if (output.contains("help")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_F1);

                     robot.keyRelease(KeyEvent.VK_F1);
                 }
             }
             else if (output.contains("rename")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_F2);

                     robot.keyRelease(KeyEvent.VK_F2);
                 }
             }
             else if (output.contains("refresh")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_F5);

                     robot.keyRelease(KeyEvent.VK_F5);
                 }
             }
             else if (output.contains("new window") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_N);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_N);
                 }
             }
             else if (output.contains("select all")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_A);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_A);
                 }
             }
             else if (output.contains("find")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_F);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_F);
                 }
             }
             else if (output.contains("save this file") || output.contains("save this page")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_S);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_S);
                 }
             }
             else if (output.contains("cut")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_X);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_X);
                 }
             }
             else if (output.contains("copy")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_C);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_C);
                 }
             }
             else if (output.contains("paste")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_V);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_V);
                 }
             }
             else if (output.contains("delete")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_DELETE);

                     robot.keyRelease(KeyEvent.VK_DELETE);
                 }
             }
             else if (output.contains("permanently delete")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_SHIFT);
                     robot.keyPress(KeyEvent.VK_DELETE);

                     robot.keyRelease(KeyEvent.VK_SHIFT);
                     robot.keyRelease(KeyEvent.VK_DELETE);
                 }
             }
             else if (output.contains("undo")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_Z);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_Z);
                 }
             }
             else if (output.contains("redo")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_Y);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_Y);
                 }
             }
             else if (output.contains("print") && output.contains("document") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_P);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_P);
                 }
             }
             else if (output.contains("open") && output.contains("properties") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_ALT);
                     robot.keyPress(KeyEvent.VK_ENTER);

                     robot.keyRelease(KeyEvent.VK_ALT);
                     robot.keyRelease(KeyEvent.VK_ENTER);
                 }
             }
             else if (output.contains("start") && output.contains("menu") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                 }
             }
             else if (output.contains("open") && output.contains("settings") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_I);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_I);
                 }
             }
             else if (output.contains("lock") && output.contains("computer") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_L);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_L);
                 }
             }
             else if (output.contains("minimise all Windows")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_M);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_M);
                 }
             }
             else if (output.contains("tasks")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_TAB);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_TAB);
                 }
             }
             else if (output.contains("screenshot")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_PRINTSCREEN);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
                 }
             }
             else if (output.contains("address of this file") || output.contains("address of this folder")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_F4);

                     robot.keyRelease(KeyEvent.VK_F4);
                 }
             }
             else if (output.contains("maximise") && output.contains("window")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_UP);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_UP);
                 }
             }
             else if (output.contains("minimise")&& output.contains("window")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_DOWN);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_DOWN);
                 }
             }
             else if (output.contains("new tab") ) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_T);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_T);
                 }
             }
             else if (output.contains("reopen")&& output.contains("tab")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_SHIFT);
                     robot.keyPress(KeyEvent.VK_T);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_SHIFT);
                     robot.keyRelease(KeyEvent.VK_T);
                 }
             }
             else if (output.contains("previous")&& output.contains("tab")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_SHIFT);
                     robot.keyPress(KeyEvent.VK_TAB);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_SHIFT);
                     robot.keyRelease(KeyEvent.VK_TAB);
                 }
             }
             else if (output.contains("next")&& output.contains("tab")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_TAB);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_TAB);
                 }
             }
             else if (output.contains("right")&& output.contains("click")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_SHIFT);
                     robot.keyPress(KeyEvent.VK_F10);

                     robot.keyRelease(KeyEvent.VK_SHIFT);
                     robot.keyRelease(KeyEvent.VK_F10);
                 }
             }
             else if (output.contains("open")&& output.contains("magnifier")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_PLUS);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_PLUS);
                 }
             }
             else if (output.contains("close")&& output.contains("magnifier")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_ESCAPE);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_ESCAPE);
                 }
             }
             else if (output.contains("zoom")&& output.contains("in")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_PLUS);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_PLUS);
                 }
             }
             else if (output.contains("zoom")&& output.contains("out")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_MINUS);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_MINUS);
                 }
             }
             else if (output.contains("open history") || output.contains("show history")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_H);

                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyRelease(KeyEvent.VK_H);
                 }
             }
             else if (output.contains("show specifications") || output.contains("open specifications")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     robot.keyPress(KeyEvent.VK_WINDOWS);
                     robot.keyPress(KeyEvent.VK_PAUSE);

                     robot.keyRelease(KeyEvent.VK_WINDOWS);
                     robot.keyRelease(KeyEvent.VK_PAUSE);
                 }
             }
             else if (output.contains("print")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("done");
                     output = output.replaceAll("print", "");
                     String text = output;
                     StringSelection stringSelection = new StringSelection(text);
                     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     clipboard.setContents(stringSelection, stringSelection);

                     Robot robot = new Robot();
                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_CONTROL);
                 }
             }

             //opening folders
             else if (output.contains("go to") && output.contains("folder")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     output = output.replace("go to ", "").replace(" folder", "");
                     output = "\\" + output;
                     StringSelection stringSelection = new StringSelection(output);
                     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     clipboard.setContents(stringSelection, stringSelection);

                     robot.keyPress(KeyEvent.VK_F4);
                     robot.keyRelease(KeyEvent.VK_F4);

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_ENTER);
                     robot.keyRelease(KeyEvent.VK_ENTER);
                 }
             }
             else if (output.contains("open") && output.contains("folder")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     output = output.replace("open ", "").replace(" folder", "");
                     StringSelection stringSelection = new StringSelection(output);
                     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     clipboard.setContents(stringSelection, stringSelection);

                     robot.keyPress(KeyEvent.VK_F4);
                     robot.keyRelease(KeyEvent.VK_F4);

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_ENTER);
                     robot.keyRelease(KeyEvent.VK_ENTER);
                 }
             }
             else if (output.contains("open") && output.contains("file")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("as you wish!");
                     Robot robot = new Robot();

                     output = output.replace("open ", "").replace(" file", "");
                     output = "\\" + output;
                     StringSelection stringSelection = new StringSelection(output);
                     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     clipboard.setContents(stringSelection, stringSelection);

                     robot.keyPress(KeyEvent.VK_F4);
                     robot.keyRelease(KeyEvent.VK_F4);

                     robot.keyPress(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_V);
                     robot.keyRelease(KeyEvent.VK_CONTROL);
                     robot.keyPress(KeyEvent.VK_ENTER);
                     robot.keyRelease(KeyEvent.VK_ENTER);
                 }
             }


             //getting reports
             else if (output.contains("date")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     CurrentDateTime.run();
                     speak("Today's date is: " + CurrentDateTime.date + CurrentDateTime.month + CurrentDateTime.year);
                 }
             }
             else if (output.contains("time")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     CurrentDateTime.run();
                     speak("time is " + CurrentDateTime.hour + " hours " + CurrentDateTime.minute + " minutes " + CurrentDateTime.sec + " seconds");
                 }
             }
             else if (output.contains("what") && output.contains("day")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     CurrentDateTime.run();
                     speak("its " + CurrentDateTime.day);
                 }
             }
             else if (output.contains("temperature")){
                 if(!tts.isSpeaking() && verification.equals("assistant")){
                     speak("temperature is " + CurrentTemperature.search() + " degree celcius");
                 }
             }
             else if (output.contains("Corona") || output.contains("covid-19")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     CoronaReport.provide();
                     speak("Fetching Covid-19 report.");
                 }
             }
             else if (output.contains(" wind ") || output.contains("wind speed")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(WindReport.search());
                 }
             }else if (output.contains("humidity") || output.contains("humid")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(HumidityReport.search());
                 }
             }else if (output.contains("visibility")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(VisibilityReport.search());
                 }
             }
             else if (output.contains("rain")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(RainReport.search());
                 }
             }
             else if (output.contains("air quality") || output.contains("quality of air")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(AirQualityIndex.search());
                 }
             }
             else if (output.contains("news ") || output.contains("headlines")){
                 if(!tts.isSpeaking() && verification.equals("assistant")) {
                     speak(NewsReport.TimesOfIndia());
                 }
             }


             //Wikipedia Search
             else if (output.contains("about") && output.contains("Wikipedia")){
                 output = StringUtils.substringBetween(output, "about ", "Wikipedia");
                 output = output.replaceAll(" from ", "").replaceAll(" in ", "").replaceAll(" within ", "").replaceAll(" on ", "").replaceAll("inside", "");
                 System.out.println(output);
                 if(!tts.isSpeaking() && verification.equals("assistant"))
                     ReadFromWikipedia.search(output.toLowerCase());

             }
             else if (output.contains("for") && output.contains("Wikipedia")){
                 output = StringUtils.substringBetween(output, "for ", "Wikipedia");
                 output = output.replaceAll(" from ", "").replaceAll(" in ", "").replaceAll(" within ", "").replaceAll(" on ", "").replaceAll("inside", "");
                 System.out.println(output);
                 if(!tts.isSpeaking() && verification.equals("assistant"))
                     ReadFromWikipedia.search(output);

             }
             //settings
             else if (output.contains("brightness")){
                 output = StringUtils.substringAfter(output, "to ");
                 output = output.replaceAll("percent", "");
                 output = output.replaceAll("%", "");
                 System.out.println(output);
                 if(!tts.isSpeaking() && verification.equals("assistant"))
                     BrightnessManager.setBrightness(Integer.parseInt(output));

             }
             //greetings
             else if (output.contains("morning") || output.contains("Afternoon") || output.contains("evening") || output.contains("night") || output.contains("afternoon")){
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     if (hour > 2 && hour < 12)
                         speak("Good morning sir!");

                     else if (hour > 11 && hour < 17)
                         speak("Good afternoon sir!");

                     else if (hour > 16 && hour < 20)
                         speak("Good evening sir!");

                     else if (hour > 19 && hour < 25)
                         speak("Good night sir!");


                     else if (hour >= 0 && hour < 3)
                         speak("Good night sir!");
                 }
             }
             //WhatsApp Automation
             else if (output.contains("chat")&& output.contains("WhatsApp")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Okay sir!");
                     WhatsAppAutomation.run(output);
                     speak("Now you can talk.");
                 }
             }
             
//            else if (output.contains("why do you speak like that")) {
//                 if (!tts.isSpeaking() && verification.equals("assistant"))
//                     speak("My speaking skills were always in your hand");
//
//            }
            else if (output.contains("say hi to")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     output = StringUtils.substringAfter(output, "to ");
                     speak("Hello" + output);
                 }
            }

            else if (output.contains("open") && output.contains("website")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Which website do you want me to open?");
                     verification = "website";
                 }
            }
             else if (output.contains("Google") && output.contains("search")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Please speak your query and then say \"search\"");
                     verification = "Google";
                 }
             }
             else if (output.contains("Wikipedia")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Please speak your query and then say \"search\"");
                     verification = "Wikipedia";
                 }
             }
             else if (output.contains(" activate") || output.contains("on") && output.contains("chatting")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Chatting mode activated. You just have to say \"send\" after completing your sentence.");
                     verification = "Chatting mode on";
                 }
             }
             else if (output.contains("developer mode") && output.contains("on") || output.contains(" activate")) {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     speak("Developer mode activated.");
                     verification = "Developer mode on";
                 }
             }
            else if (output.contains(".com") || output.contains(".in") || output.contains(".net") || output.contains(".co") || output.contains(".uk")) {
                 if (!tts.isSpeaking() && verification.equals("website")) {
                     speak("Okay");
                     try {
                         URI uri = new URI("www." + output );
                         Desktop.getDesktop().browse(uri);
                         System.out.println("Web page opened in browser");
                         speak("Okay sir, opening " + output);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     verification = "assistant";
                 }
            }
            else if (output.contains("search") && verification.equals("Google")) {
                 if (!tts.isSpeaking()) {
                     speak("Okay sir, I am opening the page");
                     output = output.replaceAll(" search", "");
                     output = output.replaceAll(" ", "%20");
                     try {
                         URI uri = new URI("http://www.google.com/search?q=" + output);
                         Desktop.getDesktop().browse(uri);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     verification = "assistant";
                 }
            }
             else if (output.contains("search") && verification.equals("Wikipedia")) {
                 if (!tts.isSpeaking()) {
                     output = output.replaceAll(" search", "");
                     ReadFromWikipedia.search(output);
                     verification = "assistant";
                 }
             }
            else if (output.contains("who is") || output.contains("what is")) {
                if (!tts.isSpeaking() && output.contains("Jarvis")) {
                    output = StringUtils.substringAfter(output, "is ");
                    output = output.replaceAll(" Jarvis", "");
                    speak(Search.search(output));
                }
            }
            else if (output.contains("stop speech recognition")) {//Stop Speech Recognition
                stopSpeechRecognition();

            }
            else if (verification.equals("assistant")) {
                //String output = "tell me about cheetah";
                for (int i = 0; i < JarvisDB.inputList.size(); i++) {
                    String input = JarvisDB.inputList.get(i);
                    if (output.contains(input)) {
                        String out1 = JarvisDB.output1List.get(i);
                        String out2 = JarvisDB.output2List.get(i);
                        String out3 = JarvisDB.output3List.get(i);
                        String[] outList = {out1, out2, out3};
                        String randomOut = outList[random.nextInt(outList.length)];
                        if (!tts.isSpeaking())
                            speak(randomOut);
                    }
                }
            }
            else {
                 if (!tts.isSpeaking() && verification.equals("assistant")) {
                     System.out.println("Trying to understand...");
                 }
            }
        }
    public void makeDecisionForChat(String output) throws AWTException {

        output = output.trim();

        //We don't want duplicate responses
        if (!oldText.equals(output))
            oldText = output;
        else
            return;

        if (output.contains("send") && verification.equals("Chatting mode on")) {
            if (!tts.isSpeaking()) {
                speak("done");
                output = output.replaceAll(" send", "");
                String text = output;
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, stringSelection);

                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
        }
        else if (output.contains("chatting mode") && output.contains("off") || output.contains(" deactivate")) {
            if (!tts.isSpeaking() && verification.equals("Chatting mode on")) {
                speak("Chatting mode deactivated.");
                verification = "assistant";
            }
        }
    }
    public void makeDecisionForDeveloper(String output) throws AWTException, SQLException, ClassNotFoundException {

        output = output.trim();

        //We don't want duplicate responses
        if (!oldText.equals(output))
            oldText = output;
        else
            return;

        if (output.contains("verified") && developerVerification.equals("input")) {
            if (!tts.isSpeaking()) {
                speak("done");
                if (developerInput.contains("'")) {
                    developerInput = developerInput.replace("'", "\\\'");
                    String query = "INSERT INTO `jarvisdb`.`users` (`input`) VALUES ('" + developerInput + "')"; //GIVING INPUT
                    JarvisDB.statement.execute(query);
                }
                else{
                    String query = "INSERT INTO `jarvisdb`.`users` (`input`) VALUES ('" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
            }
        }
        else if (output.contains("verified") && developerVerification.equals("output1")) {
            if (!tts.isSpeaking()) {
                speak("done");
                if (developerOutput.contains("'")) {
                    developerOutput = developerOutput.replace("'", "\\\'");
                    String query = "UPDATE `jarvisdb`.`users` SET `output1` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
                else{
                    String query = "UPDATE `jarvisdb`.`users` SET `output1` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
            }
        }
        else if (output.contains("verified") && developerVerification.equals("output2")) {
            if (!tts.isSpeaking()) {
                speak("done");
                if (developerOutput.contains("'")) {
                    developerOutput = developerOutput.replace("'", "\\\'");
                    String query = "UPDATE `jarvisdb`.`users` SET `output2` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
                else{
                    String query = "UPDATE `jarvisdb`.`users` SET `output2` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
            }
        }
        else if (output.contains("verified") && developerVerification.equals("output3")) {
            if (!tts.isSpeaking()) {
                speak("done");
                if (developerOutput.contains("'")) {
                    developerOutput = developerOutput.replace("'", "\\\'");
                    String query = "UPDATE `jarvisdb`.`users` SET `output3` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
                else{
                    String query = "UPDATE `jarvisdb`.`users` SET `output3` = '" + developerOutput + "' WHERE (`input` = '" + developerInput + "')"; //GIVING INPUT
                    System.out.println(query);
                    JarvisDB.statement.execute(query);
                }
            }
        }
        else if (output.contains("input")) {
            if (!tts.isSpeaking()) {
                speak("okay");
                developerInput = output.replaceAll(" is the input", "").replaceAll(" is the first input", "");
                developerVerification = "input";
            }
        }
        else if (output.contains("first output")) {
            if (!tts.isSpeaking()) {
                speak("okay");
                developerOutput = output.replaceAll(" is the first output", "");
                developerVerification = "output1";
            }
        }
        else if (output.contains("second output")) {
            if (!tts.isSpeaking()) {
                speak("okay");
                developerOutput = output.replaceAll(" is the second output", "");
                developerVerification = "output2";
            }
        }
        else if (output.contains("third output")) {
            if (!tts.isSpeaking()) {
                speak("okay");
                developerOutput = output.replaceAll(" is the third output", "");
                developerVerification = "output3";
            }
        }
        else if (output.contains("developer mode") && output.contains("off") || output.contains(" deactivate")) {
            if (!tts.isSpeaking() && verification.equals("Developer mode on")) {
                JarvisDB.statement.close();
                speak("Database updated successfully. I am gonna remember everything you told me.");

                JarvisDB.createConnection();
                verification = "assistant";
                speak("Developer mode deactivated.");
            }
        }
    }


    /**
     * Calls the MaryTTS to say the given text
     *
     * @param text
     */
    public void speak(String text) {
        System.out.println("Jarvis: " + text);
        //Check if it is already speaking
        if (!tts.isSpeaking())
            new Thread(() -> tts.speak(text, 2.0f, true, true)).start();

    }


    /**
     * Starts the Speech Recognition
     */
    public void startSpeechRecognition() {
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

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new Application();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new JarvisGUI();
            JarvisGUI.jarvisFrame();
        }).start();
    }
}

class JarvisGUI {
    public static JFrame imageFrame;
    public static JPanel imagePanel;

    public static void jarvisFrame() {
        imageFrame = new JFrame("Beginning");
        imageFrame.setSize(510, 530);

        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imagePanel = new JPanel();
        imageFrame.add(imagePanel);

        placeComponents(imagePanel);
        imageFrame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\MINAKSHI\\IdeaProjects\\Beginning\\src\\com\\beginning\\GIF2.gif");
        JLabel image = new JLabel(imageIcon);
        imageFrame.add(image);
    }
}