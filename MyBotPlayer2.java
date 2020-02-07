import java.util.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
class MyBotPlayer2 implements BotPlayer{
	private Circle ball;
	private Position position;
	private Map map;
	private Food food;
	private static ArrayList<Position> path = new ArrayList<>();
	public MyBotPlayer2(Map map){
		this.map = map;
		this.position = map.getStartPosition();
		this.ball = new Circle(position.getX()* map.getUnit() + map.getUnit() / 2, position.getY() * map.getUnit()+ map.getUnit() / 2, map.getUnit() / 2, Color.RED);
		this.map.getChildren().add(ball);
	}
	// Passes the initialized food to the bot player
	public void feed(Food f){
		this.food = f;
	}
	// In a map without any walls
	// eats all food elements
	// by choosing the shortest path
	public void eat(){
		new Thread(()->{
			try{
				while(true){
					getPath();
					for(int i = 0; i < path.size(); i++){
						if(!food.getPosition().equals(path.get(path.size() - 1))){
							clearPath();
							break;
						}
						if(this.getPosition().equals(food.getPosition())){
							clearPath();
							break;
						}
						if(path.get(i).getX() > this.getPosition().getX()){
							moveRight();
							this.setPosition(path.get(i));
						}
						else if(path.get(i).getX() < this.getPosition().getX()){
							moveLeft();
							this.setPosition(path.get(i));
						}
						if(path.get(i).getY() < this.getPosition().getY()){
							moveUp();
							this.setPosition(path.get(i));
						}
						else if(path.get(i).getY() > this.getPosition().getY()){
							moveDown();
						this.setPosition(path.get(i));
						}
						Thread.sleep(400);
					}
					clearPath();
				}
			}
			catch(InterruptedException ex){
				System.out.println("InterruptedException");
			}
		}).start();
	}
	public void getPath(){
		int startX = this.getPosition().getX();
		int startY = this.getPosition().getY();
		int endX = food.getPosition().getX();
		int endY = food.getPosition().getY();
		while(startX != endX){
			if(startX > endX){
				path.add(new Position(startX, startY));
				startX--;
			}
			else if(startX < endX){
				path.add(new Position(startX, startY));
				startX++;
			}
		}
		while(startY != endY){
			if(startY > endY){
				path.add(new Position(startX, startY));
				startY--;
			}
			else if(startY < endY){
				path.add(new Position(startX, startY));
				startY++;
			}
		}
		path.add(new Position(startX, startY));
	}
	public void find(){}
	public void moveRight(){
		position.setX(position.getX() + 1);
		ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
	}
	public void moveLeft(){
		position.setX(position.getX() - 1);
		ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
	}
	public void moveUp(){
		position.setY(position.getY() - 1);
		ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
	}
	public void moveDown(){
		position.setY(position.getY() + 1);
		ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
	}
	public Position getPosition(){
		return position;
	}
	public Circle getBall(){
		return this.ball;
	}
	public void setPosition(Position pos){
		this.position = pos;
	}
	public void clearPath(){
		this.path.clear();
	}
}	