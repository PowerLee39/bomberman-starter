package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.entities.Enemy.Balloon;
import com.example.bomberman.entities.LayerEntity.LayerEntity;
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
    private int destroyRadius = 1;
    private int boomNumber = 50;

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
        boomNumber = 50;
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
        if (boomNumber > 0) {
            int xUnit = (int) Math.round(x / Sprite.SCALED_SIZE);
            int yUnit = (int) Math.round(y/Sprite.SCALED_SIZE);
            if (canPlantBoom(xUnit, yUnit)) {
                Bomb bomb = new Bomb(xUnit, yUnit, Sprite.bomb.getFxImage(), gameScene, destroyRadius);
                bombList.add(bomb);
                boomNumber--;
            }
        }

    }

    public void eatBoomItem() {
        boomNumber += 5;
    }

    public void eatFlameItem() {
        destroyRadius++;
    }

    public void eatSpeedItem() {
        speed += 0.5;
    }

    public void moveLeft() {
        faceDir = Direction.left;
        double xtmp = x - speed;
        int xUnit = (int) Math.round(xtmp / Sprite.SCALED_SIZE);
        Entity e = gameScene.getAt(xUnit, getYunit());
        if (gameScene.canMove(e)) {
            x -= speed;
        } else {

            x = (xUnit+1) *Sprite.SCALED_SIZE;
        }

    }

    public void moveRight() {
        faceDir = Direction.right;
        double xtmp = x + speed + Sprite.SCALED_SIZE;
        int xUnit = (int) Math.round(xtmp / Sprite.SCALED_SIZE);
        Entity e = gameScene.getAt(xUnit, getYunit());
        if (gameScene.canMove(e)) {
            x += speed;
        } else {
            if(xtmp > (xUnit * Sprite.SCALED_SIZE)){
                x = (xUnit-1) * Sprite.SCALED_SIZE;
            }else{
                x += speed;
            }
//            x = x + speed - (xtmp - xUnit * Sprite.SCALED_SIZE) + 0.5;

        }

    }

    public void moveUp() {
        faceDir = Direction.down;
        double ytmp = y - speed;
        int yUnit = (int) Math.round(ytmp / Sprite.SCALED_SIZE);
        Entity e = gameScene.getAt(getXunit(), yUnit);
        if (gameScene.canMove(e)) {
            y -= speed;
        } else {
            y = (yUnit+1) * Sprite.SCALED_SIZE;
        }

    }

    public void moveDown() {
        faceDir = Direction.down;
        double ytmp = y + speed + Sprite.SCALED_SIZE;
        int yUnit = (int) Math.round(ytmp / Sprite.SCALED_SIZE);
        Entity e = gameScene.getAt(getXunit(), yUnit);
        if (gameScene.canMove(e)) {
            y += speed;
        } else {

            y = y + speed - (ytmp - yUnit * Sprite.SCALED_SIZE) + 0.5;

        }
    }

    private boolean canPlantBoom(int xUnit, int yUnit) {
        Entity obj = gameScene.getAt(xUnit, yUnit);
        if (obj instanceof Grass) {
            for (int i = 0; i < bombList.size(); i++) {
                int xb = bombList.get(i).getXunit();
                int yb = bombList.get(i).getYunit();
                if (xb == xUnit && yb == yUnit && bombList.get(i).isDestroy == false) {
                    return false;
                }

            }
            return true;
        } else {
            if(obj instanceof Wall){
                System.out.println("not plant boom in wall");
            } else if(obj instanceof Brick){
                System.out.println("not plant in brick + %d, %d".formatted(xUnit, yUnit));
            }
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