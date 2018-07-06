import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;


class ObjectSpawner extends FixedRateService {
	
	int delay = 0;
	boolean spawning = false;
	int spawnrateMultiplicator = 900;
	static Controller controller;
	int fieldSize;
	
	ObjectSpawner(String name){
		super(name);
		controller = controller.getController();
		System.out.println(controller == null);
		fieldSize = controller.getFieldSize();
	}
	
	ObjectType[][] getInitialSpawns (int spawnNumber){
		System.out.println("Objectspawner: Creating initial objects");
		ObjectType[][] spawns = getVoidField();
		for (int i = 0; i < spawnNumber; i++){
			spawns[getRandomPosition()][getRandomPosition()] = ObjectType.APPLE;
		}

		return spawns;
	}
	
	ObjectType[][] getVoidField (){
		System.out.println("Create void field");
		ObjectType[][] newField = new ObjectType[fieldSize][fieldSize];
		for (int i = 0; i < fieldSize; i++){
			for (int k = 0; k < fieldSize; k++){
				newField[i][k] = ObjectType.VOID;
			}
		}
		return newField;
	}
	
	private int getRandomPosition (){
		int rand = (int)  (Math.random()*fieldSize);
		return (int)  (rand - (rand % 10)+ Math.random()*10);
	}
	
	void startAutoSpawn(int spawnRate){
		interval = spawnRate*spawnrateMultiplicator;
		start();
		spawning = true;
	}
	
	boolean isSpawning (){
		return spawning;
	}

	
	Runnable createTask(){
		return new Runnable(){
			@Override
			public void run(){
				ObjectType[][] currentField = controller.getGameField();
				boolean placed = false;
				while (placed == false){
					int x = getRandomPosition();
					int y = getRandomPosition();
					if (currentField[x][y] == ObjectType.VOID && currentField[x][y] != ObjectType.SNAKE){
						if (getRandomPosition() < currentField.length/3){
							currentField[x][y] = ObjectType.APPLE;
						} 
						else{
							currentField[x][y] = ObjectType.POISON;
							//Field.paintTileAt(x,y,ObjectType.POISON.getColor());
						} 
						placed = true;
						System.out.println("OS: new spawn placed: "+x+", "+y);
					}	
				}
			}
		};
	}
}
