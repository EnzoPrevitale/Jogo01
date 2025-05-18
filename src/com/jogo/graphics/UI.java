package com.jogo.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jogo.entities.Entity;
import com.jogo.entities.Player;
import com.jogo.main.Game;
import com.jogo.world.Camera;

public class UI {
	
	public static boolean save = false,showSave = false;
	private int saveFrames = 0;
	
	public static int UIfps;
	
	public void render(Graphics g) {
		//XP
		g.setColor(Color.gray);
		g.fillRect(10, 20, 50, 2);
		g.setColor(Color.blue);
		g.fillRect(10, 20, (int)(Player.xp/2), 2);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,10));
		g.drawString(""+Game.player.level, 50, 30);
		g.drawString(Game.player.xp+"/"+Game.player.levelUpXp, 10, 30);
		
		//Barra de Vida
		if(Game.player.maxHealth <= 25) {
		g.setColor(Color.white);
		g.fillRect(9, 9, (int)Game.player.maxHealth * 5 + 2, 4);
		g.setColor(Color.red);
		g.fillRect(10, 10, (int)Game.player.maxHealth * 5, 2);
		g.setColor(Color.green);
		g.fillRect(10, 10, (int)(Game.player.health * 5), 2);
		}
		else if(Game.player.maxHealth > 25) {
			g.setColor(Color.white);
			g.fillRect(9, 9, 25 * 5 + 2, 4);
			g.setColor(Color.red);
			g.fillRect(10, 10, 125, 2);
			g.setColor(Color.green);
			g.fillRect(10, 10, (int)(Game.player.health * 125 / Game.player.maxHealth), 2);
		}
		
		//FPS
		g.setFont(new Font("Arial",Font.BOLD, 10));
		g.setColor(new Color(255,255,255));	
		g.drawString(UIfps +" FPS", 100, 10);
		
		
		//Munição
		if(Game.player.with[1] && !Game.player.inventoryOpen || Game.player.with[2] && !Game.player.inventoryOpen ) {
		g.setColor(Color.yellow);
		g.drawImage(Entity.AMMO_EN, 191, 31, 32, 32, null);
		if(Game.player.with[1]) g.drawString(": "+Game.player.ammo[0]+" / "+Game.player.revolverAmmoLeft,210, 50);
		else if(Game.player.with[2]) g.drawString(": "+Game.player.ammo[1],210, 50);
		}
		
		//Slots
		if(!Game.player.inventoryOpen)	{
			if(Game.player.fastSlotID[Game.player.currentSlot] == 3 || Game.player.fastSlotID[Game.player.currentSlot] == 4 || Game.player.fastSlotID[Game.player.currentSlot] == 5) {
				g.drawRect(Game.WIDTH-40, 43, 8, 8);
				g.setFont(new Font("Arial",Font.BOLD,7));
				g.drawString("E",Game.WIDTH-39, 50);
				g.setFont(new Font("Arial",Font.BOLD,9));
				g.drawString("Usar",Game.WIDTH-30, 52);
			}
			for(int n = 0; n < 3; n++) {
				
				g.setColor(Color.LIGHT_GRAY);
				if(Game.player.fastSlotID[n] == 1 && !Game.player.inventoryOpen) {
					g.drawImage(Entity.REVOLVER_EN, 149+n*31, 3, 32, 32, null);
				}
				if(Game.player.fastSlotID[n] == 2 && !Game.player.inventoryOpen) {
					g.drawImage(Entity.MINIASSAULTRIFLE_EN, 152+n*31, 5, 23, 23, null);
				}
				if(Game.player.fastSlotID[n] == 3 && !Game.player.inventoryOpen) {
					g.drawImage(Entity.APPLE_EN, 154+n*31, 5, 20, 20, null);
				}
				if(Game.player.fastSlotID[n] == 4 && !Game.player.inventoryOpen) {
					g.drawImage(Entity.GREENAPPLE_EN, 154+n*31, 5, 20, 20, null);
				}
				if(Game.player.fastSlotID[n] == 5 && !Game.player.inventoryOpen) {
					g.drawImage(Entity.GOLDENAPPLE_EN, 154+n*31, 5, 20, 20, null);
				}
			}
		}
		//Coordenadas
		/*g.setColor(Color.white);
		g.drawString(""+Game.player.getX()+" "+Game.player.getY(), 60, 20);
		*/
		
		
		//Save				
				if(save) {
					this.saveFrames++;
					if(saveFrames >= 50) {
						showSave = false;
						saveFrames = 0;
						save = false;
					}else {
						showSave = true;
					}
				}
				
				if(showSave) {
					g.drawImage(Entity.SAVE_UI,200,100,32,32,null);
				}
		
			}
		public void renderSlots(Graphics g) {
			if(!Game.player.inventoryOpen)
			{
				for(int n = 0; n < 3; n++) {
				g.setColor(Color.white);
				for(int i = 0; i < 6; i++) {
					g.drawRect(Game.SCALE*150+n*31*Game.SCALE+i, Game.SCALE*5+i, 26*Game.SCALE, 26*Game.SCALE);
				}
				if(Game.player.currentSlot == n) {
				g.setColor(Color.blue);
				for(int i = 0; i < 6; i++) {
				g.drawRect(Game.SCALE*150+n*31*Game.SCALE+i, Game.SCALE*5+i, 26*Game.SCALE, 26*Game.SCALE);
				}
				}
				}
				}
				}
				}
