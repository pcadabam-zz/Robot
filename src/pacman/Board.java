

package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

	Dimension d;
	Font smallfont = new Font("Helvetica", Font.BOLD, 14);

	FontMetrics fmsmall, fmlarge;
	Image ii;
	Color mazecolor;

	boolean ingame = false;
	boolean dying = false;

	final int blockSize = 24;
	final int numOfBlocks = 15;
	final int screenSize = numOfBlocks * blockSize;
	final int maxRobots = 12;


	int numOfRobots = 1;
	int[] dx, dy;
	int[] robotX, robotY, robotDx, robotDy, robotSpeed;


	Image robot;
	Image brick;
	int reqdx, reqdy, viewdx, viewdy;

	/*final short leveldata[] ={
			19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
			21, 0,  0,  0,  17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
			21, 0,  0,  0,  17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
			21, 0,  0,  0,  17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
			17, 18, 18, 18, 16, 16, 20, 0,  17, 16, 16, 16, 16, 16, 20,
			17, 16, 16, 16, 16, 16, 20, 0,  17, 16, 16, 16, 16, 24, 20,
			25, 16, 16, 16, 24, 24, 28, 0,  25, 24, 24, 16, 20, 0,  21,
			1,  17, 16, 20, 0,  0,  0,  0,  0,  0,  0,  17, 20, 0,  21,
			1,  17, 16, 16, 18, 18, 22, 0,  19, 18, 18, 16, 20, 0,  21,
			1,  17, 16, 16, 16, 16, 20, 0,  17, 16, 16, 16, 20, 0,  21,
			1,  17, 16, 16, 16, 16, 20, 0,  17, 16, 16, 16, 20, 0,  21,
			1,  17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0,  21,
			1,  17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  21,
			1,  25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
			9,  8,  8,  8,  8,  8,  8,  8,  8,  8,  25, 24, 24, 24, 28 
	};*/

	/*final short leveldata[] ={
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 0,
			0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 0,
			0, 1, 0, 0, 1, 1, 12, 12,12, 1, 1, 1,1, 1, 0,
			0, 1, 1, 1, 1, 1, 12, 0, 12, 1, 1, 1,1, 1, 0,
			0, 1, 1, 1, 1, 1, 12, 0, 12, 1, 1, 1,1, 1, 0,
		 0, 1, 1, 1, 12, 12, 12, 0, 12, 12, 12, 1,1, 1, 0,
			0, 1, 1, 12, 0, 0, 0, 0, 0, 0, 0, 12,1, 1, 0,
		 0, 1, 1, 1, 12, 12, 12, 0, 12, 12, 12, 1,1, 1, 0,
			0, 1, 1, 0, 1, 1, 12, 0, 12, 1, 1, 1,1, 1, 0,
			0, 1, 1, 1, 1, 1, 12, 0, 12, 1, 1, 1,1, 1, 0,
		    0, 1, 1, 0, 1, 1, 12, 12,12, 1, 1, 1,1, 1, 0,
			0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 0,
			0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 
	};*/

	final short leveldata[] ={
			11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
			11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 11,
			11, 12, 10, 10, 10, 10, 12, 11, 12, 11, 11, 11, 12, 12, 11,
			11, 12, 10, 10, 10, 10, 12, 10, 12, 11, 12, 11, 12, 12, 11,
			11, 12, 12, 12, 12, 12, 12, 11, 12, 12, 12, 11, 12, 12, 11,
			11, 12, 12, 11, 11, 12, 12, 11, 12, 11, 12, 12, 11, 12, 11,
			11, 12, 12, 12, 12, 12, 12, 11, 12, 12, 11, 12, 11, 12, 11,
			11, 12, 12, 11, 12, 10, 12, 12, 12, 12, 12, 12, 11, 12, 11,
			11, 12, 10, 12, 11, 12, 12, 11, 12, 12, 11, 12, 12, 12, 11,
			11, 12, 12, 11, 12, 12, 12, 11, 12, 12, 11, 10, 10, 12, 11,
			11, 12, 12, 12, 12, 11, 11, 11, 11, 12, 12, 11, 12, 12, 11,
			11, 12, 12, 11, 11, 11, 12, 12, 12, 12, 12, 11, 12, 12, 11,
			11, 12, 12, 12, 12, 12, 10, 10, 12, 11, 11, 12, 12, 12, 11,
			11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 11,
			11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11 
	};

	//final int mazeData [][] = {{1}};
	final int validspeeds[] = { 1, 2, 3, 4, 6, 8 };
	final int maxspeed = 6;

	int currentspeed = 3;
	short[] screendata;
	Timer timer;


	public Board() {

		GetImages();
		addKeyListener(new TAdapter());

		screendata = new short[numOfBlocks * numOfBlocks];
		mazecolor = new Color(5, 100, 5);
		setFocusable(true);

		d = new Dimension(400, 400);

		setBackground(Color.black);
		setDoubleBuffered(true);

		robotX = new int[maxRobots];
		robotDx = new int[maxRobots];
		robotY = new int[maxRobots];
		robotDy = new int[maxRobots];
		robotSpeed = new int[maxRobots];
		dx = new int[4];
		dy = new int[4];
		timer = new Timer(40, this);
		timer.start();
	}

	public void addNotify() {
		super.addNotify();
		GameInit();
	}

	public void PlayGame(Graphics2D g2d) {

		moveRobots(g2d);
		CheckMaze();

	}
	
	//public void sampleMethod(){
	} 

	public void ShowIntroScreen(Graphics2D g2d) {

		g2d.setColor(new Color(0, 32, 48));
		g2d.fillRect(50, screenSize / 2 - 30, screenSize - 100, 50);
		g2d.setColor(Color.white);
		g2d.drawRect(50, screenSize / 2 - 30, screenSize - 100, 50);

		String s = "Press s to start.";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2);
	}




	public void CheckMaze() {
		short i = 0;
		boolean finished = true;

		while (i < numOfBlocks * numOfBlocks && finished) {
			if ((screendata[i] & 48) != 0)
				finished = false;
			i++;
		}
	}



	public void moveRobots(Graphics2D g2d) {
		short i;
		int pos;
		int count;

		int routeArray[] = {-1,0,1};

		for (i = 0; i < numOfRobots; i++) {
			/*if (robotX[i] % blockSize == 0 && robotY[i] % blockSize == 0) {
				pos =
					robotX[i] / blockSize + numOfBlocks * (int)(robotY[i] / blockSize);

				count = 0;
				if ((screendata[pos] & 1) == 0 && robotDx[i] != 1) {
					dx[count] = -1;
					dy[count] = 0;
					count++;
				}
				if ((screendata[pos] & 2) == 0 && robotDy[i] != 1) {
					dx[count] = 0;
					dy[count] = -1;
					count++;
				}
				if ((screendata[pos] & 4) == 0 && robotDx[i] != -1) {
					dx[count] = 1;
					dy[count] = 0;
					count++;
				}
				if ((screendata[pos] & 8) == 0 && robotDy[i] != -1) {
					dx[count] = 0;
					dy[count] = 1;
					count++;
				}


				if (count == 0) {
					if ((screendata[pos] & 15) == 15) {
						robotDx[i] = 0;
						robotDy[i] = 0;
					} else {
						robotDx[i] = -robotDx[i];
						robotDy[i] = -robotDy[i];
					}
				} else {
					count = (int)(Math.random() * count);
					if (count > 3)
						count = 3;
					robotDx[i] = dx[count];
					robotDy[i] = dy[count];
				}

			}


			 */

			if (robotX[i] % blockSize == 0 && robotY[i] % blockSize == 0) {
				pos = robotX[i] / blockSize + numOfBlocks * (int)(robotY[i] / blockSize);
				//int posNew = 10;
				if(screendata[pos]==10){
					//do{
					int random = -1 +(int)(Math.random() *3);
					robotDx[i]=random;
					random = -1 +(int)(Math.random() *3);
					robotDy[i]=random;

					//posNew = (robotX[i] / blockSize + numOfBlocks * (int)(robotY[i] / blockSize));
					//posNew = posNew + robotDx[i] + robotDy[i];
					//}while(screendata[posNew]!=0);	

				}

				if(screendata[pos]==11 || screendata[pos]==12){
					robotDx[i] = -robotDx[i];
					robotDy[i] = -robotDy[i];
				}
			}

			robotX[i] = robotX[i] + (robotDx[i] * robotSpeed[i]);
			robotY[i] = robotY[i] + (robotDy[i] * robotSpeed[i]);
			drawRobot(g2d, robotX[i] + 1, robotY[i] + 1);

		}
	}


	public void drawRobot(Graphics2D g2d, int x, int y) {
		g2d.drawImage(robot, x, y, this);
	}


	public void DrawMaze(Graphics2D g2d) {
		short i = 0;
		int x, y;

		for (y = 0; y < screenSize; y += blockSize) {
			for (x = 0; x < screenSize; x += blockSize) {
				g2d.setColor(mazecolor);
				g2d.setStroke(new BasicStroke(2));

				if(screendata[i]==11 ){
					g2d.drawImage(brick, x, y, this);
				}
				i++;
			}
		}
	}

	public void GameInit() {
		LevelInit();
		numOfRobots = 6;
		currentspeed = 3;
	}


	public void LevelInit() {
		int i;
		for (i = 0; i < numOfBlocks * numOfBlocks; i++)
			screendata[i] = leveldata[i];

		LevelContinue();
	}


	public void LevelContinue() {
		short i;
		int dx = 1;
		int random;

		for (i = 0; i < numOfRobots; i++) {
			robotY[i] = 4 * blockSize;
			robotX[i] = 4 * blockSize;
			robotDy[i] = 1;
			robotDx[i] = dx;
			dx = -dx;
			random = (int)(Math.random() * (currentspeed + 1));
			if (random > currentspeed)
				random = currentspeed;
			robotSpeed[i] = validspeeds[random];
		}

		dying = false;
	}

	public void GetImages()
	{

		robot = new ImageIcon(Board.class.getResource("../pacpix/robot.png")).getImage();
		brick = new ImageIcon(Board.class.getResource("../pacpix/brick.png")).getImage();

	}

	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);

		DrawMaze(g2d);
		if (ingame)
			PlayGame(g2d);
		else
			ShowIntroScreen(g2d);

		g.drawImage(ii, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();
			if (key == 's' || key == 'S')
			{
				ingame=true;
				GameInit();
			}

		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
