package interno.jogo.Jumpman;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import interno.jogo.Jumpman.screens.MainMenu;
import interno.jogo.Jumpman.screens.Play;
import interno.jogo.Jumpman.screens.Pontuacao;
import interno.jogo.Jumpman.Plataforma;

public class Player  {
    // Atributos da classe Player
    private Sprite sprite;  // Representa a imagem do jogador
    private Vector2 position;  // Vetor que armazena a posição (x, y) do jogador
    private Vector2 velocity;  // Vetor para a velocidade (x, y) do jogador
    private float gravity = -250f;  // Força da gravidade aplicada ao jogador
    private boolean jumping = false;  // Indica se o jogador está pulando
    private boolean IsDead = false;  // Indica se o jogador "morreu"
    private boolean IsFliped = false;  // Indica se o jogador esta virado
    private float jumpVelocity = 350f, horizontaVelocity = 300f;  // Velocidades de pulo e movimento horizontal
    public int width = 64, height = 132;  // Tamanho do sprite do jogador

    // Construtor da classe Player
    public Player(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  // Inicializa o sprite com a textura passada como argumento
        this.position = new Vector2(x, y);  // Define a posição inicial do jogador
        this.velocity = new Vector2(0, 0);  // Define a velocidade inicial do jogador (parado)

        // Define o tamanho do sprite
        sprite.setSize(width, height);
        sprite.setPosition(position.x, position.y);  // Posiciona o sprite na tela com base na posição do jogador
    }

    // Método update: atualiza a lógica do jogador a cada frame
    public void update(float deltaTime, Array<Plataforma> plataformas) {
		System.out.println(velocity.y);
		
		// Lógica de movimentação para reposicionar o jogador se sair da tela
        float playerWidth = sprite.getWidth();
        if (position.x > (Gdx.graphics.getWidth() - playerWidth /2)) {
            position.x = -playerWidth /2;  // Reposiciona o jogador à esquerda quando sai pela direita
        } else if (position.x < (-playerWidth /2)) {
            position.x = Gdx.graphics.getWidth() - playerWidth /2;  // Reposiciona o jogador à direita quando sai pela esquerda
        }

        // Aplica a gravidade se o jogador estiver no ar
        if (position.y > 0 || velocity.y > 0) {
            velocity.y += gravity * deltaTime;  // Atualiza a velocidade vertical com base na gravidade
        }

        // Atualiza a posição do jogador com base na velocidade
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        // Verifica colisões com plataformas
        for (Plataforma plataforma : plataformas) {
            if (isOnPlatform(plataforma) && velocity.y < 0) {
                // O jogador pula automaticamente ao pousar em uma plataforma
                jump();
                break;
            }
        }

        // Impede o jogador de cair abaixo do "chão"
        if (position.y <= 0) {
            jumping = false;  // O jogador não está mais pulando
            IsDead = true;  // Marca o jogador como "morto"
        }
        
         // Impede o jogador de cair abaixo do "chão"
        if (position.y >= Gdx.graphics.getHeight() /1.75f) {
        	position.y = Gdx.graphics.getHeight() /1.75f;
        	// Ativa o movimento da plataforma para baixo na velocidade adequada
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(true);
                plataforma.setVel(velocity);

            }
        }
        // para o movimento se não estiver em posição
        else {
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(false);
            }
        }

        // Verifica se o jogador está na posição desejada

        // Atualiza o estado de movimento de todas as plataformas

        // Se o jogador está "morto", muda para a tela de pontuação
        if (IsDead == true) {
            sprite.getTexture().dispose();  // Libera os recursos da textura do jogador
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Pontuacao());  // Troca para a tela de pontuação
        }
        
        // Atualiza a posição do sprite com base na nova posição do jogador
        sprite.setPosition(position.x, position.y);
    }

    // Verifica se o jogador está em uma plataforma
    private boolean isOnPlatform(Plataforma plataforma) {
        return position.y > plataforma.getPosition().y  &&  // O jogador está acima da plataforma
               position.y - velocity.y * Gdx.graphics.getDeltaTime() <= plataforma.getPosition().y + plataforma.getSprite().getHeight() &&  // O jogador está pousando na plataforma
               position.x + sprite.getWidth() > plataforma.getPosition().x &&  // Verifica sobreposição no eixo X
               position.x < plataforma.getPosition().x + plataforma.getSprite().getWidth();  // Verifica sobreposição no eixo X
    }

    // Método que faz o jogador pular
    public void jump() {
        velocity.y = jumpVelocity;  // Define a velocidade vertical para pular
        jumping = true;  // Marca que o jogador está pulando
    }

    // Métodos getters para o sprite e a posição
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    // Lida com eventos de tecla pressionada
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());  // Vai para o menu principal
                break;
            case Keys.D:
            case Keys.RIGHT:
                if (IsFliped) {
                    sprite.flip(true, false); // Vira o sprite
                    IsFliped = false; // Atualiza a direção
                }
                velocity.x = horizontaVelocity; // Move o jogador para a direita                IsFliped = true;
                break;
            case Keys.A:
            case Keys.LEFT:
                if (!IsFliped) {
                    sprite.flip(true, false); // Vira o sprite
                    IsFliped = true; // Atualiza a direção
                }
                velocity.x = -horizontaVelocity;  // Move o jogador para a esquerda
                break;
            case Keys.W:  // Para testes: faz o jogador pular ao pressionar W
                jump();
                break;
            default:
                return false;
        }
        return true;
    }

    // Lida com eventos de tecla solta
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A || keycode == Keys.D || keycode == Keys.LEFT || keycode == Keys.RIGHT) {
            velocity.x = 0;  // Para a movimentação horizontal quando as teclas A ou D são soltas
        } else {
            return false;
        }
        return true;
    }
}
