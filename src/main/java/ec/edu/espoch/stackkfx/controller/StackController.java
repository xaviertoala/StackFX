/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espoch.stackkfx.controller;

import ec.edu.espoch.stackkfx.model.Stack;
import ec.edu.espoch.stackkfx.view.StackCanvas;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;


public class StackController {

    @FXML
    private TextField txtValue;
    @FXML
    private StackPane canvasHolder;
    private StackCanvas canvas; 
    
    private final Stack stack = new Stack ();
    
    @FXML
    private void initialize() {
        canvas = new StackCanvas();
        canvasHolder.getChildren().add(canvas);

        canvas.clearHighlight();
        refreshView();
    }

    @FXML
    private void push() {
        System.out.println("Hola Pedro");
        Integer v = readInt();
        if (v == null) {
            //lblStatus.setText("Entrada inválida.");
            return;
        }

        stack.push(v);
        canvas.clearHighlight();
        canvas.render();
        //lblStatus.setText("Insertado al inicio: " + v);
        txtValue.selectAll();
        txtValue.requestFocus();
        refreshView();
    }

    @FXML
    private void pop() {
        try {
            int removed = stack.pop();              // 1) operación del modelo
            //lblStatus.setText("Pop → " + removed);  // 2) feedback al usuario
            refreshView();                          // 3) actualizar la vista
        } catch (NoSuchElementException ex) {
            //lblStatus.setText(ex.getMessage());
        }
    }
    
    private Integer readInt() {
        try {
            String s = (txtValue.getText() == null) ? "" : txtValue.getText().trim();
            if (s.isEmpty()) {
                return null;
            }
            return Integer.valueOf(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void refreshView() {
        canvas.setValues(stack.toList());
        canvas.render();
    }
    
}
