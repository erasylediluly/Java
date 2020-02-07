import java.util.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
class MyBotPlayer implements BotPlayer{
	private Circle ball;
	private Position position;
	private Map map;
	private Food food;
	private static ArrayList<Position> path = new ArrayList<>();
	public MyBotPlayer(Map map){
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
	// In a customized map
	// finds a valid path to food and eats it 
	public void find(){
		new Thread(()->{
			try{
				while(true){
					findPath();
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
	//path for empty map
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
	//path for non-empty map
	public void findPath(){
		int[][] arr1 = map.getMap();
		int[][] arr2 = new int[map.getSize()][map.getSize()];
		for(int i = 0; i < arr2.length; i++){
			for(int j = 0; j < arr2[i].length; j++){
				arr2[i][j] = arr1[i][j];
			}
		}
		for(int i = 0; i < arr2.length; i++){
			for(int j = 0; j < arr2[i].length; j++){
				if(arr2[i][j] == 1){
					arr2[i][j] = -1;
				}
				if(arr2[i][j] == 2){
					arr2[i][j] = 0;
				}
			}
		}
		//marked positions
		ArrayList<Position> markedPositions = new ArrayList<>();
		//initial number
		int d = 1;
		//positions
		Position startPosition = this.getPosition();
		Position endPosition = food.getPosition();
		//add startPosition
		markedPositions.add(startPosition);
		//add to arr
		arr2[startPosition.getX()][startPosition.getY()] = d;
		//filling two dimensional array with numbers
		while(!containsPosition(markedPositions,endPosition)){  
			for(int i = 0; i < markedPositions.size(); i++){
				if(arr2[markedPositions.get(i).getX()][markedPositions.get(i).getY()] == d){
					for(int j = 0; j < getNeighbors(markedPositions.get(i)).size(); j++){
						if(!containsPosition(markedPositions,getNeighbors(markedPositions.get(i)).get(j)) && arr2[getNeighbors(markedPositions.get(i)).get(j).getX()]
									[getNeighbors(markedPositions.get(i)).get(j).getY()] != -1){
							markedPositions.add(getNeighbors(markedPositions.get(i)).get(j));
							arr2[getNeighbors(markedPositions.get(i)).get(j).getX()][getNeighbors(markedPositions.get(i)).get(j).getY()] = d+1;
						}
					}
				}
			}
			d++;
		}
		//finding path
		ArrayList<Position> finalPath = new ArrayList<>();
		finalPath.add(endPosition);
		Position currentPosition = endPosition;
		while(!currentPosition.equals(startPosition)){
			for(int i = 0; i < getNeighbors(currentPosition).size(); i++){
				if(arr2[getNeighbors(currentPosition).get(i).getX()][getNeighbors(currentPosition).get(i).getY()] == 
						arr2[currentPosition.getX()][currentPosition.getY()]-1){
							currentPosition = getNeighbors(currentPosition).get(i);
							finalPath.add(currentPosition);
							
							
						}
						
			}
		}
		Collections.reverse(finalPath);
		this.path = finalPath;
	}
	//returns ArrayList of neighboring positions
	public ArrayList<Position> getNeighbors(Position pos){
		ArrayList<Position> neighbors = new ArrayList<>();
		Position pos1 = new Position(pos.getX()-1, pos.getY());
		Position pos2 = new Position(pos.getX()+1, pos.getY());
		Position pos3 = new Position(pos.getX(), pos.getY()-1);
		Position pos4 = new Position(pos.getX(), pos.getY()+1);
		//all positions
		ArrayList<Position> allPositions = new ArrayList<>();
		for(int i = 0; i < map.getSize(); i++){
			for(int j = 0; j < map.getSize(); j++){
				allPositions.add(new Position(j,i));
			}
		}
		for(int i = 0; i < allPositions.size(); i++){
			if(allPositions.get(i).equals(pos1)){
				neighbors.add(pos1);
			}
			if(allPositions.get(i).equals(pos2)){
				neighbors.add(pos2);
			}
			if(allPositions.get(i).equals(pos3)){
				neighbors.add(pos3);
			}
			if(allPositions.get(i).equals(pos4)){
				neighbors.add(pos4);
			}
		}
		return neighbors;
	}
	//contains method
	public static boolean containsPosition(ArrayList<Position> arr, Position pos1){
		for(int i = 0; i < arr.size(); i++){
			if(arr.get(i).equals(pos1)){
				return true;
			}
		}
		return false;
	}
}