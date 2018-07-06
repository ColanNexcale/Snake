import javafx.scene.paint.Color;

public enum ObjectType{

	SNAKE(3, -1, Color.BLACK),
	APPLE(1, 10, Color.GREEN),
	POISON(1, -1, Color.RED),
	FIELD(50, 0, Color.WHITE),
	VOID (1, 0, Color.WHITE);
	
	private final int initialLength;
	private final Color coloring;
	private final int value;
	
	ObjectType(int initialLength, int value, Color coloring){
		this.initialLength = initialLength;
		this.coloring = coloring;
		this.value = value;
	}
	
	int getLength() {return initialLength;}
	Color getColor (){return coloring;}
	int getValue(){return value;}
}
