package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import interno.jogo.Jumpman.tween.SpriteAccessor;

public class Pontuacao implements Screen {

    private SpriteBatch batch; // SpriteBatch para renderizar os elementos gráficos
    private Stage stage; // Stage para gerenciar os elementos da interface do usuário
    private TextureAtlas atlas; // Atlas de texturas para os botões
    private Skin skin; // Skin para o estilo dos botões
    private Table table; // Tabela para organizar os elementos na tela
    private ImageButton buttonReplay, buttonReturn; // Botões de replay e retorno
    private BitmapFont white, black; // Fontes para renderizar texto
    private Texture playTexture, playPressedTexture, exitTexture, exitPressedTexture; // Texturas dos botões
    private Label heading; // Label para exibir o texto de pontuação
    private Viewport viewport; // Viewport para ajustar a tela à resolução do dispositivo
    private Sprite bg; // Sprite de fundo
    private TweenManager tweenManager; // Gerenciador de animações
    private Music lose; // Música de fundo

    @Override
    public void show() {
        // Inicializa a música de fundo
        lose = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.wav"));
        lose.setVolume(0.45f);
        lose.setLooping(true);  
        lose.play();  // Começa a música de fundo

        // Inicializa o SpriteBatch e o TweenManager
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor()); // Registra o acessador para animações do Sprite

        // Carrega a textura de fundo
        Texture bgTexture = new Texture(Gdx.files.internal("img/Score.png"));
        bg = new Sprite(bgTexture);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Ajusta o tamanho do fundo
        bg.setPosition(0, -Gdx.graphics.getHeight()); // Começa a animação fora da tela

        // Cria o Viewport e o Stage para a interface
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        viewport = new StretchViewport(width, height); // Adapta a tela ao tamanho da janela
        stage = new Stage(viewport); // Cria o Stage com o viewport configurado
        Gdx.input.setInputProcessor(stage); // Define o stage como processador de entrada

        // Carrega o atlas de texturas e a skin para os botões
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        // Inicializa a tabela que organiza os elementos da interface
        table = new Table();
        table.setFillParent(true); // A tabela ocupa toda a tela

        // Carrega as fontes em bitmap para os textos
        white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);

        // Carrega as texturas dos botões de Play e Exit
        playTexture = new Texture("img/Bplay.png");  // Botão Play
        playPressedTexture = new Texture("img/BHplay.png");  // Botão Play pressionado
        exitTexture = new Texture("img/Bexit.png");  // Botão Exit
        exitPressedTexture = new Texture("img/BHexit.png");  // Botão Exit pressionado

        // Configura os estilos dos botões com as texturas carregadas
        ImageButtonStyle playButtonStyle = new ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion(playTexture)); // Estado normal
        playButtonStyle.down = new TextureRegionDrawable(new TextureRegion(playPressedTexture)); // Estado pressionado

        ImageButtonStyle exitButtonStyle = new ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(exitTexture)); // Estado normal
        exitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(exitPressedTexture)); // Estado pressionado

        // Criação do botão "MENU"
        buttonReturn = new ImageButton(exitButtonStyle);
        buttonReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu.click.play(); // Som ao clicar
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu()); // Muda para a tela do menu principal
            }
        });
        buttonReturn.pad(29); // Adiciona padding ao botão

        // Criação do botão "PLAY AGAIN"
        buttonReplay = new ImageButton(playButtonStyle);
        buttonReplay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu.click.play(); // Som ao clicar
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play()); // Reinicia o jogo
            }
        });
        buttonReplay.pad(25); // Adiciona padding ao botão

        // Criação do Label para exibir a pontuação
        heading = new Label("Score: " + Play.score, new LabelStyle(black, Color.BLACK));
        heading.setFontScale(1.3f); // Aumenta o tamanho da fonte

        // Limpa a tabela antes de adicionar novos elementos
        table.clear();  

        // Organiza os itens no topo da tela
        table.top(); // Posiciona o conteúdo no topo
        table.add(heading).padBottom(450).padLeft(width / 2); // Adiciona a pontuação no topo
        table.row(); // Nova linha para separar os itens

        // Organiza os botões na parte inferior da tela
        table.bottom(); // Posiciona os itens na parte inferior
        table.row().padBottom(20); // Adiciona espaçamento inferior entre os botões

        // Adiciona os botões na tabela
        table.add(buttonReplay).left().padLeft(200); // Botão de replay
        table.add(buttonReturn).right().padRight(200); // Botão de retorno ao menu

        // Adiciona a tabela ao stage
        stage.addActor(table);
        table.setVisible(false); // Oculta a tabela inicialmente

        // Inicia a animação de subida do fundo
        Tween.to(bg, SpriteAccessor.Y_POSITION, 2) // Move o sprite para a posição 0 (visível)
            .target(0)
            .setCallbackTriggers(TweenCallback.COMPLETE) // Chama a callback quando a animação termina
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    if (type == TweenCallback.COMPLETE) {
                        table.setVisible(true); // Torna a tabela visível após a animação
                    }
                }
            })
            .start(tweenManager); // Inicia a animação
    }

    @Override
    public void render(float delta) {
        // Limpa a tela e define a cor de fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Atualiza o TweenManager para animar elementos
        tweenManager.update(delta);

        // Começa a desenhar o fundo
        batch.begin();
        bg.draw(batch);
        batch.end();

        // Atualiza e desenha o stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Atualiza o Viewport com as novas dimensões
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        // Libera os recursos da música quando a tela for escondida
        lose.dispose();
    }

    @Override
    public void dispose() {
        // Libera todos os recursos utilizados
        batch.dispose();
        bg.getTexture().dispose();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        lose.dispose();
    }
}
