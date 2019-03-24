package animation;

public class Sun {
	private double rA;
	private double dec;
	private double mA;
	private double mL;
	private double x;
	private double y;
	private double z;
	private double lS;
	
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
		lS = rev(v + w);															// longitude of the sun
		x = r * Math.cos(lS/radeg);														// ecliptic rectangular geocentric coordinates
		y = r * Math.sin(lS/radeg);
		z = 0.0; 
		double yEq = y*Math.cos(oE/radeg) - z*Math.sin(oE/radeg);						// equatorial rectangular geocentric coordinates
		double zEq = y*Math.sin(oE/radeg) + z*Math.cos(oE/radeg);
		
		rA = rev(radeg * Math.atan2(yEq,x));											// right ascension of the sun
		dec = radeg * Math.atan2(zEq,Math.sqrt(x*x + yEq*yEq));							// declination of the sun
	}
	
	private double rev(double x) {
		return  x - Math.floor(x/360.0)*360.0;
    }
	
	public double getRA(){
		return rA;
	}
	
	public double getDec(){
		return dec;
	}
	
	public double getMA(){
		return mA;
	}
	
	public double getML(){
		return mL;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public double getLS() {
		return lS;
	}
}
