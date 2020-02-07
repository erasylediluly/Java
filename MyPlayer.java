import javafx.scene.shape.*;
import javafx.scene.paint.*;
public class MyPlayer implements Player{
	private Circle ball = new Circle();
	private Map map;
	private Position position;
	public MyPlayer(Map map){
		this.map = map;
		this.position = map.getStartPosition();
		ball = new Circle(position.getX()* map.getUnit() + map.getUnit() / 2, position.getY() * map.getUnit()+ map.getUnit() / 2, map.getUnit() / 2, Color.RED);
		this.map.getChildren().add(ball);
	}
	public void moveRight(){
		position.setX(position.getX() + 1);
		ball.setCenterX(position.getX() * map.getUnit()+ map.getUnit() / 2);
	}
	public void moveLeft(){
		position.setX(position.getX() - 1);
		ball.setCenterX(position.getX()* map.getUnit() + map.getUnit() / 2);
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
}