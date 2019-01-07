package animation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChartAnimation extends Application{
	
	private int chartWidth = 1080; 
    private int chartHeight = 500;
    private int rAMult = 45;
    private int decMult = 3;
    private int mSun = 10;
    private int mMoon = 10;
    private int mPlanet = 2;
    private int mEcliptic = 1;
    private int hourDeg = 15;
    private double x;
    private double y;
    private Circle[] ballP = new Circle[7];
    private Circle[] cEcliptic = new Circle[100];
    private Text[] tPlanets = new Text[7];
    private int fontSize = 15;
    private int pFontSize = 12;
    private Date date;
    private Calendar calendar;
    private Scene scene0, scene;
    private boolean runIndicator = false;
    
    @Override
    public void start(Stage stage) {
    	
    	date = new Date();
    	calendar = new GregorianCalendar();
        calendar.setTime(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        
        //Scene 0
        Pane canvas0 = new Pane();
        canvas0.setStyle("-fx-background-color: cornflowerblue;");
        scene0 = new Scene(canvas0, chartWidth, chartHeight, Color.CORNFLOWERBLUE);
        Button button0 = new Button("Run");
        canvas0.getChildren().add(button0);
        button0.setLayoutX(canvas0.getWidth()/2);
    	button0.setLayoutY(0.9*canvas0.getHeight());
      		
        //Scene
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: cornflowerblue;");
        //canvas.setStyle("-fx-background-color: black;");
        scene = new Scene(canvas, chartWidth, chartHeight, Color.CORNFLOWERBLUE);
    	Line equator = new Line(0, chartHeight/2, chartWidth, chartHeight/2);
    	Circle ball = new Circle(mSun, Color.YELLOW);
    	Circle ballM = new Circle(mMoon, Color.WHITE);
    	Text tDate = new Text (25,50,strDate);
    	Button button1 = new Button("Exit");
    	
    	for(int k=0; k<tPlanets.length; k++) {
    		tPlanets[k] = new Text (Planet.names[k]);
    	}
        
        canvas.getChildren().add(equator);
        canvas.getChildren().add(tDate);
        canvas.getChildren().addAll(tPlanets);
        canvas.getChildren().add(ball);
        canvas.getChildren().add(ballM);
        canvas.getChildren().add(button1);
        
        for(int k=0; k<ballP.length; k++) {
			ballP[k] = new Circle(mPlanet, Color.RED);
			canvas.getChildren().add(ballP[k]);
		}
        
        for(int k=0; k<cEcliptic.length; k++) {
			cEcliptic[k] = new Circle(mEcliptic, Color.YELLOW);
			canvas.getChildren().add(cEcliptic[k]);
        }
        
        stage.setTitle("Star Chart Animation");
        stage.setScene(scene0);
        
        button0.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	runIndicator = true;
            	date = new Date();
            	calendar = new GregorianCalendar();
                calendar.setTime(date);
                stage.setScene(scene);
            }
        });
        
        //button1.setOnAction(e -> stage.setScene(scene0)); 
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	runIndicator = false;
                stage.setScene(scene0);
            }
        });
        
        stage.setOnCloseRequest(e -> {
        	Platform.exit();
        });
        
        stage.show();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), 
                new EventHandler<ActionEvent>() {

        	@Override
            public void handle(ActionEvent t) {
        		
        		if (runIndicator) {
        			
        			//System.out.println("running");
        		
        			button0.setLayoutX(canvas0.getWidth()/2);
        			button0.setLayoutY(0.9*canvas0.getHeight());
        		
        			button1.setLayoutX(canvas.getWidth()/2);
        			button1.setLayoutY(0.9*canvas.getHeight());
            	
        			//draw the equator
        			equator.setEndX(canvas.getWidth());
        			equator.setStartY(canvas.getHeight()/2);
        			equator.setEndY(canvas.getHeight()/2);
        			equator.toBack();
        		
        			//draw the ecliptic
        			for(int k=0; k<cEcliptic.length; k++) {
        				cEcliptic[k].setLayoutX(canvas.getWidth()*k/(cEcliptic.length-1));
        				cEcliptic[k].setLayoutY((canvas.getHeight()/2 + 23.5*decMult*(canvas.getHeight()/chartHeight)*Math.sin((cEcliptic[k].getLayoutX()/canvas.getWidth())*2*Math.PI)));
        				cEcliptic[k].toBack();
        			}
        		
        			//calculate the sun's chart coordinates and radius
        			Sun sun = new Sun(d(calendar));
        			x = x(sun.getRA())*canvas.getWidth()/chartWidth;
        			y = y(sun.getDec())*canvas.getHeight()/chartHeight;
        			ball.setRadius(r(mSun,canvas.getWidth(),canvas.getHeight()));
        			ball.setLayoutX(x);
        			ball.setLayoutY(y);
        			ball.toFront();
            	
        			//calculate the moon's chart coordinates and radius
        			Moon moon = new Moon (d(calendar), sun.getML() , sun.getMA());
        			x = x(moon.getRA())*canvas.getWidth()/chartWidth;
        			y = y(moon.getDec())*canvas.getHeight()/chartHeight;
        			ballM.setRadius(r(mMoon,canvas.getWidth(),canvas.getHeight()));
        			ballM.setLayoutX(x);
        			ballM.setLayoutY(y);
        			ballM.toFront();
            	
        			//calculate the coordinates and radii of the planets
        			Planet[] planets = calcPlanets(d(calendar), sun.getX(), sun.getY(), sun.getZ());
        			int j = 0;
        			for (Planet p : planets) {
        			
        				p.convertToGeocentric();
        				x = x(p.getRA())*canvas.getWidth()/chartWidth;
        				y = y(p.getDec())*canvas.getHeight()/chartHeight;
                	
        				ballP[j].setRadius(r(mPlanet,canvas.getWidth(),canvas.getHeight()));
        				ballP[j].setLayoutX(x);
        				ballP[j].setLayoutY(y);
        				tPlanets[j].setX(x+2*mPlanet);
        				tPlanets[j].setY(y+8*mPlanet);
        				tPlanets[j].setFont(new Font(r(pFontSize,canvas.getWidth(),canvas.getHeight())));
                	
        				j++;
        			}
        		
        			//move time forward by one hour
        			calendar.add(Calendar.HOUR_OF_DAY, 1);
        			String dt = dateFormat.format(calendar.getTime());
        			tDate.setText("Date:  " + dt);
        			tDate.setFont(new Font(r(fontSize,canvas.getWidth(),canvas.getHeight())));
            	
        			//move time forward by one day
        			//calendar.add(Calendar.DAY_OF_MONTH, 1);
        			//String dt = dateFormat.format(calendar.getTime());
        			//tDate.setText(dt);
            	
        			//move time forward by one minute
        			//calendar.add(Calendar.MINUTE, 1);
        		}
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
    }
    
    private double x(double rA){
    	double x = chartWidth-rAMult*rA/hourDeg;
    	return x;
    }
    
    private double y(double dec){
    	double y = (chartHeight/2)-decMult*dec;
    	return y;
    }
    
    private double r(int m, double w, double h){
    	double r = m*Math.sqrt((Math.pow(w,2)+Math.pow(h,2))/(Math.pow(chartWidth,2)+Math.pow(chartHeight,2)));
    	return r;
    }
    
    private Planet[] calcPlanets(double d, double sx, double sy, double sz){
    	
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
    
    private double d(Calendar c){
    	
    	int year   = c.get(Calendar.YEAR);
        int month  = c.get(Calendar.MONTH) + 1;
        int day    = c.get(Calendar.DAY_OF_MONTH);
        int hour   = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int dInt   = 367*year - 7*(year+(month+9)/12)/4 + 275*month/9 + day - 730530;
        double d   = dInt + (hour + minute/60.0)/24.0;
    	
        return d;
    }
    
    public static void main(String[] args) {
       
       launch();
    }
    
    public void runChart(){
    	System.out.println("test");
    	launch();
    }
}