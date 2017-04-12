package edu.oakland;

/**
 * Created by Justin Kur on 3/9/2017.
 */
public class TreeNode extends Person {

    private TreeNode leftChild;
    private TreeNode rightChild;

    /**
     * Creates a TreeNode using the parameters for defining a person
     * @param name Person's name
     * @param telephoneNumber Person's telephone number
     * @param address Person's address
     */
    public TreeNode(String name, String telephoneNumber, String address) {
        super(name, telephoneNumber, address); //Call to parent constructor
        //Children are initialized to null
        leftChild = null;
        rightChild = null;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
