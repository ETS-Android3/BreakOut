package com.android.breakout.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Vector;

public class Ball extends ActorDefault{
    public boolean pausa;

    //Constructor.
    public Ball(float x, float y, Stage stage){
        super(x, y, stage);
        cargarTextura("juego/pelota.png", true); //Imagen de la pelota que se usará.

        setVelocidad(40); //Asignamos a la velocidad que irá la pelota.
        setAngulo(anguloInicial(45, 135));
        setLimitePoligono(12);
        setPausa(true);
    }

    //Métodos pausa.
    public void setPausa(boolean pausab){
        pausa = pausab;
    }

    public boolean isPausa(){
        return pausa;
    }

    //Ángulo inicial de la pelota.
    public int anguloInicial(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    //Método rebotar, hereda del actor default. (overlap)
    public void rebotar(ActorDefault actorDefault){
        Vector2 vector = this.overLap(actorDefault);
        if(Math.abs(vector.x) >= Math.abs(vector.y)){
            this.velocidad.x *=-1;
        }else{
            this.velocidad.y *= -1;
        }
    }

    public void movimiento(){
        setX(getX() + velocidad.x);
        setY(getY() + velocidad.y);
    }

    //Velocidad actualizando.
    public void velocidadAct (){
        this.velocidad.x += (this.velocidad.x > 0 ? 0.0001 : -0.0001);
        this.velocidad.y += (this.velocidad.y > 0 ? 0.0001 : -0.0001);
    }

}
