package edu.oakland;

/**
 * Created by Justin Kur on 4/2/2017.
 */
public class DoubleHashing extends LinearProbing {

    private int offsetHash; //Holds the integer which defines the second hash function

    /**
     * Creates a new hash table with double hashing
     * @param size Size of the table must be greater than or equal to the number of inputs and prime
     * @param offsetHash A prime number which is smaller than the size
     */
    public DoubleHashing(int size, int offsetHash) {
        super(size); //Calls the parent constructor
        this.offsetHash = offsetHash; //Sets the offset parameter
    }

    /**
     * Return the name mod the offsetHash to get the offset for traversing through the table
     * @param name Name of the person
     * @return The offset value
     */
    @Override
    protected int getOffset(String name) {
        return Math.abs(name.hashCode() % offsetHash) + 1; //+1 to avoid an offset of 0.
    }

    /**
     * Gets the name of the data structure being used
     * @return The name of the data structure
     */
    @Override
    public String getStructureName() {
        return "Double Hashing";
    }
}
