package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.example.bomberman.graphics.Sprite;


public abstract class Entity {
    public Entity(MainGameScene gameScene) {
        this.gameScene = gameScene;
    }

    protected double x;
    protected double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    //Tọa độ X tính từ góc trái trên trong Canvas

    protected Image img;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public final MainGameScene gameScene;


    public int getXunit() {
        return (int)Math.round(x/Sprite.SCALED_SIZE);
    }

    public void setXunit(int xunit) {
        this.x = xunit * Sprite.SCALED_SIZE;

    }

    public int getYunit() {
        return (int)Math.round(y/Sprite.SCALED_SIZE);
    }

    public void setYunit(int yunit) {
        this.y = yunit * Sprite.SCALED_SIZE;
    }


    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.gameScene = gameScene;
    }


    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public abstract void destroy();

    public abstract boolean collision(Entity e);
}
