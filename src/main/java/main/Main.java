package main;

import GUI.*;
import database.Database;
import user.UserData;

public class Main {

    public static void main(String[] args) {
        // Load the database
        Database.loadDatabase();

        if (args.length > 0 && args[0].equals("-admin")) {
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(Admin.class);
                }
            }.start();
        } else {
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(UI.class);
                }
            }.start();
        }
    }
}
