package com.android.breakout.screens;

import com.android.breakout.MainGame;
import com.android.breakout.sonido.SonidosMusica;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {

    Image gameOverIcon, fondo;
    Button jugar, menu;

    Stage stage;
    int nivelActual;
    MainGame juego;

    //Constructor.
    public GameOverScreen(MainGame juego, int nivelActual){
        this.juego = juego;
        this.nivelActual = nivelActual;
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Inicializamos todos los atributos con sus imágenes
        fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/fondoJuego.png"))));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        jugar = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("inicio/jugar.png")))));
        jugar.setSize(jugar.getWidth(), jugar.getHeight());
        jugar.setPosition(Gdx.graphics.getWidth()/2 - jugar.getWidth()/2,
                Gdx.graphics.getHeight() / 3);
        jugar.setTouchable(Touchable.enabled);


        menu = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("juego/menu.png")))));
        menu.setSize(menu.getWidth(), menu.getHeight());
        menu.setPosition(Gdx.graphics.getWidth()/2 - menu.getWidth()/2,
                Gdx.graphics.getHeight() / 3 - menu.getHeight() - menu.getHeight() / 3);
        menu.setTouchable(Touchable.enabled);


        gameOverIcon = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/lose.png"))));
        gameOverIcon.setPosition(Gdx.graphics.getWidth()/2 - gameOverIcon.getWidth()/2, Gdx.graphics.getHeight() / 2);

        //Botón seguir jugando
        jugar.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SonidosMusica.stopPerder();
                juego.setScreen(new BreakOut(juego, nivelActual));//Seguir jugando
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //Botón salir, menú.
        menu.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SonidosMusica.stopPerder();
                juego.setScreen(new MenuScreen(juego)); //Volvemos al menú.
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        SonidosMusica.playPerder();

        stage.addActor(fondo);
        stage.addActor(jugar);
        stage.addActor(menu);
        stage.addActor(gameOverIcon);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
