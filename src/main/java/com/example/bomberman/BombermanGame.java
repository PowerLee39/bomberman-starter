package com.example.bomberman;

import com.example.bomberman.player.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import com.example.bomberman.entities.*;
import com.example.bomberman.graphics.Sprite;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class BombermanGame extends Application {
    public static int WIDTH = 20;
    public static int HEIGHT = 15;


    private GraphicsContext gc;
    private Canvas canvas;

    private GameScene currentScene;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 15);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tao scene

       currentScene = new MainGameScene(root, gc, canvas,this);

        // Them scene vao stage
        stage.setScene(currentScene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

    }



    public void update() {
        currentScene.update();
    }

    public void render() {
        currentScene.render();
    }
}