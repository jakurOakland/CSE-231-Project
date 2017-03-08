package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class LinkedList {
    /*
    Singly Linked list implementation
     */
    private PersonNode head;

    /**
     * Creates a linked list with the head set to null (empty list)
     */
    public LinkedList() {
        head = null;
    }

    /**
     * Creates a linked list beginning with a single element
     * @param node The node which will be the head
     */
    public LinkedList(PersonNode node) {
        head = node;
    }

    /**
     * Returns a string representation of the list
     * @return A string representation of the list
     */
    public String toString() {
        String string = "";
        PersonNode nptr = head;
        while(nptr != null) {
            string += nptr.toString() + " ->";
            nptr = nptr.getNext();
        }
        return string;
    }

    /**
     * Adds a node to the front of the list.
     * @param node PersonNode to be added
     */
    public void add(PersonNode node) {
        node.setNext(head);
        head = node;
    }

    /**
     * Gets a PersonNode matching a given name string
     * @param name The name string to search for
     * @return The PersonNode with the given name, or null if it was not found
     */
    public PersonNode get(String name) {
        PersonNode nptr = head;
        //Iterate through the entire list, looking for the name string.
        while(nptr != null) {
            if(nptr.getName().equals(name)) {
                return nptr;
            }
            nptr = nptr.getNext();
        }
        return null; //Return null if no node with that name string was found.
    }

    /**
     * Removes a PersonNode matching a given name string
     * @param name The name string to search for
     */
    public void remove(String name) {
        //Special action is taken for the head, because it has no previous node to handle
        if(head.getName().equals(name)) {
            head = head.getNext();
            return;
        }
        //The head has already been checked
        PersonNode previous = head;
        PersonNode nptr = head.getNext();
        //Search for the name string
        while(nptr != null) {
            if(nptr.getName().equals(name)) {
                /*
                The previous node's next is set to the next of the current node, in essence cutting
                the current node from the list
                 */
                previous.setNext(nptr.getNext());
                return;
            }
            previous = nptr;
            nptr = nptr.getNext();
        }
    }
}
