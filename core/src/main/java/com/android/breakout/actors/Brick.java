package com.android.breakout.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brick extends ActorDefault{
    //Nivel de brick.
    int nivel;

    public Brick(int nivel, float x, float y, Stage stage){
        super(x, y, stage);
        this.nivel = nivel;
        cargarTextura("juego/brick_nivel_" +this.nivel+ ".png", true); //Para diferenciar el nivel del brick, se pone en distinto color.
    }

    public int getNivel(){
        return nivel;
    }
}
