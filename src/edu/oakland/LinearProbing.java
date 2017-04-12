package edu.oakland;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Justin Kur on 3/16/2017.
 */
public class LinearProbing implements DataStructure {

    protected Person[] book; //holds a Person, null, or the tombstone
    //Used to denote a deleted node which should be overlooked when searching but overwritten when inserting
    protected Person tombstone;

    /**
     * Creates a new hashtable with linear probing of a given size
     * @param size Must be greater than or equal to size of inputs
     */
    public LinearProbing(int size) {
        book = new Person[size]; //size must be prime
        tombstone = new Person("TOMBSTONE", "", ""); //Any parameters would do
    }

    /**
     * Inserts a new person into the table using String information to create a new Person object
     * @param name Person's name
     * @param telephoneNumber Person's telephone number
     * @param address Person' address
     */
    public void insert(String name, String telephoneNumber, String address) {
        insert(new Person(name, telephoneNumber, address));
    }

    /**
     * Inserts a person into a table using a Person object
     * @param person Person to be inserted
     */
    public void insert(Person person) {
        int position = Math.abs(person.getName().hashCode() % book.length); //Position determined by first hash
        int offset = getOffset(person.getName()); //For standard linear probing, 1
        for(; book[position] != null && book[position] != tombstone; position+= offset) {
            if(position + offset > book.length - 1) { //Cycle back to the beginning of the book
                position -= book.length;
            }
        }
        book[position] = person; //Overwrite the null or tombstone with this person
    }

    /**
     * Retrieves a person with a given name from the table
     * @param name Name of the person to search for
     * @return The person object if it is found, null if it is not
     */
    public Person retrieve(String name) {
        int position = Math.abs(name.hashCode() % book.length); //Position determined by first hash function
        int offset = getOffset(name); //For standard linear probing, 1
        for(; book[position] != null; position += offset) {

            if(book[position].getName().equals(name)) {
                return book[position]; //Return person whose name matches the given one
            }
            if(position + offset > book.length - 1) { //Cycle back to the beginning of the book
                position -= book.length;
            }
        }
        return null; //Return null if the person wasn't found
    }

    /**
     * Prints a representation of the data structure to the command line
     */
    public void display() {
        for(int i = 0; i < book.length; i++) {
            System.out.println("table[" + i + "] => " + book[i]);
        }
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
            for(int i = 0; i < book.length; i++) {
                if(book[i] != null && book[i] != tombstone) {
                    Person p = book[i];
                    writer.write(p.getName() + "," + p.getTelephoneNumber() + "," + p.getAddress() + "\n");
                }
            }
            writer.close();
        } catch(java.io.IOException e) {
            System.out.println("Error writing to file");
        }
    }

    /**
     * Deletes a person with a given node from the table
     * @param name Name of the person to be deleted
     * @return True if the deletion was successful, false if the person wasn't found
     */
    public boolean delete(String name) {
        int position = Math.abs(name.hashCode() % book.length);
        int offset = getOffset(name); //For standard linear probing
        for(; book[position] != null; position += offset) {

            if(book[position].getName().equals(name)) {
                book[position] = tombstone; //Marking this as a node which has been removed
                return true; //Successfully removed
            }
            if(position + offset > book.length - 1) { //Cycle back to the beginning of the book
                position -= book.length;
            }

        }
        return false; //Not found
    }

    /**
     * Gets the offset uses by the linear probing system. Returns 1 in this superclass
     * @param name Name of the person
     * @return The offset, in this case 1
     */
    protected int getOffset(String name) {
        return 1; //Standard linear probing has only one hash function
    }

    /**
     * Gets the name of the data structure being used
     * @return The name of the data structure
     */
    public String getStructureName() {
        return "Linear Probing";
    }

    /**
     * Used to check if the user should be warned about verbose printing
     * @return True if the user should be warned, false if not
     */
    public boolean printWarning() {
        return book.length >= 1000;
    }

    /**
     * Updates the fields of a Person only if that Person is already present in the data structure
     * @param name Name of the person
     * @param telephoneNumber Their new telephone number
     * @param address Their new address
     * @return True if the update was successful, false if no node matching the name was found
     */
    public boolean update(String name, String telephoneNumber, String address) {
        int position = Math.abs(name.hashCode() % book.length); //Position determined by first hash function
        int offset = getOffset(name); //For standard linear probing, 1
        for(; book[position] != null; position += offset) {
            if(book[position].getName().equals(name)) {
                Person p = book[position];
                p.setAddress(address);
                p.setTelephoneNumber(telephoneNumber);
                return true; //Successfully updated
            }
            if(position + offset > book.length - 1) { //Cycle back to the beginning of the book
                position -= book.length;
            }
        }
        return false; //Node not found
    }


}
