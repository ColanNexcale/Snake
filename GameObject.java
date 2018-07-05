import javafx.scene.paint.Color;

abstract class GameObject {
	
 	Position pos;
	int length;
	ObjectType which;
	
	GameObject(Position pos, ObjectType which){
		this.pos = pos;
		this.length = which.getLength();
		this.which = which;
	}
	
	abstract Position getPosition();
	
	ObjectType getType(){
		return which;
	}
	
	int getLength(){
		return length;
	}
		
		
}
