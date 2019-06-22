package com.spaceescape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceescape.game.Asteroide;

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


	//Atributos de Configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private Random random;
	private float variacao = 0;
	private float posicaoHorizontalNave;
	private float posicaoVerticalNave;


	//Parâmetros

	private int velocidadeNave = 5;

	@Override
	public void create () {
		inicializaTexturas();
		inicializaOjetos();
		adicionaAsteroide();
		adicionaAsteroide();
		adicionaAsteroide();
	}

	@Override
	public void render () {

		//LIMPAR FRAMES ANTERIORES
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		//desenharTexturas();
		verificaEstadoJogo();

	}

	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

	private void inicializaTexturas(){

		nave = new Texture[3];
		nave[0] = new Texture("nave13.png");
		nave[1] = new Texture("nave14.png");
		nave[2] = new Texture("nave11.png");
		fundo = new Texture("fundo.png");

	}

	private void verificaEstadoJogo(){
		//int w = Gdx.graphics.getWidth();
		//int h = Gdx.graphics.getHeight();
		//Gdx.app.log("FPS", "" + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		desenharTexturas();
	}

	private void inicializaOjetos(){
		batch = new SpriteBatch();
		random = new Random();

		renderX = 100;
		renderY = 100;

		nave[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoHorizontalNave = larguraDispositivo /2;
		posicaoVerticalNave = alturaDispositivo /2;


	}

	private void desenharTexturas() {

		renderX += Gdx.input.getAccelerometerY() * velocidadeNave;
		renderY -= Gdx.input.getAccelerometerX() * velocidadeNave;

		if(renderX < 0) renderX = 0;
		if(renderX > Gdx.graphics.getWidth()- 200) renderX = Gdx.graphics.getWidth() - 200;
		if(renderY < 0) renderY = 0;
		if(renderY > Gdx.graphics.getHeight() - 200) renderY = Gdx.graphics.getHeight() - 200;

		batch.begin();
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(nave[(int) variacao], renderX, renderY, 200, 200);

		for (int i = 0; i < 3; i++) {
			batch.draw(asteroides.get(i).getAsteroide(),asteroides.get(i).getX(),asteroides.get(i).getY(),asteroides.get(i).getLargura(),asteroides.get(i).getAltura());
		}
		batch.end();



		variacao += Gdx.graphics.getDeltaTime() * 10;
		if (variacao > 3) {
			variacao = 0;
		}
	}


	@Override

	public void pause() {

	}

	@Override

	public void resume() {

	}

	public void adicionaAsteroide() {
		Asteroide addAsteroide = new Asteroide();
		addAsteroide.start();
		asteroides.add(addAsteroide);

	}



}