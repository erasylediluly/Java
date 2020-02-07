import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;	
import java.io.*;
import java.util.*;
public class Map extends Pane{
	private int unit;
	private int size;
	private int[][] map;
	private Position start;
	public Map(String f){
		this.unit = 50;
		File file = new File(f);
		try{
			Scanner input = new Scanner(file);
			this.size = Integer.parseInt(input.nextLine());
			this.map = new int[this.size][this.size];
			for(int i = 0; i < this.size; i++){
				String[] line = (input.nextLine()).split(" ");
				for(int j = 0; j < line.length; j++){
					this.map[j][i] = Integer.parseInt(line[j]);
					if(line[j].equals("2")){
						start = new Position(j, i);
					}
				}
			}
			drawMap();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public int getUnit(){
		return this.unit;
	}
	public int getSize(){
		return this.size;
	}
	public int[][] getMap(){
		return this.map;
	}
	public Position getStartPosition(){
		return this.start;
	}
	//drawing map
	public void drawMap(){
		GridPane grid = new GridPane();
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				if(this.map[i][j] == 1){
					grid.add(new Rectangle(this.getUnit(), this.getUnit(), Color.BLACK), i, j);
				}
				else{
					grid.add(new Rectangle(this.getUnit(), this.getUnit(), Color.TRANSPARENT), i, j);
				}
			}
		}
		grid.setGridLinesVisible(true);
		this.getChildren().add(grid);
	}
}