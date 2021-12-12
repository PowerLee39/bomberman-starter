package com.example.bomberman.entities.LayerEntity;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Bomber;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.LayerEntity.LayerEntity;
import javafx.scene.image.Image;

public class Portal extends LayerEntity {


    public Portal(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }

    public void update() {

    }

    @Override
    public boolean collision(Entity e) {
        if(e instanceof Bomber){
            // if kill all enemies:
            // if check bomber stand on portal -> return true;
            // else:
            return false;
        }
        return false;
    }

}
