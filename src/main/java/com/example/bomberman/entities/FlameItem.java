package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import javafx.scene.image.Image;

public class FlameItem extends Item {
    private String type;

    public FlameItem(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }


    public void update() {

    }

}
