package com.android.breakout;

import com.android.breakout.screens.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MainGame extends Game {
    @Override
    public void create() {
        setScreen(new BaseScreen(this)); //Iniciamos con la pantalla de splash.
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}
