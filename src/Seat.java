// This class reflecting the seats in flight reservation system III
public class Seat {

	// Attributes of class Seat
	private String seatNumber;
	private String passengerName;

	// Constructor that take seat number to make objects
	public Seat(String seatNumber) throws IllegalArgumentException {
		if (isValid(seatNumber)) {
			this.seatNumber = seatNumber;
			passengerName =null;
		} else
			throw exception(seatNumber);
	}

	// This method to get the passenger name
	public String getPassengerName() {
		return passengerName;
	}

	// This method to change the passenger name
	public void setPassengerName(String passengerName) {
		if (passengerName == null)
			this.passengerName = passengerName;
		else
			this.passengerName = passengerName.trim();
	}

	// This method to get seat number
	public String getSeatNumber() {
		return seatNumber;
	}

	// This method to change seat number
	public void setSeatNumber(String seatNumber) throws IllegalArgumentException {
		if (isValid(seatNumber))
			this.seatNumber = seatNumber;
		else
			throw exception(seatNumber);
	}

	// This method to get row of seat number
	public static int getRow(String seatNumber) throws IllegalArgumentException {
		if (isValid(seatNumber))
			return (Integer.parseInt(seatNumber.substring(1)));
		throw exception(seatNumber);
	}

	// This method to get column of seat number
	public static int getColumn(String seatNumber) throws IllegalArgumentException {
		if (isValid(seatNumber)) {
			int column = seatNumber.charAt(0) - 64;
			if (getRow(seatNumber) < 4 && column == 3)
				column = 2;
			return column;
		}
		throw exception(seatNumber);

	}

	// This method check if the number seat is valid in plane
	public static boolean isValid(String seatNumber) throws IllegalArgumentException {
		for (int i = 1; i <= 12; i++) {
			if (seatNumber.equals("A" + i)) {
				return true;
			}
			if (seatNumber.equals("B" + i) && i >= 4) {
				return true;
			}
			if (seatNumber.equals("C" + i)) {
				return true;
			}
			if (seatNumber.equals("D" + i) && i >= 4) {
				return true;
			}
		}
		throw exception(seatNumber);
	}

	// This method to check if the seat is empty of not
	public boolean isEmpty() throws IllegalArgumentException {
		if (passengerName == null)
			return true;
		throw new IllegalArgumentException("--The seat number " + seatNumber + " is reserved!!");
	}

	// This method to check if the seat is reserved of not
	public boolean isReserved() throws IllegalArgumentException {
		if (passengerName != null)
			return true;
		throw new IllegalArgumentException("--The seat number " + seatNumber + " is empty!!");
	}

	// This method to print the value of attributes in this class
	public String toString() {
		return seatNumber + ": " + passengerName;
	}

	// This method to get object from IllegalArgumentException class
	private static IllegalArgumentException exception(String seatNumber) {
		return new IllegalArgumentException("--The seat number " + seatNumber + " is not valid!!");
	}

}
