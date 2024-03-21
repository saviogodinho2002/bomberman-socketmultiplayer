import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Rectangle{
	private int enemySpeed = 1;
	public Enemy(int enemyX,int enemyY) {
		super(enemyX,enemyY, 32,32);
		// TODO Auto-generated constructor stub
	}

	public void tick(int x, int y) {

			this.y = y;
			this.y = x;

	}
	public void render(Graphics graphics) {
		graphics.drawImage(SpriteSheet.enemyFront, this.x,this.y,this.width,this.height, null);
		
	}

}
