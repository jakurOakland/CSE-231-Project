package edu.oakland;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Justin Kur on 3/9/2017.
 */
public class BinarySearchTree implements DataStructure {

    private TreeNode root;
    private int elements;

    /**
     * Initializes the root to null and the number of elements to 0
     */
    public BinarySearchTree() {
        root = null;
        elements = 0;
    }

    /**
     *
     * @param name Name of the person to be inserted
     * @param telephoneNumber Telephone number of the person to be inserted
     * @param address Address of the person to be inserted
     */
    public void insert(String name, String telephoneNumber, String address) {
        insert(new TreeNode(name, telephoneNumber, address));
    }

    /**
     * Insert an already created TreeNode into the BST
     * @param node The TreeNode to be inserted
     */
    public void insert(TreeNode node) {
        //First check if the tree is empty
        elements++;
        if(root == null) {
            root = node;
            return;
        }
        TreeNode nptr = root;

        while(nptr != null) {
            if(node.getName().hashCode() < nptr.getName().hashCode()) { //Go left
                if(nptr.getLeftChild() == null) { //If the node we're going to is empty, insert our node here
                    nptr.setLeftChild(node);
                    return;
                }
                nptr = nptr.getLeftChild();
            }
            else { //If the hashCode is greater than or equal to the parent's, go right
                if(nptr.getRightChild() == null) { //If the node we're going to is empty, insert our node here
                    nptr.setRightChild(node);
                    return;
                }
                nptr = nptr.getRightChild();
            }
        }
    }

    /**
     * Retrieves the TreeNode containing the name entered, or null if it is not found
     * @param name Name of the person to be retrieved from the tree
     * @return The TreeNode that was found
     */
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

    /**
     * Updates the fields of a Person only if that Person is already present in the data structure
     * @param name Name of the person
     * @param telephoneNumber Their new telephone number
     * @param address Their new address
     * @return True if the update was successful, false if no node matching the name was found
     */
    public boolean update(String name, String telephoneNumber, String address) {
        TreeNode nptr = root;

        while(nptr != null) {
            if(name.hashCode() < nptr.getName().hashCode()) {
                nptr = nptr.getLeftChild(); //There was no hashcode match, so we simply go left
            }
            else {
                if(nptr.getName().equals(name)) { //Check to see if this is our node
                    nptr.setTelephoneNumber(telephoneNumber);
                    nptr.setAddress(address);
                    return true; //Updated successfully
                }
                nptr = nptr.getRightChild(); //If it isn't, go right
            }
        }
        return false; //Node not found
    }

    /**
     * Deletes the first occurrence of a node with the given name from the tree
     * @param name The name of the person to be deleted from the BST
     * @return True if the deletion was successful, false if the node wasn't found
     */
    public boolean delete(String name) {
        TreeNode parent = null;
        TreeNode nptr = root;
        TreeNode placeHolder; //This will temporarily hold the "replacement" for the deleted node
        while(nptr != null) {
            if(nptr.getName().equals(name)) { //If we found the node we're trying to delete
                if (nptr.getRightChild() != null && nptr.getLeftChild() != null) { //If it has two children
                    TreeNode[] nodes = successorAndParent(nptr); //Get its successor and the successor's parent
                    if (nodes[0] != nptr) { //If the successor is not a direct child
                        /*
                        First any right children of the successor are assigned to the successor's parent as its
                        left child (note that the successor cannot have left children by definition). Then the children
                        of nptr become the children of the successor.
                         */
                        nodes[0].setLeftChild(nodes[1].getRightChild());
                        nodes[1].setRightChild(nptr.getRightChild());
                        nodes[1].setLeftChild(nptr.getLeftChild());
                    } else {
                        //If the successor is nptr's right child, the transition is simple
                        nodes[1].setLeftChild(nptr.getLeftChild());
                    }
                    placeHolder = nodes[1]; //placeHolder holds the replacement node (successor)
                }
                //If the node has one or no children, the deletion is trivial
                else if (nptr.getRightChild() != null) {
                    placeHolder = nptr.getRightChild();
                } else if (nptr.getLeftChild() != null) {
                    placeHolder = nptr.getLeftChild();
                } else {
                    placeHolder = null;
                }

                if(parent != null) { //If nptr is not the root
                    //The successor takes nptr's spot as its parent's child
                    if(nptr == parent.getLeftChild()) {
                        parent.setLeftChild(placeHolder);
                    }
                    else {
                        parent.setRightChild(placeHolder);
                    }
                }
                else {
                    root = placeHolder; //If the root is the target of the deletion, the successor becomes the root
                }
                elements--;
                return true; //Indicating successful deletion
            }
            //Traversing through the tree
            if(name.hashCode() < nptr.getName().hashCode()) { //Go left
                parent = nptr;
                nptr = nptr.getLeftChild();
            }
            else { //Go right
                parent = nptr;
                nptr = nptr.getRightChild();
            }
        }
        return false; //Indicating that the no node with that name was found
    }

    /**
     * Gets a node's successor and its parent.
     * @param node Node whose successor is wanted
     * @return An array containing the successor's parent and the successor in that order
     */
    private TreeNode[] successorAndParent(TreeNode node) {
        //This will only be called when the node has two children, so parent and nptr should never be null
        TreeNode parent = node;
        TreeNode nptr = node.getRightChild();
        //Iterate through until the successor is found
        while(nptr.getLeftChild() != null) {
            parent = nptr;
            nptr = nptr.getLeftChild();
        }
        return new TreeNode[]{parent, nptr}; //Array containing just the parent of the successor and the successor
    }

    /**
     * Prints the inorder traversal of the tree
     */
    public void display() {
        inOrderTraverse(root);
    }

    /**
     * Prints an inorder traversal of the tree, used for printing to the command line
     * @param node Upon first call, this should be the root
     */
    private void inOrderTraverse(TreeNode node) {
        if(node != null) {
            inOrderTraverse(node.getLeftChild());
            System.out.println(node.getName() + " (" + node.getAddress() + ") " + node.getTelephoneNumber());
            inOrderTraverse(node.getRightChild());
        }
    }
    /**
     * Writes a representation of the data structure to the output file
     */
    public void writeToFile() {
        class Traversal {
            private Writer writer;

            private Traversal() {
                try {
                    writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(Main.outputFileName), "utf-8"));
                }
                catch(Exception e) {
                    System.out.println("Error initializing file writer");
                }
            }
            private void traverseAndWrite(TreeNode node) throws java.io.IOException {
                if(node != null) {
                    traverseAndWrite(node.getLeftChild());
                    writer.write(node.name + "," + node.telephoneNumber + "," + node.address + "\n");
                    traverseAndWrite(node.getRightChild());
                }
            }
        }
        Traversal t = new Traversal();
        try {
            if(t.writer != null) {
                t.traverseAndWrite(root);
                t.writer.close();
            }
            else {
                System.out.println("Error: Writer was not properly initialized");
            }
        } catch(java.io.IOException e) {
            System.out.println("Error writing to file");
        }
    }

    /**
     * Gets the name of the data structure being used
     * @return The name of the data structure
     */
    public String getStructureName() {
        return "Binary Search Tree";
    }

    /**
     * Used to check if the user should be warned about verbose printing
     * @return True if the user should be warned, false if not
     */
    public boolean printWarning() {
        return elements >= 1000;
    }
}
