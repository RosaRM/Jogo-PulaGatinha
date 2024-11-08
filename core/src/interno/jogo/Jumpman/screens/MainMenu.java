package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import interno.jogo.Jumpman.JumpMain;

public class MainMenu implements Screen {

    // Atributos principais da classe MainMenu
    private SpriteBatch batch;  // Para desenhar imagens na tela
    private Stage stage;  // Gerencia atores (bot�es, labels, etc.)
    private TextureAtlas atlas;  // Armazena as imagens dos bot�es
    private Skin skin;  // Aplica o estilo do bot�o
    private Table table;  // Organiza visualmente os elementos da interface
    private TextButton buttonPlay, buttonExit, buttonHistoria;  // Bot�es do menu
    private BitmapFont white, black;  // Fontes utilizadas no texto dos bot�es
    private Label heading;  // T�tulo do menu
    private Viewport viewport;  // Gerencia as dimens�es da tela
    private Sprite bg;  // Imagem de fundo do menu

    @Override
    public void show() {
        // Inicializa��o dos elementos visuais
        batch = new SpriteBatch();  // Inicializa o batch para desenhar os sprites
        Texture bgTexture = new Texture(Gdx.files.internal("img/Rafundo.jpeg"));  // Carrega a imagem de fundo
        bg = new Sprite(bgTexture);  // Cria o sprite para a imagem de fundo
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Ajusta o tamanho do fundo para preencher a tela

        // Obt�m a largura e a altura da tela
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Cria o viewport para ajustar o tamanho da tela ao Stage
        viewport = new StretchViewport(width, height);

        stage = new Stage(viewport);  // Inicializa o stage com o viewport
        Gdx.input.setInputProcessor(stage);  // Define o stage como processador de entrada (para intera��o com bot�es)

        atlas = new TextureAtlas("ui/button.pack");  // Carrega as imagens dos bot�es a partir de um atlas de texturas
        skin = new Skin(atlas);  // Associa o atlas ao skin

        table = new Table();  // Cria a tabela para organizar os elementos
        table.top();  // Posiciona a tabela no topo
        table.setFillParent(true);  // Faz a tabela preencher todo o stage

        // Carregando fontes para os textos
        white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);  // Fonte branca
        black = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);  // Fonte preta

        // Criando e configurando os bot�es
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("btn-up");  // Estado normal do bot�o
        textButtonStyle.down = skin.getDrawable("btn-down");  // Estado pressionado do bot�o
        textButtonStyle.pressedOffsetX = 1;  // Ajuste visual ao pressionar (X)
        textButtonStyle.pressedOffsetY = -1;  // Ajuste visual ao pressionar (Y)
        textButtonStyle.font = black;  // Define a fonte preta para o texto do bot�o

        // Bot�o EXIT
        buttonExit = new TextButton("EXIT", textButtonStyle);  // Cria o bot�o de sair
        buttonExit.addListener(new ClickListener() {  // Adiciona um listener para capturar o clique
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Encerra o jogo ao clicar
            }
        });
        buttonExit.pad(29);  // Adiciona espa�amento interno ao bot�o

        // Bot�o HIST�RIA
        buttonHistoria = new TextButton("HIST�RIA", textButtonStyle);  // Cria o bot�o da hist�ria
        buttonHistoria.addListener(new ClickListener() {  // Listener para mudar de tela
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Historia());  // Troca para a tela de Hist�ria
            }
        });
        buttonHistoria.pad(25);  // Adiciona espa�amento interno ao bot�o

        // Bot�o PLAY
        buttonPlay = new TextButton("PLAY", textButtonStyle);  // Cria o bot�o de jogar
        buttonPlay.addListener(new ClickListener() {  // Listener para iniciar o jogo
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());  // Troca para a tela de jogo
            }
        });
        buttonPlay.pad(25);  // Adiciona espa�amento interno ao bot�o

        // Configurando o t�tulo do menu
        heading = new Label(JumpMain.TITULO, new LabelStyle(white, Color.WHITE));  // Cria o t�tulo com fonte branca
        heading.setFontScale(2f);  // Define o tamanho do t�tulo

        // Organizando os elementos dentro da tabela
        table.add(heading).expandX().top().padTop(20);  // Adiciona o t�tulo no topo da tela
        table.getCell(heading).spaceBottom(450);  // Espa�amento abaixo do t�tulo
        table.row();  // Pr�xima linha na tabela
        table.add(buttonPlay).uniform().spaceBottom(15);  // Adiciona o bot�o PLAY com espa�amento uniforme
        table.row();  // Pr�xima linha
        table.add(buttonHistoria).uniform().spaceBottom(15);  // Adiciona o bot�o HIST�RIA
        table.row();  // Pr�xima linha
        table.add(buttonExit).uniform().spaceBottom(150);  // Adiciona o bot�o EXIT

        // Adiciona a tabela ao stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com cor preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Come�a a desenhar o fundo e os elementos
        batch.begin();
        bg.draw(batch);  // Desenha o fundo
        batch.end();

        stage.act(delta);  // Atualiza o stage (elementos interativos)
        stage.draw();  // Desenha o stage
    }

    @Override
    public void resize(int width, int height) {
        // Atualiza o viewport se a tela for redimensionada
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Libera os recursos ao encerrar a tela
        batch.dispose();
        bg.getTexture().dispose();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
