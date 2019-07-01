package com.spaceescape.game;

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
    private int x ;
    private int y ;

    private int velx, vely;
    private int identificador;
    private Texture asteroide;
    private boolean visivel;
    private int altura, largura;
    private int vida = 8000;

    public Asteroide() {
        asteroide = new Texture("asteroide1.png");
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
        validaPosicaoInicial(x,y);
    }

    public Asteroide(String nomeAsteroide,int x,int y) {
        asteroide = new Texture(nomeAsteroide);
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
        Boolean ativa = true;

        while (ativa == true) {
            try {
                Thread.sleep(25);
                this.mover();
            } catch (InterruptedException e) {
            }
           if (vida == 0) {
               ativa = false;
           }
            vida = vida - 4;
        }
    }

    public void mover() {


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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public int geradorX(){

        int auxX = gerador.nextInt(Gdx.graphics.getHeight())- 100;
        int valorInicial =0;
        int valorFinal = Gdx.graphics.getWidth();

        if((auxX >= valorInicial && auxX <= valorFinal)){
            do{
                auxX = gerador.nextInt(Gdx.graphics.getHeight())- 100;
            }while(auxX >= valorInicial && auxX <= valorFinal);
        }

        return auxX;
    }

    public int geradorY(){

        int auxY = gerador.nextInt(Gdx.graphics.getWidth()) + 200;
        int valorInicial =0;
        int valorFinal = Gdx.graphics.getWidth();

        if((auxY >= valorInicial && auxY <= valorFinal)){
            do{
                auxY = gerador.nextInt(Gdx.graphics.getWidth()) + 200;
            }while(auxY >= valorInicial && auxY <= valorFinal);
        }

        return auxY;
    }

    public void validaPosicaoInicial (int x,int y){

        int valorInicialX = 0;
        int valorInicialY = 0;
        int valorFinalX = Gdx.graphics.getWidth();
        int valorFinalY = Gdx.graphics.getHeight();


        if((x >= valorInicialX && x <= valorFinalX) || (y >= valorFinalY && y <= valorFinalY) ) {

        }
    }
}
