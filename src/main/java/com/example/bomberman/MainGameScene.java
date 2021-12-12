package com.example.bomberman;

import com.example.bomberman.entities.*;
import com.example.bomberman.entities.Enemy.Balloon;
import com.example.bomberman.entities.Enemy.Enemy;
import com.example.bomberman.entities.LayerEntity.*;
import com.example.bomberman.graphics.Sprite;
import com.example.bomberman.player.Player;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainGameScene extends GameScene {


    public char[][] _map;
    public String resourcePath = "/levels/Level";
    public static int WIDTH = 20;
    public static int HEIGHT = 15;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Bomber bomber;
    private BombermanGame gameController;
    private Entity[][] stillObjectMap;
    private List<Bomb> bombList = new ArrayList<>();
    public List<Flame> flames = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    private boolean gameOver;
    private Text gameOverText = new Text("Game Over");


    public MainGameScene(Group root, GraphicsContext gc, Canvas canvas) {
        super(root, gc, canvas);
        gameOver = false;
    }

    public MainGameScene(Group root, GraphicsContext gc, Canvas canvas, BombermanGame gameController) {
        super(root, gc, canvas);
        this.gameController = gameController;
        gameOver = false;
        init();
    }

    @Override
    public void init() {
        buildMap();
        keyPressedHandler();
    }

    public void loadMapLevel() {
        String path = resourcePath + Player.level + ".txt";
        URL loc = gameController.getClass().getResource(path);

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(loc.openStream()));
            String line = bufferedReader.readLine();
            String[] line1List = line.split(" ");
            HEIGHT = Integer.parseInt(line1List[1]);
            WIDTH = Integer.parseInt(line1List[2]);

            _map = new char[HEIGHT][WIDTH];
            stillObjectMap = new Entity[HEIGHT][WIDTH];

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    _map[i][j] = (char) bufferedReader.read();
                }
                bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            //generate map
            e.printStackTrace();
        }


    }

    private void printMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(_map[i][j]);
            }
            System.out.println();
        }
    }

    public void buildMap() {
        loadMapLevel();
        canvas.setHeight((HEIGHT) * Sprite.SCALED_SIZE);
        canvas.setWidth((WIDTH + 4) * Sprite.SCALED_SIZE);

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage(), this);
                switch (_map[i][j]) {
                    case '#': {
                        // wall
                        object = new Wall(j, i, Sprite.wall.getFxImage(), this);

                        break;
                    }
                    case '*': {
                        // brick
                        object = new Brick(j, i, Sprite.brick.getFxImage(), this);

                        break;

                    }
                    case 'x': {
                        // portal
                        object = new Portal(j, i, Sprite.portal.getFxImage(), this);

                        break;

                    }
                    case 'p': {
                        // main character: Bomber
                        bomber = new Bomber(j, i, Sprite.player_right_2.getFxImage(), this);
                        object = new Grass(j, i, Sprite.grass.getFxImage(), this);
//                        stillObjectMap[i][j] = new Grass(j, i, Sprite.grass.getFxImage(), this);
                        break;

                    }
                    case '1': {
                        //  Ballon
                        Enemy e = new Balloon(j, i, Sprite.balloom_left1.getFxImage(), this);
                        enemies.add(e);
                        object = new Grass(j, i, Sprite.grass.getFxImage(), this);
                        break;

                    }
//                    case '2': {
//                        // moving entity
//                        break;
//
//                    }
//                    case '3': {
//                        // moving entity
//                        break;
//
//                    }
//                    case '4': {
//                        // moving entity
//                        break;
//
//                    }
//                    case '5': {
//                        break;
//
//                    }
                    case 'b': {
                        // bomb item: add quanity
                        object = new BombItem(j, i, Sprite.bomb.getFxImage(), this);
                        break;

                    }
                    case 'f': {
                        // flame:  destroy++
                        object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage(), this);

                        break;

                    }
                    case 's': {
                        // speed: velocity++
                        object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage(), this);

                        break;

                    }
                    default: {
                        // grass
                        object = new Grass(j, i, Sprite.grass.getFxImage(), this);

                        break;

                    }
                }
                stillObjects.add(object);
                stillObjectMap[i][j] = object;
            }
        }
    }

    public MainGameScene(Parent parent) {
        super(parent);
        init();
    }

    public boolean collider(Rectangle r1, Rectangle r2) {

        if (r1.intersects(r2)) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).isDestroy) {
                flames.remove(flames.get(i));
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isLiving()) {
                enemies.remove(enemies.get(i));
            }
        }
        if (!gameOver) {
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity e = stillObjects.get(i);
                if (e instanceof Item) {
                    Rectangle r1 = new Rectangle((int) bomber.getX(), (int) bomber.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                    Rectangle r2 = new Rectangle((int) e.getX(), (int) e.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                    if (collider(r1, r2)) {
                        e.collision(bomber);
                    }
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
            }
            for (int i = 0; i < flames.size(); i++) {
                Flame f = flames.get(i);
                Rectangle r2 = new Rectangle((int) f.getX(), (int) f.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);
                    Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);


                    if (e.isLiving() && collider(r1, r2)) {
                        e.destroy();
                    }
                }
                Rectangle r1 = new Rectangle((int) bomber.getX() + Sprite.SCALED_SIZE / 4, (int) bomber.getY() + Sprite.SCALED_SIZE / 4, Sprite.SCALED_SIZE / 3, Sprite.SCALED_SIZE / 3);

                if (collider(r1, r2)) {
                    bomber.destroy();
                    gameOver();
                }
            }
        }

    }

    private void gameOver() {
        gameOver = true;


        //setting the position of the text
        gameOverText.setX(WIDTH / 2);
        gameOverText.setY(HEIGHT / 2);


        gameOverText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

    }

    public boolean canMove(Entity obj) {
        if (obj instanceof Wall) {
            return false;
        }
        if (obj instanceof Brick) {
            Brick brick = (Brick) obj;
            if (!brick.isDestroy()) {
                return false;
            }
        }
        if (obj instanceof Item) {
            Item item = (Item) obj;
            if (item.getLayer() == 0) {
                return false;
            }
        }
        if (obj instanceof Portal) {
            Portal portal = (Portal) obj;
            //if(portal){}
            return false;
        }
        return true;
    }

    public void keyPressedHandler() {
        this.setOnKeyPressed(keyEvent -> {
            KeyCode key = keyEvent.getCode();
            switch (key) {
                case LEFT:
                case A:
                    bomber.moveLeft();
                    break;
                case RIGHT:
                case D:
                    bomber.moveRight();
                    break;
                case UP:
                case W:
                    bomber.moveUp();
                    break;
                case DOWN:
                case S:
                    bomber.moveDown();
                    break;
                case SPACE:
                    bomber.plantBoom();
                    break;
            }
        });
    }


    public void destroyAt(int xUnit, int yUnit) {
        Entity e = getAt(xUnit, yUnit);
        if (e instanceof Item) {
            Item item = (Item) e;
            item.destroy();
            return;
        }
        if (e instanceof Brick) {
            Brick brick = (Brick) e;
            brick.destroy();
            return;
        }
        if (e instanceof Portal) {
            Portal portal = (Portal) e;
            portal.destroy();
            return;
        }
    }

    public void deleteEnemy(Enemy e) {
        enemies.remove(e);
    }

    public void setGrass(int xUnit, int yUnit) {
        Entity o = getAt(xUnit, yUnit);
        stillObjects.remove(o);
        o = new Grass(xUnit, yUnit, Sprite.grass.getFxImage(), this);
        stillObjectMap[yUnit][xUnit] = o;
        stillObjects.add(o);
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(entity -> entity.render(gc));
        bombList.forEach(bomb -> {
            if (!bomb.isDestroy)
                bomb.render(gc);
        });
        bomber.render(gc);
        flames.forEach(f -> {
            if (!f.isDestroy)
                f.render(gc);
        });
        enemies.forEach(enemy -> enemy.render(gc));
        if (gameOver) {
            Font fontLarge = Font.font("Droid Sans", FontWeight.BOLD, 70);
            gc.setFont(fontLarge);
            gc.fillText("Game Over",
                    WIDTH * Sprite.SCALED_SIZE / 2 - 100, HEIGHT * Sprite.SCALED_SIZE / 2 - 100);
        }
    }

    public Entity getAt(int xUnit, int yUnit) {
        if (xUnit < 0 || xUnit >= WIDTH) {
            return null;
        }
        if (yUnit < 0 || yUnit >= HEIGHT) {
            return null;
        }
        return stillObjectMap[yUnit][xUnit];
    }
}
