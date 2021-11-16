package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import javafx.scene.image.Image;

public class Grass extends Entity {


    public Grass(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }

    public void update() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean collision(Entity e) {
        return true;
    }
}