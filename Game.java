import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
public class Game extends Application{
	private Map map;
	private Player player;
	private Food food;
	public void start(Stage primaryStage) throws InterruptedException{
		map = new Map(getParameters().getRaw().get(0));
		System.out.println("Map size: " + map.getSize());
		player = new MyPlayer(map);  
		food = new Food(map, player);
		Circle ball = player.getBall();
		Scene scene = new Scene(map);
		ball.setOnKeyPressed(e->{    
			switch(e.getCode()){
				case UP:
					if(player.getPosition().getY() - 1 >= 0 && player.getPosition().getY() - 1 < map.getSize()
									&& map.getMap()[player.getPosition().getX()][(player.getPosition().getY() - 1)] != 1){
						player.moveUp();
					}
					else{
						System.out.println("Invalid position");
					}
					break;
				case DOWN:
					if(player.getPosition().getY() + 1 >= 0 && player.getPosition().getY() + 1 < map.getSize()
									&& map.getMap()[player.getPosition().getX()][(player.getPosition().getY() + 1)] != 1){
						player.moveDown();
					}
					else{
						System.out.println("Invalid position");
					}
					break;
				case RIGHT:
					if(player.getPosition().getX() + 1 >= 0 && player.getPosition().getX() + 1 < map.getSize()
									&& map.getMap()[player.getPosition().getX() + 1][(player.getPosition().getY())] != 1){
						player.moveRight();
					}
					else{
						System.out.println("Invalid position");
					}
					break;
				case LEFT:
					if(player.getPosition().getX() - 1 >= 0 && player.getPosition().getX() - 1 < map.getSize()
									&& map.getMap()[player.getPosition().getX() - 1][(player.getPosition().getY())] != 1){
						player.moveLeft();
					}
					else{
						System.out.println("Invalid position");
					}
					break;
			}
		});/*
		scene.setOnKeyPressed(ev->{
			switch(ev.getCode()){
				case E:
					System.out.println("E (eat) key pressed");
					map.getChildren().remove(1);
					map.getChildren().remove(1);
					player = new MyBotPlayer2(map);
					food = new Food(map, player);
					((MyBotPlayer2)player).feed(food);
					((MyBotPlayer2)player).eat();
					break;
				case F:
					System.out.println("F (find) key pressed");
					map.getChildren().remove(1);
					map.getChildren().remove(1);
					player = new MyBotPlayer(map);
					food = new Food(map, player);
					((MyBotPlayer)player).feed(food);
					((MyBotPlayer)player).find();
					break;
			}
		});*/
		primaryStage.setScene(scene);
		primaryStage.show();
		ball.requestFocus();
	}
	public static void main(String[] args){
		launch(args);
	}
}