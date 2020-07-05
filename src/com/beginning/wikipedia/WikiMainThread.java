package com.beginning.wikipedia;

import javax.swing.*;

import static com.beginning.GUI.MainGUI.*;

public class WikiMainThread extends Thread{
    public static JLabel wikiQueryAns;
    public static String query;
    @Override
    public void run(){
        query = queryText.getText();

        if (!query.equals("quit")) {
            WikiQueryAnsThread t6 = new WikiQueryAnsThread();
            t6.start();
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wikiQueryAns = new JLabel("<html>" +(ReadFromWikipedia.result)+"</html>");
            wikiQueryAns.setBounds(10, 180, 820, 60);
            mainPanel.add(wikiQueryAns);
            mainFrame.pack();
            mainFrame.setSize(850, 300);
            mainFrame.setVisible(true);


        } else
            mainFrame.dispose();

    }
}
