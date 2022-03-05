package com.android.breakout.screens;

import com.android.breakout.MainGame;
import com.android.breakout.actors.Ball;
import com.android.breakout.actors.Brick;
import com.android.breakout.actors.Paddle;
import com.android.breakout.sonido.SonidosMusica;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BreakOut implements Screen {
    Paddle paddle;
    Ball pelota;

    int width, height;
    int nivelActual, maxNivel;
    boolean perder, completado;
    public static Stage stage;

    Image fondo, imgNivel;
    Image izqPant, drcPant, arPant;

    MainGame juego;

    //Juego.
    public BreakOut(MainGame juego, int nivel){
        this.juego = juego;
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        maxNivel = 4;
        nivelActual =nivel;

        iniciaJuego();
    }

    public void iniciaJuego(){
        perder = false;
        completado = false;

        //Silenciamos musica de inicio.
        SonidosMusica.stopInicioMusic();

        //Fondo del juego.
        fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/fondoJuego.png"))));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(fondo);

        //Marco izquierdo.
        izqPant = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/pantalla.png"))));
        izqPant.setSize(Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight());
        stage.addActor(izqPant);

        //Marco derecho.
        drcPant = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/pantalla.png"))));
        drcPant.setSize(Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight());
        drcPant.setPosition(width - width/30, 0);
        stage.addActor(drcPant);

        //Marco de arriba.
        arPant = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/pantalla.png"))));
        arPant.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/10);
        arPant.setPosition(0, height - height/10);
        stage.addActor(arPant);

        //Número de nivel.
        imgNivel = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/nivel_"+nivelActual+".png"))));
        imgNivel.setPosition(arPant.getWidth()/2 - imgNivel.getWidth()/2, height - arPant.getHeight()/2 - imgNivel.getHeight()/2);
        stage.addActor(imgNivel);

        //Paddle, inicio en paddle nivel 1.
        Paddle paddleM = new Paddle(1, 0, 0, stage);
        float paddleWidth = paddleM.getWidth();
        paddleM.remove();

        //Pelota.
        Ball ballM = new Ball(0, 0, stage);
        float ballWidth = ballM.getWidth();
        ballM.remove();

        //Pasamos información para ambos, dependiendo del nivel.
        paddle = new Paddle(nivelActual, width/2 - paddleWidth/2, 40, stage);
        pelota = new Ball(width/2 - ballWidth/2, paddle.getHeight()+5, stage);

        //Bricks, asignando su respectivo nivel.
        Brick brickM = new Brick(1, 0, 0, stage);
        float brickWidth = brickM.getWidth();
        float brickHeight = brickM.getHeight();
        brickM.remove();

        niveles(brickWidth, brickHeight);//Despues

    }

    //-------Niveles------//

    public void niveles(float brickWidth, float brickHeight) {
        String fileLevel = "nivel/nivel" + nivelActual + ".txt"; //Cargamos orden de bricks con un archivo txt.
        int totalRows = 6;
        int totalCols = 6;

        //Margin x e y de los bricks.
        float marginX = (width - totalCols*brickWidth-650) /4;
        float marginY = (height - totalRows*brickHeight) - 390;

        //Lectura del archivo txt para el posicionamiento, número y tipo de bricks.
        try {
            FileHandle fileHandle = Gdx.files.internal(fileLevel);
            InputStream inputStream = fileHandle.read();
            int i, j = 0;
            while((i = inputStream.read())!=-1) {
                int colNum = (totalCols - j % totalCols);
                int rowNum = (totalRows - j / totalRows);

                float brickX = marginX + colNum * brickWidth;
                float brickY = marginY + rowNum * brickHeight;

                switch ((char)i) {
                    case '_':
                        j++;
                        break;
                    case '1':
                        j++;
                        new Brick(1, brickX, brickY, stage);
                        break;
                    case '2':
                        j++;
                        new Brick(2, brickX, brickY, stage);
                        break;
                    case '3':
                        j++;
                        new Brick(3, brickX, brickY, stage);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());

        //Mientras que no pierda.
        if (!perder)
            stage.draw();
        //Al perder.
        else {
            stage.clear();
            juego.setScreen(new GameOverScreen(juego, nivelActual));
            dispose();
        }

        //Si el nivel haa sido completado, hay dos opciones.
        if (completado) {
            stage.clear();
            //En caso de que no se haya llegado al nivel 4 que es el nivel máximo, pasaría al siguiente nivel.
            if (nivelActual < maxNivel) {
                stage.clear();
                juego.setScreen(new SubeNivelScreen(juego, nivelActual + 1)); //Pasando por parámetro la información para el siguiente nivel.
                dispose();
            }
            //En caso contrario, el juego ha terminado.
            else {
                stage.clear();
                juego.setScreen(new CompleteScreen(juego));//Lanzamos pantalla de juego completado.
            }
        }
    }

    public void boundToScreen() {
        if(paddle.getX() < izqPant.getWidth()) paddle.setX(izqPant.getWidth());
        if(paddle.getX() + paddle.getWidth() > width - drcPant.getWidth())
            paddle.setX(width - drcPant.getWidth() - paddle.getWidth());
    }

    public void update(float dt) {

        if(Gdx.input.isTouched()) {
            if(pelota.isPausa() == true) {
                pelota.setPausa(false);
            }
        }

        int lastTouched = 0;
        for(int i=0; i<20; i++) if(Gdx.input.isTouched(i)) lastTouched = i;

        if(Gdx.input.isTouched(lastTouched)) {
            float touchedX = Gdx.input.getX(lastTouched);
            paddle.setX(touchedX - paddle.getWidth()/2);
            boundToScreen();
        }

        //Si la pelota está en pausa.
        if(pelota.isPausa()) {
            SonidosMusica.stopKnock();
            pelota.setX( paddle.getX() + paddle.getWidth()/2 - pelota.getWidth()/2 );
            pelota.setY( paddle.getY() + paddle.getHeight()/2 + pelota.getHeight()/2 );
        } else{
            pelota.velocidadAct();
        }

        //Pelota al chocar con el paddle.
        if (pelota.overlapsPoligono(paddle)) {
            //Al iniciar.
            if(pelota.isPausa()){
                SonidosMusica.stopKnock();
                float ballCenterX = pelota.getX() + pelota.getWidth()/2;
                float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
                float bounceAngle = MathUtils.lerp( 150, 30, paddlePercentHit );
                pelota.setAngulo(bounceAngle);
            }
            //En funcionamiento, juego.
            else{
                SonidosMusica.playKnock();
                float ballCenterX = pelota.getX() + pelota.getWidth()/2;
                float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
                float bounceAngle = MathUtils.lerp( 150, 30, paddlePercentHit );
                pelota.setAngulo(bounceAngle);
            }
        }

        for(Brick brick: getListBrick()) {
            //Pelota choca con un brick.
            if ( pelota.overlapsPoligono(brick) ) {
                SonidosMusica.playKnock();

                pelota.rebotar(brick);

                //Brick nivel 2: Hay que darle dos veces para que se pueda romper.
                if(brick.getNivel()==2) {
                    float brickX = brick.getX();
                    float brickY = brick.getY();
                    new Brick(1, brickX, brickY, stage);
                }

                //Brick nivel 3: Hay que darle tres veces para que se pueda romper.
                else if(brick.getNivel()==3) {
                    float brickX = brick.getX();
                    float brickY = brick.getY();
                    new Brick(2, brickX, brickY, stage);
                }
                brick.remove();
            }
        }

        //Si todos los bricks se han roto, el nivel estaría completado.
        if(countBrick() == 0) completado= true;

        pelota.movimiento();

        //Medidas pelota, funcionamiento.
        if(pelota.getX() > width - drcPant.getWidth() - pelota.getWidth() || pelota.getX() < izqPant.getWidth()) {
            pelota.setAngulo(180 - pelota.getAngulo());
        }
        if(pelota.getY() > height - arPant.getHeight() - pelota.getHeight()) {
            pelota.setAngulo(pelota.getAngulo() + (180 - 2 * (90 - (180 - pelota.getAngulo()))));
        }

        //Si la pelota hace contacto con la superficie del suelo, pierde(menor 0).
        if(pelota.getY() < 0) perder = true;
    }

    //Array de bricks.
    public ArrayList<Brick> getListBrick() {
        ArrayList<Brick> list = new ArrayList<>();

        for(Actor actor: stage.getActors()) {
            if(actor instanceof Brick)
                list.add(((Brick) actor));
        }

        return list;
    }

    //Tamaño de lista bricks.
    public int countBrick() {
        return getListBrick().size();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
    }
}
