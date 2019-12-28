import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener
{
	final int WIDTH = 1000;
	final int HEIGHT = 600;
	
	Paddle paddle;
	Ball ball;
	Bricks bricks;
	
	int brickRow = 5;
	int brickCol = 5;
	int boundX = 70;
	int boundY = 70;
	int ballWidth = 20;
	int ballHeight = 20;
	double xSpeed = 1;
	double ySpeed = 1;
	int level = 1;
	
	JPanel options;
	String [] buttonText = {"Easy", "Medium", "Hard"};
	JRadioButton [] difficulty;
	ButtonGroup group;
	JButton restart;
	
	JLabel scoreDisplay;
	int score;
	
	KeyPressed key;
	MouseTracker mt;
	ButtonClicked clicked;
	boolean mouseControls = true;
	
	GameDisplay display;
	int rate = 10;
	Timer timer;
	
	
    public Main() 
    {   
    	setTitle("Break Breaker");
    	setLayout(null);
    	
    	bricks = new Bricks(HEIGHT, HEIGHT, brickRow, brickCol, boundX, boundY);
		nextLevel();
    	paddle = new Paddle(265, 500, 100, 15, 255, 80, 153, 0, HEIGHT);
    	ball = new Ball(180, 300, ballWidth, ballHeight, 255, 80, 153, 0, HEIGHT, 0, HEIGHT, xSpeed, ySpeed, 1, 1);
    	
    	options = new JPanel();
    	options.setLocation(HEIGHT, 0);
    	options.setSize(WIDTH-HEIGHT, HEIGHT);
    	options.setLayout(null);
    	options.setBackground(Color.WHITE);
    	add(options);
    	
    	scoreDisplay = new JLabel("SCORE: "+score);
    	scoreDisplay.setFont(new Font("Arial", Font.BOLD, 40));
    	scoreDisplay.setLocation(0, 0);
    	scoreDisplay.setSize(WIDTH-HEIGHT, HEIGHT/3);
    	scoreDisplay.setForeground(Color.BLACK);
    	options.add(scoreDisplay);
    	score = 0;
    	
    	clicked = new ButtonClicked();
    	
    	difficulty = new JRadioButton[3];
    	group = new ButtonGroup();
    	for(int i = 0; i < difficulty.length; i++)
    	{
    		difficulty[i] = new JRadioButton(buttonText[i]);
    		difficulty[i].setFont(new Font("Arial", Font.BOLD, 30));
    		difficulty[i].setBackground(Color.WHITE);
    		difficulty[i].setForeground(Color.BLACK);
    		difficulty[i].setSize(300, 80);
    		difficulty[i].setLocation(0, HEIGHT/3-120+60*(i+1));
    		difficulty[i].addActionListener(clicked);
    		difficulty[i].setFocusable(false);
    		options.add(difficulty[i]);
    		group.add(difficulty[i]);
    	}
    	difficulty[1].setSelected(true);
    	
    	restart = new JButton("RESTART");
    	restart.setFont(new Font("Arial", Font.BOLD, 40));
    	restart.setLocation(0, 2*HEIGHT/3-50);
    	restart.setSize(WIDTH-HEIGHT, HEIGHT/3);
    	restart.setFocusable(false);
    	restart.addActionListener(clicked);
    	restart.setBackground(Color.WHITE);
    	restart.setForeground(Color.BLACK);
    	options.add(restart);
    	
    	display = new GameDisplay(HEIGHT, HEIGHT, bricks, paddle, ball);
    	display.setBackground(0, 0, 0);
    	display.setLevel(level);
    	add(display);    
    	
    	key = new KeyPressed();
    	mt = new MouseTracker();
    	addMouseListener(mt); 
		addMouseMotionListener(mt);
    	addKeyListener(key);
    	
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setResizable(false);
    	setSize(WIDTH, HEIGHT);
    	setVisible(true);
    	
    	timer = new Timer(rate, this);
    	timer.start();
    }
    
    public void actionPerformed(ActionEvent e) 
    {	
		timer.start();
		if(!ball.move())
		{
			message(false);
		}
		paddle.move();
		
		ball.checkCollision(paddle.getX(), paddle.getY(), paddle.getWidth());
		
		score += bricks.checkCollision(ball, ball.getWidth(), ball.getHeight());
		scoreDisplay.setText("SCORE: "+score);
	
		display.draw();
		display.repaint();
		
		if(bricks.checkWin())
		{
			level = (level == 5) ? 1 : ++level;
			display.setLevel(level);
			nextLevel();
			message(true);
		}
	}
	
	private class ButtonClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == restart)
			{
				restart();
			}
			else
			{
				JRadioButton hold = (JRadioButton) e.getSource();
				int i;
				for(i = 0; !buttonText[i].equals(hold.getText()); i++);
				changeWidth(i);
			}
		}
	}
	
	private void changeWidth(int i)
	{
		paddle.setWidth(125-i*25);
		difficulty[i].setSelected(true);
	}
	
	private class KeyPressed implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_LEFT)
			{
				paddle.setDir(-2);
			}
			if(keyCode == KeyEvent.VK_RIGHT)
			{
				paddle.setDir(2);	
			}
			if(keyCode == KeyEvent.VK_R)
			{
				restart();
			}
			if(keyCode == KeyEvent.VK_SPACE)
			{
				bricks.clearBricks();
			}
			if(keyCode == KeyEvent.VK_1)
			{
				changeWidth(0);
			}
			if(keyCode == KeyEvent.VK_2)
			{
				changeWidth(1);
			}
			if(keyCode == KeyEvent.VK_3)
			{
				changeWidth(2);
			}
			if(keyCode == KeyEvent.VK_ESCAPE)
			{
				System.exit(0);
			}
		}
		public void keyReleased(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_LEFT)
			{
				paddle.setDir(0);
			}
			if(keyCode == KeyEvent.VK_RIGHT)
			{
				paddle.setDir(0);	
			}
		}
	}
	
	public void restart()
	{
		bricks = new Bricks(HEIGHT, HEIGHT, brickRow, brickCol, boundX, boundY);
		nextLevel();
    	paddle = new Paddle(265, 500, 100, 15, 255, 80, 153, 0, HEIGHT);
    	ball = new Ball(180, 300, ballWidth, ballHeight, 255, 80, 153, 0, HEIGHT, 0, HEIGHT, xSpeed, ySpeed, 1, 1);
   		display.setPaddle(paddle);
    	display.setBall(ball);
    	display.setBricks(bricks);
    	display.setBackground(0, 0, 0);
    	add(display);  
    	score = 0;  
    	difficulty[1].setSelected(true);
    	timer.start();
	}
	public void nextLevel()
	{
		int[][] brickLayout;
		int score = 0;
		if(level == 1)
		{
			brickRow = 5; 
			brickCol = 5;
			
			brickLayout = new int[brickRow][brickCol];
    	    	
	    	for(int i = 0; i < brickLayout.length; i++)
	    	{
	    		for(int j = 0; j < brickLayout[0].length; j++)
	    		{
	    			brickLayout[i][j] = 1;
	    		}
	    	}
	    	bricks.setBricks(brickLayout);
		}
		else if(level == 2)
		{
			brickRow = 5; 
			brickCol = 8; 

			brickLayout = new int[brickRow][brickCol];
			
			for(int i = 0; i < brickLayout.length; i++)
    		{
	    		for(int j = 0; j < brickLayout[0].length; j++)
	    		{
	    			brickLayout[i][j] = (i > 0 && i < brickLayout.length-1 && j > 0 && j < brickLayout[0].length-1) ? 2 : 1 ;
	    		}
    		} 
    		bricks.setBricks(brickLayout);
		}
		else if(level == 3)
		{
			brickRow = 5; 
			brickCol = 8; 

			brickLayout = new int[brickRow][brickCol];
			
			for(int i = 0; i < brickLayout.length; i++)
    		{
	    		for(int j = 0; j < brickLayout[0].length; j++)
	    		{
	    			brickLayout[i][j] = (i > 0 && i < brickLayout.length-1 && j > 0 && j < brickLayout[0].length-1) ? 2 : 1 ;
	    		}
    		} 
    		bricks.setBricks(brickLayout);
		}
		else if(level == 4)
		{
			brickRow = 6; 
			brickCol = 10; 

			brickLayout = new int[brickRow][brickCol];
			
			for(int i = 0; i < brickLayout.length; i++)
    		{
	    		for(int j = 0; j < brickLayout[0].length; j++)
	    		{
	    			brickLayout[i][j] = (i > 0 && i < brickLayout.length-1 && j > 0 && j < brickLayout[0].length-1) ? 3 : 2 ;
	    		}
    		} 
    		bricks.setBricks(brickLayout);
		}
		else if(level == 5)
		{
			brickRow = 6;
			brickCol = 10;
			
			brickLayout = new int[brickRow][brickCol];
			for(int i = 0, strength = 1; i < brickLayout.length; i++, strength *= -1)
    		{
	    		for(int j = 0; j < brickLayout[0].length; j++, strength *= -1)
	    		{
	    			if(i > 1 && i < brickLayout.length-2 && j > 1 && j < brickLayout[0].length-2)
	    			{
	    				brickLayout[i][j] = 3;	
	    			}
	    			else
	    			{
	    				brickLayout[i][j] = (i > 0 && i < brickLayout.length-1 && j > 0 && j < brickLayout[0].length-1) ? 2 : 1 ;
	    			}
	    		}
    		}
			bricks.setBricks(brickLayout);
		}	
	}
	public void message(boolean win)
	{
		timer.stop();
		String str = new String();
		
		if(win)
		{
			str = (level == 1) ? "Congratulations, you beat the game." : "You win.";
		}
		else
		{
			str = "You lose.";
		}
		
		JOptionPane pane = new JOptionPane(str);
		pane.setFont(new Font("Arial", Font.BOLD, 20));
    	JDialog d = pane.createDialog(null, null);
    	d.setFont(new Font("Arial", Font.BOLD, 50));
   		d.setLocation(WIDTH/2-50, HEIGHT/2-50);
   		d.setSize(100, 150);
    	d.setVisible(true);
	}
	
	private class MouseTracker implements MouseListener, MouseMotionListener 
	{
		public void mouseClicked(MouseEvent e){}
		public void mouseDragged(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseMoved(MouseEvent e) 
		{
			if(mouseControls)
			{
				int x = e.getX();
			
				if(x > 0 && x+paddle.getWidth() < HEIGHT)
				{
					paddle.setX(x);
				}
			}
		}
	}
	
   	public static void main(String[] args)
   	{
   		new Main();	
   	}
}