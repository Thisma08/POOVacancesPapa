package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import modele.Player;
import vue.BoardBP;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {		
			BorderPane bp = new BoardBP();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(bp,950,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
