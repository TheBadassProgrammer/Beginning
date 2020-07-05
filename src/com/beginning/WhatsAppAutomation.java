package com.beginning;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WhatsAppAutomation {
    public static void run(String output) throws AWTException, URISyntaxException, IOException, InterruptedException {
        Robot robot = new Robot();
        URI uri = new URI("https://web.whatsapp.com/");

        Desktop.getDesktop().browse(uri);
        Thread.sleep(10000);
        robot.mouseMove(125, 155);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        output = StringUtils.substringAfter(output, " with ");
        output = output.replaceAll(" on WhatsApp", "");

        String text = output;
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }
}
