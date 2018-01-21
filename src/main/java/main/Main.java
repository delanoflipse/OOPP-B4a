package main;

import GUI.*;
import database.Database;
import user.UserData;

public class Main {

    public static void main(String[] args) {
        // Load the database
        Database.loadDatabase();
        new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(UI.class);
                }
            }.start();
    }
}
