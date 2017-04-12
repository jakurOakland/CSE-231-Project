package edu.oakland;

/**
 * Created by Justin Kur on 4/9/2017.
 */
public interface DataStructure {
    /**
     * Inserts a new node into the data structure
     * @param name Person's name
     * @param telephoneNumber Person's telephone number
     * @param address Person's address
     */
    void insert(String name, String telephoneNumber, String address);

    /**
     * Retrieves a person using their name
     * @param name Person's name
     * @return The person object representing that person, or null if one was not found
     */
    Person retrieve(String name);

    /**
     * Deletes a person from the data structure
     * @param name Name of the person to be deleted
     * @return True if the deletion was successful, false if no such node was found
     */
    boolean delete(String name);

    /**
     * Prints a representation of the data structure to the command line
     */
    void display();

    /**
     * Writes a representation of the data structure to the output file
     */
    void writeToFile();

    /**
     * Gets the name of the data structure being used
     * @return The name of the data structure
     */
    String getStructureName();

    /**
     * Used to check if the user should be warned about verbose printing
     * @return True if the user should be warned, false if not
     */
    boolean printWarning();

    /**
     * Updates the fields of a Person only if that Person is already present in the data structure
     * @param name Name of the person
     * @param telephoneNumber Their new telephone number
     * @param address Their new address
     * @return True if the update was successful, false if no node matching the name was found
     */
    boolean update(String name, String telephoneNumber, String address);

}
