package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class TelephoneBook {
    /*
    An implementation of Hashing with Chaining
     */
    private LinkedList[] book;

    /**
     * Creates a new telephone book with an array of size 7
     */
    public TelephoneBook() {
        this(7);
    }

    /**
     * Creates a new telephone book with an array of the specified size
     * @param size Size of the array
     */
    public TelephoneBook(int size) {
        book = new LinkedList[size];
        //Initialize all the values in the array to empty lists
        for(int n = 0; n < book.length; n++) {
            book[n] = new LinkedList();
        }
    }

    /**
     * Returns a string representation of the telephone book
     * @return A string representation of the telephone book
     */
    public String toString() {
        String string = "";
        for(int n = 0; n < book.length; n++) {
            string += "Slot " + n + ": " + book[n].toString() + "\n";
        }
        return string;
    }

    /**
     * Insert a person into the book given string parameters
     * @param name Person's name
     * @param telephoneNumber Person's phone number
     * @param address Person's address
     */
    public void insert(String name, String telephoneNumber, String address) {
        insert(new PersonNode(name, telephoneNumber, address)); //Create new PersonNode object and call other method
    }

    /**
     * Inserts a person into the book given a PersonNode object
     * @param node PersonNode to insert into the book
     */
    public void insert(PersonNode node) {
        int hash = node.getName().hashCode(); //Getting hash code for the person's name
        LinkedList list = book[hash % book.length]; //Getting list based off of compression function
        list.add(node);
    }

    /**
     * Finds a PersonNode in the book given a name
     * @param name The name of the PersonNode to be found
     * @return The PersonNode with the given name
     */
    public PersonNode retrieve(String name) {
        int hash = name.hashCode(); //Getting hash code for the person's name
        LinkedList list = book[hash % book.length]; //Getting list based off of compression function
        return list.get(name);
    }

    /**
     * Removes a PersonNode from the list given a string
     * @param name The name string of the node to be removed
     */
    public void delete(String name) {
        int hash = name.hashCode(); //Getting hash code for the person's name
        LinkedList list = book[hash % book.length]; //Getting list based off of compression function
        list.remove(name);
    }
}
