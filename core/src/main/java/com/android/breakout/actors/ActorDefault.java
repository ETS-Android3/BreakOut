package com.android.breakout.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.w3c.dom.Text;


//Todos los actores heredarán de esta clase como actor base.
public class ActorDefault extends Actor {

    //Declaramos los siguientes atributos.
    Texture textura;
    protected Vector2 velocidad;
    Polygon poligono;

    //Constructor.
    public ActorDefault (float x, float y, Stage stage){
        setPosition(x, y);
        stage.addActor(this);

        velocidad = new Vector2(0,0);
        poligono = null;
    }

    //Método para cargar las imágenes para los distintos actores.
    public void cargarTextura(String texturaNombre, boolean ft){
        textura = new Texture(Gdx.files.internal(texturaNombre), ft);
        textura.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //Obtenemos ancho y alto.
        setWidth(textura.getWidth());
        setHeight(textura.getHeight());

        if (poligono==null) setLimiteTexture();
    }

    //-------------------------Medidas, control----------------------------//

    //Obtiene medidas y le da al poligono sus medidas a base de los vertices(recoge w y h).
    public void setLimiteTexture(){
        float width = getWidth();
        float height = getHeight();

        float[] vertices ={0, 0, width, 0, width, height, 0, height};//Array vertices, medidas para p.
        poligono = new Polygon(vertices);
    }

    //Método: Medidas (MathUtils) para el desplazamiento del actor ball.
    public void setLimitePoligono(int num){
        float width = getWidth();
        float height = getHeight();

        float[] vertices = new float[2*num];
        for(int i=0; i<num; i++){
            float angulo = i *6.28f / num;
            //Ancho
            vertices[2*i] = width/2 * MathUtils.cos(angulo) + width/2;

            //Alto
            vertices[2*i+1] = height/2 * MathUtils.sin(angulo) + height/2;

        }

        poligono = new Polygon(vertices);
    }

    //Control cuando esté en el espacio. (Ball)
    public Polygon getLimitePoligono(){
        poligono.setPosition(getX(), getY());
        poligono.setOrigin(getOriginX(), getOriginY());
        poligono.setRotation(getRotation());
        poligono.setScale(getScaleX(), getScaleY());

        return poligono;
    }

    //Rebote control.
    public boolean overlapsPoligono(ActorDefault actorDefault){
        Polygon p1 = this.getLimitePoligono();
        Polygon p2 = actorDefault.getLimitePoligono();

        if(!p1.getBoundingRectangle().overlaps(p2.getBoundingRectangle()))
            return false;

        return Intersector.overlapConvexPolygons(p1, p2);
    }

    //Método que usará el actor ball para rebotar.
    public Vector2 overLap (ActorDefault actorDefault){
        Polygon p1 = this.getLimitePoligono();
        Polygon p2 = actorDefault.getLimitePoligono();

        if(!p1.getBoundingRectangle().overlaps(p2.getBoundingRectangle())){
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean overlapP = Intersector.overlapConvexPolygons(p1, p2, mtv);

        if(!overlapP){
            return null;
        }

        this.moveBy(mtv.normal.x*mtv.depth, mtv.normal.y*mtv.depth);
        return mtv.normal;
    }

    public void draw(Batch batch, float pA){
        Color color =getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        batch.draw(textura, getX(), getY());
        super.draw(batch, pA);
    }

    //Método que usará el actor ball para su velocidad.
    public void setVelocidad(float v){
        if(velocidad.len() == 0){
            velocidad.set(v, 0);
        }
        else {
            velocidad.setLength(v);
        }
    }

    //Métodos get y set de velocidad.
    public float getAngulo(){
        return velocidad.angleDeg();
    }

    public void setAngulo(float angulo){
        velocidad.setAngleDeg(angulo);
    }
}
