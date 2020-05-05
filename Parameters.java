package animation;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Parameters {
	
	int chartWidth = 1080; 
	int chartHeight = 500;
	int rAMult = 45;
	int decMult = 3;
	int mSun = 8;
	int mMoon = 8;
	int mPlanet = 2;
	int mEcliptic = 1;
	int hourDeg = 15;
	int fontSize = 15;
	int pFontSize = 12;
	double mStar = 0.5;
	double chartWidth1 = 1080; 
	double chartHeight1 = 500;
	boolean runIndicator = false;
	
	double x;
    double y;
    double z;
    int hInt;
    int mInt;
    
    Star2 stars;
    Text tDate;
    DateFormat dateFormat;
    String strDate;
    String hString;
    String mString;
    List<Double> raStars;
    List<Double> decStars;	
    List<Double> lonStars;
    List<Double> latStars;	
    List<Double> magStars;
    Stage stage;
    Date date;
    Calendar calendar;
    Scene scene;
    Circle[] cStars;
    
    Circle[] ballP = new Circle[7];
    Circle[] cEcliptic = new Circle[300];
    Circle ball = new Circle(mSun, Color.YELLOW);
	Circle ballM = new Circle(mMoon, Color.WHITE);
	Line equator = new Line(0, chartHeight/2, chartWidth, chartHeight/2);
    Text[] tPlanets = new Text[7];
    Button button1 = new Button("Exit");
	Pane canvas = new Pane();
	
	
	ObservableList<String> speedList = FXCollections.observableArrayList("Static Picture","Slow Animation","Fast Animation");
	ObservableList<String> hourList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
	ObservableList<String> minuteList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
			"40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"
			);
	ObservableList<String> trailsList = FXCollections.observableArrayList("N","Y");
	ObservableList<String> coordsList = FXCollections.observableArrayList("Equatorial","Ecliptic");
	
    @FXML
    DatePicker startDate;

    @FXML
    ComboBox speedBox;
    
    @FXML
    ComboBox hourBox;
    
    @FXML
    ComboBox minuteBox;

    @FXML
    RadioButton sunIndicator;
    
    @FXML
    RadioButton moonIndicator;
    
    @FXML
    RadioButton mercuryIndicator;
    
    @FXML
    RadioButton venusIndicator; 
    
    @FXML
    RadioButton marsIndicator;
    
    @FXML
    RadioButton jupiterIndicator;
    
    @FXML
    RadioButton saturnIndicator;
    
    @FXML
    RadioButton uranusIndicator;
    
    @FXML
    RadioButton neptuneIndicator;
    
    @FXML
    ComboBox trailsBox;
    
    @FXML
    ComboBox coordsBox;

    @FXML
    Button runButton;

}
