import javafx.scene.paint.Color;

public enum ObjectType{

	SNAKE(3, Color.BLACK),
	APPLE(1, Color.GREEN),
	POISON(1, Color.RED),
	FIELD(50, Color.WHITE),
	VOID (1, Color.WHITE);
	
	private final int initialLength;
	private final Color coloring;
	
	ObjectType(int initialLength, Color coloring){
		this.initialLength = initialLength;
		this.coloring = coloring;
	}
	
	int getLength() {return initialLength;}
	Color getColor (){return coloring;}
}
