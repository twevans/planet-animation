package animation;

/**
 * 
 * @author twevans
 *
 */
public class Sun {
	private double rA;
	private double dec;
	private double mA;
	private double mL;
	private double x;
	private double y;
	private double z;
	private double lS;
	
	/**
	 * This constructs a sun object
	 * @param d day number
	 */
	public Sun(double d){
		double w = rev(282.9404 + 4.70935 * Math.pow(10,-5) * d); 						// longitude of perihelion
		double a = 1.000000;									  						// mean distance, a.u.
		double e = rev(0.016709 - 1.151 * Math.pow(10,-9) * d);   						// eccentricity
		mA = rev(356.0470 + 0.9856002585 * d);			  								// mean anomaly
		double oE = rev(23.4393 - 3.563 * Math.pow(10,-7) * d);   						// obliquity of the ecliptic
		mL = rev(w + mA);								      							// mean longitude of the sun
		double radeg = 180/Math.PI;
		double eA = rev(mA + radeg*e*Math.sin(mA/radeg)*(1+e*Math.cos(mA/radeg)));		// eccentric anomaly
		x = Math.cos(eA/radeg) - e;										
		y = Math.sin(eA/radeg) * Math.sqrt(1-e*e);
		double r = Math.sqrt(x*x + y*y);
		double v = rev(radeg*Math.atan2(y,x));
		lS = rev(v + w);															    // longitude of the sun
		x = r * Math.cos(lS/radeg);														// ecliptic rectangular geocentric coordinates
		y = r * Math.sin(lS/radeg);
		z = 0.0; 
		double yEq = y*Math.cos(oE/radeg) - z*Math.sin(oE/radeg);						// equatorial rectangular geocentric coordinates
		double zEq = y*Math.sin(oE/radeg) + z*Math.cos(oE/radeg);
		
		rA = rev(radeg * Math.atan2(yEq,x));											// right ascension of the sun
		dec = radeg * Math.atan2(zEq,Math.sqrt(x*x + yEq*yEq));							// declination of the sun
	}
	
	/**
	 * This returns an angle between 0 and 360 degrees
	 * @param x any angle
	 * @return an angle between 0 and 360 degrees
	 */
	private double rev(double x) {
		return  x - Math.floor(x/360.0)*360.0;
    }
	
	/** 
	 * This returns the right ascension
	 * @return right ascension
	 */
	public double getRA(){
		return rA;
	}
	
	/** 
	 * This returns the declination
	 * @return declination
	 */
	public double getDec(){
		return dec;
	}
	
	/**
	 * This returns the mean anomaly
	 * @return mean anomaly
	 */
	public double getMA(){
		return mA;
	}
	
	/**
	 * This returns the mean longitude
	 * @return mean longitude
	 */
	public double getML(){
		return mL;
	}
	
	/**
	 * This returns the ecliptic rectangular geocentric x coordinate
	 * @return ecliptic rectangular geocentric x coordinate
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * This returns the ecliptic rectangular geocentric y coordinate
	 * @return ecliptic rectangular geocentric y coordinate
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * This returns the ecliptic rectangular geocentric z coordinate
	 * @return ecliptic rectangular geocentric z coordinate
	 */
	public double getZ(){
		return z;
	}
	
	/**
	 * This returns the longitude of the sun
	 * @return longitude of the sun
	 */
	public double getLS() {
		return lS;
	}
}
