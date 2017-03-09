package edu.oakland;

/**
 * Created by Gaming on 3/9/2017.
 */
public class TreeNode extends Person {

    private TreeNode leftChild;
    private TreeNode rightChild;

    public TreeNode(String name, String telephoneNumber, String address) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
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
