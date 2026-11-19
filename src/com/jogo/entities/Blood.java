package com.jogo.entities;

import com.jogo.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Blood extends Entity{

    private int size;
    private Color color = Color.red;

    public Blood(int x, int y, int width, int height, BufferedImage sprite, int size) {
        super(x, y, width, height, sprite);
        this.size = size;
    }

    public Blood(int x, int y, int width, int height, BufferedImage sprite, int size, Color color) {
        super(x, y, width, height, sprite);
        this.size = size;
        this.color = color;
    }

    private void drop() {
        for(int i = 10; i > 0; i--) {
            this.y -= i;
        }
    }

    @Override
    public void tick() {
        drop();
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, size, size);
    }

}
