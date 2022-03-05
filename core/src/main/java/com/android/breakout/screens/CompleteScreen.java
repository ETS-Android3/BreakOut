package com.android.breakout.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.android.breakout.MainGame;
import com.android.breakout.sonido.SonidosMusica;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CompleteScreen implements Screen {

    //Atributos.
    Stage stage;
    Image fondo, iconComplete;
    MainGame juego;

    //Constructor.
    public CompleteScreen(MainGame juego){
        this.juego = juego;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        //Iniciamos atributos.
        fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/fondoJuego.png"))));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        iconComplete = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/ganador.png"))));
        iconComplete.setPosition(Gdx.graphics.getWidth()/2 - iconComplete.getWidth()/2, Gdx.graphics.getHeight() / 2);


        SonidosMusica.playSubeNivel();
        stage.addActor(fondo);
        stage.addActor(iconComplete);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Tiempo stage, ganador.
        stage.act();
        stage.addAction(sequence(
                delay( 4f),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        SonidosMusica.playSubeNivel();
                        juego.setScreen(new MenuScreen(juego)); //Retorna al menu.
                        return true;
                    }}));

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
