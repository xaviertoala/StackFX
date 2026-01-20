
package ec.edu.espoch.stackkfx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class Stack {

    private NodoStack top;
    private int size;

    // ================================
    // PUSH
    // ================================
    public void push(int dato) {
        NodoStack nuevo = new NodoStack(dato);
        nuevo.next = top;
        top = nuevo;
        size++;
    }

    // ================================
    // POP
    // ================================
    public int pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        int valor = top.dato;
        top = top.next;
        size--;
        return valor;
    }

    // ================================
    // PEEK
    // ================================
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }
        return top.dato;
    }

    // ================================
    // IS EMPTY
    // ================================
    public boolean isEmpty() {
        return top == null;
    }

    // ================================
    // PARA EL CANVAS
    // ================================
    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        NodoStack aux = top;

        while (aux != null) {
            list.add(aux.dato);
            aux = aux.next;
        }
        return list;
    }

    public int size() {
        return size;
    }
    
    
    
}
