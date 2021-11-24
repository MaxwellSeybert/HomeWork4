import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class SearchSort {
	private ArrayList<String> countyName = new ArrayList<String>();
	private ArrayList<String> stateName = new ArrayList<String>();
	private ArrayList<Integer> stateCode = new ArrayList<Integer>();
	private ArrayList<Integer> countyCode = new ArrayList<Integer>();
	private ArrayList<Integer> confirmed96 = new ArrayList<Integer>();
	private ArrayList<Integer> confirmed01 = new ArrayList<Integer>();
	private ArrayList<Integer> confirmed06 = new ArrayList<Integer>();
	private ArrayList<Integer> confirmed11 = new ArrayList<Integer>();

	public void getData() {
		BufferedReader readBuffer = null;

		try {
			// reads in the file location from the user and opens the file
			String inputLine;
			String fileLoc = null;
			Scanner scnr = new Scanner(System.in);
			System.out.println("Welcome to Lyme Disease Counts (1992-2011)!");
			System.out.println("Enter a full pathname and filename for the input file:");
			fileLoc = scnr.nextLine();
			readBuffer = new BufferedReader(new FileReader(fileLoc));
			// used to ensure that the first line of the file is not saved
			int lineCount = 0;

			while ((inputLine = readBuffer.readLine()) != null) {

				if (lineCount == 0) {
					// skips first line
				} else {
					ArrayList<String> data = CSVtoArrayList(inputLine);

					// adds information from each line to its respective array list, there is an
					// array list for each category of information
					stateName.add(data.get(2));
					countyName.add(data.get(3));
					stateCode.add(Integer.parseInt(data.get(0)));
					countyCode.add(Integer.parseInt(data.get(1)));
					confirmed96.add(Integer.parseInt(data.get(4)));
					confirmed01.add(Integer.parseInt(data.get(5)));
					confirmed06.add(Integer.parseInt(data.get(6)));
					confirmed11.add(Integer.parseInt(data.get(7)));

				}
				// adds to the line count so only the first line is skipped
				lineCount += 1;
			}
			System.out.println("Reading File... Done!");
			this.searchOrSort();

		}
		// catches a IO exception for if the user puts in the wrong location
		catch (IOException e) {
			System.out.println("This filename cannot be found.  Exiting.");
			// e.printStackTrace();
		} finally {
			try {
				if (readBuffer != null) {
					readBuffer.close();
				}

			} catch (IOException readException) {
				System.out.println("Ran into an error reading the file");
				// readException.printStackTrace();
			}

		}

	}

	public void searchOrSort() {
		Scanner scnr = new Scanner(System.in);
		String userInput;
		int userChoice;
		while (true) {
			try {
				// asks the user if they would like to search or sort, then uses try and catch
				// to make sure input is correct
				System.out.print("Do you wish to (1) Search or (2) Sort by a column? (Enter 1 or 2):");
				userInput = scnr.nextLine();
				userChoice = Integer.parseInt(userInput);
				if (userChoice > 2) {
					throw new Exception("Index too large");
				} else if (userChoice < 1) {
					throw new Exception("Index too small");
				} else {
					break;
				}

			} catch (Exception e) {
				System.out.println("Invalid input.  Enter 1 or 2 only.");

			}
		}
		// if the user chooses to search it runs this code which will call on the
		// generic search method
		if (userChoice == 1) {
			int searchBy;
			System.out.println("Search By: ");
			System.out.println();
			System.out.println("1. State Code");
			System.out.println("2. State Name");
			System.out.println("3. County Code");
			System.out.println("4. County Name");
			while (true) {
				// asks the user how they would like to search by in try block
				try {
					System.out.println("Enter your choice (1-4): ");
					userInput = scnr.nextLine();
					searchBy = Integer.parseInt(userInput);
					if (searchBy < 1) {
						throw new Exception("Index too large");
					} else if (searchBy > 4) {
						throw new Exception("Index too small");
					} else {
						break;
					}
				}
				// if exception is caught tells the user invalid input
				catch (Exception e) {
					System.out.println("Invalid input.  Enter 1-4 only.");
				}
			}
			// if the user wants to search by state code this code is executed
			if (searchBy == 1) {
				int stateCode;
				while (true) {
					try {
						// makes sure the user enters an integer for the state code
						System.out.println("Search by state code.  Enter a state code: ");
						stateCode = Integer.parseInt(scnr.next());
						break;
					} catch (Exception e) {
						System.out.println("Integer Not entered please try again.");
					}

				}
				// calls on the search method that searches by state code
				search(stateCode, searchBy);

			}
			// if the user wants to search by state code this code runs
			if (searchBy == 2) {
				System.out.println("Search by state name. Enter a state name: ");
				String stateName = scnr.nextLine();
				// trims the state name so there are not extra spaces at the end
				stateName = stateName.trim();
				// uses the search method to search by state code
				search(stateName, searchBy);
			}
			// if the user wants to search by county code this code runs
			if (searchBy == 3) {
				int countyCode;
				while (true) {
					// makes sure a number is entered for a county code
					try {
						System.out.println("Search by county code. Enter a county code: ");
						countyCode = Integer.parseInt(scnr.next());
						break;
					} catch (Exception e) {
						System.out.println("Integer Not entered please try again.");
					}

				}
				search(countyCode, searchBy);

			}
			// if user wants to search by a county name a string input is entered.
			if (searchBy == 4) {
				System.out.println("Search by county name. Enter a county name: ");
				String countyName = scnr.nextLine();
				countyName = countyName.trim();
				search(countyName, searchBy);

			}

		}
		// if the user wants to sort they are asked how they would like to sort
		if (userChoice == 2) {
			System.out.println("1. State Code");
			System.out.println("2. County Code");
			System.out.println("3. State Name");
			System.out.println("4. County Name");
			System.out.println("5. Confirmed Count (1992-1996)");
			System.out.println("6. Confirmed Count (1997-2001)");
			System.out.println("7. Confirmed Count (2002-2006)");
			System.out.println("8. Confirmed Count (2007-2011)");
			System.out.println();

			Integer primarySort;
			// secondary sort is initialized to -1 so it can be checked later to see if it
			// is the same as primary sort
			Integer secondarySort = -1;
			String primaryOrder;
			String secondaryOrder;

			// asks the user how they would like to primary sort, then ensures that they
			// choose a number 1-8
			while (true) {
				try {
					System.out.println("Enter the primary sort column: ");
					userInput = scnr.nextLine();
					primarySort = Integer.parseInt(userInput);
					if (primarySort > 8) {
						throw new Exception("Index too large");
					}
					if (primarySort < 1) {
						throw new Exception("Index too small");
					}
					break;

				} catch (Exception e) {
					System.out.println("Invalid input.  Enter 1-8 only.");
				}
			}
			// asks the user how they would like their secondary sort to be sorted, and
			// ensures that a number is picked that is 1-8 and not the same as primary sort
			while (true) {
				try {
					System.out.println("Enter the secondary sort column: ");
					userInput = scnr.nextLine();
					secondarySort = Integer.parseInt(userInput);
					if (secondarySort > 8) {
						throw new Exception("Index too large");
					}
					if (secondarySort < 1) {
						throw new Exception("Index too small");
					}
					if (secondarySort.equals(primarySort)) {
						throw new Exception("Same as primary");
					}
					break;

				} catch (Exception e) {
					if (secondarySort.equals(primarySort)) {
						System.out.println("Primary and secondary sort column is the same. Please retry.");
					} else {
						System.out.println("Invalid input.  Enter 1-8 only.");
					}
				}
			}
			// asks the user if they would like to sort in ascending or descending order,
			// then ensures they input a or d
			while (true) {
				try {
					System.out.println("Sort primary column in (a)scending or (d)escending order: (a or d)? ");
					primaryOrder = scnr.next();
					primaryOrder = primaryOrder.trim();
					primaryOrder.toLowerCase();
					boolean isA = primaryOrder.equalsIgnoreCase("a");
					boolean isD = primaryOrder.equalsIgnoreCase("d");
					if (isA) {
						break;
					} else if (isD) {
						break;
					} else {
						throw new Exception("Invalid input.  Enter a or d only.");
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			while (true) {
				try {
					System.out.println("Sort secondary column in (a)scending or (d)escending order: (a or d)? ");
					secondaryOrder = scnr.next();
					secondaryOrder = secondaryOrder.trim();
					secondaryOrder.toLowerCase();
					boolean isA = secondaryOrder.equalsIgnoreCase("a");
					boolean isD = secondaryOrder.equalsIgnoreCase("d");
					if (isA) {
						break;
					} else if (isD) {
						break;
					} else {
						throw new Exception("Invalid input.  Enter a or d only.");
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			// calls on the sort method to sort the data
			sort(primarySort, secondarySort, primaryOrder, secondaryOrder);

		}
	}

	// Generic method that searches with the users input
	public <T extends Comparable<T>> void search(T searchTerm, int searchBy) {
		int length = stateName.size() - 1;
		// int i and k are set up as values that will be used to iterate through lists
		// and save the index the found information is at
		int i = 0;
		int k = 0;
		// variables used to store the information after location is found
		int found96 = 0;
		int found01 = 0;
		int found06 = 0;
		int found11 = 0;

		// if the user wants to search by state code this runs
		if (searchBy == 1) {
			// this compares the state codes that the user searched for with all saved state
			// codes, then when found saves the index of found state code
			while (i < length) {
				Integer sCode = stateCode.get(i);

				if (sCode.equals(searchTerm)) {
					break;
				}

				i++;
			}
			// this uses the index to find out the name of the state associated with the
			// state code
			String foundState = stateName.get(i);
			// this goes through every county, if that county is a part of the found state
			// it adds the count of cases for that state.
			for (k = 0; k < length; k++) {
				if (stateCode.get(k).equals(stateCode.get(i))) {
					found96 += confirmed96.get(k);
					found01 += confirmed01.get(k);
					found06 += confirmed06.get(k);
					found11 += confirmed11.get(k);
				}
			}
			// prints results
			System.out.println(searchTerm + " is " + foundState);
			System.out.println("Count 92-96: " + found96);
			System.out.println("Count 97-01: " + found01);
			System.out.println("Count 02-06: " + found06);
			System.out.println("Count 07-11: " + found11);

		}
		// if user wants to search by state name this runs
		if (searchBy == 2) {
			// iterates through state names until it find a match
			while (i < length) {
				String sName = stateName.get(i);
				if (sName.equals(searchTerm)) {
					break;
				}
				i++;
			}
			// iterates through finding all counties in the state and adds their data
			for (k = 0; k < length; k++) {
				if (stateName.get(k).equals(stateName.get(i))) {
					found96 += confirmed96.get(k);
					found01 += confirmed01.get(k);
					found06 += confirmed06.get(k);
					found11 += confirmed11.get(k);
				}
			}
			// prints out the data
			System.out.println("Count 92-96: " + found96);
			System.out.println("Count 97-01: " + found01);
			System.out.println("Count 02-06: " + found06);
			System.out.println("Count 07-11: " + found11);

		}
		// searches by county code
		if (searchBy == 3) {
			while (i < length) {
				// finds county with matching county code
				Integer cCode = countyCode.get(i);
				if (cCode.equals(searchTerm)) {
					break;
				}
				i++;
			}
			// for every county with the searched county code the data is added
			for (k = 0; k < length; k++) {
				if (countyCode.get(k).equals(countyCode.get(i))) {
					found96 += confirmed96.get(k);
					found01 += confirmed01.get(k);
					found06 += confirmed06.get(k);
					found11 += confirmed11.get(k);

				}
			}
			// prints out data
			System.out.println("Counties with code " + searchTerm);
			System.out.println("Count 92-96: " + found96);
			System.out.println("Count 97-01: " + found01);
			System.out.println("Count 02-06: " + found06);
			System.out.println("Count 07-11: " + found11);

		}
		// if user wants to search by county name this code runs
		if (searchBy == 4) {
			// finds county with this name
			while (i < length) {
				String cName = countyName.get(i);
				if (cName.equals(searchTerm)) {
					break;
				}
				i++;
			}
			// adds up all counties with this name and saves the data
			for (k = 0; k < length; k++) {
				if (countyName.get(k).equals(countyName.get(i))) {
					found96 += confirmed96.get(k);
					found01 += confirmed01.get(k);
					found06 += confirmed06.get(k);
					found11 += confirmed11.get(k);
				}
			}
			// outputs the data
			System.out.println("Count 92-96: " + found96);
			System.out.println("Count 97-01: " + found01);
			System.out.println("Count 02-06: " + found06);
			System.out.println("Count 07-11: " + found11);

		}

	}

	// calls on different methods to find indexes for sorting and sorts primary then secondary
	public void sort(int primary, int secondary, String primaryOrder, String secondaryOrder) {
		//creates an array list of Data objects which hold all the data for each county
		ArrayList<Data> info = new ArrayList<Data>();
		ArrayList<Pairs> indexForSecondary = new ArrayList<Pairs>();
		// adds all the information to the list
		for (int i = 0; i < countyName.size(); i++) {
			info.add(new Data(countyName.get(i), stateName.get(i), countyCode.get(i), stateCode.get(i),
					confirmed96.get(i), confirmed01.get(i), confirmed06.get(i), confirmed11.get(i)));
		}
		// primary sort sorts entire list
		info = sortAt(info, primary, primaryOrder, 0, info.size());
		// finds the indexes to sort the secondary sort
		indexForSecondary = findSecondarySort(info, primary);
		//for the indexes that the primary values are the same runs sortAt with these indexes performing a secondary sort
		for (Pairs i: indexForSecondary) {
			
			int start = i.getStart();
			int end = i.getEnd();
			info = sortAt(info,secondary, secondaryOrder, start, end+1);
		}
		//prints out the information 
		for (Data i : info) {
			i.printData();
		}

	}
	//sorts At indexes given using selection sort primary sort sorts from start to end, secondary sort only sorts at intervals where primary value is the same
	public <T extends Comparable<T>> ArrayList<Data> sortAt(ArrayList<Data> info, int sortBy, String order, int start,
			int end) {
		// creates generic values that will be used to compare values
		T compareVal1;
		T compareVal2;
		// loops used for selection sort
		for (int i = start; i < end; i++) {
			int position = i;
			for (int j = i + 1; j < end; j++) {
				// all the if statements find the values that are to be sorted based on the user specification
				if (sortBy == 1) {
					Integer tempVal = info.get(position).getStateCode();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getStateCode();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 2) {
					Integer tempVal = info.get(position).getCountyCode();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getCountyCode();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 3) {
					String tempVal = info.get(position).getStateName();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getStateName();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 4) {
					String tempVal = info.get(position).getCountyName();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getCountyName();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 5) {
					Integer tempVal = info.get(position).getConfirmed96();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getConfirmed96();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 6) {
					Integer tempVal = info.get(position).getConfirmed01();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getConfirmed01();
					compareVal1 = (T) tempVal;
				} else if (sortBy == 7) {
					Integer tempVal = info.get(position).getConfirmed06();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getConfirmed06();
					compareVal1 = (T) tempVal;
				} else {
					Integer tempVal = info.get(position).getConfirmed11();
					compareVal2 = (T) tempVal;
					tempVal = info.get(j).getConfirmed11();
					compareVal1 = (T) tempVal;
				}
				// if the user wants the sort to be in ascending order, the first value is checked to be less than 
				if (order.equals("a")) {
					if (compareVal1.compareTo(compareVal2) < 0) {
						position = j;
					}
				}
				
				if (order.equals("A")) {
					if (compareVal1.compareTo(compareVal2) < 0) {
						position = j;
					}
				}
				// if the user wants to sort descending order,the first value is checked to be less than
				if (order.equals("d")) {
					if (compareVal1.compareTo(compareVal2) > 0) {
						position = j;
					}
				}
				if (order.equals("D")) {
					if (compareVal1.compareTo(compareVal2) > 0) {
						position = j;
					}
				}

			}
			//swaps the positions of found value that is lower(or higher based on user input) and original value to sort the list
			Collections.swap(info, i, position);

		}
		return (info);

	}

	public <T extends Comparable<T>> ArrayList<Pairs> findSecondarySort(ArrayList<Data> info, int primary) {
		int startIndex, endIndex;
		startIndex = 0;
		endIndex = 1;
		int length = info.size();
		T primaryVal1, primaryVal2;

		ArrayList<Pairs> startEnd = new ArrayList<Pairs>();

		while (startIndex < length - 1) {

			int position = startIndex;
			if (primary == 1) {
				Integer tempVal = info.get(position).getStateCode();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getStateCode();
				primaryVal1 = (T) tempVal;
			} else if (primary == 2) {
				Integer tempVal = info.get(position).getCountyCode();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getCountyCode();
				primaryVal1 = (T) tempVal;
			} else if (primary == 3) {
				String tempVal = info.get(position).getStateName();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getStateName();
				primaryVal1 = (T) tempVal;
			} else if (primary == 4) {
				String tempVal = info.get(position).getCountyName();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getCountyName();
				primaryVal1 = (T) tempVal;
			} else if (primary == 5) {
				Integer tempVal = info.get(position).getConfirmed96();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getConfirmed96();
				primaryVal1 = (T) tempVal;
			} else if (primary == 6) {
				Integer tempVal = info.get(position).getConfirmed01();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getConfirmed01();
				primaryVal1 = (T) tempVal;
			} else if (primary == 7) {
				Integer tempVal = info.get(position).getConfirmed06();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getConfirmed06();
				primaryVal1 = (T) tempVal;
			} else {
				Integer tempVal = info.get(position).getConfirmed11();
				primaryVal2 = (T) tempVal;
				tempVal = info.get(startIndex + 1).getConfirmed11();
				primaryVal1 = (T) tempVal;
			}
			if (primaryVal1.equals(primaryVal2)) {
				for (int j = startIndex + 1; j < length; j++) {
					if (primary == 1) {
						Integer tempVal = info.get(position).getStateCode();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getStateCode();
						primaryVal1 = (T) tempVal;
					} else if (primary == 2) {
						Integer tempVal = info.get(position).getCountyCode();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getCountyCode();
						primaryVal1 = (T) tempVal;
					} else if (primary == 3) {
						String tempVal = info.get(position).getStateName();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getStateName();
						primaryVal1 = (T) tempVal;
					} else if (primary == 4) {
						String tempVal = info.get(position).getCountyName();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getCountyName();
						primaryVal1 = (T) tempVal;
					} else if (primary == 5) {
						Integer tempVal = info.get(position).getConfirmed96();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getConfirmed96();
						primaryVal1 = (T) tempVal;
					} else if (primary == 6) {
						Integer tempVal = info.get(position).getConfirmed01();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getConfirmed01();
						primaryVal1 = (T) tempVal;
					} else if (primary == 7) {
						Integer tempVal = info.get(position).getConfirmed06();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getConfirmed06();
						primaryVal1 = (T) tempVal;
					} else {
						Integer tempVal = info.get(position).getConfirmed11();
						primaryVal2 = (T) tempVal;
						tempVal = info.get(j).getConfirmed11();
						primaryVal1 = (T) tempVal;
					}
					if (primaryVal2.equals(primaryVal1)) {
						endIndex = j;
					}
				}
				startEnd.add(new Pairs(startIndex, endIndex));
				startIndex = endIndex;
			} else {
				startIndex += 1;
			}

		}

		return (startEnd);

	}

	// Utility which converts CSV to ArrayList using Split operation
	public static ArrayList<String> CSVtoArrayList(String CSVFileName) {

		ArrayList<String> arrlist = new ArrayList<String>();

		if (CSVFileName != null) {
			String[] splitData = CSVFileName.split("\\,", -1); // the -1 helps handle the null values

			for (int i = 0; i < splitData.length; i++) {
				// if it is null, replace it with a 0
				if (splitData[i].length() == 0) {
					splitData[i] = "0";
				}
				// as long as it is not null and the length is not 0, trim the value and add it
				// to the arraylist
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					arrlist.add(splitData[i].trim());
				}
			}
		}
		return arrlist;
	}
}
