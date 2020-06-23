package administration.Housetypes;

public abstract class House {
	private  int flats;
    private int occupiedFlats;
    private  int birthday;
	
	public boolean checkFullHouse() {
        return this.occupiedFlats == this.flats;
    }

    public boolean checkEmptyHouse() {
        return this.occupiedFlats == 0;
    }

    public boolean checkWreck( int year) {
        return this.checkEmptyHouse() == true && year - this.birthday >= 5;
    }

    public int getFlats() {
        return this.flats;
    }

    public int getOccupiedFlats() {
        return this.occupiedFlats;
    }

    public int getEmptyFlats() {
        return this.flats - this.occupiedFlats;
    }

    public void setOccupiedFlats( int occupiedFlats) {
        this.occupiedFlats = occupiedFlats;
	}
	
	public House(int flats, int birthday){
		this.flats = flats;
		this.birthday = birthday;
	}
}