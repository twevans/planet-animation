package animation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class ChartFormController2 extends Calculations {
	
    /**
     * This sets the default options in the UI
     */
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
    protected void handleRunButtonAction(ActionEvent event) throws SQLException, FileNotFoundException {
    	
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
        
        initializeChart();
                
        for(int k=0; k<ballP.length; k++) {
        	if(planetIndicators[k]==true) {
        		initializePlanet(k);	
        	}
		}
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Star Chart Animation");
        stage.setScene(scene);
        runIndicator = true;
            
        //button1 returns the user to the interface 
        
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	runIndicator = false;
            	chartWidth1 = chartWidth; 
            	chartHeight1 = chartHeight;
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
        
        // calculate the positions and sizes of the background stars
        
        initializeStars();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), 
                new EventHandler<ActionEvent>() {

        	/**
        	 * Updates the positions of the objects in the chart and moves time forward
        	 */
        	@Override
            public void handle(ActionEvent t) {
        		
        		if (runIndicator) {
        			
        			drawExitButton();
        			
        			drawChartLines();
        			        			
        			// if the dimensions of the animation window have changed, update the star sizes and positions
        			
        			if (chartWidth1 == canvas.getWidth() & chartHeight1 == canvas.getHeight()) {
        				
        			} else {
        				
        				updateStars();
        				
        			}
        			 
        			//calculate the sun's chart coordinates and radius
        			
        			Sun sun = new Sun(d(calendar));
        			
        			if(sunIndicator.isSelected()) {
        				
        				updateSun(sun);
        				
        			}
        			
        			//calculate the moon's chart coordinates and radius
        			
        			if(moonIndicator.isSelected()) {
        				
        				Moon moon = new Moon (d(calendar), sun.getML() , sun.getMA());
        				
        				updateMoon(moon);
        				
        			}
            	
        			//calculate the coordinates and radii of the planets
        			
        			Planet[] planets = Planet.calcPlanets(d(calendar), sun.getX(), sun.getY(), sun.getZ());
        			
        			int j = 0;
        			
        			for (Planet p : planets) {
        			
        				if(planetIndicators[j]==true) {
        					
        					updatePlanet(p,j);
        					
        				}
        				
        				j++;
        			}
        			
        			advanceTime();
        			
        		}
            }
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
}

