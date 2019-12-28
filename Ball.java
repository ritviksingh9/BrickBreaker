import java.awt.*;

public class Ball 
{
	private double x;
	private double y;
	private int width;
	private int height;
	private int red;
	private int green;
	private int blue;
	private int boundX1;
	private int boundX2;
	private int boundY1;
	private int boundY2;
	private double xSpeed;
	private double ySpeed;
	private double xInc;
	private double yInc;
	private int xDir;
	private int yDir;
	
    public Ball() 
    {
    	
    }
    public Ball(double x, double y, int width, int height, int red, int green, int blue, int boundX1, int boundX2, int boundY1, int boundY2, double xSpeed, double ySpeed, int xDir, int yDir)
    {
    	this.x = x;
    	this.y = y;
    	
    	this.width = width;
    	this.height = height;
    	
    	this.red = red;
    	this.green = green;
    	this.blue = blue;
    	
    	this.boundX1 = boundX1;
    	this.boundX2 = boundX2;
    	this.boundY1 = boundY1;
    	this.boundY2 = boundY2;
    	
    	this.xSpeed = xSpeed;
    	this.ySpeed = ySpeed;
    	
    	this.xDir = xDir;
    	this.yDir = yDir;
    	
    	xInc = 0.5;
    	yInc = 0;	
    }
    
    protected void draw(Graphics g)
    {
    	g.setColor(new Color(red, green, blue));
    	g.fillOval((int) x, (int) y, width, height);
    }
    protected boolean move()
    {
    	boolean alive = true;
    	
    	x += (xSpeed+xInc) * xDir;
    	y += (ySpeed+yInc) * yDir;
    	
    	if(x < boundX1 || x+width > boundX2)
    	{
    		xDir *= -1;
    	}
    	if(y < boundY1)
    	{
    		yDir *= -1;
    	}
    	
    	if(y >= boundY2-height)
    	{
    		alive = false;
    	}
    	return alive;
    }
    protected void checkCollision(int posX, int posY, int pWidth)
    {
   		if((int)y+height == posY && (int)x+width >= posX && (int)x <= posX+pWidth) 
    	{
    		xDir = (x+width/2 < posX+pWidth/2) ? -1 : 1;
    		
    		if(x >= pWidth/4+posX && x+width <= posX+pWidth-pWidth/4)
    		{
    			xInc = 0;
 				yInc = 0.5;
    		}
    		else
    		{
				xInc = 0.5;
				yInc = 0;
    		}
    		yDir *= -1;
    	} 
    }
    protected void reflect(int num)
    {
    	if(num == 1)
    	{
    		yDir = -1;
    	}
    	else if(num == 2)
    	{
    		yDir = 1;
    	}
    	else if(num == 3)
    	{
    		xDir = -1;
    	}
    	else if(num == 4)
    	{
    		xDir = 1;
    	}
    }
    protected void setSpeed(double xSpeed, double ySpeed)
    {
    	this.xSpeed = xSpeed;
    	this.ySpeed = ySpeed;
    }
    protected int getX()
    {
    	return (int) x;
    }    
    protected int getY()
    {
    	return (int) y;
    }
    protected int getHeight()
    {
    	return height;
    }
    protected int getWidth()
    {
    	return width;
    }
    protected double getXSpeed()
    {
    	return xSpeed;
    }
    protected double getYSpeed()
    {
    	return ySpeed;
    }
}