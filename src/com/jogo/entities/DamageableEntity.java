package com.jogo.entities;

import java.awt.image.BufferedImage;

public class DamageableEntity extends Entity {

    public int health;

    public DamageableEntity(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void applyDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

}
