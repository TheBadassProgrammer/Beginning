package com.beginning;

import java.sql.*;
import java.util.ArrayList;


public class JarvisDB {
    public static ResultSet resultSet;
    public static Statement statement;
    public static Connection connection;
    public static String inputDB;
    public static String output1DB;
    public static String output2DB;
    public static String output3DB;
    public static ArrayList<String> inputList;
    public static ArrayList<String> output1List;
    public static ArrayList<String> output2List;
    public static ArrayList<String> output3List;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        createConnection();

    }
    public static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jarvisdb", "root", "nitin001");
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM jarvisdb.USERS");//GETTING OUTPUT

        inputList = new ArrayList<String>();
        output1List = new ArrayList<String>();
        output2List = new ArrayList<String>();
        output3List = new ArrayList<String>();
        while (resultSet.next()) {
            inputDB = resultSet.getString("input");
            inputList.add(inputDB);

            output1DB = resultSet.getString("output1");
            output1List.add(output1DB);

            output2DB = resultSet.getString("output2");
            output2List.add(output2DB);

            output3DB = resultSet.getString("output3");
            output3List.add(output3DB);
        }
    }
}
