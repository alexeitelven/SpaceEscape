package com.spaceescape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;


public class SpaceEscape extends ApplicationAdapter {

    // TEXTURAS
    private SpriteBatch batch;
    private Texture[] nave;
    private Texture fundo;
    private float renderX = 100;
    private float renderY = 100;

    private ArrayList<Asteroide> asteroides = new ArrayList();
    //public ArrayList<Asteroide> asteroides;


    //Formas para Colisão
    private ShapeRenderer shapeRenderer;
    private Circle circuloNave, circuloAsteroide;
    private Rectangle retanguloCanoCima;


    //Atributos de Configurações
    private float larguraDispositivo;
    private float alturaDispositivo;
    private Random random;
    private float variacao = 0;
    private float tempoGeraAsteroide = 0;
    private float posicaoHorizontalNave;
    private float posicaoVerticalNave;
    private long startTime;

    //Parâmetros

    private int velocidadeNave = 5;


    @Override
    public void create() {
        inicializaTexturas();
        inicializaOjetos();


    }

    @Override
    public void render() {

        //LIMPAR FRAMES ANTERIORES
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        //desenharTexturas();
        verificaEstadoJogo();
        detectarColisoes();
        geraAsteroides();

    }

    @Override
    public void dispose() {
        batch.dispose();
        //img.dispose();
    }

    private void inicializaTexturas() {

        nave = new Texture[3];
        nave[0] = new Texture("nave1.png");
        nave[1] = new Texture("nave2.png");
        nave[2] = new Texture("nave3.png");
        fundo = new Texture("fundo.png");

    }

    private void verificaEstadoJogo() {
        //int w = Gdx.graphics.getWidth();
        //int h = Gdx.graphics.getHeight();
        //Gdx.app.log("FPS", "" + Gdx.graphics.getFramesPerSecond());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        desenharTexturas();
    }


    private void inicializaOjetos() {
        batch = new SpriteBatch();
        random = new Random();

        renderX = 100;
        renderY = 100;

        nave[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoHorizontalNave = larguraDispositivo / 2;
        posicaoVerticalNave = alturaDispositivo / 2;

        //Formas para Colisões
        shapeRenderer = new ShapeRenderer();
        circuloNave = new Circle();
        circuloAsteroide = new Circle();


        // controle de tempo
        //startTime = TimeUtils.millis();


    }

    private void desenharTexturas() {

        renderX += Gdx.input.getAccelerometerY() * velocidadeNave;
        renderY -= Gdx.input.getAccelerometerX() * velocidadeNave;

        if (renderX < 0) renderX = 0;
        if (renderX > Gdx.graphics.getWidth() - 200) renderX = Gdx.graphics.getWidth() - 200;
        if (renderY < 0) renderY = 0;
        if (renderY > Gdx.graphics.getHeight() - 200) renderY = Gdx.graphics.getHeight() - 200;

        batch.begin();
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(nave[(int) variacao], renderX, renderY, 150, 150);

//		for (int i = 0; i < asteroides.size(); i++) {
//			if(asteroides.get(i).isVisible() == true) {
//				batch.draw(asteroides.get(i).getAsteroide(), asteroides.get(i).getX(), asteroides.get(i).getY(), asteroides.get(i).getLargura(), asteroides.get(i).getAltura());
//			}
//		}


        for (int i = 0; i < asteroides.size(); i++) {
            if(asteroides.get(i).getVida()== 0){
                asteroides.get(i).setVisible(false);
                asteroides.get(i).setX(Gdx.graphics.getWidth());
                asteroides.get(i).setY(Gdx.graphics.getHeight());
            }
            if (asteroides.get(i).isVisible() == true) {
                batch.draw(asteroides.get(i).getAsteroide(), asteroides.get(i).getX(), asteroides.get(i).getY(), asteroides.get(i).getLargura(), asteroides.get(i).getAltura());
            }
        }

        batch.end();

        tempoGeraAsteroide += Gdx.graphics.getDeltaTime()*40;
        if (tempoGeraAsteroide * 2 > 99) {
            tempoGeraAsteroide = 0;
        }


        variacao += Gdx.graphics.getDeltaTime() * 10;
        if (variacao > 3) {
            variacao = 0;
        }
    }

    private void geraAsteroides() {

        if (tempoGeraAsteroide >= 3 && tempoGeraAsteroide <= 3.5) {
            adicionaAsteroide1();
        } else if (tempoGeraAsteroide >= 2 && tempoGeraAsteroide <= 2.5){
            adicionaAsteroide2();
        }
    }


    private void detectarColisoes() {

        Circle formaAsteroide;
        //FORMAS PARA COLISAO


        //NAVE
        circuloNave.set(renderX + 150 / 2, renderY + 150 / 2, 150 / 2);
        //ASTEROIDES
        for (int j = 0; j < asteroides.size(); j++) {
            Asteroide tempAsteroide = asteroides.get(j);
            formaAsteroide = tempAsteroide.getFiguraColisao();
            if (tempAsteroide.isVisible() == true) {
                boolean colidiuAsteroide1 = Intersector.overlaps(circuloNave, formaAsteroide);

                if (colidiuAsteroide1 == true) {
                    Gdx.app.log("colisao", "COLIDIU ESSA MERDA!");
                    tempAsteroide.setVisible(false);
                    //tempAsteroide.stop();
                }
            }
        }


//        // DESENHANDO AS FORMAS GEOMETRICAS - para entender a colisao
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(renderX + 150/2,renderY +150/2,150/2);
//
//		for (int i = 0; i < asteroides.size(); i++) {
//			shapeRenderer.circle(asteroides.get(i).getX() + asteroides.get(i).getLargura()/2,
//					asteroides.get(i).getY() + asteroides.get(i).getAltura()/2,
//					asteroides.get(i).getAltura()/2);
//		}
//        shapeRenderer.end();
//

    }


    @Override

    public void pause() {

    }

    @Override

    public void resume() {

    }

    public void adicionaAsteroide1() {
        Asteroide addAsteroide = new Asteroide("asteroide1.png");
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }

    public void adicionaAsteroide2() {
        Asteroide addAsteroide = new Asteroide("asteroide2.png");
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }


}