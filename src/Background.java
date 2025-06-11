import java.awt.*;



import javax.swing.ImageIcon;

public class Background extends Rectangle{
Image wizardImage;
	Background(int x, int y, int width, int height){
		super(x,y,width,height);
		wizardImage = new ImageIcon("./Image/wizard.jpg").getImage();
	}
	public void draw(Graphics g) {
		g.drawImage(wizardImage,x,y,width,height,null);
	}
}
