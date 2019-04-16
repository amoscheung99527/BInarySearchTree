/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

/**
 *
 * @author Amos
 */
public class Treestack{
    private int n;
    private Node first;
    
    private class Node{
        private TNode item;
        private Node next;
    }
    
    public Treestack(){
        first = null;
        n = 0;
    }

    public boolean isEmpty(){
        return first == null;
    }


    public int size(){
        return n;
    }

    public void push(TNode item){
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public TNode pop(){
        if (isEmpty()) return null;
        TNode item = first.item;        
        first = first.next;            
        n--;
        return item;                   
    }

    public TNode peek(){
        if (isEmpty()) return null;
        return first.item;
    }

}
