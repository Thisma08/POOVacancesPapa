package vue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.NullPlayerException;
import exceptions.PlayersSameNameException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import modele.GestionPlayer;
import modele.Player;
import persistence.Write;

public class BoardBP extends BorderPane {
	private GestionPlayer gestion;	
	private GridPane grid;
	private Pawn pawn1;
	private Pawn pawn2;
	private Pawn pawn3;
	private Pawn pawn4;
	private Pawn pawn5;
	private Map<Player, Pawn> mapPlayers;
	private TableView<Player> tvPlayers;
	public static final int DIM_BOARD = 12 ;
	public static final int NB_Player = 4;
	public int nbJoueurDead=0;	
	private Button btnMove;
	private Label lblFinPartie;
	
	public BoardBP(GestionPlayer gestion) throws PlayersSameNameException, NullPlayerException 
	{	
		this.setLeft(getGrid());
		this.setBottom(getBtnMove());
		this.setRight(getTvPlayers());
		getTvPlayers().setPrefWidth(200);
		this.gestion = gestion;
	}

	public GridPane getGrid() 
	{
		if(grid==null) 
		{
			grid = new GridPane();
			grid.setGridLinesVisible(true);
			
			for(int j=0; j<DIM_BOARD; j++) 
			{
				for(int i=0; i<DIM_BOARD; i++) 
				{
					SquareSP square = new SquareSP(i);
					grid.add(square, i, j);
				}	
			}
			
			for(int i = 0; i<DIM_BOARD;i++) 
			{
				ColumnConstraints colConst = new ColumnConstraints();
				colConst.setPercentWidth(100.0/DIM_BOARD);
				grid.getColumnConstraints().add(colConst);
			}
			
			ObservableList<Node> squares = grid.getChildren();
			Random r = new Random();
			int randIndex = r.nextInt(squares.size());
			for(int i=1; i<=25; i++) 
			{
				SquareSP square = (SquareSP) squares.get(randIndex);
				while(square.isBlackHole()==true) 
				{
					randIndex = r.nextInt(squares.size());
					square = (SquareSP) squares.get(randIndex);					
				}
				square.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				square.setBlackHole(true);	
			}		
			grid.add(getPawn1(), 0, 0);
			grid.add(getPawn2(), 0, 1);
			grid.add(getPawn3(), 0, 2);
			grid.add(getPawn4(), 0, 3);
			grid.add(getPawn5(), 0, 4);			
		}
		return grid;
	}

	public TableView<Player> getTvPlayers() throws PlayersSameNameException, NullPlayerException {
		if(tvPlayers==null) 
		{
			tvPlayers = new TableView<Player>();			
			tvPlayers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvPlayers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);			
			tvPlayers.setItems(FXCollections.observableArrayList(this.getGestion().getPlayers()));			
			TableColumn<Player, Pawn> colName=new TableColumn<>("Name");
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));           
            TableColumn<Player, Pawn> colScore =new TableColumn<>("Score");
            colScore.setCellValueFactory(new PropertyValueFactory<>("Score"));           
            TableColumn<Player, Rectangle> colColor = new TableColumn<>("Color");            
            List<Rectangle> rects = new ArrayList<Rectangle>();
            rects.add(new Rectangle(30., 30., Color.BLUE));
            rects.add(new Rectangle(30., 30., Color.RED));
            rects.add(new Rectangle(30., 30., Color.YELLOW));
            rects.add(new Rectangle(30., 30., Color.GREEN));
            rects.add(new Rectangle(30., 30., Color.ORANGE));            
            colColor.setCellValueFactory(cellData -> 
            {
                Player player = cellData.getValue();
                return new SimpleObjectProperty<>(rects.get(player.getId()-1));
            });          
            tvPlayers.getColumns().addAll(colName, colScore, colColor);
		}
		return tvPlayers;
	}
	
	public Button getBtnMove() 
	{
		if(btnMove==null) 
		{
			btnMove = new Button("Move");
			btnMove.setPrefWidth(Integer.MAX_VALUE);
			
			btnMove.setOnAction(event -> 
			{
				for (Map.Entry<Player, Pawn> entry : mapPlayers.entrySet()) 
				{
					Player player = entry.getKey();
					Pawn pawn = entry.getValue();
					if(player.isAlive()) 
					{											
						getGrid().getChildren().remove(pawn);
						Random r = new Random();
						int newXPosition = 0 + r.nextInt(DIM_BOARD);
						int newYPosition = 0 + r.nextInt(DIM_BOARD);
						getGrid().add(pawn, newXPosition, newYPosition);
						
						 for (javafx.scene.Node node : getGrid().getChildren()) 
						 {
							 Integer columnIndex = GridPane.getColumnIndex(node);
					         Integer rowIndex = GridPane.getRowIndex(node);
					         if (columnIndex != null && rowIndex != null && columnIndex == newXPosition && rowIndex == newYPosition) 
					         {            	
					             if (node instanceof SquareSP) 
					             {
					                 SquareSP targetSquare = (SquareSP) node;
					                 Label score = targetSquare.getLblPoint();
					                 if(targetSquare.isBlackHole()) 
					                 {
		                                 player.setAlive(false);			                              
						                 nbJoueurDead++;
						                 if(nbJoueurDead==NB_Player) 
						                 {							             
						                 	 gameOver();
						                 }     
					                 }
					                 else 
					                 {						                    					                 							                    
						                 player.setScore(Integer.parseInt(score.getText()));							                    
						                 if(player.isWinner()) 
						                 {
						                	 gameOver();
						                 }				                    					            
					                 }
					                 try 
					                 {
					                	 getTvPlayers().refresh();
					                 } catch (PlayersSameNameException | NullPlayerException e) 
					                 {
					                	 // TODO Auto-generated catch block
										 e.printStackTrace();
									 }
					                 break;
					             }
					         }				
						 }
					}						
				}									
			});
		}
		return btnMove;
	}
	
	public void gameOver() 
	{
		getBtnMove().setVisible(false);
		lblFinPartie = new Label("Game Over");
		this.setBottom(lblFinPartie);
		Write.write(gestion, "finalScores.json");		
	}
	
	public GestionPlayer getGestion() throws PlayersSameNameException, NullPlayerException 
	{
		if(gestion==null) 
		{
			getMapPlayers().put(gestion.getPlayerOfIndex(0), getPawn1());
			getMapPlayers().put(gestion.getPlayerOfIndex(1), getPawn2());
			getMapPlayers().put(gestion.getPlayerOfIndex(2), getPawn3());
			getMapPlayers().put(gestion.getPlayerOfIndex(3), getPawn4());
			getMapPlayers().put(gestion.getPlayerOfIndex(4), getPawn5());
		}
		return gestion;
	}

	public Map<Player, Pawn> getMapPlayers() 
	{
		if(mapPlayers==null) 
		{
			mapPlayers = new LinkedHashMap<>();
		}
		return mapPlayers;
	}

	public Pawn getPawn1() 
	{
		if(pawn1==null)
		{
			pawn1 = new Pawn(20,Color.BLUE);
		}
		return pawn1;
	}

	public Pawn getPawn2() 
	{
		if(pawn2==null) 
		{
			pawn2 = new Pawn(20,Color.RED);
		}
		return pawn2;
	}

	public Pawn getPawn3() 
	{
		if(pawn3==null) 
		{
			pawn3 = new Pawn(20,Color.YELLOW);
		}
		return pawn3;
	}
	
	public Pawn getPawn4() 
	{
		if(pawn4==null) 
		{
			pawn4 = new Pawn(20,Color.GREEN);
		}
		return pawn4;
	}
	
	public Pawn getPawn5() 
	{
		if(pawn5==null) 
		{
			pawn5 = new Pawn(20,Color.ORANGE);
		}
		return pawn5;
	}
}
