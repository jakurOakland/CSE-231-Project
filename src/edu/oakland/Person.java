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

    public Person(String name, String telephoneNumber, String address) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }

    public String toString() {
        return name + " (" + address + ") " + telephoneNumber;
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


    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
