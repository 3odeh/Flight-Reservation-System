// Name : Abdallah Audeh
// Program : Flight Reservation System III

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Flight {

	// The constant unique key of the methods
	public static final int RESERVE_SEAT_KEY = 0;
	public static final int DELETE_SEAT_KEY = 1;

	// The seats of plane in ragged array
	private static Seat[][] seats;

	// The object of class Scanner
	public static Scanner scanner;

	// The object of class File
	public static File file;

	// This is main method that do the program
	public static void main(String[] args) {

		// This try to avoid the runtime errors and do not stop the program
		try {
			// Flag the object of File to read and write on the passengers file
			file = new File("passengers");

			// This if to create new file if the passengers file deleted
			if (file.createNewFile()) {
				System.out.println("\n--There is no passengers file and we make a new one\n");
			}

		}
		// This catch to print the errors if it happen
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Flag the object of scanner to get values form the user
		scanner = new Scanner(System.in);

		// Initialize values of the ragged array that reflecting the plane seats
		seats = new Seat[13][];
		for (int i = 1; i <= 12; i++) {
			if (i < 4)
				seats[i] = new Seat[3];
			else
				seats[i] = new Seat[5];
		}
		for (int i = 1; i < seats.length; i++)
			for (int j = 1; j < seats[i].length; j++) {
				switch (j) {
				case 1: {
					seats[i][j] = new Seat("A" + i);
					break;
				}
				case 2: {
					if (i < 4)
						seats[i][j] = new Seat("C" + i);
					else
						seats[i][j] = new Seat("B" + i);
					break;
				}
				case 3: {
					seats[i][j] = new Seat("C" + i);
					break;
				}
				case 4: {
					seats[i][j] = new Seat("D" + i);
					break;
				}
				}
			}

		// Ask the user what the operations he wants to do
		do {
			int n;
			// This try and catch to handle if the user does not enter the integer
			try {
				System.out.print(
						"You can do these operations:\n1:Read the the passengers file.\n2:Reserve seat.\n3:Delete the reserved seat."
								+ "\n4:Delete all reserved seats.\n5:Update the passengers file.\n6:Quit.\n7:Print out flight seats map.\nPlease enter the number of operation:");
				n = new Scanner(System.in).nextInt();

				switch (n) {
				case 1:// This case to read data from passengers file and save data in the ragged array
					readPassengersFile();
					break;
				case 2:// This case to reserve seat
					reserveSeat();
					break;
				case 3:// This case to delete reserved seat
					deleteReservedSeat();
					break;
				case 4:// This case to delete all reserved seat
					deleteAllReserves();
					break;
				case 5:// This case to write data on the passengers file
					updatePassengersFile();
					break;
				case 6:// This case to out from program
					exit();
					break;
				case 7:// This case to print the flight seats map
					printMapOfPlane();
					break;
				default:// If user does not enter number between 1 and 7
					System.out.println("\n--Please enter the number of opreation (1-7)\n");
				}
			} catch (Exception e) {
				System.out.println("\n--Please enter the number of opreation (1-7)\n");
			}
		} while (true);

	}

	// This method read data form passenger file after checked
	public static void readPassengersFile() {

		System.out.println();

		// This try to avoid the runtime errors and do not stop the program
		try {
			// Make object from scanner class
			Scanner fileReader = new Scanner(file);

			// This if to leave the method if the file is empty
			if (!fileReader.hasNextLine()) {
				System.out.println("\n--The file is empty,There is no data to read it\n");
				// Close the object of scanner class
				fileReader.close();
				return;
			}

			// This loop to read number of seats after check and names of reserve seats and
			// save it in
			// the object in the array
			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				if (data.isEmpty())
					continue;
				String[] inf = data.split(":");
				try {
					if (!Seat.isValid(inf[0]))
						continue;
					for (int i = 1; i < seats.length; i++)
						for (int j = 1; j < seats[i].length; j++) {
							if (seats[i][j].getSeatNumber().equals(inf[0]))
								if (seats[i][j].isEmpty())
									seats[i][j].setPassengerName(inf[1]);
						}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
			// After get data,I tell user the operation is success
			System.out.println("\n--Get data from the passengers file is successfully\n");

			// Close the object of scanner class
			fileReader.close();
		}
		// This catch to print the errors if it happen
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// This method to reserve the selected seat
	public static void reserveSeat() {

		// This if to check if we have empty seat to reserve it
		if (numberOfReservedSeats() == 42) {
			System.out.println("\n--All seats is reserved\n");
			return;
		}

		// Get number of seat from getNumberSeat method that checked
		String num = getNumberSeat(RESERVE_SEAT_KEY);

		// this if to leave the method if the user enter 0 in getNumberOfSeat method to
		// back to the operations
		if (num == null)
			return;

		// Get reserve name from the user
		String name;
		System.out.print("--Please enter the reserved name :");
		name = new Scanner(System.in).nextLine();

		// Reserve the number of seat with the name of reserve
		seats[Seat.getRow(num)][Seat.getColumn(num)].setPassengerName(name);

		// After reserve seat,I tell user the operation is success
		System.out.println("--The seat has been successfully reserved\n");
	}

	// This method to delete the selected reserved seat
	public static void deleteReservedSeat() {

		// This if to check if we have reserved seats to delete it
		if (numberOfReservedSeats() == 0) {
			System.out.println("\n--There is not reserved seats to delete it\n");
			return;
		}

		// Get number of seat from getNumberSeat method that checked
		String num = getNumberSeat(DELETE_SEAT_KEY);

		// this if to leave the method if the user enter 0 in getNumberOfSeat method to
		// back to the operations
		if (num == null)
			return;

		// delete the number of reserved seat has entered with the name of reserved
		seats[Seat.getRow(num)][Seat.getColumn(num)].setPassengerName(null);

		// After delete reserved seat,I tell user the operation is success
		System.out.println("\n--The reserved seat has been successfully deleted\n");
	}

	// This method to delete all reserved seats
	public static void deleteAllReserves() {

		// This if to check if we have reserved seats to delete it
		if (numberOfReservedSeats() == 0) {
			System.out.println("\n--There is not reserved seats to delete it\n");
			return;
		}

		// This loop to delete all reserve seats with with the name of reserved
		for (int i = 1; i < seats.length; i++)
			for (int j = 1; j < seats[i].length; j++)
				seats[i][j].setPassengerName(null);

		// After delete all reserved seats,I tell user the operation is success
		System.out.println("\n--All of the reserved seats has been deleted\n");

	}

	// This method to write data form the array and save it in the passengers file
	public static void updatePassengersFile() {

		// This try to avoid the runtime errors and do not stop the program
		try {
			// Make object from print writer class
			PrintWriter printWriter = new PrintWriter(file);

			// This loop to write number of seats and names of reserve seats from the array
			// on the passenger file
			for (int i = 1; i < seats.length; i++)
				for (int j = 1; j < seats[i].length; j++)
					try {
						if (seats[i][j].isReserved())
							printWriter.println(seats[i][j].toString());
					} catch (Exception e) {
						continue;
					}

			// After write data,I tell user the operation is success
			System.out.println("\n--Update data on the passenger file is successfully\n");

			// Close the object of print writer class
			printWriter.close();
		}
		// This catch to print the errors if it happen
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// This method to end the program and print to user good bye
	public static void exit() {
		// After end the program ,I thanks the user
		System.out.println("\n--Thank you for using my program\n--Have a nice day\n--Good bye");
		// This method to close the scanner
		scanner.close();
		// This method to end the program
		System.exit(0);
	}

	// This method to get the number of reserved seats
	private static int numberOfReservedSeats() {
		int count = 0;
		// this loop to check the reserved seats and get count of them
		for (int i = 1; i < seats.length; i++)
			for (int j = 1; j < seats[i].length; j++)
				try {
					if (seats[i][j].isReserved())
						count++;
				} catch (Exception e) {
					continue;
				}
		return count;
	}

	// This method to print the map of plane
	private static void printMapOfPlane() {

		System.out.println("\n");
		for (int i = 1; i < seats.length; i++) {

			if (i == 4)
				System.out.println("\n");
			if (i == 8)
				System.out.println("\n");
			if (i < 4)
				System.out.print("   \t\t");
			System.out.print("\t");
			for (int j = 1; j < seats[i].length; j++) {
				System.out.print(seats[i][j].toString() + "\t");
				if ((i < 4 && j == 1) || (i >= 4 && j == 2))
					System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	// This method to check the entered number seat in plane (is valid) and if the
	// user can do operations on it
	private static String getNumberSeat(int key) {

		// Ask the user to enter the number of seat
		System.out.print("\n--If you want to back enter 0\n--Please enter the seat number :");
		String seatNumber = scanner.next();
		do {
			// This if to leave the method if user has enter 0
			if (seatNumber.equals("0")) {
				System.out.println();
				return null;
			}
			// This try and catch to check if the seat number is ascceptable or not
			try {
				// This if to check the entered number is in the plane
				if (Seat.isValid(seatNumber)) {
					// This if to know what operate want to do on seat
					if (key == RESERVE_SEAT_KEY) {
						// The operate is reserve seat
						// This if to check if the seat is empty
						if (seats[Seat.getRow(seatNumber)][Seat.getColumn(seatNumber)].isEmpty()) {
							return seatNumber;
						}
					} else if (key == DELETE_SEAT_KEY) {
						// The operate is delete reserve seat
						// This if to check if the seat is empty
						if (seats[Seat.getRow(seatNumber)][Seat.getColumn(seatNumber)].isReserved()) {
							return seatNumber;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
				System.out.print("--Please enter the number of seat again:");
				seatNumber = scanner.next();
			}
		} while (true);
	}
}
