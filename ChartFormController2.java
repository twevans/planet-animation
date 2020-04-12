package animation;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class ChartFormController2 {
	
	private int chartWidth = 1080; 
    private int chartHeight = 500;
    private int rAMult = 45;
    private int decMult = 3;
    private int mSun = 8;
    private int mMoon = 8;
    private int mPlanet = 2;
    private int mStar = 1;
    private int mEcliptic = 1;
    private int hourDeg = 15;
    private double x;
    private double y;
    private Circle[] ballP = new Circle[7];
    private Circle[] cEcliptic = new Circle[300];
    private Circle[] cStars;
    private Text[] tPlanets = new Text[7];
    private int fontSize = 15;
    private int pFontSize = 12;
    private Date date;
    private Calendar calendar;
    private Scene scene;
    private boolean runIndicator = false;
	
	ObservableList<String> speedList = FXCollections.observableArrayList("Static Picture","Slow Animation","Fast Animation");
	
	ObservableList<String> hourList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");

	ObservableList<String> minuteList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
			"40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"
			);

	ObservableList<String> trailsList = FXCollections.observableArrayList("N","Y");
	
	//ObservableList<String> coordsList = FXCollections.observableArrayList("Equatorial","Ecliptic","Galactic");
	ObservableList<String> coordsList = FXCollections.observableArrayList("Equatorial","Ecliptic");
	
    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox speedBox;
    
    @FXML
    private ComboBox hourBox;
    
    @FXML
    private ComboBox minuteBox;

    @FXML
    private RadioButton sunIndicator;
    
    @FXML
    private RadioButton moonIndicator;
    
    @FXML
    private RadioButton mercuryIndicator;
    
    @FXML
    private RadioButton venusIndicator; 
    
    @FXML
    private RadioButton marsIndicator;
    
    @FXML
    private RadioButton jupiterIndicator;
    
    @FXML
    private RadioButton saturnIndicator;
    
    @FXML
    private RadioButton uranusIndicator;
    
    @FXML
    private RadioButton neptuneIndicator;
    
    @FXML
    private ComboBox trailsBox;
    
    @FXML
    private ComboBox coordsBox;

    @FXML
    private Button runButton;
    
    
    
    @FXML
    private void initialize() {
    	
    	startDate.setValue(LocalDate.now());
    	
    	speedBox.setValue("Slow Animation");
    	speedBox.setItems(speedList);
    	
    	hourBox.setValue("00");
    	hourBox.setItems(hourList);
    	
    	minuteBox.setValue("00");
    	minuteBox.setItems(minuteList);
    	
    	trailsBox.setValue("N");
    	trailsBox.setItems(trailsList);
    	
    	coordsBox.setValue("Equatorial");
    	coordsBox.setItems(coordsList);
    	
    	
    }

    @FXML
    protected void handleRunButtonAction(ActionEvent event) throws SQLException {
    	
    	//System.out.println("button has been clicked");
    	
        Window owner = runButton.getScene().getWindow();
        if(startDate.getValue()==null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter the start date");
            return;
        }
        if(speedBox.getValue()==null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please choose a speed");
            return;
        }
        
        Boolean[] planetIndicators = {mercuryIndicator.isSelected(), venusIndicator.isSelected(), 
        		marsIndicator.isSelected(), jupiterIndicator.isSelected(), saturnIndicator.isSelected(), 
        		uranusIndicator.isSelected(), neptuneIndicator.isSelected()};
        
        Star stars = new Star();
		List<Double> raStars = stars.getRA();
		List<Double> decStars = stars.getDec();	
		List<Double> lonStars = stars.getLon();
		List<Double> latStars = stars.getLat();	
		List<Double> galLonStars = stars.getGalLon();
		List<Double> galLatStars = stars.getGalLat();	
		
		
		cStars = new Circle[raStars.size()];
    	
    	
		calendar = new GregorianCalendar();
    	date = java.sql.Date.valueOf(startDate.getValue());
        calendar.setTime(date);
        
        String hString = (String) hourBox.getValue();
        String mString = (String) minuteBox.getValue();
        
        int hInt = Integer.parseInt(hString);
        int mInt = Integer.parseInt(mString);
        
        calendar.add(Calendar.HOUR_OF_DAY, hInt);
        calendar.add(Calendar.MINUTE, mInt);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        
        
      		
        //Scene
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: cornflowerblue;");
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
        //canvas.getChildren().addAll(tPlanets);
        
        if(sunIndicator.isSelected()) {
        	canvas.getChildren().add(ball);
        }
        
        if(moonIndicator.isSelected()) {
        	canvas.getChildren().add(ballM);
        }
        
        canvas.getChildren().add(button1);
        
        
        
        for(int k=0; k<ballP.length; k++) {
        	
        	
        	if(planetIndicators[k]==true) {
        		ballP[k] = new Circle(mPlanet, Color.RED);
        		canvas.getChildren().add(ballP[k]);
        		canvas.getChildren().add(tPlanets[k]);
        	}
		}
        
        for(int k=0; k<cEcliptic.length; k++) {
			cEcliptic[k] = new Circle(mEcliptic, Color.YELLOW);
			canvas.getChildren().add(cEcliptic[k]);
			
		}
        
        for(int k=0; k<raStars.size(); k++) {
			
			cStars[k] = new Circle(mStar, Color.WHITE);
			canvas.getChildren().add(cStars[k]);
        }
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setTitle("Star Chart Animation");
        
        runIndicator = true;
        
        stage.setScene(scene);
            
        
        //button1 returns the user to the interface 
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	runIndicator = false;
                Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("chart_form2.fxml"));
					stage.setTitle("Star Chart Animation");
	                stage.setScene(new Scene(root, 600, 600));
	                stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
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
        			
        			
        		
        			
        		
        			button1.setLayoutX(canvas.getWidth()/2);
        			button1.setLayoutY(0.9*canvas.getHeight());
            	
        			//draw the equator
        			equator.setEndX(canvas.getWidth());
        			equator.setStartY(canvas.getHeight()/2);
        			equator.setEndY(canvas.getHeight()/2);
        			equator.toBack();
        		
        			//draw the ecliptic
        			if(coordsBox.getValue()=="Equatorial") {
        				for(int k=0; k<cEcliptic.length; k++) {
        					cEcliptic[k].setLayoutX(canvas.getWidth()*k/(cEcliptic.length-1));
        					cEcliptic[k].setLayoutY((canvas.getHeight()/2 + 23.5*decMult*(canvas.getHeight()/chartHeight)*Math.sin((cEcliptic[k].getLayoutX()/canvas.getWidth())*2*Math.PI)));
        					cEcliptic[k].toBack();
        				
        				}	
        			}
        			
        			// calculate the positions of the background stars
        			for(int k=0; k<raStars.size(); k++) {
        				if(coordsBox.getValue()=="Equatorial") {
        				x = x(raStars.get(k)*hourDeg)*canvas.getWidth()/chartWidth;
            			y = y(decStars.get(k))*canvas.getHeight()/chartHeight;
        				} else if (coordsBox.getValue()=="Ecliptic") {
        					x = x(lonStars.get(k)*hourDeg)*canvas.getWidth()/chartWidth;
                			y = y(latStars.get(k))*canvas.getHeight()/chartHeight;
        					
        				} else {
        					x = x(galLonStars.get(k)*hourDeg)*canvas.getWidth()/chartWidth;
                			y = y(galLatStars.get(k))*canvas.getHeight()/chartHeight;
        					
        				}
        				
            			cStars[k].setRadius(r(mStar,canvas.getWidth(),canvas.getHeight()));
            			cStars[k].setLayoutX(x);
            			cStars[k].setLayoutY(y);
            			cStars[k].toBack();
        				
        			}
        			
        			//calculate the sun's chart coordinates and radius
        			Sun sun = new Sun(d(calendar));
        			if(sunIndicator.isSelected()) {
        				if(coordsBox.getValue()=="Equatorial") {
        					x = x(sun.getRA())*canvas.getWidth()/chartWidth; 	// equatorial coordinates
        					y = y(sun.getDec())*canvas.getHeight()/chartHeight; 
        				} else {
        					x = x(sun.getLS())*canvas.getWidth()/chartWidth;  // ecliptic coordinates
        					y = canvas.getHeight()/2; 						
        				}
        				
        				ball.setRadius(r(mSun,canvas.getWidth(),canvas.getHeight()));
        				ball.setLayoutX(x);
        				ball.setLayoutY(y);
        				ball.toFront();
        			}
        			
            	
        			//calculate the moon's chart coordinates and radius
        			if(moonIndicator.isSelected()) {
        				Moon moon = new Moon (d(calendar), sun.getML() , sun.getMA());
        				if(coordsBox.getValue()=="Equatorial") {
        					
        					x = x(moon.getRA())*canvas.getWidth()/chartWidth;
        					y = y(moon.getDec())*canvas.getHeight()/chartHeight;
        					
        				} else {
        					
        					x = x(moon.getLon())*canvas.getWidth()/chartWidth;
        					y = y(moon.getLat())*canvas.getHeight()/chartHeight;
        					
        					//System.out.println(moon.getLon());
        					//System.out.println(moon.getLat());
        					
        				}
        				
        				ballM.setRadius(r(mMoon,canvas.getWidth(),canvas.getHeight()));
    					ballM.setLayoutX(x);
    					ballM.setLayoutY(y);
    					ballM.toFront();
        			}
            	
        			//calculate the coordinates and radii of the planets
        			Planet[] planets = Planet.calcPlanets(d(calendar), sun.getX(), sun.getY(), sun.getZ());
        			int j = 0;
        			for (Planet p : planets) {
        			
        				if(planetIndicators[j]==true) {
        					p.convertToGeocentric();
        					if(coordsBox.getValue()=="Equatorial") {
        						x = x(p.getRA())*canvas.getWidth()/chartWidth;
        						y = y(p.getDec())*canvas.getHeight()/chartHeight;
        					} else {
        						x = x(p.getLon())*canvas.getWidth()/chartWidth;
            					y = y(p.getLat())*canvas.getHeight()/chartHeight;	
            					
            					//System.out.println("lon = " + p.getLon());
            					//System.out.println("lat = " + p.getLat());
        						
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
        				
        				j++;
        			}
        			
        			
        		
        			
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
    
}