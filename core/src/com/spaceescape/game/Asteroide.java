package com.spaceescape.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.Random;

public class Asteroide extends ApplicationAdapter {

    //Parâmetros
    private float larguraDispositivo;
    private float alturaDispositivo;

    //Variaveis
    Random gerador = new Random();
    private SpriteBatch batch;
    private int x = gerador.nextInt(Gdx.graphics.getWidth());
    private int y = gerador.nextInt(Gdx.graphics.getHeight());
    private int velx,vely;
    private int identificador;
    private Texture asteroide;
    private boolean visivel;
    private int altura,largura;
    private int vida=4000;





    public Asteroide(){
        asteroide = new Texture("nave1.png");
        batch = new SpriteBatch();
        this.x = x;
        this.y = y;
        this.velx = 1;
        this.vely = 1;
        this.largura = asteroide.getWidth();
        this.altura = asteroide.getHeight();
        this.visivel = true;
        this.identificador = 1;
        this.alturaDispositivo = Gdx.graphics.getHeight();
        this.larguraDispositivo = Gdx.graphics.getWidth();
    }
    public void run(){
        long count = 0;

        while ( true ){
            try{
                Thread.sleep(25);
            }catch(InterruptedException e){}
            //vida=vida-1;
            this.mover();
        }
    }
    public void mover(){

        if (x <= 0) {
            velx = gerador.nextInt(3);
            vely = -gerador.nextInt(3);
            x = 5;
        }
        if (x >= alturaDispositivo) { // altura pois está em landscape
            velx = -gerador.nextInt(3);
            vely = gerador.nextInt(3);
            x = 575;
        }
        if (y <= 26) {
            vely = gerador.nextInt(3);
            ;
            velx = -gerador.nextInt(3);
            y = 26;
        }
        if (y >= larguraDispositivo) { // largura pois está em landscape
            vely = -gerador.nextInt(3);
            ;
            velx = gerador.nextInt(3);
            y = 510;
        }

        this.x = x + velx;
        this.y = y + vely;

    }




    @Override
    public void create () {

    }

    @Override
    public void render () {
        if(vida<0){
            visivel=false;
        }
        if (visivel == true) {
            batch.begin();
            batch.draw(asteroide, 0, 0, largura, altura);
            batch.end();
        }

    }

    @Override
    public void dispose () {

    }




}
