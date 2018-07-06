import javafx.scene.paint.Color;


class TheSnake extends GameObject{
	
	int length = ObjectType.SNAKE.getLength();
	Color color = ObjectType.SNAKE.getColor();
	SnakeBody body;
	
	TheSnake (Position pos){
		super(pos, ObjectType.SNAKE);	
		body = new SnakeBody(pos);
	}
	
	/* LENGTH AND POSITION PROPERTIES */
	
	//gets all x,y coordinates of active, conjuncted bodyparts 
	
	int getLength(){
		return body.getLength();
	}
	
	
	Position getPosition(){
		return body.getHeadPosition();
	}
	
	Position getTail (){
		return body.getTailPosition();
	}
	
	
	/* DIRECTION MODIFICATION */
	
	Direction getDirection(){
		return body.head.getDirection();
	}
	
	void changeDirection(Direction dir){
		switch (getDirection()){
			case UP: if (dir == Direction.DOWN){} 
								else body.setDirection(dir); break;
			case LEFT: if (dir == Direction.RIGHT){} 
								else body.setDirection(dir); break;
			case DOWN: if (dir == Direction.UP){} 
								else body.setDirection(dir); break;
			case RIGHT: if (dir == Direction.LEFT){} 
								else body.setDirection(dir); break;
		}

	}
	
	/* LENGTH MODIFICATION */
	
	//add/remove bodyparts to/from the snake
	void extend (Position pos, Direction dir, int headOrTail){
		if (headOrTail == 0){
			body.prepend(pos, dir);	
		}else{
			body.append();
		}	
	}
 
	
	void shorten(){
		body.removeTail();
	}
	
	
	class SnakeBody {
	
		class BodyPart{
			private Position pos;
			private Direction dir;
			private BodyPart next;
			
			BodyPart (Position pos, Direction dir){	
				this.pos = pos;
				this.dir = dir;
			}	
			
			Direction getDirection(){
				return dir;
			}
			
			Position getPosition(){
				return pos;
			}
		}
		
		BodyPart head;
		int length = 0;
		
		//change direction of head <=> movement
		private void setDirection(Direction dir){
			head.dir = dir;
		}
		
		//constructor builds first few bodybparts as defined in enum ObjectType{}
		SnakeBody (Position pos){
			for (int i =  ObjectType.SNAKE.getLength()-1; i >= 0; i--){
				if (head == null){
					BodyPart bp = new BodyPart(new Position (pos.x, pos.y-i), Direction.RIGHT);
					head = bp;
					length++;
				}
				else
					prepend(new Position(pos.x, pos.y-i), Direction.RIGHT);	
			}
		}
		
		Position getHeadPosition(){
			return head.getPosition();
		}
		
		Position getTailPosition(){
			BodyPart bp = head;
			while (bp.next != null){
				bp = bp.next;
			}
			return bp.getPosition();
		}
		
		private int getX (){
			return head.getPosition().x;
		}
		
		private int getY(){
			return head.getPosition().y;
		}
		
		private Direction getDirection (){
			return head.getDirection() ;
		}
		
		//add/remove bodyparts
		private void prepend(Position pos, Direction dir){
			BodyPart bp = new BodyPart(pos, dir);
			bp.next = head;
			head = bp;
			length++;		
		}
		
		private void append (){
			BodyPart bp = head;
			int x = getTailPosition().x;
			int y = getTailPosition().y;
			System.out.println("test"+ " x: "+x+", y: "+y);
			while (bp.next != null)
				bp = bp.next;
				
			switch(bp.getDirection()){
				case UP: bp.next = new BodyPart(new Position(x-1, y), bp.getDirection()); break;
				case DOWN: bp.next = new BodyPart(new Position(x+1, y), bp.getDirection()); break;
				case LEFT: bp.next = new BodyPart(new Position(x, y+1), bp.getDirection()); break;
				case RIGHT: bp.next = new BodyPart(new Position(x, y-1), bp.getDirection()); break;
			}
			length++;
		}
		
		private void removeTail(){
			BodyPart bp = head;
			while (bp.next.next != null)
				bp = bp.next;
			bp.next = null;
			length--;
		}		
		
		private int getLength(){
			return length;
		}
	}
}
