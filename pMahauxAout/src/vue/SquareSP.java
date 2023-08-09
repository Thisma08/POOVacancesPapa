package vue;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SquareSP extends StackPane {	
	private Label lblPoints;
	private int col;
	private boolean blackHole;
	
	public SquareSP(int col) 
	{
		setPrefSize(65, 65);
		this.col=col;
		this.getChildren().addAll(getLblPoint());		
	}
	
	public Label getLblPoint() 
	{
		if(lblPoints==null) 
		{
			Random r = new Random();
			int i = 0 + r.nextInt(10);
			String chaine = Integer.toString(i);
			lblPoints = new Label(chaine);				
		}
		return lblPoints;
	}

	public boolean isBlackHole() 
	{
		return blackHole;
	}

	public void setBlackHole(boolean blackHole) 
	{
		this.blackHole = blackHole;
	}
	
	

}
