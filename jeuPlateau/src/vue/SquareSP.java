package vue;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SquareSP extends StackPane{
	
	private Label lblPoint;
	private int col;
	private boolean piege;
	
	public SquareSP(int col) {
		setPrefSize(60, 65);
		this.col=col;
		Random r =  new Random();
		int i = 0 + r.nextInt(11);
		if(i==10) {
			this.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
			piege=true;
		}
		this.getChildren().addAll(getLblPoint());
		
		
	}
	
	public Label getLblPoint() {
		if(lblPoint==null) {
			Random r = new Random();
			int i = 0 + r.nextInt(10);
			String chaine = Integer.toString(i);
			lblPoint = new Label(chaine);
					
		}
		return lblPoint;
	}

	public boolean isPiege() {
		return piege;
	}

	public void setPiege(boolean piege) {
		this.piege = piege;
	}
	
	

}
