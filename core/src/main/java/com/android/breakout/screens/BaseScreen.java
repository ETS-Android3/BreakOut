package com.android.breakout.screens;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.android.breakout.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//SplashScreen.
public class BaseScreen implements Screen {

    Image imagenIcon;
    Stage fondo;
    final MainGame juego;

    public BaseScreen(final MainGame juego){
        this.juego = juego;
        fondo =new Stage(new ScreenViewport());
        imagenIcon = new Image(new TextureRegion(new Texture(Gdx.files.internal("icon.png"))));
        imagenIcon.setPosition(Gdx.graphics.getWidth()/2 - imagenIcon.getWidth()/2, Gdx.graphics.getHeight()/2);

        fondo.addActor(imagenIcon);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fondo.act();
        fondo.draw();

        fondo.addAction(sequence(delay(2f), fadeOut(1.75f), new Action() {
            @Override
            public boolean act(float delta) {
                juego.setScreen(new MenuScreen(juego));
                return true;
            }
        }));

    }

    @Override
    public void show() {

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
