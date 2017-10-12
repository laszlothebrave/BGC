package Database;

import Database.UserExceptions.IncorrectEmailAdressException;
import Database.UserExceptions.IncorrectLoginDataException;

import java.sql.*;
import java.util.Scanner;

public class Source {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        MysqlConnector mysql = new MysqlConnector("37.233.99.37", "3306", "board_games_console", "root", "root");
        while (true) {
            String[] command = in.nextLine().split(" ");
            if (command[0].equals("createUser")) {
                String name = command[1];
                String password = command[2];
                String email = command[3];
                try {
                    mysql.addUser(name, password, email);
                } catch (SQLException | IncorrectEmailAdressException e) {
                    e.printStackTrace();
                    System.out.println("email incorrect");
                }
            } else if (command[0].equals("login")) {
                try {
                    mysql.login(command[1], command[2]);
                    System.out.println("corrent");
                } catch (IncorrectLoginDataException e) {
                    System.out.println("incorrect");
                }
            }
        }
    }
}
