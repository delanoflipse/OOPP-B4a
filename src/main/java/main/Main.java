package main;

import GUI.UI;

public class Main {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(UI.class);
            }
        }.start();

//        UserData data = UserData.parse("user.userdata");
//        data.name = "asdfs";
//        System.out.println(data.getPreference("useTTS"));
//        data.save();
    }
}
