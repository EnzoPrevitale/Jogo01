package com.jogo.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jogo.entities.Player;
import com.jogo.main.Sound.Clips;
import com.jogo.world.World;

public class Menu {
	
	public String[] options = {"Novo Jogo", "Carregar Jogo", "Opções", "Fechar"};
	public String[] confirmOptions = {"Sim", "Não"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public int currentConfirmOption = 0;
	public int maxConfirmOption = 1;
	
	public boolean up,down,left,right,enter;
	public static boolean pause = false;
	public boolean escPressed = true;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	private boolean showMessageFile = false;
	private int fileMessageFrames = 0;
	
	public boolean cgOpt;
	private int nCgOpt;

	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		
		if(left && cgOpt) {
			Sound.select.play();
			left = false;
			currentConfirmOption--;
			if(currentConfirmOption < 0) {
				currentConfirmOption = maxConfirmOption;
			}		
		}
		if(right && cgOpt) {
			Sound.select.play();
			right = false;
			currentConfirmOption++;
			if(currentConfirmOption > maxConfirmOption) {
				currentConfirmOption = 0;
			}
		}
		
		if(up) {
			Sound.select.play();
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			Sound.select.play();
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "Novo Jogo") {
				if(!pause) {
				cgOpt = true;
				Sound.play.play();
				}else {
					pause = false;
					Game.gameState = "NORMAL";
					Sound.play.play();
				}
			}else if(options[currentOption] == "Carregar Jogo"){
				file = new File("save.txt");
				if(file.exists()) {
					cgOpt = true;
					Sound.play.play();
				}else if (!file.exists()) {
					this.showMessageFile = true;		
				}
			}else if(options[currentOption] == "Fechar") {	
					cgOpt = true;
					Sound.play.play();
			}
			
			
			
			if(cgOpt) {
				nCgOpt++;
				if(nCgOpt >= 2) {
					if(options[currentOption] == "Fechar") {
				if(confirmOptions[currentConfirmOption] == "Sim") {
					if(!pause) {
					nCgOpt = 0;
					System.exit(1);
					}else if(pause) {
						nCgOpt = 0;
						pause = false;
						Game.gameState = "RESTART_GAME";
						cgOpt = false;
						currentOption = 0;
					}
				}else if(confirmOptions[currentConfirmOption] == "Não") {
					cgOpt = false;
					currentConfirmOption = 0;
					nCgOpt = 0;
				}
				
				}else if(options[currentOption] == "Novo Jogo") {
					if(confirmOptions[currentConfirmOption] == "Sim") {
						nCgOpt = 0;
						cgOpt = false;
						currentConfirmOption = 0;
						Game.gameState = "CHARACTER";
						showMessageFile = false;
						file = new File("save.txt");
						file.delete();
						Sound.play.play();
					}else if(confirmOptions[currentConfirmOption] == "Não") {
						cgOpt = false;
						currentConfirmOption = 0;
						nCgOpt = 0;
					}
					
				}else if(options[currentOption] == "Carregar Jogo") {
					if(confirmOptions[currentConfirmOption] == "Sim") {	
						String saver = loadGame(20);
						applySave(saver);
						cgOpt = false;
						nCgOpt = 0;
						currentConfirmOption = 0;
					}else if(confirmOptions[currentConfirmOption] == "Não") {
						cgOpt = false;
						nCgOpt = 0;
						currentConfirmOption = 0;
					}
				}
				}
			}
			
			
			
		}
		
			if(cgOpt) {
				showMessageFile = false;
			}
		
			if(this.showMessageFile) {
				this.fileMessageFrames++;
			}
				if(this.fileMessageFrames >= 100) {
					fileMessageFrames = 0;
					showMessageFile = false;
			}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
			case"a":
			World.restartGame("level"+spl2[1]+".png");
			Game.gameState = "NORMAL";
			pause = false;
			break;
			case"b":
				Game.player.health = Integer.parseInt(spl2[1]);
			break;
			case"c":
				Game.player.x = Integer.parseInt(spl2[1]);
			break;
			case"d":
				Game.player.y = Integer.parseInt(spl2[1]);
			break;
			case"e":
				Game.player.obtRevolver = Integer.parseInt(spl2[1]);
			break;
			case"f":
				Game.player.ammo[1] = Integer.parseInt(spl2[1]);
			break;
			case"g":
				Game.player.xp = Integer.parseInt(spl2[1]);
			break;
			case"h":
				Game.player.level = Integer.parseInt(spl2[1]);
			break;
			case"i":
				Game.player.dir = Integer.parseInt(spl2[1]);
			break;
			case"j":
				Game.player.currentSlot = Integer.parseInt(spl2[1]);
			break;
			case"k":
				Game.player.maxHealth = Integer.parseInt(spl2[1]);
			
			
			case"A":
				Game.player.inventory.slotID[0][0] = Integer.parseInt(spl2[1]);
			break;
			case"B":
				Game.player.inventory.slotID[0][1] = Integer.parseInt(spl2[1]);
			break;
			case "C":
				Game.player.inventory.slotID[0][2] = Integer.parseInt(spl2[1]);
			break;
			case"D":
				Game.player.inventory.slotID[0][3] = Integer.parseInt(spl2[1]);
			break;
			case"E":
				Game.player.inventory.slotID[1][0] = Integer.parseInt(spl2[1]);
			break;
			case "F":
				Game.player.inventory.slotID[1][1] = Integer.parseInt(spl2[1]);
			break;
			case "G":
				Game.player.inventory.slotID[1][2] = Integer.parseInt(spl2[1]);
			break;
			case"H":
				Game.player.inventory.slotID[1][3] = Integer.parseInt(spl2[1]);
			break;
			case"I":
				Game.player.inventory.slotID[2][0] = Integer.parseInt(spl2[1]);
			break;
			case "J":
				Game.player.inventory.slotID[2][1] = Integer.parseInt(spl2[1]);
			break;
			case "K":
				Game.player.inventory.slotID[2][2] = Integer.parseInt(spl2[1]);
			break;
			case"L":
				Game.player.inventory.slotID[2][3] = Integer.parseInt(spl2[1]);
			break;
			case"M":
				Game.player.inventory.slotID[3][0] = Integer.parseInt(spl2[1]);
			break;
			case "N":
				Game.player.inventory.slotID[3][1] = Integer.parseInt(spl2[1]);
			break;
			case "O":
				Game.player.inventory.slotID[3][2] = Integer.parseInt(spl2[1]);
			break;
			case"P":
				Game.player.inventory.slotID[3][3] = Integer.parseInt(spl2[1]);
			break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			val1[i]+=encode;
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(pause == false) {
		g2.setColor(Color.black);
		}else {
			g2.setColor(new Color(0,0,0,100));
		}
		g.fillRect(0,0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE);
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,36));
		if(pause == false) {
		g.drawString("Jogo", 50, (Game.HEIGHT*Game.SCALE)/2 - 200);
		}else {
			g.drawString("Jogo pausado", 50, (Game.HEIGHT*Game.SCALE)/2 - 200);
		}
		
		//Opções do Menu
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,28));
		if(pause == false)
		g.drawString("Novo Jogo", 50, (Game.HEIGHT*Game.SCALE)/2 - 100);
		else if(pause)
			g.drawString("Continuar", 50, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.drawString("Carregar Jogo", 50, (Game.HEIGHT*Game.SCALE)/2);
		g.drawString("Opções", 50, (Game.HEIGHT*Game.SCALE)/2 + 100);
		if(pause == false)
		g.drawString("Fechar", 50, (Game.HEIGHT*Game.SCALE)/2 + 200);
		else
		g.drawString("Retornar ao menu principal", 50, (Game.HEIGHT*Game.SCALE)/2 + 200);
		
		if(options[currentOption] == "Novo Jogo" && pause == false) {
			g.drawString(">", 32, (Game.HEIGHT*Game.SCALE)/2 - 100);
		}else if(options[currentOption] == "Novo Jogo" && pause == true) {
			g.drawString(">", 32, (Game.HEIGHT*Game.SCALE)/2 - 100);
		}else if(options[currentOption] == "Carregar Jogo") {
			g.drawString(">", 32, (Game.HEIGHT*Game.SCALE)/2);
		}else if(options[currentOption] == "Opções") {
			g.drawString(">", 32, (Game.HEIGHT*Game.SCALE)/2 + 100);
		}else if(options[currentOption] == "Fechar") {
			g.drawString(">", 32, (Game.HEIGHT*Game.SCALE)/2 + 200);
		}
		
		if(!saveExists && this.showMessageFile) {
			g.setFont(new Font("arial",Font.BOLD,18));
			g.setColor(Color.white);
			g.drawString("Nenhum arquivo encontrado", 32, 600);
		}
		
		if(cgOpt) {
			g.setFont(new Font("arial",Font.BOLD,18));
			g.setColor(Color.white);
			if(options[currentOption] == "Fechar") {
			if(!pause)
			g.drawString("Deseja fechar o jogo?", 32, 570);
			else
				g.drawString("Deseja retornar ao menu? Todo o progresso não salvo será apagado.", 32, 570);
			}else if(options[currentOption] == "Novo Jogo") {
				g.drawString("Deseja iniciar um novo jogo e apagar o seu progresso atual?", 32, 570);
			}else if (options[currentOption] == "Carregar Jogo") {
				g.drawString("Deseja carregar o último jogo salvo?", 32, 570);
			}
			
			g.setFont(new Font("arial",Font.BOLD,28));
			
			if(confirmOptions[currentConfirmOption] == "Sim")
			g.drawString(">", 14, 600);		
			g.drawString("Sim", 32, 600);
			
			if(confirmOptions[currentConfirmOption] == "Não")
			g.drawString(">", 112, 600);
			g.drawString("Não", 130, 600);
		}
	}
}
