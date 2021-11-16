package com.example.bomberman;

import javafx.animation.Animation;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public abstract class GameScene extends javafx.scene.Scene {
    protected GraphicsContext gc;
    protected Canvas canvas;
    protected List<Animation> animations = new ArrayList<>();
    public GameScene(Parent parent) {
        super(parent);

    }
    public GameScene(Parent parent, GraphicsContext gc, Canvas canvas){
        super(parent);
        this.gc = gc;
        this.canvas = canvas;
    }
    public abstract void update();
    public abstract void render();
    public abstract void init();
}
