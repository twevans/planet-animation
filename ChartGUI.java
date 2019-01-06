package animation;

import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ChartGUI extends Application {
	
	Date date = new Date();
    
    public static void main(String[] args) {
    	
    	
        
        Application.launch(args);
    }
    
    public void start(Stage primaryStage) {
        
    	//System.out.println(date);
    	
        primaryStage.setTitle("PrimaryStage");
        
        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 700, 200);
      
        //Button btn = new Button("Open New Stage");
        //btn.setOnAction(eve-> new NewStage(date));
        
        
		
		Button btn = new Button("Open New Stage");
		//ChartAnimation ca = new ChartAnimation();
		//ca.runChart();
		String[] arguments = new String[] {"123"};
        btn.setOnAction(eve-> ChartAnimation.main(arguments));
        
            
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}