package animation;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 * @author twevans
 *
 */
public class Calculations extends Parameters {

	/**
	 * Returns the x coordinate of a circle in the chart
	 * @param rA right ascension (or ecliptic longitude) of the object
	 * @return the x coordinate of the circle in the chart
	 */
	public double x(double rA){
    	double x = (chartWidth-rAMult*rA/hourDeg)/chartWidth;
    	return x;
    }
    
	/**
	 * Returns the y coordinate of a circle in the chart 
	 * @param dec declination (ecliptic latitude) of the object
	 * @return the y coordinate of the circle in the chart
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
     * @return the radius of the circle in the chart
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
     * @return the radius of the circle in the chart
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
	
    public void initializeChart() throws FileNotFoundException, SQLException {
    	
    	stars = new Star2();
        
        raStars = stars.getRA();
		decStars = stars.getDec();	
		lonStars = stars.getLon();
		latStars = stars.getLat();	
		magStars = stars.getMag();
		
		cStars = new Circle[raStars.size()];
		
    	calendar = new GregorianCalendar();
    	date = java.sql.Date.valueOf(startDate.getValue());
        calendar.setTime(date);
        
        hString = (String) hourBox.getValue();
        mString = (String) minuteBox.getValue();
        
        hInt = Integer.parseInt(hString);
        mInt = Integer.parseInt(mString);
        
        calendar.add(Calendar.HOUR_OF_DAY, hInt);
        calendar.add(Calendar.MINUTE, mInt);
        
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        strDate = dateFormat.format(date);
        tDate = new Text (25,50,strDate);
        
        
        
        for(int k=0; k<tPlanets.length; k++) {
    		tPlanets[k] = new Text (Planet.names[k]);
    	}
      		
        // Create the scene
        
        canvas.setStyle("-fx-background-color: cornflowerblue;");
        scene = new Scene(canvas, chartWidth, chartHeight, Color.CORNFLOWERBLUE);
        
    	canvas.getChildren().add(equator);
        canvas.getChildren().add(tDate);
        canvas.getChildren().add(button1);
    	
        if(sunIndicator.isSelected()) {
        	canvas.getChildren().add(ball);
        }
        
        if(moonIndicator.isSelected()) {
        	canvas.getChildren().add(ballM);
        }
        
        if(coordsBox.getValue()=="Equatorial") {
        	for(int k=0; k<cEcliptic.length; k++) {
        		cEcliptic[k] = new Circle(mEcliptic, Color.YELLOW);
        		canvas.getChildren().add(cEcliptic[k]);
			}
        }
        
        for(int k=0; k<raStars.size(); k++) {
			cStars[k] = new Circle(mStar, Color.WHITE);
			canvas.getChildren().add(cStars[k]);
        }
    	
    }
    
    public void initializePlanet(int k) {
    	
    	ballP[k] = new Circle(mPlanet, Color.RED);
		canvas.getChildren().add(ballP[k]);
		canvas.getChildren().add(tPlanets[k]);
    	
    }
    
    /**
     * Sets the initial positions and sizes of the stars in the chart.
     */
    public void initializeStars() {
    	
    	for(int k=0; k<raStars.size(); k++) {
			if(coordsBox.getValue()=="Equatorial") {
				x = x(raStars.get(k)*hourDeg)*canvas.getWidth();
				y = y(decStars.get(k))*canvas.getHeight();
			} else if (coordsBox.getValue()=="Ecliptic") {
				x = x(lonStars.get(k)*hourDeg)*canvas.getWidth();
    			y = y(latStars.get(k))*canvas.getHeight();
			} 
			
			// if the dimmest star has apparent magnitude 6.0:
			
			mStar = (6.01-magStars.get(k))/3;
			
			cStars[k].setRadius(r(mStar,canvas.getWidth(),canvas.getHeight()));
			cStars[k].setLayoutX(x);
			cStars[k].setLayoutY(y);
			cStars[k].toBack();
		}
    	
    }
    
    /**
     * Updates the positions and sizes of the stars in the chart.
     */
    public void updateStars() {
    	
    	for(int k=0; k<raStars.size(); k++) {
			x = cStars[k].getLayoutX();
			y = cStars[k].getLayoutY(); 
			z = cStars[k].getRadius(); 
			
			cStars[k].setRadius(rStar(z,canvas.getWidth(),canvas.getHeight()));
			cStars[k].setLayoutX(x*canvas.getWidth()/chartWidth1);
			cStars[k].setLayoutY(y*canvas.getHeight()/chartHeight1);
			cStars[k].toBack();
		}
	
		chartWidth1 = canvas.getWidth();
		chartHeight1 = canvas.getHeight();
    	
    }
    
    public void drawExitButton() {
    	
    	button1.setLayoutX(canvas.getWidth()/2);
		button1.setLayoutY(0.9*canvas.getHeight());
    	
    }
    
    public void drawChartLines() {
    	
    	//draw the horizontal equator / ecliptic line
		equator.setEndX(canvas.getWidth());
		equator.setStartY(canvas.getHeight()/2);
		equator.setEndY(canvas.getHeight()/2);
		equator.toBack();
	
		//draw the ecliptic curve (if equatorial coordinates have been selected)
		if(coordsBox.getValue()=="Equatorial") {
			for(int k=0; k<cEcliptic.length; k++) {
				cEcliptic[k].setLayoutX(canvas.getWidth()*k/(cEcliptic.length-1));
				cEcliptic[k].setLayoutY((canvas.getHeight()/2 + 23.5*decMult*(canvas.getHeight()/chartHeight)*Math.sin((cEcliptic[k].getLayoutX()/canvas.getWidth())*2*Math.PI)));
				cEcliptic[k].toBack();
			}	
		}
    	
    }
    
    /**
     * Updates the sun's position in the chart.
     * @param sun sun object
     */
    public void updateSun(Sun sun) {
    	
    	if(coordsBox.getValue()=="Equatorial") {
			x = x(sun.getRA())*canvas.getWidth(); 	// equatorial coordinates
			y = y(sun.getDec())*canvas.getHeight(); 
		} else {
			x = x(sun.getLS())*canvas.getWidth();  // ecliptic coordinates
			y = canvas.getHeight()/2; 
		}
		
		ball.setRadius(r(mSun,canvas.getWidth(),canvas.getHeight()));
		ball.setLayoutX(x);
		ball.setLayoutY(y);
		ball.toFront();
    	
    }
    
    /**
     * Updates the moon's position in the chart.
     * @param moon moon object
     */
    public void updateMoon(Moon moon) {
    	
    	if(coordsBox.getValue()=="Equatorial") {
			x = x(moon.getRA())*canvas.getWidth();
			y = y(moon.getDec())*canvas.getHeight();
		} else {
			x = x(moon.getLon())*canvas.getWidth();
			y = y(moon.getLat())*canvas.getHeight();
		}
		
		ballM.setRadius(r(mMoon,canvas.getWidth(),canvas.getHeight()));
		ballM.setLayoutX(x);
		ballM.setLayoutY(y);
		ballM.toFront();
    	
    	
    }
    
    /**
     * Updates this planet's position in the chart.
     * @param p planet object
     * @param j planet indicator
     */
    public void updatePlanet(Planet p, int j) {
    	
    	p.convertToGeocentric();
		
		if(coordsBox.getValue()=="Equatorial") {
			x = x(p.getRA())*canvas.getWidth();
			y = y(p.getDec())*canvas.getHeight();
		} else {
			x = x(p.getLon())*canvas.getWidth();
			y = y(p.getLat())*canvas.getHeight();
		}
		
		if(trailsBox.getValue()=="N") {
			ballP[j].setRadius(r(mPlanet,canvas.getWidth(),canvas.getHeight()));
			ballP[j].setLayoutX(x);
			ballP[j].setLayoutY(y);
			
			tPlanets[j].setX(x+2*mPlanet);
			tPlanets[j].setY(y+8*mPlanet);
			tPlanets[j].setFont(new Font(r(pFontSize,canvas.getWidth(),canvas.getHeight())));
		} else {
			Circle ballP0 = new Circle(mPlanet/2, Color.RED);
			
			canvas.getChildren().add(ballP0);
			
			ballP0.setRadius(r(mPlanet/2,canvas.getWidth(),canvas.getHeight()));
			ballP0.setLayoutX(x);
			ballP0.setLayoutY(y);
			
			tPlanets[j].setX(x+2*mPlanet);
			tPlanets[j].setY(y+8*mPlanet);
			tPlanets[j].setFont(new Font(r(pFontSize,canvas.getWidth(),canvas.getHeight())));
		}
    	
    }
    
    public void advanceTime() {
    	
    	if(speedBox.getValue()=="Slow Animation") {
			//move time forward by one hour
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			String dt = dateFormat.format(calendar.getTime());
			tDate.setText("Date:  " + dt);
			tDate.setFont(new Font(r(fontSize,canvas.getWidth(),canvas.getHeight())));
		} else if (speedBox.getValue()=="Fast Animation") {
			//move time forward by one day
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			String dt = dateFormat.format(calendar.getTime());
			tDate.setText(dt);
		}
		
		//move time forward by one minute
		//calendar.add(Calendar.MINUTE, 1);
    	
    }
	
}
