package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item extends LayerEntity {

    public Item(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
    }


    public void update() {

    }

    @Override
    public void destroy() {
        if (layer == 0) {
            layer += 1;
        }
    }

    @Override
    public boolean collision(Entity e) {
        if(e instanceof Bomber){
            return true;
        }
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(images[layer], x, y);
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
}
