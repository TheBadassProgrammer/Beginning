package com.beginning;

import java.util.ArrayList;

import static com.beginning.Application.verification;

public class Tests {
    public static void main(String[] args) {
        ArrayList<String> inputList;
        inputList = new ArrayList<String>();
        inputList.add("lion");
        inputList.add("tiger");
        inputList.add("cheetah");

        ArrayList<String> outputList;
        outputList = new ArrayList<String>();
        outputList.add("lion is king");
        outputList.add("tiger is strong");
        outputList.add("cheetah is fast");

        if (verification.equals("assistant")) {
            String output = "tell me about cheetah";
            for (int i = 0; i < 3; i++) {
                String input = inputList.get(i);
                if (output.contains(input)) {
                    System.out.println(outputList.get(i));
                }
            }
        }
    }
}
