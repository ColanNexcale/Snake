import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode; 
import javafx.application.Platform;



public class Field extends Application{
	
	private final int WINDOW_WIDTH = 400;
	private final int WINDOW_HEIGHT = 400;
	private final int SCALING = 8;
	private static final int FIELD_TILENUMBER = ObjectType.FIELD.getLength();
	private final int FIELD_PADDING = WINDOW_HEIGHT/SCALING;
	private final int TILE_SIZE = (WINDOW_WIDTH-2*FIELD_PADDING) / FIELD_TILENUMBER;  
	/static StringBuilder inputReader = new StringBuilder(20);
	Controller controller; 
	

	
	
	static GridPane gameField;
	
	static void setUp (){
		System.out.println("Field: Launching");
		launch();
	}

	
	@Override
	public void start (Stage mainStage){
		
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		gameField = new GridPane();
		gameField.setStyle("-fx-border-width: 3;"+"-fx-border-color: black;");
		

		Button btnClose = new Button("Beenden");
		btnClose.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				controller.stopGame();
				Platform.exit();
			}
		});
		
		Button btnNewGame = new Button("Neues Spiel");
		btnNewGame.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent e){
				controller.restartGame();
			}
		});
	
		
		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle (KeyEvent e){
				controller.sendInput(e);
				System.out.println(e.getCode());
			}
		}); 
		
		
		
		
		HBox topSpacing = new HBox();
			topSpacing.setPrefWidth(WINDOW_WIDTH);
			topSpacing.setPrefHeight(FIELD_PADDING);
			root.setTop(topSpacing);
			topSpacing.getChildren().add(btnClose);
			topSpacing.getChildren().add(btnNewGame);
			
		HBox botSpacing = new HBox();
			botSpacing.setPrefWidth(WINDOW_WIDTH);
			botSpacing.setPrefHeight(FIELD_PADDING);
			root.setBottom(botSpacing);
		
		VBox leftSpacing = new VBox();
			leftSpacing.setPrefWidth(FIELD_PADDING);
			leftSpacing.setPrefHeight(WINDOW_HEIGHT-(2*FIELD_PADDING));
			root.setLeft(leftSpacing);
		
		VBox rightSpacing = new VBox();
			rightSpacing.setPrefWidth(FIELD_PADDING);
			rightSpacing.setPrefHeight(WINDOW_HEIGHT-(2*FIELD_PADDING));
			root.setRight(rightSpacing);
		
		for (int i = 0; i < FIELD_TILENUMBER; i++){ 
			for (int k = 0; k < FIELD_TILENUMBER; k++){
					gameField.add(new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE),k,i);
			}	
		}


		root.setCenter(gameField);
		
		System.out.println("Field: Build ready, showing stage");
		mainStage.setScene(mainScene);
		mainStage.show();
		

		controller = controller.getController ();
		controller.setGame(new Game());
		controller.initializeGame();
			
	}
	
	static void update (ObjectType[][] field){
		for (int row = 0; row < field.length; row++){
			for (int col = 0; col < field[0].length; col++){
					paintTileAt(row, col, field[row][col].getColor());
			}
		}
	}
	
	
	
	private static void paintTileAt (int x, int y, Color newColor){
		//Umrechnen der Koordinaten in Listenindex, da getChildren eine Node List zurückgibt => Zugriff auf Grid Objekte
		int index = x*FIELD_TILENUMBER + y;
		Rectangle temp = (Rectangle) (gameField.getChildren().get(index));
		temp.setFill(newColor);
	}
	
	
}
