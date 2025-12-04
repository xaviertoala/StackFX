module ec.edu.espoch.stackkfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ec.edu.espoch.stackkfx.controller to javafx.fxml;
    exports ec.edu.espoch.stackkfx;
   
    exports ec.edu.espoch.stackkfx.controller;
    exports ec.edu.espoch.stackkfx.model;
    exports ec.edu.espoch.stackkfx.view;
    
    
    
    
    
    
}
