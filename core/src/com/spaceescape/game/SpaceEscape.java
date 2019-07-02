package com.spaceescape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.Random;

public class SpaceEscape extends ApplicationAdapter {

    // Alterar aqui antes de comitar
    //# Versão 1.11

    // TEXTURAS
    private SpriteBatch batch;
    private Texture[] nave;
    private Texture fundo;
    private Texture gameOver;
    private Texture telaInicial;
    private float renderX;
    private float renderY;

    private ArrayList<Asteroide> asteroides = new ArrayList();

    //Formas para Colisão
    private ShapeRenderer shapeRenderer;
    private Circle circuloNave, circuloAsteroide;

    //Atributos de Configurações
    private float larguraDispositivo;
    private float alturaDispositivo;
    private Random random;
    private float variacao = 0;
    private float tempoGeraAsteroide = 0;
    private float posicaoHorizontalNave;
    private float posicaoVerticalNave;
    private int pontuacao = 0;
    private int pontuacaoMaxima;

    //Objeto salvar Pontuação
    Preferences preferencias;

    //Parâmetros
    private int velocidadeNave = 5;
    private int estadoJogo = 0;

    //Exibição de Textos
    BitmapFont textoPontuacao;
    BitmapFont textoReiniciar;
    BitmapFont textoMelhorPontuacao;

    //Configuração dos Sons
    Sound somColisao;
    Music musicaInicial;
    Music musicaJogando;
    Music musicaFinal;

    @Override
    public void create() {
        inicializaTexturas();
        inicializaOjetos();
    }

    @Override
    public void render() {

        //LIMPAR FRAMES ANTERIORES
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        verificaEstadoJogo();
        desenharTexturas();
        detectarColisoes();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void inicializaTexturas() {

        nave = new Texture[3];
        nave[0] = new Texture("nave1.png");
        nave[1] = new Texture("nave2.png");
        nave[2] = new Texture("nave3.png");

        fundo = new Texture("fundo.png");
        gameOver = new Texture("game_over.png");
        telaInicial = new Texture("tela_inicial.png");
    }

    private void verificaEstadoJogo() {

             /* # - Estado do jogo
                0 - Jogo Inicial - Passaro parado
                1 - Começa o jogo
                2 - Colidiu - Perdeu!
            */
        boolean toqueTela = Gdx.input.justTouched();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        if (estadoJogo == 0) {
            /*Aplica o evento de toque na tela*/
            renderX = Gdx.graphics.getWidth() / 2 + nave[2].getWidth() / 2;
            renderY = Gdx.graphics.getHeight() / 2 + nave[2].getHeight() / 2;

            musicaInicial.play();
            musicaInicial.setLooping(true);

            if (toqueTela == true) {
                //Gdx.app.log("Toque","Toque na tela");
                estadoJogo = 1;
                musicaInicial.pause();
            }

        } else if (estadoJogo == 1) {
            // Tocar musica de fundo
            musicaJogando.play();
            musicaJogando.setLooping(true);

            // desenharTexturas();
            renderX += Gdx.input.getAccelerometerY() * velocidadeNave;
            renderY -= Gdx.input.getAccelerometerX() * velocidadeNave;

            if (renderX < 0) {
                renderX = 0;
            }
            if (renderX > Gdx.graphics.getWidth() - 200) {
                renderX = Gdx.graphics.getWidth() - 200;
            }
            if (renderY < 0) {
                renderY = 0;
            }
            if (renderY > Gdx.graphics.getHeight() - 200) {
                renderY = Gdx.graphics.getHeight() - 200;
            }

            geraAsteroides();

        } else if (estadoJogo == 2) {

            if (estadoJogo == 2) {
                musicaFinal.play();
            }

            if (pontuacao > pontuacaoMaxima) {
                pontuacaoMaxima = pontuacao;
                preferencias.putInteger("pontuacaoMaxima", pontuacaoMaxima);
            }
            //Reiniciar Partida
            if (toqueTela == true) {
                musicaFinal.pause();
                estadoJogo = 1;
                pontuacao = 0;
                musicaJogando.play();

                renderX = Gdx.graphics.getWidth() / 2 + nave[2].getWidth() / 2;
                renderY = Gdx.graphics.getHeight() / 2 + nave[2].getHeight() / 2;

                asteroides.clear();
            }
        }
    }

    private void inicializaOjetos() {
        batch = new SpriteBatch();
        random = new Random();

        nave[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoHorizontalNave = larguraDispositivo / 2;
        posicaoVerticalNave = alturaDispositivo / 2;

        //Formas para Colisões
        shapeRenderer = new ShapeRenderer();
        circuloNave = new Circle();
        circuloAsteroide = new Circle();

        //Configuração dos textos
        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(8);

        textoReiniciar = new BitmapFont();
        textoReiniciar.setColor(Color.GREEN);
        textoReiniciar.getData().setScale(5);

        textoMelhorPontuacao = new BitmapFont();
        textoMelhorPontuacao.setColor(Color.RED);
        textoMelhorPontuacao.getData().setScale(2);

        //Configura Preferências dos Objetos
        preferencias = Gdx.app.getPreferences("SpaceEscape");
        pontuacaoMaxima = preferencias.getInteger("pontuacaoMaxima");

        //Configuração dos sons
        somColisao = Gdx.audio.newSound(Gdx.files.internal("explosao.wav"));
        musicaInicial = Gdx.audio.newMusic(Gdx.files.internal("musicainicial.mp3"));
        musicaJogando = Gdx.audio.newMusic(Gdx.files.internal("musicajogando.mp3"));
        musicaFinal = Gdx.audio.newMusic(Gdx.files.internal("musicafinal.mp3"));

    }

    private void desenharTexturas() {

        batch.begin();
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(nave[(int) variacao], renderX, renderY, 150, 150);

        for (int i = 0; i < asteroides.size(); i++) {
            if (asteroides.get(i).getVida() == 0) {
                asteroides.get(i).setVisible(false);
                asteroides.get(i).setX(Gdx.graphics.getWidth());
                asteroides.get(i).setY(Gdx.graphics.getHeight());
            }
            if (asteroides.get(i).isVisible() == true) {
                batch.draw(asteroides.get(i).getAsteroide(), asteroides.get(i).getX(), asteroides.get(i).getY(), asteroides.get(i).getLargura(), asteroides.get(i).getAltura());
            }
        }

        //Desenha Pontuação
        if (estadoJogo == 0) {
            batch.draw(telaInicial, 0, 0, larguraDispositivo, alturaDispositivo);
            textoReiniciar.setColor(Color.RED);
            textoReiniciar.draw(batch, "Toque para Iniciar!", larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);
        }

        if (estadoJogo == 1) {
            textoPontuacao.draw(batch, String.valueOf(pontuacao), 100, alturaDispositivo - 150);
        }
        if (estadoJogo == 2) {
            textoPontuacao.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2 - 50, alturaDispositivo - 150);
            batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2 - gameOver.getHeight() / 2);
            textoReiniciar.setColor(Color.GREEN);
            textoReiniciar.draw(batch, "Toque para reiniciar!", larguraDispositivo / 2 - 300, alturaDispositivo / 2 - gameOver.getHeight() / 2);
            textoMelhorPontuacao.draw(batch, "Seu recorde é: " + pontuacaoMaxima + " pontos", larguraDispositivo / 2 - 160, 100);
        }

        batch.end();

        tempoGeraAsteroide += Gdx.graphics.getDeltaTime() * 40;
        if (tempoGeraAsteroide * 2 > 99) {
            tempoGeraAsteroide = 0;
        }

        variacao += Gdx.graphics.getDeltaTime() * 10;
        if (variacao > 3) {
            variacao = 0;
        }
    }

    private void geraAsteroides() {

        if (tempoGeraAsteroide >= 3 && tempoGeraAsteroide <= 3.2) {
            adicionaAsteroideDireita();
            pontuacao++;
        } else if (tempoGeraAsteroide >= 3.21 && tempoGeraAsteroide <= 3.5) {
            adicionaAsteroideEsquerda();
            pontuacao++;
        } else if (tempoGeraAsteroide >= 2 && tempoGeraAsteroide <= 2.2) {
            adicionaAsteroideCima();
            pontuacao++;
        } else if (tempoGeraAsteroide >= 2.21 && tempoGeraAsteroide <= 2.5) {
            adicionaAsteroideBaixo();
            pontuacao++;
        }
    }

    private void detectarColisoes() {

        //FORMAS PARA COLISAO
        Circle formaAsteroide;

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
                    if (estadoJogo == 1 && colidiuAsteroide1 == true) {
                        somColisao.play();
                    }
                    musicaJogando.pause();
                    estadoJogo = 2;
                    //tempAsteroide.stop();
                }
            }
        }

//      // DESENHANDO AS FORMAS GEOMETRICAS - para entender a colisao
//      shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//      shapeRenderer.setColor(Color.RED);
//      shapeRenderer.circle(renderX + 150/2,renderY +150/2,150/2);
//
//		for (int i = 0; i < asteroides.size(); i++) {
//			shapeRenderer.circle(asteroides.get(i).getX() + asteroides.get(i).getLargura()/2,
//					asteroides.get(i).getY() + asteroides.get(i).getAltura()/2,
//					asteroides.get(i).getAltura()/2);
//		}
//      shapeRenderer.end();
//
    }

    @Override

    public void pause() {

    }

    @Override

    public void resume() {

    }

    public void adicionaAsteroideEsquerda() {
        Asteroide addAsteroide = new Asteroide("asteroide1.png", -100, random.nextInt(Gdx.graphics.getHeight()));
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }

    public void adicionaAsteroideDireita() {
        Asteroide addAsteroide = new Asteroide("asteroide2.png", Gdx.graphics.getWidth() + 100, random.nextInt(Gdx.graphics.getHeight()));
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }

    public void adicionaAsteroideCima() {
        Asteroide addAsteroide = new Asteroide("asteroide2.png", random.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight() + 100);
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }

    public void adicionaAsteroideBaixo() {
        Asteroide addAsteroide = new Asteroide("asteroide2.png", random.nextInt(Gdx.graphics.getWidth()), -100);
        addAsteroide.start();
        asteroides.add(addAsteroide);
    }
}