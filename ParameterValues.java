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

public class ParameterValues {
	
	public static int chartWidth = 1080; 
	public static int chartHeight = 500;
	public static int rAMult = 45;
	public static int decMult = 3;
	public static int mSun = 8;
	public static int mMoon = 8;
	public static int mPlanet = 2;
	public static int mEcliptic = 1;
	public static int hourDeg = 15;
	public static int fontSize = 15;
	public static int pFontSize = 12;
	public static double mStar = 0.5;
	public static double chartWidth1 = 1080; 
	public static double chartHeight1 = 500;
	
	public double x;
    public double y;
    
    public double z;
    public int hInt;
    public int mInt;
    public Star2 stars;
    public Text tDate;
    public DateFormat dateFormat;
    public String strDate;
    public String hString;
    public String mString;
    public List<Double> raStars;
    public List<Double> decStars;	
    public List<Double> lonStars;
    public List<Double> latStars;	
    public List<Double> magStars;
    public Stage stage;
    public Date date;
    public Calendar calendar;
    public Scene scene;
    public Circle[] cStars;
    
    public Circle[] ballP = new Circle[7];
    public Circle[] cEcliptic = new Circle[300];
    public Circle ball = new Circle(mSun, Color.YELLOW);
	public Circle ballM = new Circle(mMoon, Color.WHITE);
	public Line equator = new Line(0, chartHeight/2, chartWidth, chartHeight/2);
    public Text[] tPlanets = new Text[7];
    public Button button1 = new Button("Exit");
	public Pane canvas = new Pane();
	public boolean runIndicator = false;
	
	public ObservableList<String> speedList = FXCollections.observableArrayList("Static Picture","Slow Animation","Fast Animation");
	public ObservableList<String> hourList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
	public ObservableList<String> minuteList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
			"40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"
			);
	public ObservableList<String> trailsList = FXCollections.observableArrayList("N","Y");
	public ObservableList<String> coordsList = FXCollections.observableArrayList("Equatorial","Ecliptic");
	
    @FXML
    public DatePicker startDate;

    @FXML
    public ComboBox speedBox;
    
    @FXML
    public ComboBox hourBox;
    
    @FXML
    public ComboBox minuteBox;

    @FXML
    public RadioButton sunIndicator;
    
    @FXML
    public RadioButton moonIndicator;
    
    @FXML
    public RadioButton mercuryIndicator;
    
    @FXML
    public RadioButton venusIndicator; 
    
    @FXML
    public RadioButton marsIndicator;
    
    @FXML
    public RadioButton jupiterIndicator;
    
    @FXML
    public RadioButton saturnIndicator;
    
    @FXML
    public RadioButton uranusIndicator;
    
    @FXML
    public RadioButton neptuneIndicator;
    
    @FXML
    public ComboBox trailsBox;
    
    @FXML
    public ComboBox coordsBox;

    @FXML
    public Button runButton;

}
