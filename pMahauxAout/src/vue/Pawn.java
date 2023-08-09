package vue;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Pawn extends Circle {
	public Pawn(double radius, Color couleur) {
		setRadius(radius);
		setFill(couleur);
		setOpacity(0.75);
	}
}
