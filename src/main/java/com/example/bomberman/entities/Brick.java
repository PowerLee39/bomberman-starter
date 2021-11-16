package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import javafx.scene.image.Image;

public class Brick extends Entity {
    boolean isDestroy = false;

    public Brick(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
        isDestroy = false;
    }


    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public void update() {

    }

    @Override
    public void destroy() {
       isDestroy = true;
       gameScene.setGrass(getXunit(), getYunit());
    }

    @Override
    public boolean collision(Entity e) {
//        if(e instanceof Flame){
//            destroy();
//        }
        return false;
    }
}
