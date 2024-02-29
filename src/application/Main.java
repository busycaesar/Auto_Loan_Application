package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a BorderPane and load the fxml file.
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("AutoLoan.fxml"));
			
			// Create a scene, load the BorderPane, and set the application dimensions.
			Scene scene = new Scene(root,815,515);
			
			// Set the scene on the stage.
			primaryStage.setScene(scene);
			
			// Set the property of the stage.
			primaryStage.setTitle("AutoLoan");
			primaryStage.setResizable(false);
			
			// Display the stage.
			primaryStage.show();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
