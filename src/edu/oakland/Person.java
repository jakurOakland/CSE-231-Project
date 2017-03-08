package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class Person {
    /*
    This class contains only the essential information for the definition of a person.
    It will be subclassed to create nodes for different data structures.
     */
    protected String name;
    protected String telephoneNumber;
    protected String address;

    public String toString() {
        return name + " " + telephoneNumber + " " + address;
    }

    public String getName() {
        return name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
