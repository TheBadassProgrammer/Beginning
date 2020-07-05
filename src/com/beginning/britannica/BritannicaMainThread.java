package com.beginning.britannica;

import javax.swing.*;

import static com.beginning.GUI.MainGUI.*;

public class BritannicaMainThread extends Thread{
    public static JLabel queryAns;
    public static String query;
    @Override
    public void run(){
        query = queryText.getText();

        if (!query.equals("quit")) {
            BritannicaQueryAnsThread t4 = new BritannicaQueryAnsThread();
            t4.start();
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queryAns = new JLabel("<html>" + ReadFromBritannica.result + "</html>");
            queryAns.setBounds(10, 180, 820, 60);
            mainPanel.add(queryAns);
            mainFrame.pack();
            mainFrame.setSize(850, 300);
            mainFrame.setVisible(true);


        } else
            mainFrame.dispose();

    }
}
