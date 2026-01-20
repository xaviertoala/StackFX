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
    private final Stack stack = new Stack();

    @FXML
    private void initialize() {
        canvas = new StackCanvas();
        canvasHolder.getChildren().add(canvas);
        refreshView();
    }

    // ================================
    // PUSH
    // ================================
    @FXML
    private void push() {
        Integer v = readInt();
        if (v == null) return;

        stack.push(v);
        canvas.clearHighlight();
        refreshView();
    }

    // ================================
    // POP
    // ================================
    @FXML
    private void pop() {
        try {
            stack.pop();
            canvas.clearHighlight();
            refreshView();
        } catch (NoSuchElementException e) {
            // opcional: mostrar mensaje
        }
    }

    // ================================
    // PEEK
    // ================================
    @FXML
    private void peek() {
        try {
            int valor = stack.peek();
            int idx = 0; // siempre el top
            canvas.setHighlightIndex(idx);
            refreshView();
        } catch (NoSuchElementException e) {
        }
    }

    // ================================
    // IS EMPTY
    // ================================
    @FXML
    private void isEmpty() {
        boolean vacia = stack.isEmpty();
        canvas.clearHighlight();
        refreshView();
    }

    // ================================
    // UTILIDADES
    // ================================
    private Integer readInt() {
        try {
            String s = txtValue.getText();
            if (s == null || s.trim().isEmpty()) return null;
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void refreshView() {
        canvas.setValues(stack.toList());
        canvas.render();
    }
    
}
