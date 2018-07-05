import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode; 

public final class Controller{

	private Game game;
	private boolean gameRunning;
	private boolean gameInstanciated;
	private static Controller theController = null;
	
	private Controller (){
		gameRunning = false;
		gameInstanciated = false;
	}
	
	public static synchronized Controller getController(){
		if (theController == null)
			theController = new Controller();
		return theController;
	}
	
	void setGame (Game game){
		this.game = game;
		gameInstanciated = true;
	}
	
	
	void initializeGame (){
	if(gameInstanciated == true)
		gameRunning = game.initialize();
	}
	
	void stopGame(){
		if (gameRunning == true){
			game.stopGame();
			gameRunning = false;
		}	
	}
	
	
	void restartGame(){
		game.restart();
	}
	
	boolean isRunning(){
		return gameRunning;
	}
	
	boolean hasGameInstance(){
		return gameInstanciated;
	}
	void sendInput(KeyEvent e){
		game.sendInput(e.getCode());
	}
	
	ObjectType[][] getGameField(){
		return game.getFieldState();
	}
	
	int getFieldSize(){
		return game.getFieldSize();
	}	
	
	void moveSnake(){
		game.moveSnake();
	}
}
