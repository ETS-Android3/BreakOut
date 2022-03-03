package com.android.breakout.sonido;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SonidosMusica {
    static Music inicio = Gdx.audio.newMusic(Gdx.files.internal("sonidos/home.mp3"));
    static Music subeNivel = Gdx.audio.newMusic(Gdx.files.internal("sonidos/win.mp3"));
    static Music perder = Gdx.audio.newMusic(Gdx.files.internal("sonidos/lose.mp3"));
    static Music juego = Gdx.audio.newMusic(Gdx.files.internal("sonidos/game.mp3"));
    static Sound knock = Gdx.audio.newSound(Gdx.files.internal("sonidos/knock.wav"));

    public static void playInicioMusic() {
        inicio.play();
        inicio.setLooping(true);
    }

    public static void stopInicioMusic(){
        inicio.stop();
    }

    public static void playSubeNivel(){
        subeNivel.play();
    }

    public static void stopSubeNivel(){
        subeNivel.stop();
    }

    public static void playKnock(){
        knock.play();
    }

    public static void stopKnock(){
        knock.stop();
    }

    public static void playPerder(){
        perder.play();
    }

    public static void stopPerder(){
        perder.stop();
    }

    public static void playJuego(){
        juego.play();
        juego.setLooping(true);
    }

    public static void stopJuego(){
        juego.stop();
    }

}
