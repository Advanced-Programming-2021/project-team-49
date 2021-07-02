module yugioh {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires opencsv;

    opens view to javafx.fxml;
    opens main to javafx.fxml;
    opens model.user to com.google.gson;
    opens model.database to com.google.gson;
    opens model.cardtemplate to com.google.gson;

    exports main;
    exports view;
    exports controller;
    exports controller.effects;
    exports exception;
    exports model.user;
    exports model.database;
    exports model.cardtemplate;
    exports model.game;
    exports model.game.card;
}