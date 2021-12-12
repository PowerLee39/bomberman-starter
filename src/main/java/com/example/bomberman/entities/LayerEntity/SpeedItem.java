package com.example.bomberman.entities.LayerEntity;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Bomber;
import com.example.bomberman.entities.Entity;
import javafx.scene.image.Image;

public class SpeedItem extends Item {

    public SpeedItem(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }

    @Override
    public void update() {

    }
    @Override
    public boolean collision(Entity e) {
        if (e instanceof Bomber) {
            if (layer == 1) {
                layer++;
                gameScene.setGrass(getXunit(),getYunit());
                Bomber b = (Bomber) e;
                b.eatSpeedItem();
                return true;
            }
        }
        return false;
    }

}
