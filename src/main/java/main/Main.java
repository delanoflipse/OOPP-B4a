package main;

import GUI.StartMenu;

public class Main {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(StartMenu.class);
            }
        }.start();
//        askMultipleChoichequestions();
    }
}
