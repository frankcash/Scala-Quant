package Tree;

/**
 * Created by Frank Cash on 4/14/2017.
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BST <Key extends Comparable<Key>, Value> {
    private Node root; // root of BST
    private class Node{
        private Key key; // sorts by key
        private Value val; // associated data
        private Node left, right; // subtrees
        private int size; // number of nodes in subtree
        public Node(Key key, Value val, int size){
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table
     */
    public BST(){
    }

    /**
     *
     * @return Returns the number of key-value pairs in this symbole table
     */
    public int size(){
        return size(root);
    }

    /**
     *
     * @param x
     * @return Returns number of key-value pairs in BST rooted at x
     */
    private int size(Node x){
        if (x == null) return 0;
        else return x.size;
    }

    /**
     *
     * @return Returns true if this symbol table is empty
     */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     *  Does the symbol table contain the given key?
     *
     * @param key the key
     * @return True if this symbol table contains key and false otherwise
     */
    public boolean contains(Key key){
        return get(key) != null;
    }

    /**
     *
     * @param key
     * @return the value associate w/ given key if the key is in the symbol table
     * @throws IllegalArgumentException if the key is {@code null}
     */
    public Value get(Key key){
        return get(root, key);
    }

    private Value get(Node x, Key key){
        if (key == null) throw new IllegalArgumentException("called get() w/ a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key); // since smaller values are sorted to the left
        else if (cmp > 0) return get(x.right, key); // since greater values are sorted to the right
        else return x.val;
    }

    /**
     * Inserts the specified key-value pair into the symbol table
     *
     * @param key the key
     * @param val the value, deletes if {@code key} is {@code null}
     * @throws IllegalArgumentException if the key is {@code null}
     */
    public void put(Key key, Value val){
        if (key == null) throw new IllegalArgumentException("called put() w/ a null key");
        if (val == null){
            delete(key);
            return;
        }

        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val){
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val); // smaller values are sorted to the left
        if (cmp > 0) x.right = put(x.right, key, val); // greater values are sorted to the right
        else x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key){
        if (key == null) throw new IllegalArgumentException("called delete() w/ null");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key){
        if( x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else{
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min(){
        if (isEmpty()) throw new NoSuchElementException("Called min()  w/ empty symbole table");
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Removes the smallest key and associated value from the symbol table
     */
    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x){
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x .size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Returns the height of the BST
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height(){
        return (height(root) + 1);
    }

    private int height(Node x){
        if( x == null ) return -1;
        return (1 + Math.max(height(x.left), height(x.right)));
    }


    private boolean check(){
        if(!isBST()) System.out.println("Not in Symmetric order");
        return isBST();
    }

    private boolean isBST(){
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Key min, Key max){
        if (x == null) return true;
        if( min != null && x.key.compareTo(min) <= 0 ) return false;
        if( max != null && x.key.compareTo(max) >= 0 ) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    public static void main(String args[]){
        BST<String, Integer> st = new BST<String, Integer>();

        int[] values = {2, 1, 3, 4};
        String[] keys = {"b", "a", "c", "d"};
        for(int i = 0; i < values.length; i++){
            st.put(keys[i], values[i]);
        }

        ArrayList<Integer> foo = new ArrayList<Integer>(20);
        foo.add(2);
        System.out.println("size is: " + st.size());
        System.out.println("Height is: " + st.height());

    }
}
