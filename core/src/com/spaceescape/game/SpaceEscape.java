package com.spaceescape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class SpaceEscape extends ApplicationAdapter {

	// TEXTURAS
	private SpriteBatch batch;
	private Texture[] nave;
	private Texture fundo;


	//Atributos de Configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private Random random;
    private float variacao = 0;
    private float posicaoHorizontalNave;
	private float posicaoVerticalNave;









	@Override
	public void create () {
		inicializaTexturas();
		inicializaOjetos();


	}

	@Override
	public void render () {

		//LIMPAR FRAMES ANTERIORES
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		desenharTexturas();


	}

	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

	private void inicializaTexturas(){

		nave = new Texture[2];
		nave[0] = new Texture("nave1.png");
		nave[1] = new Texture("nave2.png");

		fundo = new Texture("fundo.png");



	}

	private void inicializaOjetos(){
		batch = new SpriteBatch();
		random = new Random();
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoHorizontalNave = larguraDispositivo /2;
		posicaoVerticalNave = alturaDispositivo /2;


	}

	private void desenharTexturas() {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);

		batch.draw(nave[(int) variacao], posicaoHorizontalNave, posicaoVerticalNave);


		batch.end();
		variacao += Gdx.graphics.getDeltaTime() * 10;
		if (variacao > 2) {
			variacao = 0;
		}
	}


}
