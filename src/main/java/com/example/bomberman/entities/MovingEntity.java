package com.example.bomberman.entities;

import com.example.bomberman.MainGameScene;
import com.example.bomberman.enums.Direction;
import javafx.scene.image.Image;


public abstract class MovingEntity extends Entity{
    protected boolean alive = true;
    protected boolean moving = false;
    protected Direction faceDir;
    public MovingEntity(int xUnit, int yUnit, Image img, MainGameScene gameScene){
        super(xUnit,yUnit, img, gameScene);
    }
    public abstract void move(double deltaX, double deltaY);
    public abstract boolean canMove(double deltaX, double deltaY);

}
