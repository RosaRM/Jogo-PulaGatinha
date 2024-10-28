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
    private Stage stage;  // Gerencia atores (botões, labels, etc.)
    private TextureAtlas atlas;  // Armazena as imagens dos botões
    private Skin skin;  // Aplica o estilo do botão
    private Table table;  // Organiza visualmente os elementos da interface
    private TextButton buttonPlay, buttonExit, buttonHistoria;  // Botões do menu
    private BitmapFont white, black;  // Fontes utilizadas no texto dos botões
    private Label heading;  // Título do menu
    private Viewport viewport;  // Gerencia as dimensões da tela
    private Sprite bg;  // Imagem de fundo do menu

    @Override
    public void show() {
        // Inicialização dos elementos visuais
        batch = new SpriteBatch();  // Inicializa o batch para desenhar os sprites
        Texture bgTexture = new Texture(Gdx.files.internal("img/Rafundo.jpeg"));  // Carrega a imagem de fundo
        bg = new Sprite(bgTexture);  // Cria o sprite para a imagem de fundo
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Ajusta o tamanho do fundo para preencher a tela

        // Obtém a largura e a altura da tela
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Cria o viewport para ajustar o tamanho da tela ao Stage
        viewport = new StretchViewport(width, height);

        stage = new Stage(viewport);  // Inicializa o stage com o viewport
        Gdx.input.setInputProcessor(stage);  // Define o stage como processador de entrada (para interação com botões)

        atlas = new TextureAtlas("ui/button.pack");  // Carrega as imagens dos botões a partir de um atlas de texturas
        skin = new Skin(atlas);  // Associa o atlas ao skin

        table = new Table();  // Cria a tabela para organizar os elementos
        table.top();  // Posiciona a tabela no topo
        table.setFillParent(true);  // Faz a tabela preencher todo o stage

        // Carregando fontes para os textos
        white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);  // Fonte branca
        black = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);  // Fonte preta

        // Criando e configurando os botões
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("btn-up");  // Estado normal do botão
        textButtonStyle.down = skin.getDrawable("btn-down");  // Estado pressionado do botão
        textButtonStyle.pressedOffsetX = 1;  // Ajuste visual ao pressionar (X)
        textButtonStyle.pressedOffsetY = -1;  // Ajuste visual ao pressionar (Y)
        textButtonStyle.font = black;  // Define a fonte preta para o texto do botão

        // Botão EXIT
        buttonExit = new TextButton("EXIT", textButtonStyle);  // Cria o botão de sair
        buttonExit.addListener(new ClickListener() {  // Adiciona um listener para capturar o clique
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Encerra o jogo ao clicar
            }
        });
        buttonExit.pad(29);  // Adiciona espaçamento interno ao botão

        // Botão HISTÓRIA
        buttonHistoria = new TextButton("HISTÓRIA", textButtonStyle);  // Cria o botão da história
        buttonHistoria.addListener(new ClickListener() {  // Listener para mudar de tela
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Historia());  // Troca para a tela de História
            }
        });
        buttonHistoria.pad(25);  // Adiciona espaçamento interno ao botão

        // Botão PLAY
        buttonPlay = new TextButton("PLAY", textButtonStyle);  // Cria o botão de jogar
        buttonPlay.addListener(new ClickListener() {  // Listener para iniciar o jogo
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());  // Troca para a tela de jogo
            }
        });
        buttonPlay.pad(25);  // Adiciona espaçamento interno ao botão

        // Configurando o título do menu
        heading = new Label(JumpMain.TITULO, new LabelStyle(white, Color.WHITE));  // Cria o título com fonte branca
        heading.setFontScale(2f);  // Define o tamanho do título

        // Organizando os elementos dentro da tabela
        table.add(heading).expandX().top().padTop(20);  // Adiciona o título no topo da tela
        table.getCell(heading).spaceBottom(450);  // Espaçamento abaixo do título
        table.row();  // Próxima linha na tabela
        table.add(buttonPlay).uniform().spaceBottom(15);  // Adiciona o botão PLAY com espaçamento uniforme
        table.row();  // Próxima linha
        table.add(buttonHistoria).uniform().spaceBottom(15);  // Adiciona o botão HISTÓRIA
        table.row();  // Próxima linha
        table.add(buttonExit).uniform().spaceBottom(150);  // Adiciona o botão EXIT

        // Adiciona a tabela ao stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com cor preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Começa a desenhar o fundo e os elementos
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
