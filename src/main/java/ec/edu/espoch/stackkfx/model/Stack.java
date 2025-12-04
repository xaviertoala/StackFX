
package ec.edu.espoch.stackkfx.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class Stack {
    
    private Nodo top;
    private int size;
    
    public int size (){
        return size;
    }
    
    public boolean isEmpty (){
        return top == null;
    }
    
    public void push (int value){
        Nodo x = new Nodo (value);
        x.next = top;
        top = x;
        size++;
                
    }
    public int pop (){
        if (isEmpty()){
            throw new NoSuchElementException ("La pila esta vacia");
        }
        int v = top.value;
        top = top.next;
        size--;
        return v; 
    }
    
    public ArrayList<Integer> toList (){
        ArrayList<Integer> out = new ArrayList <>(size);
        Nodo cur = top;
        while  (cur != null){
            out.add(cur.value);
            cur = cur.next;
        }
        return out; 
    }
    
    
    
}
