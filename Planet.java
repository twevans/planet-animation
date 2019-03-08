package animation;

public class Planet{

	String name;
	
	public static final String[] names = {"Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
	
	public static final double[] mercury    = {48.3313, 7.0047, 29.1241, 0.387098, 0.205635, 168.6562};
	public static final double[] mercuryDot = {3.24587*Math.pow(10,-5), 5.0*Math.pow(10,-8), 1.01444*Math.pow(10,-5), 0.0, 5.59*Math.pow(10,-10), 4.0923344368};
	public static final double[] venus      = {76.6799, 3.3946, 54.8910, 0.723330, 0.006773, 48.052};
	public static final double[] venusDot   = {2.46590*Math.pow(10,-5), 2.75*Math.pow(10,-8), 1.38374*Math.pow(10,-5), 0.0, -1.302*Math.pow(10,-9), 1.6021302244};
	public static final double[] mars	    = {49.5574, 1.8497, 286.5016, 1.523688, 0.093405, 18.6021};
	public static final double[] marsDot    = {2.11081*Math.pow(10,-5), -1.78*Math.pow(10,-8), 2.92961*Math.pow(10,-5), 0.0, 2.516*Math.pow(10,-9), 0.5240207766};
	public static final double[] jupiter    = {100.4542, 1.3030, 273.8777, 5.20256, 0.048498, 19.8950};
	public static final double[] jupiterDot = {2.76854*Math.pow(10,-5), -1.557*Math.pow(10,-7), 1.64505*Math.pow(10,-5), 0.0, 4.469*Math.pow(10,-9), 0.0830853001};
	public static final double[] saturn     = {113.6634, 2.4886, 339.3939, 9.55475, 0.055546, 316.9670};
	public static final double[] saturnDot  = {2.38980*Math.pow(10,-5), -1.081*Math.pow(10,-7), 2.97661*Math.pow(10,-5), 0.0, -9.499*Math.pow(10,-9), 0.0334442282};
	public static final double[] uranus     = {74.0005, 0.7733, 96.6612, 19.18171, 0.047318, 142.5905};
	public static final double[] uranusDot  = {1.3978*Math.pow(10,-5), 1.9*Math.pow(10,-8), 3.0565*Math.pow(10,-5), -1.55*Math.pow(10,-8), 7.45*Math.pow(10,-9), 0.011725806};
	public static final double[] neptune    = {131.7806, 1.7700, 272.8461, 30.05826, 0.008606, 260.2471};
	public static final double[] neptuneDot = {3.0173*Math.pow(10,-5), -2.55*Math.pow(10,-7), -6.027*Math.pow(10,-6), 3.313*Math.pow(10,-8), 2.15*Math.pow(10,-9), 0.005995147};
	
	private double radeg = 180/Math.PI;
	private double oblecl = 23.4406;
	private double lon;
	private double lat;
	private double M;
	private double Mj;
	private double Ms;
	private double Mu;
	private double[] jLongTerms = new double[7];
	private double[] sLongTerms = new double[5];
	private double[] sLatTerms  = new double[2];
	private double[] uLongTerms = new double[3];
	private double sum;
	private double sx;
	private double sy;
	private double sz;
	private double xeclip;
	private double yeclip;
	private double zeclip;
	private double rA;
	private double dec;
	
	public Planet (double d, String str, double[] p, double[] pDot, double d1, double d2, double d3) {
		name = str;
		sx = d1;
		sy = d2;
		sz = d3;
		double N = p[0] + pDot[0]*d;		
		double i = p[1] + pDot[1]*d;
		double w = p[2] + pDot[2]*d; 
		double a = p[3] + pDot[3]*d;
		double e = p[4] + pDot[4]*d;
		M = p[5] + pDot[5]*d;
		double E0 = M + (180/Math.PI)*e*Math.sin(M/radeg)*(1+e*Math.cos(M/radeg)); 
		double E1 = E0 - (E0 - (180/Math.PI)*e*Math.sin(E0/radeg)-M) / (1-e*Math.cos(E0/radeg));

		while (Math.abs(E1 - E0) > 0.005) {
			E0 = E1;
			E1 = E0 - (E0 - (180/Math.PI)*e*Math.sin(E0/radeg)-M) / (1-e*Math.cos(E0/radeg));

		}

		double x = a * (Math.cos(E1/radeg) - e);
		double y = a * Math.sqrt(1-e*e) * Math.sin(E1/radeg);
		double r = Math.sqrt(x*x + y*y);
		double v = rev(radeg*Math.atan2(y,x));
		xeclip = r * ( Math.cos(N/radeg) * Math.cos((v+w)/radeg) - Math.sin(N/radeg) * Math.sin((v+w)/radeg) * Math.cos(i/radeg) );
		yeclip = r * ( Math.sin(N/radeg) * Math.cos((v+w)/radeg) + Math.cos(N/radeg) * Math.sin((v+w)/radeg) * Math.cos(i/radeg) );
		zeclip = r * Math.sin((v+w)/radeg) * Math.sin(i/radeg);
		double lon = rev(radeg*Math.atan2(yeclip, xeclip));
		double lat = rev(radeg*Math.atan2(zeclip, Math.sqrt(xeclip*xeclip+yeclip*yeclip)));
		
	}

	public double rev(double x) {
		return  x - Math.floor(x/360.0)*360.0;
	}
	
	public double sumArray(double[] array) {
		sum = 0.0;
		
		for(int k=0; k<array.length; k++) {
			sum = sum + array[k];
		}

		return sum;
	}
	
	public void setM(double d1, double d2, double d3) {
		Mj = d1;
		Ms = d2;
		Mu = d3;
	}
	
	public double getM(){
		return M;
	}
	
	public double getRA(){
		return rA;
	}
	
	public double getDec(){
		return dec;
	}
	
	public String getName(){
		return name;
	}
	
	public void adjustJLong() {
		jLongTerms[0] = -0.332*Math.sin((2*Mj-5*Ms-67.6)/radeg);
		jLongTerms[1] = -0.056*Math.sin((2*Mj-2*Ms+21.0)/radeg);
		jLongTerms[2] =  0.042*Math.sin((3*Mj-5*Ms+21.0)/radeg);
		jLongTerms[3] = -0.036*Math.sin((Mj-2*Ms)/radeg);
		jLongTerms[4] =  0.022*Math.cos((Mj-Ms)/radeg);
		jLongTerms[5] =  0.023*Math.sin((2*Mj-3*Ms+52.0)/radeg);
		jLongTerms[6] = -0.016*Math.sin((Mj-5*Ms-69.0)/radeg);
		lon = lon + sumArray(jLongTerms);
	}
	
    public void adjustSLong() {
    	sLongTerms[0] =  0.812*Math.sin((2*Mj-5*Ms-67.6)/radeg);
    	sLongTerms[1] = -0.229*Math.cos((2*Mj-4*Ms-2.0)/radeg);
    	sLongTerms[2] =  0.119*Math.sin((Mj-2*Ms-3.0)/radeg);
    	sLongTerms[3] =  0.046*Math.sin((2*Mj-6*Ms-69.0)/radeg);
    	sLongTerms[4] =  0.014*Math.sin((Mj-3*Ms+32.0)/radeg);
    	lon = lon + sumArray(sLongTerms);
    }

	public void adjustSLat() {
		sLatTerms[0] = -0.020*Math.cos((2*Mj-4*Ms-2.0)/radeg);
		sLatTerms[1] =  0.018*Math.sin((2*Mj-6*Ms-49.0)/radeg);
		lat = lat + sumArray(sLatTerms);
	} 

	public void adjustULong() {
		uLongTerms[0] =  0.040*Math.sin((Ms-2*Mu+6.0)/radeg);
		uLongTerms[1] =  0.035*Math.sin((Ms-3*Mu+33.0)/radeg);
		uLongTerms[2] = -0.015*Math.sin((Mj-Mu+20.0)/radeg);
		lon = lon + sumArray(uLongTerms);
	}
	
	public void convertToGeocentric() {
	
		double xgeoc = sx + xeclip;
		double ygeoc = sy + yeclip;
		double zgeoc = sz + zeclip;
		double xegeoc = xgeoc;
		double yegeoc = ygeoc*Math.cos(oblecl/radeg) - zgeoc*Math.sin(oblecl/radeg);
		double zegeoc = ygeoc*Math.sin(oblecl/radeg) + zgeoc*Math.cos(oblecl/radeg);
		rA  = rev(radeg*Math.atan2( yegeoc, xegeoc ));
		dec = radeg*Math.atan2( zegeoc, Math.sqrt(xegeoc*xegeoc+yegeoc*yegeoc) ); 
		double rgeoc = Math.sqrt(xegeoc*xegeoc+yegeoc*yegeoc+zegeoc*zegeoc);
	}
	
	public static Planet[] calcPlanets(double d, double sx, double sy, double sz){
    	
    	Planet mercury   = new Planet(d, "Mercury", Planet.mercury, Planet.mercuryDot, sx, sy, sz);
		Planet venus     = new Planet(d, "Venus", Planet.venus, Planet.venusDot, sx, sy, sz);
    	Planet mars      = new Planet(d, "Mars", Planet.mars, Planet.marsDot, sx, sy, sz);
    	Planet jupiter   = new Planet(d, "Jupiter", Planet.jupiter, Planet.jupiterDot, sx, sy, sz);
    	Planet saturn    = new Planet(d, "Saturn", Planet.saturn, Planet.saturnDot, sx, sy, sz);
    	Planet uranus    = new Planet(d, "Uranus", Planet.uranus, Planet.uranusDot, sx, sy, sz);
    	Planet neptune   = new Planet(d, "Neptune", Planet.neptune, Planet.neptuneDot, sx, sy, sz);
    	
    	Planet[] pp = {mercury, venus, mars, jupiter, saturn, uranus, neptune};
    	
    	jupiter.setM(jupiter.getM(), saturn.getM(), uranus.getM());
		saturn.setM(jupiter.getM(), saturn.getM(), uranus.getM());
		uranus.setM(jupiter.getM(), saturn.getM(), uranus.getM());
		
		jupiter.adjustJLong();
		saturn.adjustSLong();
		saturn.adjustSLat();
		uranus.adjustULong();
    	
		return pp;
    }
	
	
}
