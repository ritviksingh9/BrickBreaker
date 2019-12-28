import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GameDisplay extends JPanel 
{
	private int width;
	private int height;
	private int red;
	private int green;
	private int blue;

	private BufferedImage brImage;
	private Graphics g;
	
	private Bricks bricks;
	private Paddle paddle;
	private Ball ball;
	private int level;
	
    public GameDisplay() 
    {
    	
    }
    public GameDisplay(int width, int height, Bricks bricks, Paddle paddle, Ball ball)
    {
		setLocation(0, 0);
    	setSize(width, height);
    	this.width = width;
    	this.height = height;
    	
    	brImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	
    	g = brImage.getGraphics();	

    	this.bricks = bricks;
    	this.paddle = paddle;
    	this.ball = ball;
    }
    
	protected void draw()
	{
		g.setColor(new Color(red, green, blue));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("LEVEL: "+level, 35, 45);
		bricks.draw(g);
		paddle.draw(g);
		ball.draw(g);

	}
	protected void paintComponent(Graphics g)
	{
		Graphics paint = g;		
		paint.drawImage(brImage, 0, 0, width, height, null);
		paint.dispose();
	}
	protected void setBackground(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;	
	}
	protected void setPaddle(Paddle paddle)
	{
		this.paddle = paddle;
	}    
	protected void setBall(Ball ball)
	{
		this.ball = ball;
	}
	protected void setBricks(Bricks bricks)
	{
		this.bricks = bricks;
	}
	protected void setLevel(int level)
	{
		this.level = level;
	}
}