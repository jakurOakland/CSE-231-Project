package edu.oakland;

import java.io.*;
import java.nio.charset.Charset;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Justin Kur on 3/6/2017.
 */
public class Main {

    public static DataStructure[] structures;
    public static String outputFileName;
    public static String inputFileName;

    public static void main(String[] args) {
        //Checking that the args are acceptable
    	if(args.length != 2) {
    		System.out.println("You must submit exactly two arguments: an input file and output file");
    		return;
		}
		if(!args[1].contains(".csv")) {
    	    System.out.println("Must use a csv file for outputting!");
    	    return;
        }
        //Setting the input and output file names
        inputFileName = args[0];
    	outputFileName = args[1];


        int tableSize = 63781;

        LinkedList[] lists;
        boolean customInput = true;
        if(args[0].equals("input-project.csv")) { //We are to assume we are doing benchmarking
            //One of each structure is made
            LinearProbing linearProbing = new LinearProbing(tableSize);
            TelephoneBook chaining = new TelephoneBook(tableSize);
            DoubleHashing doubleHashing = new DoubleHashing(tableSize,7);
            BinarySearchTree tree = new BinarySearchTree();
            structures = new DataStructure[] {chaining, tree, linearProbing, doubleHashing}; //And added to the array

            lists = readDatabase(args[0], structures);
            customInput = false;
            performBenchmarks(lists, structures, 500); //Then the benchmarks are performed
        }

        runConsole(customInput); //Handles the command line functionality

    }

    /**
     * Handles running the command line program for the user
     * @param customInput If true, request certain parameters from the user, if false use the defaults
     */
    public static void runConsole(boolean customInput) {
        Scanner scanner = new Scanner(System.in);
        int input;
        MainMenuLoop:
        while(true) {
            try {
                DataStructure ds; //Will hold the data structure of the user's choice
                if(customInput && structures == null) {
                    //Get the information for the custom table sizes and double hashing second hash function
                    System.out.println("How large should our hash tables be? (Pick a prime number)");
                    input = scanner.nextInt();
                    System.out.println("How what number should our double hashing modulus function take?");
                    int input2 = scanner.nextInt();
                    structures = new DataStructure[] {new TelephoneBook(input), new BinarySearchTree(),
                            new LinearProbing(input), new DoubleHashing(input, input2)};
                    readCustomFile(inputFileName, structures);
                }
                System.out.println("\nMAIN MENU");
                System.out.println("1. Hash Table with Chaining");
                System.out.println("2. Binary Search Tree");
                System.out.println("3. Linear Probing");
                System.out.println("4. Double Hashing");
                System.out.println("5. Exit");
                System.out.print("Enter choice(1-5): ");
                input = scanner.nextInt();
                scanner.nextLine(); //Making sure the new line is handled by the scanner
                if(input >= 1 && input <= 4) { //If the user input a valid input that was not exit
                    ds = structures[input - 1];
                }
                else {
                    System.out.println("Exiting...");
                    break;
                }
                while(true) {
                    //The submenu for a given data structure
                    System.out.println("\nMAIN MENU for " + ds.getStructureName());
                    System.out.println("1. Insert telephone number");
                    System.out.println("2. Retrieve telephone number");
                    System.out.println("3. Delete telephone number");
                    System.out.println("4. Update entry");
                    System.out.println("5. Display telephone book");
                    System.out.println("6. Write to file");
                    System.out.println("7. Return to Main Menu");
                    System.out.println("8. Exit Program");
                    System.out.print("Enter choice(1-8): ");
                    input = scanner.nextInt();
                    scanner.nextLine(); //Making sure the new line is handled by the scanner
                    switch(input) {
                        case 1:
                            String[] strings = new String[3];
                            System.out.print("Enter person's name: ");
                            strings[0] = scanner.nextLine();
                            System.out.print("Enter person's telephone number: ");
                            strings[1] = scanner.nextLine();
                            System.out.print("Enter person's address: ");
                            strings[2] = scanner.nextLine();
                            ds.insert(strings[0], strings[1], strings[2]); //Inserting with the given parameters
                            break;
                        case 2:
                            System.out.print("Enter person's name: ");
                            String nameString = scanner.nextLine();
                            Person person = ds.retrieve(nameString); //Retrieving the person object
                            //And print it
                            System.out.println("The telephone number is: " + person.getTelephoneNumber());
                            break;
                        case 3:
                            System.out.print("Enter person's name: ");
                            nameString = scanner.nextLine();
                            person = ds.retrieve(nameString); //The call to retrieve is to display the person's data
                            System.out.println("Deleting: " + person);
                            ds.delete(person.getName()); //Then the delete is called as usual
                            break;
                        case 4:
                            //Collects the same data as if inserting
                            strings = new String[3];
                            System.out.print("Enter person's name: ");
                            strings[0] = scanner.nextLine();
                            System.out.print("Enter new telephone number: ");
                            strings[1] = scanner.nextLine();
                            System.out.print("Enter new address: ");
                            strings[2] = scanner.nextLine();
                            if(ds.update(strings[0], strings[1], strings[2])) { //If the update was successful
                                System.out.println("Entry successfully updated.");
                            }
                            else {
                                //Get the user's decision
                                System.out.println("No entry with that name was found. Would you like to create a new " +
                                        "entry with the given data? (y/n)");
                                String yesNo = scanner.nextLine();
                                if(yesNo.startsWith("y") || yesNo.startsWith("Y")) {
                                    ds.insert(strings[0], strings[1], strings[2]); //Conveniently insert for them
                                }
                                else {
                                    System.out.println("No new entry created."); //Notify them that nothing was changed
                                }
                            }
                            break;
                        case 5:
                            if(ds.printWarning()) { //If the structure contains many elements
                                System.out.println("This data structure has many elements and printing them all may " +
                                        "be undesirable. Would you like continue anyway? (y/n)");
                                String yesNo = scanner.nextLine();
                                if(yesNo.startsWith("y") || yesNo.startsWith("Y")) {
                                    ds.display();
                                }
                                else { //No is considered the default
                                    System.out.println("Returning to Menu for " + ds.getStructureName());
                                }

                            }
                            else { //If the structure doesn't have too many elements
                                ds.display(); //Print without prompting the user
                            }
                            break;
                        case 6:
                            ds.writeToFile();
                            System.out.println("Wrote data structure to file!");
                            break;
                        case 7:
                            continue MainMenuLoop; //Return to the main menu by continuing the outer loop
                        case 8:
                            return; //Return ends the function entirely
                        default:
                            System.out.println("Unrecognized input number, returning to Main Menu...");
                            continue MainMenuLoop; //Return to the main menu by continuing the outer loop
                    }
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input type received, ending program");
                return;
            }
        }
    }

    /**
     * Reads big database of names input-project.csv
     * @param fileString String name of the file to be read
     * @param structures Structures to inserted the people into
     * @return Lists which can be used for benchmarking, first is items not in the list, second is items in the list
     */
    public static LinkedList[] readDatabase(String fileString, DataStructure[] structures) {
    	try {
			String line;
			//Setting up the classes for file reading
			FileInputStream file = new FileInputStream(fileString);
			InputStreamReader reader = new InputStreamReader(file, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(reader);
			br.readLine(); //Ignores the first line in the file, which contains header information
            LinkedList toBeDeleted = new LinkedList();
            LinkedList toBeInserted = new LinkedList();
            //40000 is considered the full list
			for(int counter = 0; (line = br.readLine()) != null && counter < 40000; counter++) {
				//System.out.println(counter);
				String[] values = line.split(",");
				String nameString = values[0] + values[1]; //Concatenates the last name and first name fields
                if(counter % 10 == 0) { //Adds 10% of nodes to a list which will be used for insertion benchmarking
                    toBeInserted.add(new PersonNode(nameString.trim(), Integer.toString(counter),
                            "1234 Cherry Ln."));
                    continue; //We do not want this to be inserted into our list normally
                }
				for(DataStructure d : structures) {
					/*
					Trims the whitespace out of the name string, makes the phone number our simple counter, and uses
					a placeholder address (we will not be searching by address, so the address is unimportant)
					*/
					d.insert(nameString.trim(), Integer.toString(counter), "1234 Cherry Ln.");
				}

                if(counter % 10 == 1) { //Add 10% nodes to a list which will be used for deletion benchmarking
                    toBeDeleted.add(new PersonNode(nameString.trim(), Integer.toString(counter),
                            "1234 Cherry Ln."));
                }
			}
			//Closing all file reading streams
			br.close();
			reader.close();
			file.close();
			return new LinkedList[]{toBeInserted, toBeDeleted};
		}
		catch(Exception e) {
    		e.printStackTrace();
    		return new LinkedList[0]; //For error handling, just return an empty array
		}
	}

    /**
     * Reads a custom csv file instead of the test file used for benchmarking
     * @param fileString The name of the file
     * @param structures The structures that the people will be added to
     */
	public static void readCustomFile(String fileString, DataStructure[] structures) {
        try {
            String line;
            //Setting up the classes for file reading
            FileInputStream file = new FileInputStream(fileString);
            InputStreamReader reader = new InputStreamReader(file, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            for(int counter = 0; (line = br.readLine()) != null && counter < 5000; counter++) {
                String[] values = line.split(",");

                for(DataStructure d : structures) {
                    //Trims the whitespace out of the strings
                    d.insert(values[0].trim(), values[1].trim(), values[2].trim());
                }
            }
            //Close the streams and files
            br.close();
            reader.close();
            file.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Performs benchmarks for insertion and removal and prints the results
     * @param lists first is the insertion list, second is the removal list
     * @param structures Structures to be benchmarked
     * @param iterations Number of iterations to test
     */
	public static void performBenchmarks(LinkedList[] lists, DataStructure[] structures, int iterations) {

        long[] averageTimes = new long[structures.length];
        //Benchmark insertion
		for(int i = 0; i < iterations; i++) {
            long[] insertTimes = benchmarkInsert(lists[0], structures);
            for(int j = 0; j < insertTimes.length; j++) {
                averageTimes[j] += insertTimes[j]; //adding each iteration to the total
            }
            benchmarkRemove(lists[0], structures); //Removing what we inserted to prepare for next iteration
        }
        for(int i = 0; i < averageTimes.length; i++) {
		    averageTimes[i] /= iterations; //Getting the average
        }
        System.out.println("Insertion benchmarks: ");
        for(long num : averageTimes) {
		    System.out.println(num);
        }
        //End Insertion

        averageTimes = new long[structures.length];
        for(int i = 0; i < iterations; i++) {
            long[] removalTimes = benchmarkRemove(lists[1], structures);
            for(int j = 0; j < removalTimes.length; j++) {
                averageTimes[j] += removalTimes[j]; //adding each iteration to the total
            }
            benchmarkInsert(lists[1], structures); //Inserting them back, to prepare for next iteration
        }
        for(int i = 0; i < averageTimes.length; i++) {
            averageTimes[i] /= iterations; //Getting the average
        }
        System.out.println("Removal benchmarks: ");
        for(long num : averageTimes) {
            System.out.println(num);
        }

        //End removal
        averageTimes = new long[structures.length];
        for(int i = 0; i < iterations; i++) {
            long[] times = benchmarkUpdate(lists[0], structures);
            for(int j = 0; j < times.length; j++) {
                averageTimes[j] += times[j];
            }
        }
        for(int i = 0; i < averageTimes.length; i++) {
            averageTimes[i] /= iterations; //Getting the average
        }
        System.out.println("Update benchmarks: ");
        for(long num : averageTimes) {
            System.out.println(num);
        }

        //End update
        averageTimes = new long[structures.length];
        for(int i = 0; i < iterations; i++) {
            long[] times = benchmarkSearch(lists[0], structures);
            for(int j = 0; j < times.length; j++) {
                averageTimes[j] += times[j];
            }
        }
        for(int i = 0; i < averageTimes.length; i++) {
            averageTimes[i] /= iterations; //Getting the average
        }
        System.out.println("Search (retrieval) benchmarks: ");
        for(long num : averageTimes) {
            System.out.println(num);
        }

        //End retrieval
    }

    /**
     * Benchmarks insertion
     * @param insertList List of items to be inserted
     * @param structures Structures into which they will be inserted
     * @return Array containing the times taken
     */
	public static long[] benchmarkInsert(LinkedList insertList, DataStructure[] structures) {

        long[] insertionTimes = new long[structures.length]; //All the insertion times, in order of structures array
        for(int i = 0; i < structures.length; i++) { //For each structure
            //System.out.println(i);
            DataStructure ds = structures[i];
            PersonNode nptr = insertList.getHead();
            long time = 0; //Start benchmark
            while(nptr != null) { //For each node in the insertList
                //System.out.println(nptr.getName());
                long time1 = System.nanoTime();
                ds.insert(nptr.getName(), nptr.getTelephoneNumber(), nptr.getAddress()); //Insert into the structure
                long time2 = System.nanoTime() - time1;
                nptr = nptr.getNext();
                time += time2;
            }
            //Record benchmark
            //long time = System.nanoTime() - startTime;
            insertionTimes[i] = time;
        }
        return insertionTimes;
    }

    /**
     * Benchmarks update
     * @param insertList List of items to be updated
     * @param structures Structures containing them
     * @return Array containing the times taken
     */
    public static long[] benchmarkUpdate(LinkedList insertList, DataStructure[] structures) {

        long[] insertionTimes = new long[structures.length]; //All the insertion times, in order of structures array
        for(int i = 0; i < structures.length; i++) { //For each structure
            //System.out.println(i);
            DataStructure ds = structures[i];
            PersonNode nptr = insertList.getHead();
            long time = 0; //Start benchmark
            while(nptr != null) { //For each node in the insertList
                long time1 = System.nanoTime();
                ds.update(nptr.getName(), nptr.getTelephoneNumber(), nptr.getAddress()); //Update fields
                long time2 = System.nanoTime() - time1;
                nptr = nptr.getNext();
                time += time2;
            }
            //Record benchmark
            insertionTimes[i] = time;
        }
        return insertionTimes;
    }

    /**
     * Benchmarks search
     * @param insertList List of items to be searched for
     * @param structures Structures containing them
     * @return Array containing the times taken
     */
    public static long[] benchmarkSearch(LinkedList insertList, DataStructure[] structures) {

        long[] insertionTimes = new long[structures.length]; //All the insertion times, in order of structures array
        for(int i = 0; i < structures.length; i++) { //For each structure
            //System.out.println(i);
            DataStructure ds = structures[i];
            PersonNode nptr = insertList.getHead();
            long time = 0; //Start benchmark
            while(nptr != null) { //For each node in the insertList
                //System.out.println(nptr.getName());
                long time1 = System.nanoTime();
                ds.retrieve(nptr.name);
                long time2 = System.nanoTime() - time1;
                nptr = nptr.getNext();
                time += time2;
            }
            //Record benchmark
            insertionTimes[i] = time;
        }
        return insertionTimes;
    }

    /**
     * Benchmarks removal
     * @param removeList List of items to be removed
     * @param structures Data structures from which they'll be removed
     * @return The times taken by the different structures
     */
    public static long[] benchmarkRemove(LinkedList removeList, DataStructure[] structures) {

        long[] removalTimes = new long[structures.length]; //All the removal times, in order of structures array
        for(int i = 0; i < structures.length; i++) { //For each structure
            DataStructure ds = structures[i];
            PersonNode nptr = removeList.getHead();
            long startTime = System.nanoTime(); //Start benchmark
            while(nptr != null) { //For each node in the insertList
                ds.delete(nptr.getName()); //Delete from each data structure based on name
                nptr = nptr.getNext();
            }
            //Record benchmark
            long time = System.nanoTime() - startTime;
            removalTimes[i] = time;
        }
        return removalTimes;
    }

}
