/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 3.0 1/11/15 16:49:42
 *
 *  @author Daniel Cosgrove
 *
 *************************************************************************/

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    /**
     * Private node class.
     */
    private class Node {
        private Key key;           // sorted by key (comparable: compare equal keys get 0, each node has a unique key)
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree (size value)

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return size() == 0; }

    // return number of key-value pairs in BST
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    /**
     *  Search BST for given key.
     *  Does there exist a key-value pair with given key?
     *
     *  @param key the search key
     *  @return true if key is found and false otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     *  Search BST for given key.
     *  What is the value associated with given key?
     *
     *  @param key the search key
     *  @return value associated with the given key if found, or null if no such key exists.
     */
    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     *  Insert key-value pair into BST.
     *  If key already exists, update with new value.
     *
     *  @param key the key to insert
     *  @param val the value associated with key
     */
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Tree height.
     *
     * Asymptotic worst-case running time using Theta notation: TODO
     *
     * @return the number of links from the root to the deepest leaf.
     *
     * Example 1: for an empty tree this should return -1.
     * Example 2: for a tree with only one node it should return 0.
     * Example 3: for the following tree it should return 2.
     *   B
     *  / \
     * A   C
     *      \
     *       D
     */
    public int height() {
      //TODO fill in the correct implementation.
    	int count = -1;
    	if(isEmpty()){return count;}
    	return height(root, count);
    	//return count;
    }
    
    private boolean isEnd(Node x){
    	if(x.left == null & x.right == null){
        	return true;
        	}
    	return false;
    }
    
    private Node getLargestChildTree(Node x){
    	if(x.right == null){
    		return x.left;
    	}
    	else if(x.left == null){
    		return x.right;
    	}
    	if(size(x.left) > size(x.right)){
    		return x.left;
    	}
    	return x.right;
    }
    
    private int height(Node x, int count){
    	if(isEnd(x)){
    		//System.out.println("found end: node "+x.key.toString()+"\n");
    		count++;
    		//System.out.println("returning "+count + "\n");
    		return count;
    	}
    	else{
    		count = height(getLargestChildTree(x), count);
    		count ++;
    		//System.out.println("returning "+count + "\n");
    		return count;
    	}
    }

    /**
     * Median key.
     * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key 
     * is the element at position (N+1)/2 (where "/" here is integer division)
     *
     * @return the median key, or null if the tree is empty.
     */
    public Key select(int n){
    	if(n<0 | n>= size())return null;
    	Node x = select(root,n);
    	return x.key;
    }
    //select the node of rank n, in sub-tree rooted at x
    private Node select(Node x , int n){
    	if(x==null) return null;
    	int t = size(x.left);
    	if(t>n) return select(x.left, n);
    	else if(t<n) return select(x.right, n-t-1);
    	else return x;
    }
    
    
    public Key median() {
      if (isEmpty()) return null;
      //TODO fill in the correct implementation. The running time should be Theta(h), where h is the height of the tree.
      int median = (((size(root) +1)/2) -1);
      return select(median);
    }


    /**
     * Print all keys of the tree in a sequence, in-order.
     * That is, for each node, the keys in the left subtree should appear before the key in the node.
     * Also, for each node, the keys in the right subtree should appear before the key in the node.
     * For each subtree, its keys should appear within a parenthesis.
     *
     * Example 1: Empty tree -- output: "()"
     * Example 2: Tree containing only "A" -- output: "(()A())"
     * Example 3: Tree:
     *   B
     *  / \
     * A   C
     *      \
     *       D
     *
     * output: "((()A())B(()C(()D())))"
     *
     * output of example in the assignment: (((()A(()C()))E((()H(()M()))R()))S(()X()))
     *
     * @return a String with all keys in the tree, in order, parenthesized.
     */
    public String printKeysInOrder() {
      if (isEmpty()) return "()";
      // TODO fill in the correct implementation
      return "("+printKeysInOrder(root)+")";
    }
    
    private String printKeysInOrder(Node x){
    	if(x == null) return "";
    	return '(' + printKeysInOrder(x.left) + ")" + x.key.toString() + "(" + printKeysInOrder(x.right) + ")";
    }
    
    /**
     * Pretty Printing the tree. Each node is on one line -- see assignment for details.
     *
     * @return a multi-line string with the pretty ascii picture of the tree.
     */
    public String prettyPrintKeys() {
      //TODO fill in the correct implementation.
    	if(isEmpty())return "-null\n";
    	return prettyPrintKeys(root, "");
    }
    
    private String prettyPrintKeys(Node x, String prefix){
    	if(x == null)return prefix + "-null\n";
    	else return prefix + "-" + x.key.toString() + "\n" + prettyPrintKeys(x.left, prefix + " |")  + prettyPrintKeys(x.right, prefix + "  ");
    }

    /**
     * Deletes a key from a tree (if the key is in the tree).
     * Note that this method works symmetrically from the Hibbard deletion:
     * If the node to be deleted has two child nodes, then it needs to be
     * replaced with its predecessor (not its successor) node.
     *
     * @param key the key to delete
     */
    
    
    private Node max(Node x){
    	if(x.right == null)return x;
    	x = max(x.right);
    	return x;
    }
    
	private void deleteMax(){
		root = deleteMax(root);
	}
	
	private Node deleteMax(Node x){
		if(x.right == null)return x.left; //hit Max
		x.right = deleteMax(x.right); //if x.right is max, replace with max.left, else return x.right;
		x.N = size(x.left) + size(x.right) + 1; //subtree size = invariant expression
		return x;
	}
    
    public void delete(Key key) {
      //TODO fill in the correct implementation.
    	//begin path through tree
    	root = delete(root, key);
    }
    
    private Node delete(Node x, Key key){
    	if(x == null)return null;
    	int cmp = key.compareTo(x.key);
    	if(cmp<0)x.left = delete(x.left, key);
    	else if(cmp>0)x.right = delete(x.right, key);
    	else{
    		if(x.right == null)return x.left;	//1 child or 0 children
    		if(x.left == null)return x.right;	//1 child or 0 children
    		
    		Node t = x;
    		x = max(t.left);	//get predecessor (max node in left subtree: largest key less than x)
    		x.left = deleteMax(t.left); //manipulate subtree with root t.left, to delete max, and return the (new) root of the subtree
    		x.right = t.right;	//predecessor replaces t, so inherits its right child (and left child, above)
    	}
    	x.N = size(x.left) + size(x.right) + 1;
    	return x;	//parent which called delete changes one of it's child links to x
    }
}
