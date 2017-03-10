package edu.oakland;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class Main {

    public static void main(String[] args) {
	    TreeNode p1 = new TreeNode("Jack", "2223333", "9911 Cherry Ln.");
	    TreeNode p2 = new TreeNode("Roger Federer", "2332322", "Basel Switzerland");
	    TreeNode p3 = new TreeNode("Smith", "3434446", "2330 Cherry Ln.");
	    TreeNode p4 = new TreeNode("Robert Irvine", "", "");
	    TreeNode p5 = new TreeNode("Guy Fieri", "","2023 Flavor Town");
	    TreeNode p6 = new TreeNode("Napoleon Bonaparte", "", "1821 Saint Helena");
	    TreeNode p7 = new TreeNode("Example", "", "");
	    System.out.println("Hashcodes: " + p1.hashCode() + " " + p2.hashCode() + " " + p3.hashCode() + "\n" +
		p4.hashCode() + " " + p5.hashCode() + " " + p6.hashCode() + " " + p7.hashCode());
//		TelephoneBook phoneBook = new TelephoneBook();
//		phoneBook.insert(p1);
//		phoneBook.insert(p2);
//		phoneBook.insert(p3);
//		phoneBook.delete(p1.getName());
//		System.out.println(phoneBook);
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(p2);
		tree.insert(p4);
		tree.insert(p3);
		tree.insert(p6);
		tree.insert(p1);
		tree.insert(p5);
		tree.insert(p7);
		tree.delete("Smith");
		System.out.println(tree.retrieve("Guy Fieri"));
    }
}
