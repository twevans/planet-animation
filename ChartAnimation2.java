package animation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChartAnimation2 extends Application{
	
	
    
    @Override
    public void start(Stage stage) throws SQLException, Exception   {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("chart_form2.fxml"));
    	stage.setTitle("Star Chart Animation");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    	
    	
    }
    
    
    
    public static void main(String[] args) {
       
       launch();
    }
    
    
}