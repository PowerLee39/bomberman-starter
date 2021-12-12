package com.example.bomberman.entities.Enemy;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Bomber;
import com.example.bomberman.entities.Enemy.Enemy;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.Flame;
import javafx.scene.image.Image;

public class Balloon extends Enemy {


    public Balloon(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
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
