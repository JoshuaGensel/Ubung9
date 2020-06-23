package administration;

import administration.Housetypes.*;

public class Street {

	
	/**
	 * null in Haus-Array stands for empty space
	 */
	private House[] houses;
	public int size;

	Street(int size) {
		this.houses = new House[size];
		this.size = size;
	}

	/**
	 * Returns if building process was successful
	 */
	public boolean buildHouse(int houseNumber, House house) {

		if (this.houses[houseNumber] == null) {
			this.houses[houseNumber] = house;
			return true;
		}

		return false;
	}

	public House[] getHouses() {
		return this.houses;
	}

	public int getStreetSize(){
		return this.size;
	}

}
