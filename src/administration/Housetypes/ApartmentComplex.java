package administration.Housetypes;

public class ApartmentComplex extends House{

	private boolean fireExitStairs;

	public ApartmentComplex(int flats,  int birthday) {
		super(flats, birthday);

		if (flats >= 10) {
			this.fireExitStairs = true;
		} 
		else {
			this.fireExitStairs = false;
		}
	}

	public boolean getFireExitStairs(){
		return this.fireExitStairs;
	}
}