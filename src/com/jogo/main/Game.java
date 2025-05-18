package com.jogo.main;
	
	import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.jogo.entities.Ammo;
import com.jogo.entities.AssaultRifle;
import com.jogo.entities.Bullet;
import com.jogo.entities.Enemy;
import com.jogo.entities.Entity;
import com.jogo.entities.Gun38;
import com.jogo.entities.Heart;
import com.jogo.entities.Player;
import com.jogo.graphics.Spritesheet;
import com.jogo.graphics.UI;
import com.jogo.world.AStar;
import com.jogo.world.Camera;
import com.jogo.world.World;

import java.awt.image.DataBufferInt;

	public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

		private static final long serialVersionUID = 1L;
		
		public static boolean saveGame = false;
		
		public static JFrame frame;
		private Thread thread;
		private boolean isRunning = true;
		public static int WIDTH = 240;
		public static int HEIGHT = 135;
		public static final int SCALE = 5;
		public static int frames;
		public static boolean fpsTime;
		public static double timer;
		
		private BufferedImage image;
		
		public static boolean battle = false;
		
		public static int cur_level = 1, max_level = 2;
		
		public static List<Entity> entities;
		public static List<Enemy> enemies;
		public static List<Bullet> bullets;
		public static List<Gun38> revolver;
		
		public static Spritesheet spritesheet;
		
		public static World world;
		
		public static Player player;

		public static Random rand;
		
		public static Sound sound;
		public int xx,yy;
		
		public static int mx,my;
		
		public UI ui;
		
		public static String gameState = "MENU";
		private boolean showMessageGameOver = false;
		private int gameOverFrames = 0;
		public static boolean restartGame = false;
		
		public static Menu menu;
		public Character character;
		public Battle bttl;
		
		public int[] pixels;
		
		public static BufferedImage minimap;
		public static int minimapPixels[];
		
		public static int enemyID;
		
		public Game() {
			rand = new Random();
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
			initFrame();
			
			ui = new UI();
			image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
			pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			entities = new ArrayList<Entity>();
			enemies = new ArrayList<Enemy>();
			bullets = new ArrayList<Bullet>();
			revolver = new ArrayList<Gun38>();

			spritesheet = new Spritesheet("/spritesheet.png");
			player = new Player(0, 0, 32, 32,spritesheet.getSprite(0, 0, 32, 32));
			world = new World("/level1.png");
			
			minimap = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
			minimapPixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
			
			entities.add(player);
			menu = new Menu();
			character = new Character();
			sound = new Sound();
			
		}
		
		public void initFrame() {
			frame = new JFrame("Jogo");
			frame.add(this);
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
		
		public synchronized void start() {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
		
		public synchronized void stop() {
			isRunning = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			Game game = new Game();
			game.start();
		}
		
		public void tick() {
			Enemy.depth = 9;
			Ammo.depth = 1;
			Gun38.depth = 1;
			AssaultRifle.depth = 1;
			Heart.depth = 1;
			
			if(player.iPressed && !player.inventoryOpen) {
				player.iPressed = false;
				player.inventoryOpen= true;
			}else if(player.iPressed && player.inventoryOpen) {
				player.iPressed = false;
				player.inventoryOpen = false;
				player.inventory.curOption = 0;
				player.inventory.optOpen = false;
				Game.gameState = "NORMAL";		
			}
			
			if(battle) {
				battle = false;
				gameState = "BATTLE";
				bttl = new Battle(enemies.get(enemyID));
			}
			
			if(gameState == "NORMAL") {
				if(this.saveGame) {
					ui.save = true;
					this.saveGame = false;
					String[] opt1 = {"a","b","c","d","e","f","g","h","i", "j","k","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"};
					int[] opt2 = {this.cur_level, (int) player.health, (int) player.x, (int) player.y,
							player.obtRevolver, player.ammo[0], player.xp, player.level, player.dir, player.currentSlot,(int)player.maxHealth,
							player.inventory.slotID[0][0],player.inventory.slotID[0][1],player.inventory.slotID[0][2],player.inventory.slotID[0][3],player.inventory.slotID[1][0],
									player.inventory.slotID[1][1],player.inventory.slotID[1][2],player.inventory.slotID[1][3],player.inventory.slotID[2][0],
									player.inventory.slotID[2][1],player.inventory.slotID[2][2],player.inventory.slotID[2][3],player.inventory.slotID[3][0],
									player.inventory.slotID[3][1],player.inventory.slotID[3][2],player.inventory.slotID[3][3]};
					Menu.saveGame(opt1,opt2,20);
				}
				
				this.restartGame = false;
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			
			/*if(player.getX() == 32 && player.getY() == 32) {
				cur_level++;
				if(cur_level > max_level) {
					cur_level = 1;
				}
				String newWorld = "level"+cur_level+".png";
				world.restartGame(newWorld);
			}*/
			
			}else if(gameState == "GAME_OVER") {
				this.gameOverFrames++;
				if(gameOverFrames == 50) {
					this.gameOverFrames = 0;
					if(this.showMessageGameOver) {
						this.showMessageGameOver = false;
					}else {
						this.showMessageGameOver = true;
					}
					
					if(restartGame) {
						this.restartGame = false;
						this.gameState = "NORMAL";
						player.health = Player.maxHealth;
						cur_level=1;
						String newWorld = "level"+cur_level+".png";
						world.restartGame(newWorld);
					}
				}
			}else if(gameState == "MENU") {
				menu.tick();
			}
				if(gameState == "INVENTORY") {
					player.inventory.tick();
				}
				
				if(gameState == "CHARACTER") {
					character.tick();
				}
				
				if(gameState == "BATTLE") {
					bttl.tick();
				}
			
			if(gameState == "RESTART_GAME") {
				this.gameState = "MENU";
				
				for(int xx = 0; xx < 4; xx ++) {
					for(int yy = 0; yy < 4; yy++) {
					player.inventory.slotID[yy][xx] = 0;
					}
				}		
				player.currentSlot = 0;
				player.xp = 0;
				player.level = 1;
				player.health = player.maxHealth;
				
				cur_level=1;
				String newWorld = "level"+cur_level+".png";
				world.restartGame(newWorld);
			}
		}
		
	/*	public void drawRectangleExample(int xoff, int yoff) {
			for(int xx = 0; xx < 32; xx++) {
				for(int yy = 0; yy < 32; yy++) {
					int xOff = xx + xoff;
					int yOff = yy + yoff;
					if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
						continue;
					pixels[xOff + yOff*WIDTH] = 0xff0000;
				}
			}
		}*/
		
		public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		    FontMetrics metrics = g.getFontMetrics(font);
		    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;    
		    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();  
		    g.setFont(font);
		    g.drawString(text, x, y);
		}
		
		public void render() {
			BufferStrategy bs = this.getBufferStrategy();
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			g.setColor(new Color (0,0,0));
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			/*Renderização do Jogo*/
			Collections.sort(entities,Entity.depthSorter);
			Graphics2D g2d = (Graphics2D) g;
			world.render(g);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render(g);
			}
			ui.render(g);
			
			
			g.setColor(Color.white);
			
			/***/
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
			if(gameState != "INVENTORY") {
				ui.renderSlots(g);
			}
			/*if(gameState == "NORMAL" || gameState == "INVENTORY") {
				world.renderMinimap();
				g.setColor(Color.white);
			//	g.fillRect(0, HEIGHT*SCALE-22*7, 22*7, 22*7);
				g.drawImage(minimap,7,HEIGHT*SCALE-20*7,20*7,20*7,null);
			}*/
		
			if(gameState == "GAME_OVER") {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(0,0,0,100));
				g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
				g.setColor(Color.white);
				
				Rectangle gmOvRect = new Rectangle(WIDTH*SCALE,HEIGHT*SCALE-20*SCALE);
				Rectangle screen = new Rectangle(WIDTH*SCALE,HEIGHT*SCALE);
				
				drawCenteredString(g, "Você morreu!", gmOvRect, new Font("arial",Font.BOLD,40));
				g.setColor(Color.gray);
				if(showMessageGameOver)
				drawCenteredString(g, ">Pressione ENTER para reiniciar<", screen, new Font("arial",Font.BOLD,20));
			}else if(gameState == "MENU") {
				menu.render(g);
			}else if(gameState == "INVENTORY") {
				player.inventory.render(g);
			}else if(gameState == "CHARACTER") {
				character.render(g);
			}else if(gameState == "BATTLE") {
				bttl.render(g);
			}
			
			bs.show();
		}
		
		public void run() {
			requestFocus();
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			frames = 0;
			timer = System.currentTimeMillis();
			while (isRunning) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;	
				lastTime = now;
				if(delta >= 1) {
					tick();
					render();
					frames++;
					delta--;
				}
				
				if(System.currentTimeMillis() - timer >= 1000) {
					UI.UIfps = frames;
					frames = 0;
					timer+=1000;
				}
			}
			
			stop();
		}
		
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_D:
				player.right = true;
				if(gameState == "MENU") {
					menu.right = true;
				}else if(gameState == "INVENTORY" && !player.inventory.optOpen) {
					player.inventory.right = true;
				}else if(gameState == "CHARACTER") {
					character.right = true;
				}else if(gameState == "BATTLE") {
					this.bttl.right = true;
					//System.out.println(bttl.right);
				}
			break;
			case KeyEvent.VK_A:
				player.left = true;
				if(gameState == "MENU") {
					menu.left = true;
				}else if(gameState == "INVENTORY" && !player.inventory.optOpen) {
					player.inventory.left = true;
				}else if(gameState == "CHARACTER") {
					character.left = true;
				}else if(gameState == "BATTLE") {
					bttl.left = true;
				}
			break;
			}
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_W:
				player.up = true;
				if(gameState == "MENU" && !menu.cgOpt) {
					menu.up = true;
				}else if(gameState == "INVENTORY") {
					player.inventory.up = true;
				}else if(gameState == "CHARACTER") {
					character.up = true;
				}else if(gameState == "BATTLE") {
					bttl.up = true;
				}
			break;
			case KeyEvent.VK_S:
				player.down = true;
				if(gameState == "MENU" && !menu.cgOpt) {
					menu.down = true;
				}else if(gameState == "INVENTORY") {
					player.inventory.down = true;
				}else if(gameState == "CHARACTER") {
					character.down = true;
				}else if(gameState == "BATTLE") {
					bttl.down = true;
				}
			break;
			}
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_SHIFT:
				if(!player.isJumping)
				player.sprint = true;
			break;
			}
			
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ENTER:
				this.restartGame = true;
				if(gameState == "MENU") {
					menu.enter = true;
				}
				
			break;
			}
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_R:
				if(gameState == "NORMAL") {
					
				}
			break;
			}
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_RIGHT:
				if(gameState == "INVENTORY") player.inventory.swap = true;
			break;
			}
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(gameState == "CHARACTER") character.swap = true;
			}
			
			
			if(gameState == "NORMAL" || gameState == "INVENTORY" || (gameState == "CHARACTER" && !character.newGame) ) {
				switch (e.getKeyCode())
				{
				case KeyEvent.VK_I:
					player.iPressed = true;
					player.inventory.curSlotX = 0;
					player.inventory.curSlotY = 0;
					character.close();
				break;
				}
				
			}
			
			/*if(e.getKeyCode() == KeyEvent.VK_I) {
				if(gameState == "CHARACTER") player;
			}*/
			
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				menu.escPressed = true;
				
			if(gameState == "NORMAL") {
				if(!player.inventoryOpen) {
				gameState = "MENU";
				menu.pause = true;
				menu.currentOption = 0;
				}
			}else if(gameState == "MENU" && menu.pause) {
				gameState = "NORMAL";
				menu.pause = false;
				Sound.play.play();
			}
			}
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_SPACE:
				if(gameState == "NORMAL")
				player.jump = true;
			}
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_E:
				if(gameState == "NORMAL") {
					if(player.fastSlotID[player.currentSlot] == 3) {
						Game.player.useApple(player.currentSlot, 0);
					}
					
					else if(player.fastSlotID[player.currentSlot] == 4) {
						Game.player.useGreenApple(player.currentSlot, 0);
					}
					
					else if(player.fastSlotID[player.currentSlot] == 5) {
						player.useGoldenApple(player.currentSlot, 0);
					}
				}
				 if(gameState == "NORMAL") this.saveGame = true;
			else if(gameState == "INVENTORY") player.inventory.e = true;
			else if(gameState == "CHARACTER") character.enter = true;
			else if(gameState == "BATTLE") bttl.select = true;
					
			break;
			}
		if(gameState == "NORMAL") {	
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_1:
				player.currentSlot = 0;
			break;
			case KeyEvent.VK_2:
				player.currentSlot = 1;
			break;
			case KeyEvent.VK_3:
				player.currentSlot = 2;
			break;
			}
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_I:
				player.iPressed = true;
			break;
			}
			}
			}

		@Override
		public void keyReleased(KeyEvent e) {
			
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_D:
				player.right = false;
			break;
			case KeyEvent.VK_A:
				player.left = false;
			break;
			}
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_W:
				player.up = false;
			break;
			case KeyEvent.VK_S:
				player.down = false;
			break;
			}
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_SHIFT:
				player.sprint = false;
			}
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch(e.getButton())
			{
			case MouseEvent.BUTTON1:
				if(player.with[1] && player.ammo[0] > 0) {
				player.mouseShoot = true;
				player.mx = (e.getX() / SCALE);
				player.my = (e.getY() / SCALE);
				}else if(player.with[2]) {
					player.rightButtonPressed = true;
					player.mxA = e.getX()/SCALE;
					player.myA = e.getY()/SCALE;
				}
			break;
			
			
			}
		}
		

		@Override
		public void mouseReleased(MouseEvent e) {
			switch(e.getButton())
			{
			case MouseEvent.BUTTON1:
				player.rightButtonPressed = false;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			player.mxA = e.getX()/SCALE;
			player.myA = e.getY()/SCALE;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
		}

	


}
