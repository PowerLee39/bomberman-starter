package com.example.bomberman.entities.Enemy;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.Flame;
import com.example.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy extends Entity {
    protected boolean living = true;
    private int timeUpdate = 10;
    protected double speed = Sprite.SCALED_SIZE/4;
    protected Random random = new Random();
    private int timeRepeat = 100;
    private int dir = random.nextInt(4);

    public Enemy(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
        living = true;
    }

    public void moveLeft() {

        Entity e = gameScene.getAt(getXunit()-1, getYunit());
        if (gameScene.canMove(e)) {
            x -= speed;
        } else {

        }

    }

    public void moveRight() {
        Entity e = gameScene.getAt(getXunit()+1, getYunit());
        if (gameScene.canMove(e)) {
            x += speed;
        } else {


        }

    }

    public void moveUp() {

        Entity e = gameScene.getAt(getXunit(), getYunit()-1);
        if (gameScene.canMove(e)) {

            y -= speed;
        } else {
        }

    }

    public void moveDown() {


        Entity e = gameScene.getAt(getXunit(), getYunit()+1);
        if (gameScene.canMove(e)) {
            y += speed;
        } else {



        }
    }

    @Override
    public void update() {
        if(timeUpdate == 0){
            dir = random.nextInt(4);
            switch (dir) {
                case 0: {
                    moveLeft();
                    break;
                }
                case 1: {
                    moveRight();
                    break;
                }
                case 2: {
                    moveUp();
                    break;
                }
                case 3: {
                    moveDown();
                    break;
                }
            }
            timeUpdate = 10;
        }
        timeUpdate--;

    }

    @Override
    public void destroy() {
       living = false;
    }

    public boolean isLiving() {
        return living;
    }

    @Override
    public boolean collision(Entity e) {
        if (e instanceof Flame){
            destroy();
        }
        return false;
    }
}
