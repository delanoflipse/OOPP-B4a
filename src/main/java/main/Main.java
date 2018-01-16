package main;

import GUI.*;

public class Main {

    public static void main(String[] args) {
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


//        UserData data = UserData.parse("user.userdata");
//        data.name = "asdfs";
//        System.out.println(data.getPreference("useTTS"));
//        data.save();
    }
}
