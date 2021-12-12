package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.graphics.Sprite;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;

import javafx.util.Duration;

public class Bomb extends Entity {
    public int destroyRadius;
    private int spriteIndex = 0;
    private Animation spriteChangeAnim;
    private Animation countDown;
    private int timeDestroy = 2;
    public boolean isDestroy;

    public static final Image[] sprites = {
            Sprite.bomb.getFxImage(), Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage(),
    };
    public Bomb(double x, double y, Image img, MainGameScene gameScene){
        super(gameScene);
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public Bomb(int xUnit, int yUnit, Image img, MainGameScene gameScene,int destroyRadius) {
        super(xUnit, yUnit, img, gameScene);
        this.destroyRadius = destroyRadius;
        spriteIndex = 0;
        init();
    }


    public void init() {
        spriteChangeAnim = new Timeline(
                new KeyFrame(
                        Duration.millis(300),
                        e -> {
                            spriteIndex += 1;
                            spriteIndex = spriteIndex % sprites.length;
                            this.img = sprites[spriteIndex];
                        }

                )
        );
        spriteChangeAnim.setCycleCount(Animation.INDEFINITE);

        countDown = new Timeline(
                new KeyFrame(Duration.seconds(timeDestroy),
                        e -> {
                            spriteChangeAnim.stop();
                            destroy();
                        })
        );
        spriteChangeAnim.play();
        countDown.play();

    }

    @Override
    public void destroy() {

        isDestroy = true;
        // sinh ra Flame
        for (int i = -destroyRadius; i < 0; i++) {
            Entity e = gameScene.getAt(getXunit() + i, getYunit());
            if(e == null){
                continue;
            }
            // call e destroy
            if (!(e instanceof Wall)) {
                addFlames(getXunit() + i, getYunit(), "left");
                gameScene.destroyAt(getXunit() + i, getYunit());
            }
            e = gameScene.getAt(getXunit(), getYunit() + i);
            if (!(e instanceof Wall)) {
                addFlames(getXunit(), getYunit() + i, "up");
                gameScene.destroyAt(getXunit(), getYunit() + i);
            }
        }
        for (int i = 1; i < destroyRadius + 1; i++) {
            Entity e = gameScene.getAt(getXunit() + i, getYunit());
            if(e == null){
                continue;
            }
            if (!(e instanceof Wall)) {
                addFlames(getXunit() + i, getYunit(), "right");
                gameScene.destroyAt(getXunit()+i, getYunit());
            }
            e = gameScene.getAt(getXunit(), getYunit() + i);
            if (!(e instanceof Wall)) {
                addFlames(getXunit(), getYunit() + i, "down");
                gameScene.destroyAt(getXunit(), getYunit() + i);
            }
        }
        addFlames(getXunit(), getYunit(), "center");

    }

    @Override
    public boolean collision(Entity e) {
        if(e instanceof Bomber){
            return true;
        }
        return false;
    }

    private void addFlames(int xUnit, int yUnit, String type) {
        Flame flame = new Flame(xUnit, yUnit, gameScene, type);
        gameScene.flames.add(flame);

    }


    public int getDestroyRadius() {
        return destroyRadius;
    }

    public void setDestroyRadius(int destroyRadius) {
        this.destroyRadius = destroyRadius;
    }

    public Animation getSpriteChangeAnim() {
        return spriteChangeAnim;
    }

    public void setSpriteChangeAnim(Animation spriteChangeAnim) {
        this.spriteChangeAnim = spriteChangeAnim;
    }

    public Animation getCountDown() {
        return countDown;
    }

    public void setCountDown(Animation countDown) {
        this.countDown = countDown;
    }

    @Override
    public void update() {

    }
}
