package com.android.breakout.screens;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.Action;
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

public class SubeNivelScreen implements Screen {
    int nivel;
    final MainGame juego;
    Image imgSubeNivel, fondo;
    Stage stage, bg;

    public SubeNivelScreen(MainGame juego, int nivel){
        this.juego = juego;
        this.nivel = nivel;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        bg = new Stage(new ScreenViewport());
        //Inicializamos atributos, imgs
        imgSubeNivel = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/subeNivel.png"))));
        imgSubeNivel.setPosition(Gdx.graphics.getWidth()/2 - imgSubeNivel.getWidth()/2, Gdx.graphics.getHeight()/2);

        fondo = new Image(new TextureRegion(new Texture(Gdx.files.internal("juego/fondoJuego.png"))));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //Sonido al subir de nivel.
        SonidosMusica.playSubeNivel();

        bg.addActor(fondo);
        stage.addActor(imgSubeNivel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bg.act();
        bg.draw();
        stage.act();
        stage.draw();
        //Pasa al siguiente nivel.
        stage.addAction(sequence(delay(2f), fadeIn(1.75f), new Action() {
            @Override
            public boolean act(float delta) {
                SonidosMusica.stopSubeNivel();
                juego.setScreen(new BreakOut(juego, nivel));
                return true;
            }
        }));
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
