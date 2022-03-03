package com.android.breakout.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Paddle extends ActorDefault{
    public Paddle(int nivel, float x, float y, Stage stage) {
        super(x, y, stage);
        //Dependiendo del nivel, se asiganrá un paddle distinto.
        cargarTextura("juego/paddle_nivel_"+nivel+".png", true);
    }
}