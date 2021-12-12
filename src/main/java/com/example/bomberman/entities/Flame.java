package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.graphics.Sprite;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Flame extends Entity {
    private String type;
    private int spriteIndex;
    private Image[] sprites;
    public boolean isDestroy;

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }


    public Flame(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

    }

    public Flame(int xUnit, int yUnit,MainGameScene gameScene, String type) {
        super(gameScene);
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
//        this.gameScene = gameScene;
        isDestroy = false;
        switch (type) {
            case "left":
            case "right": {
                sprites = new Image[]{
                        Sprite.explosion_horizontal.getFxImage(),
                        Sprite.explosion_horizontal1.getFxImage(),
                        Sprite.explosion_horizontal2.getFxImage(),

                };
                break;
            }
            case "up":
            case "down": {
                sprites = new Image[]{
                        Sprite.explosion_vertical.getFxImage(),
                        Sprite.explosion_vertical1.getFxImage(),
                        Sprite.explosion_vertical2.getFxImage(),

                };
                break;
            }
            case "center": {
                sprites = new Image[]{
                        Sprite.bomb_exploded.getFxImage(),
                        Sprite.bomb_exploded1.getFxImage(),
                        Sprite.bomb_exploded2.getFxImage(),

                };
                break;

            }
        }
        spriteIndex = 0;
        Animation spriteChange = new Timeline(
                new KeyFrame(Duration.millis(300),
                        e -> {
                            spriteIndex += 1;
                            spriteIndex = spriteIndex % sprites.length;
                            this.img = sprites[spriteIndex];
                        })
        );
        spriteChange.setCycleCount(Animation.INDEFINITE);
        spriteChange.play();
        Animation countDown = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        e -> {

                            spriteChange.stop();
                            destroy();
                        })
        );
        countDown.play();
    }

    public void update() {

    }

    @Override
    public void destroy() {
        isDestroy = true;
    }

    @Override
    public boolean collision(Entity e) {
        return true;
    }
}
