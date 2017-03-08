package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class Main {

    public static void main(String[] args) {
	    PersonNode p1 = new PersonNode("Jack", "2223333", "9911 Cherry Ln.");
	    PersonNode p2 = new PersonNode("Roger Federer", "2332322", "Basel Switzerland");
	    PersonNode p3 = new PersonNode("Smith", "3434446", "2330 Cherry Ln.");

		TelephoneBook phoneBook = new TelephoneBook();
		phoneBook.insert(p1);
		phoneBook.insert(p2);
		phoneBook.insert(p3);
		phoneBook.delete(p1.getName());
		System.out.println(phoneBook);
    }
}
