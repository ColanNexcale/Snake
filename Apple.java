
class Apple extends GameObject{
	
	final int GROWTH = 1;
	
	Apple (int x, int y, ObjectType which){
		super(x, y, which);
	}
	
	int getEffect(){
		return GROWTH;
	}
	
	int getX(){
		return xPos;
	}
	
	int getY(){
		return yPos;
	}
}
