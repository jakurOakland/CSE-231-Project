package edu.oakland;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class TelephoneBook implements DataStructure {
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
     * Prints a representation of the data structure to the command line
     */
    public void display() {
        System.out.println(toString());
    }

    /**
     * Prints out the leading 5 values of the telephone book
     */
    public void printHead() {
        printHead(5);
    }

    /**
     * Prints out the leading i values of the telephone book
     * @param i Number of values
     */
    public void printHead(int i) {
        int threshold = i;
        if(book.length < i) {
            threshold = book.length;
        }
        for(int n = 0; n < threshold; n++) {
            System.out.println("Slot " + n + ": " + book[n].toString());
        }
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
        //Math.floorMod(hash, book.length)

        LinkedList list = book[Math.abs(hash % book.length)]; //Getting list based off of compression function
        list.add(node);
    }

    /**
     * Finds a PersonNode in the book given a name
     * @param name The name of the PersonNode to be found
     * @return The PersonNode with the given name
     */
    public PersonNode retrieve(String name) {
        int hash = name.hashCode(); //Getting hash code for the person's name
        LinkedList list = book[Math.abs(hash % book.length)]; //Getting list based off of compression function
        return list.get(name);
    }

    /**
     * Updates the fields of a Person only if that Person is already present in the data structure
     * @param name Name of the person
     * @param telephoneNumber Their new telephone number
     * @param address Their new address
     * @return True if the update was successful, false if no node matching the name was found
     */
    public boolean update(String name, String telephoneNumber, String address) {
        int hash = name.hashCode(); //Getting hash code for the person's name
        LinkedList list = book[Math.abs(hash % book.length)]; //Getting list based off of compression function
        PersonNode node = list.get(name);
        if(node != null) {
            node.setTelephoneNumber(telephoneNumber);
            node.setAddress(address);
            return true; //Successfully updated
        }
        else {
            return false; //Node not found
        }
    }

    /**
     * Removes a PersonNode from the list given a string
     * @param name The name string of the node to be removed
     * @return True if the node was found and removed, false if the node was not found
     */
    public boolean delete(String name) {
        int hash = name.hashCode(); //Getting hash code for the person's name
        LinkedList list = book[Math.abs(hash % book.length)]; //Getting list based off of compression function
        return list.remove(name);
    }

    /**
     * Writes a representation of the data structure to the output file
     */
    public void writeToFile() {
        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Main.outputFileName), "utf-8"));
        }
        catch(Exception e) {
            System.out.println("Error initializing file writer");
            return;
        }
        try {
            for(LinkedList list : book) {
                PersonNode node = list.getHead();
                while(node != null){
                    writer.write(node.getName() + "," + node.getTelephoneNumber() + "," + node.getAddress() + "\n");
                    node = node.getNext();
                }
            }
            writer.close();
        } catch(java.io.IOException e) {
            System.out.println("Error writing to file");
        }
    }

    /**
     * Returns a string representation of the data structure name
     * @return Data structure name
     */
    public String getStructureName() {
        return "Hash Table with Chaining";
    }

    /**
     * Used to check if the user should be warned about verbose printing
     * @return True if the user should be warned, false if not
     */
    public boolean printWarning() {
        return book.length >= 1000;
    }
}
