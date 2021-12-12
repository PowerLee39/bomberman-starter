package com.example.bomberman.entities.LayerEntity;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class LayerEntity extends Entity {
    protected int layer = 0;
    protected Image[] images = {
            Sprite.brick.getFxImage(),
            img
    };

    public LayerEntity(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
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
