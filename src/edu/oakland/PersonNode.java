package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class PersonNode extends Person {
    /*
    The person node for Hashing with Chaining (where chains are singly linked lists)
     */
    private PersonNode next;

    public PersonNode(String name, String telephoneNumber, String address) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.next = null;
    }

    public PersonNode getNext() {
        return next;
    }

    public void setNext(PersonNode next) {
        this.next = next;
    }
}
