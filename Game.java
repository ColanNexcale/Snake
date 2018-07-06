import javafx.scene.input.KeyCode; 
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//TODO 
/*
- Field => View etablieren, update(ObjectType[][] ota) als einzige schnittstelle
- Game => ruft nur update auf, ObjectField als Repräsentation (speichert alle positionen)


- x,y als Pos-Object codieren


*/

public class Game{
	
	ObjectType[][] field;
	final int FIELD_SIZE = ObjectType.FIELD.getLength();
	private TheSnake snek;
	private Field gui;
	private AutoMover amo;
	private ObjectSpawner os;
	private Score score;
	GameView view;
	private int countdownToAutospawn = 1;
	private int autoSpawnRate = 10;
	private int initialX = FIELD_SIZE/2;
	private int initialY = FIELD_SIZE/2;
	private int speed = 500;
	//private int speedIncrease = 50;
	private int initialSpawnNumber = FIELD_SIZE;

	
	Game(){	}
	
	boolean initialize(){

		os = new ObjectSpawner("Object Spawner");
		view = new GameView();
		score = new Score(0);
		field = os.getInitialSpawns(initialSpawnNumber);
		snek = placeSnake();
		view.update(field, score);  
		startAutoMove();
		return true;
	}
	
	
	private TheSnake placeSnake (){
		System.out.println("Game: Creating the snake");
		TheSnake snek = new TheSnake(new Position (initialX, initialY));
		for (int i = 0; i < snek.getLength(); i++){
			field[initialX][initialY-i] = ObjectType.SNAKE;	
		}
		return snek;
	}
	
	void restart (){
		stopGame();
		initialize();
	}
	
	void gameLost(){
		System.out.println("Lost");
		amo.stop();
		os.stop();
	};
	
	void stopGame(){
		amo.stop();
		os.stop();
	}
	
	ObjectType[][] getFieldState(){
		return field;
	}
	
	int getFieldSize(){
		return FIELD_SIZE;
	}

	Direction getCurrentSnakeDirection (){
		return snek.getDirection();
	}	

	private	void startAutoMove(){
		amo = new AutoMover(speed, "Auto Mover");
		amo.start();
	}
	

	private void checkAutospawn(){
		 if (countdownToAutospawn <= 0 && os.isSpawning() == false){
		 	os.startAutoSpawn(autoSpawnRate);
		 }
	}	
	
	
	void sendInput (KeyCode input){
		switch (input){
			case W: snek.changeDirection(Direction.UP); break;
			case A: snek.changeDirection(Direction.LEFT); break;
			case S: snek.changeDirection(Direction.DOWN); break;
			case D: snek.changeDirection(Direction.RIGHT); break;
		}
	}
	
	void moveSnake (){
		int x = snek.getPosition().x;
		int y = snek. getPosition().y;
		Direction dir = snek.getDirection();
		switch (dir){
			case UP:if(!collides(x-1,y)){
								move(x-1, y, dir);
							}			
							else {
							 gameLost();
							 
							}				 
							break;
							
			case LEFT: if(!collides(x, y-1)){
									move(x, y-1, dir);
								}	
								else gameLost(); 
								break;
								
			case DOWN:if (!collides(x+1, y)){
										move(x+1, y, dir);
									}
								else gameLost();
								break;
			case RIGHT:	if(!collides(x, y+1)){
											move(x, y+1, dir);
										}
									else gameLost();
									break;
		}
	}

	private void move (int x, int y, Direction dir){
		
		ObjectType next = field[x][y];
		switch (next){
			case VOID: movementStep(x,y,dir); break;
			case APPLE: eatingStep(x,y,dir);break;
			case POISON: gameLost(); break;
		}
	}
	
	//TODO implement score

	private void eatingStep(int x, int y, Direction dir){
		snek.extend(new Position(x, y), dir, 0);
		score.add(field[x][y].getValue());
		field[x][y] = ObjectType.SNAKE;
		view.update(field, score);
		amo.updateSpeed();
		countdownToAutospawn--;
		checkAutospawn();

	}
	
	private void movementStep (int x, int y, Direction dir){
		snek.extend(new Position(x,y), dir, 0);
		field[x][y] = ObjectType.SNAKE;
		Position tail = snek.getTail();
		field[tail.x][tail.y] = ObjectType.VOID;
		snek.shorten();
		view.update(field);
	}

	
	private boolean insideBounds (int x, int y){
		return ((x >= 0 && x < field.length) && (y >= 0 && y < field[0].length));
	}
	
	private boolean collides(int newX, int newY){
		if (!insideBounds(newX, newY)){
			return true;
		}
		else return field[newX][newY] == ObjectType.SNAKE;
	}
	
}
