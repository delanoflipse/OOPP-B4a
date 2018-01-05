package main;

import GUI.StartMenu;
import user.UserData;

public class Main {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(StartMenu.class);
            }
        }.start();

//        UserData data = UserData.parse("user.userdata");
//        data.save();
    }
}
