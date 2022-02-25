package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager 
{
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[] [];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImg();
		loadMap("/maps/map.txt");	
	}

	public void getTileImg() {
		try {
			tile[0] = new Tile();
			tile[0].img = ImageIO.read(getClass().getResourceAsStream("/tiles/detailedgrass.png"));
			
			tile[1] = new Tile();
			tile[1].img = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collisions = true;
			
			tile[2] = new Tile();
			tile[2].img = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collisions = true;
			
			tile[3] = new Tile();
			tile[3].img = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
			
			tile[4] = new Tile();
			tile[4].img = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collisions = true;
			
			tile[5] = new Tile();
			tile[5].img = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadMap(String filePath) {
		try 
		{
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}
		catch(Exception e) 
		{
			
		}
	}
	public void draw(Graphics2D g2) {
		int worldRow = 0;
		int worldCol = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.Tilesize;
			int worldY = worldRow * gp.Tilesize;
			
			int screenX = worldX - gp.player.Worldx + gp.player.screenX;
			int screenY = worldY - gp.player.Worldy + gp.player.screenY;
			
			if(worldX + gp.Tilesize > gp.player.Worldx - gp.player.screenX&&
					worldX - gp.Tilesize < gp.player.Worldx + gp.player.screenX&&
					worldY + gp.Tilesize > gp.player.Worldy - gp.player.screenY&&
					worldY - gp.Tilesize < gp.player.Worldy + gp.player.screenY) {
				g2.drawImage(tile[tileNum].img, screenX, screenY, gp.Tilesize, gp.Tilesize, null);
			}
						
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}