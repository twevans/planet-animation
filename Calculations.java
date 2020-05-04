package animation;

import java.util.Calendar;

/**
 * 
 * @author twevans
 *
 */
public class Calculations extends ParameterValues {

	/**
	 * Returns the x coordinate of a circle in the chart
	 * @param rA right ascension (or the ecliptic longitude) of the object
	 * @return the x coordinate of the object in the chart
	 */
	public double x(double rA){
    	double x = (chartWidth-rAMult*rA/hourDeg)/chartWidth;
    	return x;
    }
    
	/**
	 * Returns the y coordinate of a circle in the chart 
	 * @param dec declination (or ecliptic latitude) of the object
	 * @return the y coordinate of the object in the chart
	 */
    public double y(double dec){
    	double y = ((chartHeight/2)-decMult*dec)/chartHeight;
    	return y;
    }
    
    /**
     * Returns the radius of a circle in the chart
     * @param m apparent magnitude of the object
     * @param w the width of the chart
     * @param h the height of the chart
     * @return
     */
    public double r(double m, double w, double h){
    	double r = m*Math.sqrt((Math.pow(w,2)+Math.pow(h,2))/(Math.pow(chartWidth,2)+Math.pow(chartHeight,2)));
    	return r;
    }
    
    /**
     * Returns the radius of a circle in the chart
     * @param m the apparent magnitude of the star
     * @param w the width of the chart
     * @param h the height of the chart
     * @return
     */
    public double rStar(double m, double w, double h){
        	double r = m*Math.sqrt((Math.pow(w,2)+Math.pow(h,2))/(Math.pow(chartWidth1,2)+Math.pow(chartHeight1,2)));
        	return r;
    }
    
    /**
     * Returns a day number 
     * @param c calendar object
     * @return day number
     */
    public double d(Calendar c){
    	int year   = c.get(Calendar.YEAR);
        int month  = c.get(Calendar.MONTH) + 1;
        int day    = c.get(Calendar.DAY_OF_MONTH);
        int hour   = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int dInt   = 367*year - 7*(year+(month+9)/12)/4 + 275*month/9 + day - 730530;
        double d   = dInt + (hour + minute/60.0)/24.0;
        return d;
    }
	
	
}
