package animation;

/**
 * 
 * @author twevans
 *
 */
public class Moon {
	private double rA;
	private double dec;
	private double lon;
	private double lat;
	
	/**
	 * This constructs a moon object
	 * @param d day number
	 * @param lS longitude of the sun
	 * @param mS mean anomaly of the sun
	 */
	public Moon (double d, double lS, double mS) {
		double n = rev(125.1228 - 0.0529538083 * d); 	// longitude of the ascending node
		double i = 5.1454;								// inclination
		double w = rev(318.0634 + 0.1643573223 * d);	// longitude of perihelion
		double a = 60.2666;								// mean distance, a.u.
		double e = 0.054900;							// eccentricity
		double m = rev(115.3654 + 13.0649929509 * d);	// mean anomaly
		double radeg = 180/Math.PI;
		double e0 = m + (180/Math.PI)*e*Math.sin(m/radeg)*(1+e*Math.cos(m/radeg)); 
		double e1 = e0 - (e0 - (180/Math.PI)*e*Math.sin(e0/radeg)-m) / (1-e*Math.cos(e0/radeg));
		
		while (Math.abs(e1 - e0) > 0.005) {
			e0 = e1;
			e1 = e0 - (e0 - (180/Math.PI)*e*Math.sin(e0/radeg)-m) / (1-e*Math.cos(e0/radeg));
		}
		
		double x = a * (Math.cos(e1/radeg) - e);
		double y = a * Math.sqrt(1-e*e) * Math.sin(e1/radeg);
		double r = Math.sqrt(x*x + y*y);
		double v = rev(radeg*Math.atan2(y,x));
		double xEc = r * ( Math.cos(n/radeg) * Math.cos((v+w)/radeg) - Math.sin(n/radeg) * Math.sin((v+w)/radeg) * Math.cos(i/radeg) );
		double yEc = r * ( Math.sin(n/radeg) * Math.cos((v+w)/radeg) + Math.cos(n/radeg) * Math.sin((v+w)/radeg) * Math.cos(i/radeg) );
		double zEc = r * Math.sin((v+w)/radeg) * Math.sin(i/radeg);
		lon = rev(radeg*Math.atan2(yEc, xEc));
		lat = rev(radeg*Math.atan2(zEc, Math.sqrt(xEc*xEc+yEc*yEc)));
		double revLS = rev(lS);
		double revLM = rev(n + w + m);
		double revMS = rev(mS);
		double revMM = rev(m);
		double revD  = rev(revLM - revLS);
		double revF  = rev(revLM - n);
		double[] pLong = new double[12];
		double[] pLat  = new double[5];
		double[] pDist = new double[2];
		pLong[0]  = -1.274 * Math.sin((revMM - 2*revD)/radeg);
		pLong[1]  =  0.658 * Math.sin(2*revD/radeg);
		pLong[2]  = -0.186 * Math.sin(revMS/radeg);
		pLong[3]  = -0.059 * Math.sin((2*revMM - 2*revD)/radeg);
		pLong[4]  = -0.057 * Math.sin((revMM - 2*revD + revMS)/radeg);
		pLong[5]  =  0.053 * Math.sin((revMM + 2*revD)/radeg);
		pLong[6]  =  0.046 * Math.sin((2*revD - revMS)/radeg);
		pLong[7]  =  0.041 * Math.sin((revMM - revMS)/radeg);
		pLong[8]  = -0.035 * Math.sin(revD/radeg);
		pLong[9]  = -0.031 * Math.sin((revMM + revMS)/radeg);
		pLong[10] = -0.015 * Math.sin((2*revF - 2*revD)/radeg);
		pLong[11] =  0.011 * Math.sin((revMM - 4*revD)/radeg);
		pLat[0] = -0.173 * Math.sin((revF - 2*revD)/radeg);
		pLat[1] = -0.055 * Math.sin((revMM - revF - 2*revD)/radeg);
		pLat[2] = -0.046 * Math.sin((revMM + revF - 2*revD)/radeg);
		pLat[3] = 0.033 * Math.sin((revF + 2*revD)/radeg);
		pLat[4] = 0.017 * Math.sin((2*revMM + revF)/radeg);
		pDist[0] = -0.58 * Math.cos((revMM - 2*revD)/radeg);
		pDist[1] = -0.46 * Math.cos(2*revD/radeg);
		double pLongSum = 0.0;
		double pLatSum  = 0.0;
		
		for (int j=0; j<12; j++){
				pLongSum = pLongSum + pLong[j];
		}
		
		for (int j=0; j<5; j++){
				pLatSum = pLatSum + pLat[j];
		}
		
		double pDistSum = pDist[0] + pDist[1];
		lon = lon + pLongSum; 		
		lat = lat + pLatSum - 360; 
		double dist2 = r + pDistSum; 		
		double x2 = Math.cos(lon/radeg) * Math.cos(lat/radeg);
    	double y2 = Math.sin(lon/radeg) * Math.cos(lat/radeg);
    	double z2 = Math.sin(lat/radeg);
    	double oEcl = 23.4406;
    	double xe = x2;
    	double ye = y2*Math.cos(oEcl/radeg) - z2*Math.sin(oEcl/radeg);
    	double ze = y2*Math.sin(oEcl/radeg) + z2*Math.cos(oEcl/radeg);
    	rA  = rev(radeg*Math.atan2( ye, xe ));
    	dec = radeg*Math.atan2( ze, Math.sqrt(xe*xe+ye*ye) ); 
    	double lST = 221.8388;
    	double mPar = radeg*Math.asin(1/dist2);
		double gClat = 60 - 0.1924*Math.sin(2*60/radeg);
		double rho   = 0.99833 + 0.00167*Math.cos(2*60/radeg);		
		double hA = rev(lST - rA);
		double g = radeg*Math.atan(Math.tan(gClat/radeg)/Math.cos(hA/radeg));		
		rA   = rA   - mPar*rho*Math.cos(gClat/radeg)*Math.sin(hA/radeg)/Math.cos(dec/radeg);
    	dec  = dec  - mPar*rho*Math.sin(gClat/radeg)*Math.sin((g - dec)/radeg)/Math.sin(g/radeg); 
	}

	/**
	 * This returns an angle between 0 and 360 degrees
	 * @param x any angle
	 * @return an angle between 0 and 360 degrees
	 */
	public double rev(double x) {
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
	 * This returns the longitude
	 * @return longitude
	 */
	public double getLon(){
		return lon;
	}
	
	/**
	 * This returns the latitude
	 * @return latitude
	 */
	public double getLat(){
		if(lat > -180) {
			return lat;
		} else {
			return lat + 360.0;
		}
		
	}
}
