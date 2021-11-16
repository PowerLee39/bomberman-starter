package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.enums.Direction;
import com.example.bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends MovingEntity {
    protected static final double EPSILON = 0.00001;

    private double speed = 10;
    private int speedUp = 1;
    private List<Bomb> bombList;


    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        bombList.forEach(bomb -> {
            if (!bomb.isDestroy) {
                bomb.render(gc);
            }
        });
    }

    public Bomber(int xUnit, int yUnit, Image img, MainGameScene gameScene) {
        super(xUnit, yUnit, img, gameScene);
        bombList = new ArrayList<>();
    }

    public void update() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean collision(Entity e) {
        if (e instanceof Balloon || e instanceof Flame) {
            return true;
        }
        return true;
    }


    public void plantBoom() {
        int xUnit = getXunit();
        int yUnit = getYunit();
        if (canPlantBoom(xUnit, yUnit)) {
            Bomb bomb = new Bomb(xUnit, yUnit, Sprite.bomb.getFxImage(), gameScene);
            bombList.add(bomb);
        }


    }

    public void moveLeft() {
        faceDir = Direction.left;
        if (gameScene.canMove(x - speed, y)) {
            int xUnit = getXunit();
            if (gameScene.canMove((xUnit - 1) * Sprite.SCALED_SIZE, y)) {
                x -= speed;
            } else {
                if (x - Sprite.SCALED_SIZE > (xUnit - 1) * Sprite.SCALED_SIZE) {
                    x -= speed;
                }
            }
        }
    }

    public void moveRight() {
        faceDir = Direction.right;
        if (gameScene.canMove(x + speed, y)) {
            int xUnit = getXunit();
            if (gameScene.canMove((xUnit + 1) * Sprite.SCALED_SIZE, y)) {
                x += speed;
            } else {
                if (x + Sprite.SCALED_SIZE < (xUnit + 1) * Sprite.SCALED_SIZE) {
                    x += speed;
                }
            }
        }

    }

    public void moveUp() {
        faceDir = Direction.up;
        if (gameScene.canMove(x, y - speed)) {
            int yUnit = getYunit();
            if (gameScene.canMove(x, (yUnit - 1) * Sprite.SCALED_SIZE)) {
                y -= speed;
            } else {
                if (y - Sprite.SCALED_SIZE > (yUnit - 1) * Sprite.SCALED_SIZE) {
                    y -= speed;
                }
            }
        }

    }

    public void moveDown() {
        faceDir = Direction.down;
        if (gameScene.canMove(x, y + speed)) {
            int yUnit = getYunit();
            if (gameScene.canMove(x, (yUnit + 1) * Sprite.SCALED_SIZE)) {
                y += speed;
            } else {
                if (y + Sprite.SCALED_SIZE < (yUnit + 1) * Sprite.SCALED_SIZE) {
                    y += speed;
                }
            }
        }


    }

    private boolean canPlantBoom(int xUnit, int yUnit) {
        Entity obj = gameScene.getAt(xUnit, yUnit);
        if (obj instanceof Grass) {
           for(int i = 0; i < bombList.size(); i++){
               int xb = bombList.get(i).getXunit();
               int yb = bombList.get(i).getYunit();
               if(xb == xUnit && yb == yUnit && bombList.get(i).isDestroy == false){
                   return false;
               }

           }
            return true;
        }
        return false;
    }

    @Override
    public void move(double x, double y) {

    }

    @Override
    public boolean canMove(double x, double y) {
        double xOffset;
        double yOffset;
        int xCoor = (int) x;
        int yCoor = (int) y;
        switch (faceDir) {
            case left -> {
                yOffset = y - yCoor;
                if (yOffset == 0) {
                    Entity obj = gameScene.getAt((int) x / Sprite.SCALED_SIZE, (int) y / Sprite.SCALED_SIZE);
                    if (!(obj instanceof Grass || obj instanceof LayerEntity)) {
                        return Math.abs(x - obj.x) < 0.7;
                    }
                } else if (yOffset <= 0.5) {
                    Entity e1 = gameScene.getAt(getXunit() + 1, getYunit());
                    Entity e2 = gameScene.getAt(getXunit() + 1, getYunit() + 1);
                    if (!(e1 instanceof Grass || e1 instanceof Flame))
                        return false;
                    if (!(e2 instanceof Grass || e2 instanceof Flame)) {
                        y -= speed;
                    }
                    return false;

                } else {

                    Entity e1 = gameScene.getAt(getXunit() + 1, getYunit());
                    Entity e2 = gameScene.getAt(getXunit() + 1, getYunit() + 1);
                    if (!(e2 instanceof Grass || e1 instanceof Flame))
                        return false;
                    if (!(e1 instanceof Grass || e2 instanceof Flame)) {
                        y += speed;
                    }
                    return false;
                }
                break;
            }
        }
        return true;

    }
}