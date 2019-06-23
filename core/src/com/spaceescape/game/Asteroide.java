package com.spaceescape.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;


import java.util.Random;

public class Asteroide extends Thread {

    //Parâmetros
    private float larguraDispositivo;
    private float alturaDispositivo;

    //Variaveis
    Random gerador = new Random();
    private SpriteBatch batch;
    private int x = gerador.nextInt(Gdx.graphics.getWidth());
    private int y = gerador.nextInt(Gdx.graphics.getHeight());
    private int velx, vely;
    private int identificador;
    private Texture asteroide;
    private boolean visivel;
    private int altura, largura;
    private int vida = 4000;


    public Asteroide() {
        asteroide = new Texture("asteroide2.png");
        batch = new SpriteBatch();
        this.x = x;
        this.y = y;
        this.velx = 10;
        this.vely = 10;
        this.largura = asteroide.getWidth();
        this.altura = asteroide.getHeight();
        this.visivel = true;
        this.identificador = 1;
        this.alturaDispositivo = Gdx.graphics.getHeight();
        this.larguraDispositivo = Gdx.graphics.getWidth();
    }

    public void run() {
        long count = 0;

        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
            }
            //vida=vida-1;
            this.mover();
        }
    }


    public void mover() {

//        if (x <= 0) {
//            velx = gerador.nextInt(10);
//            vely = -gerador.nextInt(10);
//            x = 5;
//        }
//        if (x >= alturaDispositivo) { // altura pois está em landscape
//            velx = -gerador.nextInt(10);
//            vely = gerador.nextInt(10);
//            x = (int)alturaDispositivo;
//        }
//        if (y <= 26) {
//            vely = gerador.nextInt(10);
//            velx = -gerador.nextInt(10);
//            y = 26;
//        }
//        if (y >= larguraDispositivo) { // largura pois está em landscape
//            vely = -gerador.nextInt(10);
//            velx = gerador.nextInt(10);
//            y = (int)larguraDispositivo;
//        }

        if (x < -500) {
            velx = gerador.nextInt(10);
            vely = -gerador.nextInt(10);
            x = -500;
        }
        if (x > Gdx.graphics.getWidth()+500) {
            velx = -gerador.nextInt(10);
            vely = gerador.nextInt(10);
            x = Gdx.graphics.getWidth() +500;
        }
        if (y < -500) {
            vely = gerador.nextInt(10);
            velx = -gerador.nextInt(10);
            y = -500;

        }
        if (y > Gdx.graphics.getHeight()+500){
            vely = -gerador.nextInt(10);
            velx = gerador.nextInt(10);
            y = Gdx.graphics.getHeight() + 500;
        }
        this.x = x + velx;
        this.y = y + vely;

    }

    public Circle getFiguraColisao(){
        return new Circle(this.x + this.largura/2,this.y + this.altura/2,this.largura/2);
        //return new Rectangle(this.x,this.y,this.largura,this.altura);
    }

    public Texture getAsteroide() {
        return asteroide;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }


    public boolean isVisible() {
        return visivel;
    }

    public void setVisible(boolean visible) {
        this.visivel = visivel;
    }



}
