package edu.oakland;

/**
 * Created by Justin Kur on 3/9/2017.
 */
public class BinarySearchTree {

    private TreeNode root;

    public BinarySearchTree() {
        root = null;
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
     * @return
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
                return true; //Indicating successful deletion
            }
            //Traversing through the tree
            if(name.hashCode() < nptr.getName().hashCode()) {
                parent = nptr;
                nptr = nptr.getLeftChild();
            }
            else {
                parent = nptr;
                nptr = nptr.getRightChild();
            }
        }
        return false; //Indicating that the no node with that name was found
    }

    private TreeNode[] successorAndParent(TreeNode node) {
        //This will only be called when the node has two children, so parent and nptr should never be null
        TreeNode parent = node;
        TreeNode nptr = node.getRightChild();
        while(nptr.getLeftChild() != null) {
            parent = nptr;
            nptr = nptr.getLeftChild();
        }
        return new TreeNode[]{parent, nptr};
    }
}
