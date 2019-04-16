/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;
import lab3.Treestack;
import java.util.*;

/**
 *
 * @author Amos
 */
public class BSTSet {
    private TNode root;
    
    public int size(){
        return size(root);
    }
    
    public int size(TNode n){
        if (n == null) 
            return 0; 
        else
            return(size(n.left) + 1 + size(n.right)); 
    }
    
    public int height(){
        return height(root);
    }
    
    public int height(TNode n)  
    { 
        if (n == null) 
            return -1; 
        else 
        { 
            int lheight = height(n.left); 
            int rheight = height(n.right); 

            if (lheight > rheight) 
                return (lheight + 1); 
             else 
                return (rheight + 1); 
        } 
    }
    
    public BSTSet(){
        root=null;
    }
    
    public BSTSet(int[] input){
        int size =input.length;
        for (int i=0; i<size;i++){
            for (int k=i+1; k<size; k++){
                if(input[i]==input[k]){
                    input[k]=input[k+1];
                    size--;
                    k--;
                    
                }
            }
        }
        Sort(input);
        Build(input,0,input.length-1);
    }   
     
    public void Sort(int[] array)
    {
        for (int i=0;i<array.length-1;i++)
        {
            int swab = i;
            for (int j = i+1; j < array.length;j++){
                if (array[swab] > array[j]){
                    swab = j;
                }
            }    
            int min = array[swab];
            array[swab] = array[i];
            array[i] = min;
        }
    }
        
    public void Build(int[] array,int low, int high)
    {
        if (low <= high){
            int mid = (low + high + 1)/2;
            this.add(array[mid]);
            Build(array,low,mid-1);
            Build(array,mid+1,high);
        }
    }
    
    public boolean isIn(int v){
        if(root==null){
            return false;
        }
        TNode n=root;
        while(n!=null){
            if(v==n.element){
                return true;
            }
            else if (v<n.element){
                n=n.left;
            }
            else{
                n=n.right;
            }
        }
        return false;
    }  
    
    public void add(int v)
    {
        if (root==null){
            root = new TNode(v,null,null);
        }
        else{
            TNode n = root;
            while (n!=null)
            {
                if(v==n.element){
                    return;
                }
                else if (v > n.element){
                    if (n.right==null)
                    {
                        n.right = new TNode(v,null,null);
                        return;
                    }
                    else{
                        n = n.right;
                    }
                }
                  
                else{
                    if (n.left==null){
                        n.left = new TNode(v,null,null);
                        return;
                    }
                    else{
                        n = n.left;
                    }
                }
            }
        }
    }
    
    public boolean remove(int v){
        if (isIn(v)==true){
            delNode(v,root);
            return true;
        }
        else{
            return false;
        }
    }
    
    public TNode delNode(int v, TNode n){
        if (v < n.element){
            n.left = delNode(v,n.left);
        }
        else if (v > n.element){
            n.right = delNode(v,n.right);
        }
        else if (n.left!=null && n.right!=null){
            n.element = (findmin(n.right)).element;
            n.right = delmin(n.right);
        }
        else{
            n = (n.left!=null) ? n.left:n.right;
        }
        return n;
    }
    
    public TNode findmin(TNode t){
        TNode minimum = t;
        while (t.right!=null){
            t = t.right;
            minimum = t;
        }
        return minimum;
    }
    
    public TNode delmin(TNode t){
        if (t.right!=null)
        {
            t.right = delmin(t.right);
            return t;
        }
        else
            return t.left;
    }
    
    public BSTSet union(BSTSet s){
        BSTSet union = new BSTSet();
        union.Combine(this.root);
        union.Combine(s.root);
        return union;
    }
    
    public BSTSet Combine(TNode t){
        if(t!=null){
            this.add(t.element);
            this.Combine(t.left);
            this.Combine(t.right);
        }
        return this;
    }
   
    public BSTSet intersection(BSTSet s){
        ArrayList<Integer> intersect = new ArrayList<Integer>();
        Treestack s1 = new Treestack ();  
        Treestack s2 = new Treestack ();
        

        while (true){  
            if (this.root != null){  
                s1.push(this.root);
                this.root = this.root.left;  
            }  

            else if (s.root != null){  
                s2.push(s.root);  
                s.root = s.root.left;  
            }  
            
            else if (!s1.isEmpty() && !s2.isEmpty()){  
                this.root = s1.peek();  
                s.root = s2.peek();  

                if (this.root.element == s.root.element){
                    intersect.add(this.root.element);
                    s1.pop();  
                    s2.pop();  
                    this.root = this.root.right;  
                    s.root = s.root.right;  
                }  

                else if (this.root.element < s.root.element){  
                    s1.pop();  
                    this.root = this.root.right;  
                    s.root = null;  
                }  
                else if (this.root.element > s.root.element){  
                    s2.pop();  
                    s.root = s.root.right;  
                    this.root = null;  
                }  
            }  

            else break;  
        }
        int[] int_intersect = new int[intersect.size()];
        for(int i =0; i<intersect.size();i++){
            int_intersect[i] = intersect.get(i);
        }
        return new BSTSet(int_intersect);
    }
    
    public BSTSet difference(BSTSet s){
        BSTSet diff= new BSTSet();
        diff.Combine(this.root);
        BSTSet sub = this.intersection(s);
        diff.Subtree(sub.root);
        return diff;
    }
    
    public BSTSet Subtree(TNode t){
        if(t!=null){
            this.remove(t.element);
            this.Subtree(t.left);
            this.Subtree(t.right);
        }
        return this;
    }
    
    public void printNonRec(){
        if(root!=null){
            Treestack print = new Treestack();
            TNode t = root;
            
            while(t!=null){
                print.push(t);
                t=t.left;
            }
            
            while(!print.isEmpty()){
                t=print.pop();
                System.out.print(t.element+ ",");
                if(t.right!=null){
                    t=t.right;
                    
                    while(t!=null){
                        print.push(t);
                        t=t.left;
                    }
                }
            }
            System.out.print("\n");
        }
        
        else{
            System.out.print("The set is empty");
            System.out.print("\n");
        }

    }
    
    public void printBSTSet(){
        if(root==null)
            System.out.println("The set is empty");
        else{
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.print("\n");
        }
    }
    private void printBSTSet(TNode t){
        if(t!=null){
            printBSTSet(t.left);
            System.out.print(" " + t.element+",");
            printBSTSet(t.right);
        }
    }
}
