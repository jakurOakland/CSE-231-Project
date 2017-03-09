package edu.oakland;

/**
 * Created by Justin Kur on 3/9/2017.
 */
public class BinarySearchTree {

    private TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(String name, String telephoneNumber, String address) {
        insert(new TreeNode(name, telephoneNumber, address));
    }

    public void insert(TreeNode node) {
        TreeNode nptr = root;

        while(nptr != null) {
            if(node.getName().hashCode() < nptr.getName().hashCode()) { //Go left
                if(nptr.getLeftChild() == null) { //If the node we're going to is empty, insert our node here
                    nptr.setLeftChild(node);
                }
                nptr = nptr.getLeftChild();
            }
            else { //If the hashCode is greater than or equal to the parent's, go right
                if(nptr.getRightChild() == null) { //If the node we're going to is empty, insert our node here
                    nptr.setRightChild(node);
                }
                nptr = nptr.getRightChild();
            }
        }
    }

    public TreeNode retrieve(String name) {
        TreeNode nptr = root;

        while(nptr != null) {
            if(name.hashCode() < nptr.getName().hashCode()) {
                nptr = nptr.getLeftChild(); //There was no hashcode match, so we simply go left
            }
            else {
                if(nptr.getName().equals(name)) { //Check to see if this is our node
                    return nptr;
                }
                nptr = nptr.getRightChild(); //If it isn't, go right
            }
        }
        return null;
    }
}
