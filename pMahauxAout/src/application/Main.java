package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import modele.GestionPlayer;
import modele.Player;
import vue.BoardBP;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try 
		{
			Player p1 = new Player("P1");
			Player p2 = new Player("P2");
			Player p3 = new Player("P3");
			Player p4 = new Player("P4");
			Player p5 = new Player("P5");
			GestionPlayer gestion = new GestionPlayer();
			gestion.add(p1);
			gestion.add(p2);
			gestion.add(p3);
			gestion.add(p4);
			gestion.add(p5);
			System.out.println(gestion.toString());
			BorderPane bp = new BoardBP(gestion);
			Scene scene = new Scene(bp,980,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
