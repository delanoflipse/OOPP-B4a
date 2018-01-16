package GUI;

import database.Database;

public class UIState {
    Database database;
    String username = "";
    int userscore = 0;

    UIContext context = new UIContext();

    public void setContext(UIContext context) {
        this.context = context;
    }

}
