package animation;

import java.util.Calendar;

public class Calculations extends ParameterValues {

	public double x(double rA){
    	double x = (chartWidth-rAMult*rA/hourDeg)/chartWidth;
    	return x;
    }
    
    public double y(double dec){
    	double y = ((chartHeight/2)-decMult*dec)/chartHeight;
    	return y;
    }
    
    public double r(double m, double w, double h){
    	double r = m*Math.sqrt((Math.pow(w,2)+Math.pow(h,2))/(Math.pow(chartWidth,2)+Math.pow(chartHeight,2)));
    	return r;
    }
    
    public double rStar(double m, double w, double h){
        	double r = m*Math.sqrt((Math.pow(w,2)+Math.pow(h,2))/(Math.pow(chartWidth1,2)+Math.pow(chartHeight1,2)));
        	return r;
    }
    
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
