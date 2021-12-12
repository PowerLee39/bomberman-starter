package com.example.bomberman.entities.LayerEntity;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Bomber;
import com.example.bomberman.entities.Entity;
import javafx.scene.image.Image;

public class FlameItem extends Item {
    private String type;

    public FlameItem(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }


    public void update() {

    }
    @Override
    public boolean collision(Entity e) {
        if (e instanceof Bomber) {
            if (layer == 1) {
                layer++;
                Bomber b = (Bomber) e;
                b.eatFlameItem();
                gameScene.setGrass(getXunit(),getYunit());

                return true;
            }
        }
        return false;
    }

}
