import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shield extends Rectangle {
	int num;
Shield(int x, int y, int width, int height, int num){
	super(x,y,width,height);
	this.num = num;
}

public Rectangle getBounds() {
	return new Rectangle(x,y,width,height);
}

public void updatePosition(int x, int y) {
	this.x = x;
	this.y = y;
}


public void draw(Graphics g) {
	if(num == 1) {
	g.setColor(Color.RED);
	g.drawOval(x,y,width,height);
	}
	
	if(num==2) {
		g.setColor(Color.BLUE);
		g.drawOval(x,y,width,height);
	}
}



}
