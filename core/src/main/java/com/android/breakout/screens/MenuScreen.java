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

public class MenuScreen implements Screen {
    Button botonPlay;
    Image fondo;
    Image iconImagen;
    MainGame juego;
    Stage stage;
    ScreenViewport viewport;

    public MenuScreen(final MainGame juego){
        //Fondo inicio.
        this.juego = juego;
        this.viewport = new ScreenViewport();
        this.viewport.apply();
        stage =new Stage(this.viewport);
        fondo =new Image(new TextureRegion(new Texture(Gdx.files.internal("inicio/fondo.png"))));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Boton play/jugar en inicio.
        botonPlay = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("inicio/jugar.png")))));
        botonPlay.setSize(botonPlay.getWidth(), botonPlay.getHeight());
        botonPlay.setPosition(Gdx.graphics.getWidth()/2 - botonPlay.getWidth()/2, Gdx.graphics.getHeight()/2-100);

        iconImagen = new Image(new TextureRegion((new Texture(Gdx.files.internal("iconText.png")))));
        iconImagen.setPosition(Gdx.graphics.getWidth()/2 - iconImagen.getWidth()/2, Gdx.graphics.getHeight()/2 + iconImagen.getHeight()/3);

        botonPlay.setTouchable(Touchable.enabled);
        botonPlay.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int p, int boton){
                juego.setScreen(new BreakOut(juego, 1));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int p, int boton){
                return true;
            }
        });

        stage.addActor(fondo);
        stage.addActor(botonPlay);
        stage.addActor(iconImagen);

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        SonidosMusica.playInicioMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width,height);
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
