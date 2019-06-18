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

	// Variaveis
	private Random random;



	@Override
	public void create () {
		inicializaTexturas();


	}

	@Override
	public void render () {
		batch.begin();
		//batch.draw(im, 0, 0);
		batch.end();
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



	}
}
