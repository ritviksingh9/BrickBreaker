import java.awt.*;

public class Bricks 
{
	private int[][] bricks;
	private int row;
	private int col;
	private int width;
	private int height;
	private int boundX;
	private int boundY;
	private boolean check = true;
	
    public Bricks() 
    {
    	
    }
    public Bricks(int screenWidth, int screenHeight, int row, int col, int boundX, int boundY)
    {
    	this.row = row;
    	this.col = col;
    	bricks = new int[row][col];
    	    	
    	this.boundX = boundX;
    	this.boundY = boundY;	
    	
    	this.width = (screenWidth-2*boundX)/col;
    	this.height = (screenHeight*2/5-boundY)/row;
    }
    
    protected void draw(Graphics g)
    {    	
    	for(int i = 0; i < bricks.length; i++)
    	{
    		for(int j = 0; j < bricks[0].length; j++)
    		{
    			if(bricks[i][j] > 0)
    			{
    				if(bricks[i][j] == 1)
    				{
    					g.setColor(new Color(204, 255, 255));
    				}
    				else if(bricks[i][j] == 2)
    				{
    					g.setColor(new Color(102, 255, 255));
    				}
    				else if(bricks[i][j] == 3)
    				{
    					g.setColor(new Color(0, 227, 255));
    				}
	    			g.fillRect(width*j+boundX, height*i+boundY, width, height);
	    			g.setColor(Color.BLACK);
	    			g.drawRect(width*j+boundX, height*i+boundY, width, height);
    			}
    			
    		}
    	}	
    }
    protected int checkCollision(Ball ball, int ballWidth, int ballHeight)
    {
    	int score = 0;
    	for(int i = 0; i < bricks.length && check; i++)
    	{
    		for(int j = 0; j < bricks[0].length && check; j++)
    		{
    			if(bricks[i][j] != 0 && ball.getX()+ballWidth >= width*j+boundX && ball.getX() <= width*j+boundX+width && ball.getY()+ballHeight >= height*i+boundY && ball.getY() <= height*i+boundY+height)
    			{
    				check = false;
    				bricks[i][j]--;
    				    				
    				if(ball.getX() <= width*j+boundX)
    				{
    					ball.reflect(3);
    				}
    				else if(ball.getX()+ballWidth >= width*j+boundX+width)
    				{
    					ball.reflect(4);
    				}
    				
    				if(Math.abs(ball.getY()+ballHeight-(height*i+boundY)) <= 2)
    				{
    					ball.reflect(1);
    				}
    				else if(Math.abs(ball.getY()-(height*i+boundY+height)) <= 2)
    				{
    					ball.reflect(2);
    				}
    				ball.move();
    				score = 10;
    				ball.move();
    			}
    			
    		}
    	}
    	check = true;
    	
    	return score;
    }
    protected boolean checkWin()
    {
    	int sum = 0;
    	for(int i = 0; i < bricks.length && sum == 0; i++)
    	{
    		for(int j = 0; j < bricks[0].length && sum == 0; j++)
    		{
    			sum += bricks[i][j];
    		}
    	}
    	
    	return (sum == 0) ? true : false;
    }
    protected void setBricks(int[][] bricks)
    {
    	this.row = bricks.length;
    	this.col = bricks[0].length;
    	this.bricks = bricks;
    }
    protected void clearBricks()
    {
    	for(int i = 0; i < row; i++)
    	{
    		for(int j = 0; j < col; j++)
    		{
    			bricks[i][j] = 0;
    		}
    	}
    }
}