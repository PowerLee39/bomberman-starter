package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import javafx.scene.image.Image;

public class Balloon extends Entity {


    public Balloon(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }

    public void update() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean collision(Entity e) {
        if(e instanceof Flame){
            destroy();
            return true;
        }
        if(e instanceof Bomber){
            Bomber bomber = (Bomber) e;
            e.destroy();
            return false;
        }
        return true;
    }
}
