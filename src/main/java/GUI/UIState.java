package GUI;

import database.Database;
import user.UserData;

public class UIState {
    Database database;
    public UserData user = null;

    UIContext context = new UIContext();

    public void setContext(UIContext context) {
        this.context = context;
    }

}
