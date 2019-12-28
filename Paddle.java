import java.awt.*;

public class Paddle 
{
	private int x;
	private int y;
	private int width;
	private int height;
	private int red;
	private int green;
	private int blue;
	private int bound1;
	private int bound2;
	private int dir;

    public Paddle() 
    {
    	
    }
    public Paddle(int x, int y, int width, int height, int red, int green, int blue, int bound1, int bound2)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	this.red = red;
    	this.green = green;
    	this.blue = blue;
    	this.bound1 = bound1;
    	this.bound2 = bound2;
    }
    
    protected void draw(Graphics g)	
    {
    	g.setColor(new Color(red, green, blue));
    	g.fillRect(x, y, width, height);
    }
    protected void move()
    {
    	x += dir*3;
    	if(x < 0)
    	{
    		x = 0;
    	}
    	else if(x  > bound2-width)
    	{
    		x = bound2-width;
    	}
    }
    protected void setDir(int dir)
    {
    	this.dir = dir;
    }
    protected int getX()
    {
    	return x;
    }
    protected void setX(int x)
    {
    	this.x=x;
    }
    protected int getY()
    {
    	return y;
    }
    protected void setWidth(int width)
    {
    	this.width = width;
    }
    protected int getWidth()
    {
    	return width;
    }
}