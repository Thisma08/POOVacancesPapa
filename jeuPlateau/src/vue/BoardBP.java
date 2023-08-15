package vue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exception.DeuxJoueurMemeNom;
import exception.JoueurNull;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import modele.GestionPlayer;
import modele.Player;
import persistance.Ecrire;
import persistance.Lire;


public class BoardBP extends BorderPane {
	
	private GestionPlayer gestion;
	
	private GridPane grid;
	private Pawn pion1;
	private Pawn pion2;
	private Pawn pion3;
	
	private Player player1;
	private Player player2;
	private Player player3;
	
	private Map<Player, Pawn> mapPlayers;
	
	private TableView<Player> tvPlayer;
	
	public static final int NB_CASE = 12 ;
	public static final int NB_Player = 2;
	public int nbJoueurDead=0;
	
	private Button btnMove;
	private Label lblFinPartie;
	
	//Lecture
	private Gson gson=new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private String json = Lire.lire();
	private GestionPlayer gestionImport = gson.fromJson(json, GestionPlayer.class);
	
	
	public BoardBP() throws DeuxJoueurMemeNom, JoueurNull {
		
		this.setLeft(getGrid());
		this.setBottom(getBtnMove());
		this.setRight(getTvPlayer());
	}


	public GridPane getGrid() {
		if(grid==null) {
			grid = new GridPane();
			grid.setGridLinesVisible(true);
			
			for(int j=0; j<=NB_Player; j++) {
				for(int i=0; i<NB_CASE; i++) {
					SquareSP square = new SquareSP(i);
					grid.add(square, i, j);
				}	
			}
			
			for(int i = 0; i<NB_CASE;i++) {
				ColumnConstraints colConst = new ColumnConstraints();
				colConst.setPercentWidth(100.0/NB_CASE);
				grid.getColumnConstraints().add(colConst);
			}
			
			grid.add(getPion1(), 0, 0);
			grid.add(getPion2(), 0, 1);
			grid.add(getPion3(), 0, 2);
			
		}
		return grid;
	}


	public TableView<Player> getTvPlayer() throws DeuxJoueurMemeNom, JoueurNull {
		if(tvPlayer==null) {
			tvPlayer = new TableView<Player>();
			
			tvPlayer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvPlayer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			tvPlayer.setItems(FXCollections.observableArrayList(this.getGestion().getPlayers()));
			
			TableColumn<Player, Pawn> colName=new TableColumn<>("Name");
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            
            TableColumn<Player, Pawn> colScore =new TableColumn<>("Score");
            colScore.setCellValueFactory(new PropertyValueFactory<>("Score"));
            
            TableColumn<Player, Rectangle> colIcon = new TableColumn<>("Icon");
            
            List<Rectangle> rects = new ArrayList<Rectangle>();
            rects.add(new Rectangle(30., 30., Color.BLUE));
            rects.add(new Rectangle(30., 30., Color.RED));
            rects.add(new Rectangle(30., 30., Color.YELLOW));
            
            
            colIcon.setCellValueFactory(cellData -> {
                Player player = cellData.getValue();
                return new SimpleObjectProperty<>(rects.get(player.getId()-1));

            });
            
            tvPlayer.getColumns().addAll(colIcon, colName, colScore);
		}
		return tvPlayer;
	}

// Backup
//	public Button getBtnMove() {
//		if(btnMove==null) {
//			btnMove = new Button("Move");
//			btnMove.setPrefWidth(Integer.MAX_VALUE);
//			
//			btnMove.setOnAction(new EventHandler<ActionEvent>() {
//				
//				@Override
//				public void handle(ActionEvent event) {
//					// TODO Auto-generated method stub
//					
//					//Variable qui nous permettra d'indiquer sur quelle ligne on travaille
//					int numLigne = 0;
//					
//					for (Map.Entry<Player, Pawn> entry : mapPlayers.entrySet()) {
//						
//						//On récupère la pion associé au joueur
//						Player player = entry.getKey();
//						Pawn pawn = entry.getValue();
//						//Si le joueur est toujours en vie
//						if(player.isAlive()) {
//													
//							//génère la nouvelle position
//							getGrid().getChildren().remove(pawn);
//							Random r = new Random();
//							int newPosition = 0 + r.nextInt(NB_CASE);
//							getGrid().add(pawn, newPosition, numLigne);
//							
//							 for (javafx.scene.Node node : getGrid().getChildren()) {
//						            Integer columnIndex = GridPane.getColumnIndex(node);
//						            Integer rowIndex = GridPane.getRowIndex(node);
//						            if (columnIndex != null && rowIndex != null && columnIndex == newPosition && rowIndex == numLigne) {
//						            	
//						                // Objet trouvé aux coordonnées spécifiées
//						                if (node instanceof SquareSP) {
//						                    SquareSP targetSquare = (SquareSP) node;
//						                    Label score = targetSquare.getLblPoint();
//						                    //vérifie la case si piège ou non
//						                    if(targetSquare.isPiege()) {
//						                    	// Retire le joueur et son pion de mapPlayers
//			                                    player.setAlive(false);			                              
//			                                    // Ecrire la score en console
//							                    System.out.println(player.getName()+" est tombé sur une case piege ");
//							                    System.out.println("Sa partie est terminée");
//							                    
//							                    //Incrementation nb joueur mort + vérification si un joueur en vie restant
//							                    nbJoueurDead++;
//							                    if(nbJoueurDead==NB_Player) {
//							                    	finPartie();
//							                    }
//							                    
//						                    }
//						                    else {
//						                    					                    
//							                    // Ecrire la score en console
//							                    System.out.println(player.getName()+" est tombé sur la case " + score.getText());
//							                    
//							                    //Additionner/Donner un score au joueur en question
//							                    player.setScore(Integer.parseInt(score.getText()));
//							                    System.out.println(player.getScore());
//							                    
//							                    //Verifier si joueur est gagnant ? 
//							                    if(player.joueurGagnant()) {
//							                    	finPartie();
//							                    }
//						                    					            
//						                    }
//						                    //Synchroniser score avec tableView
//						                    try {
//												getTvPlayer().refresh();
//											} catch (DeuxJoueurMemeNom | JoueurNull e) {
//												// TODO Auto-generated catch block
//												e.printStackTrace();
//											}
//						                    break;
//						                }
//						            }				
//							 }
//							 numLigne++;
//						}
//						else {
//							numLigne++;
//						}
//					}
//				}
//			});
//		}
//		return btnMove;
//	}
	
	
	public Button getBtnMove() {
		if(btnMove==null) {
			btnMove = new Button("Move");
			btnMove.setPrefWidth(Integer.MAX_VALUE);
			
			btnMove.setOnAction(event -> {
				
				
					
					//Variable qui nous permettra d'indiquer sur quelle ligne on travaille
					int numLigne = 0;
					
					for (Map.Entry<Player, Pawn> entry : mapPlayers.entrySet()) {
						
						//On récupère la pion associé au joueur
						Player player = entry.getKey();
						Pawn pawn = entry.getValue();
						//Si le joueur est toujours en vie
						if(player.isAlive()) {
													
							//génère la nouvelle position
							getGrid().getChildren().remove(pawn);
							Random r = new Random();
							int newPosition = 0 + r.nextInt(NB_CASE);
							getGrid().add(pawn, newPosition, numLigne);
							
							 for (javafx.scene.Node node : getGrid().getChildren()) {
						            Integer columnIndex = GridPane.getColumnIndex(node);
						            Integer rowIndex = GridPane.getRowIndex(node);
						            if (columnIndex != null && rowIndex != null && columnIndex == newPosition && rowIndex == numLigne) {
						            	
						                // Objet trouvé aux coordonnées spécifiées
						                if (node instanceof SquareSP) {
						                    SquareSP targetSquare = (SquareSP) node;
						                    Label score = targetSquare.getLblPoint();
						                    //vérifie la case si piège ou non
						                    if(targetSquare.isPiege()) {
						                    	// Retire le joueur et son pion de mapPlayers
			                                    player.setAlive(false);			                              
			                                    // Ecrire la score en console
							                    System.out.println(player.getName()+" est tombé sur une case piege ");
							                    System.out.println("Sa partie est terminée");
							                    
							                    //Incrementation nb joueur mort + vérification si un joueur en vie restant
							                    nbJoueurDead++;
							                    if(nbJoueurDead==NB_Player) {
							                    	finPartie();
							                    }
							                    
						                    }
						                    else {
						                    					                    
							                    // Ecrire la score en console
							                    System.out.println(player.getName()+" est tombé sur la case " + score.getText());
							                    
							                    //Additionner/Donner un score au joueur en question
							                    player.setScore(Integer.parseInt(score.getText()));
							                    System.out.println(player.getScore());
							                    
							                    //Verifier si joueur est gagnant ? 
							                    if(player.joueurGagnant()) {
							                    	finPartie();
							                    }
						                    					            
						                    }
						                    //Synchroniser score avec tableView
						                    try {
												getTvPlayer().refresh();
											} catch (DeuxJoueurMemeNom | JoueurNull e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
						                    break;
						                }
						            }				
							 }
						}						
					numLigne++;
					}				
			});
		}
		return btnMove;
	}
	
	public void debutPartie() {
		
	}
	
	public void finPartie() {
		
		getBtnMove().setVisible(false);
		
		lblFinPartie = new Label("Fin de la partie");
		this.setBottom(lblFinPartie);
		
		//Persistance, ecrire le scores des joueurs dans Json
		Ecrire.ecrire(gestion,  "scoreFinDePartie.json");
		
	}
	
	public GestionPlayer getGestion() throws DeuxJoueurMemeNom, JoueurNull {
		if(gestion==null) {
			gestion = gestionImport;
			
			
			getMapPlayers().put(gestion.getPlayerOfIndex(0), getPion1());
			getMapPlayers().put(gestion.getPlayerOfIndex(1), getPion2());
			getMapPlayers().put(gestion.getPlayerOfIndex(2), getPion3());
		}
		return gestion;
	}
	
	public Map<Player, Pawn> getMapPlayers() {
		if(mapPlayers==null) {
			mapPlayers = new LinkedHashMap<>();
		}
		return mapPlayers;
	}


	public Pawn getPion1() {
		if(pion1==null) {
			pion1 = new Pawn(20,Color.BLUE);
		}
		return pion1;
	}


	public Pawn getPion2() {
		if(pion2==null) {
			pion2 = new Pawn (20,Color.RED);
		}
		return pion2;
	}


	public Pawn getPion3() {
		if(pion3==null) {
			pion3 = new Pawn (20,Color.YELLOW);
		}
		return pion3;
	}
}
