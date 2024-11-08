package interno.jogo.Jumpman.entities;

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


public class Player  {
    // Atributos da classe Player
    protected Sprite sprite;  // Representa a imagem do jogador
    protected Vector2 position;  // Vetor que armazena a posi��o (x, y) do jogador
    protected Vector2 velocity;  // Vetor para a velocidade (x, y) do jogador
    private float gravity;  // For�a da gravidade aplicada ao jogador
    private boolean IsJumping = false;  // Indica se o jogador est� pulando
    private boolean IsDead = false;  // Indica se o jogador "morreu"
    private boolean IsRising = false;  // Indica se o jogador "morreu"
    protected boolean IsFliped = false;  // Indica se o jogador esta virado
    private float jumpVelocity = 650f, horizontaVelocity = 275f;  // Velocidades de pulo e movimento horizontal
    public int width = 64, height = 132;  // Tamanho do sprite do jogador

    // Construtor da classe Player
    public Player(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  // Inicializa o sprite com a textura passada como argumento
        this.position = new Vector2(x, y);  // Define a posi��o inicial do jogador
        this.velocity = new Vector2(0, 0);  // Define a velocidade inicial do jogador (parado)

        // Define o tamanho do sprite
        sprite.setSize(width, height);
        sprite.setPosition(position.x, position.y);  // Posiciona o sprite na tela com base na posi��o do jogador
    }

    /*
    // Verifica se o jogador est� em uma plataforma
    private boolean isOnPlatform(Plataforma plataforma) {
    	float playerBottom = position.y; // Posi��o atual do  p� do jogador
    	float playerPrevBottom = position.y - velocity.y * Gdx.graphics.getDeltaTime(); // Previs�o so p� do jogador
    	if (!IsFliped) {
    		return playerPrevBottom <= plataforma.getPosition().y + plataforma.getSprite().getHeight() &&
    				playerBottom > plataforma.getPosition().y && 
    				position.x + sprite.getWidth()> plataforma.getPosition().x &&  // Verifica sobreposi��o no eixo X
    				position.x  + 25  < plataforma.getPosition().x + plataforma.getSprite().getWidth();  // Verifica sobreposi��o no eixo X       
    	}
    	else {
    		return playerPrevBottom <= plataforma.getPosition().y + plataforma.getSprite().getHeight() && // Check if the player was above the platform
    				playerBottom > plataforma.getPosition().y && //
    				position.x + sprite.getWidth() - 25 > plataforma.getPosition().x &&  // Verifica sobreposi��o no eixo X
    				position.x   < plataforma.getPosition().x + plataforma.getSprite().getWidth();  // Verifica sobreposi��o no eixo X       
    	}
    }
    */

    // M�todo update: atualiza a l�gica do jogador a cada frame
    public void update(float deltaTime, Array<Plataforma> plataformas) {
        System.out.println();	
        // Verifica colis�es com plataformas
        for (Plataforma plataforma : plataformas) {
            if (plataforma.isOnPlatform(this) ) {
            	
                // O jogador pula automaticamente ao pousar em uma plataforma
                //jump();
                break;
            }
        }
        
		// L�gica de movimenta��o para reposicionar o jogador se sair da tela
        float playerWidth = sprite.getWidth();
        if (position.x > (Gdx.graphics.getWidth() - playerWidth /2)) {
            position.x = -playerWidth /2;  // Reposiciona o jogador � esquerda quando sai pela direita
        } else if (position.x < (-playerWidth /2)) {
            position.x = Gdx.graphics.getWidth() - playerWidth /2;  // Reposiciona o jogador � direita quando sai pela esquerda
        }

        // Aplica a gravidade se o jogador estiver no ar
        if (position.y > 0 || velocity.y > 0) {
            velocity.y += gravity * deltaTime;  // Atualiza a velocidade vertical com base na dade
        }
        if (velocity.y < -900) {
        	velocity.y = -900;
        }
        // Aplica a gravidade se o jogador estiver no ar
        if (velocity.y < 0) {

        	gravity = -750 * 1.6f;
        }else
        	gravity = -750;
        
        // Atualiza a posi��o do jogador com base na velocidade
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);



        // Impede o jogador de cair abaixo do "ch�o"
        if (position.y <= 0) {
            IsJumping = false;  // O jogador n�o est� mais pulando
            IsDead = true;  // Marca o jogador como "morto"
        }
        
         // Impede o jogador de cair abaixo do "ch�o"
        if (position.y >= Gdx.graphics.getHeight() /1.75f) {
        	position.y = Gdx.graphics.getHeight() /1.75f;
        	IsRising = true;
        	// Ativa o movimento da plataforma para baixo na velocidade adequada
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(true);
                plataforma.setVel(velocity);
            }

        }
        // para o movimento se n�o estiver em posi��o
        else {
        	IsRising = false;
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(false);
            }
        }

        // Verifica se o jogador est� na posi��o desejada

        // Atualiza o estado de movimento de todas as plataformas

        // Se o jogador est� "morto", muda para a tela de pontua��o
        if (IsDead == true) {
            sprite.getTexture().dispose();  // Libera os recursos da textura do jogador
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Pontuacao());  // Troca para a tela de pontua��o
        }
        
        // Atualiza a posi��o do sprite com base na nova posi��o do jogador
        sprite.setPosition(position.x, position.y);
    }

    

  

    // M�todo que faz o jogador pular
    public void jump() {
        velocity.y = jumpVelocity;  // Define a velocidade vertical para pular
        IsJumping = true;  // Marca que o jogador est� pulando
    }
    
    public boolean isRising() {
        return IsRising;
    }

    // M�todos getters para o sprite e a posi��o
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
                    IsFliped = false; // Atualiza a dire��o

                }
                velocity.x = horizontaVelocity; // Move o jogador para a direita                
                break;
            case Keys.A:
            case Keys.LEFT:
                if (!IsFliped) {
                    sprite.flip(true, false); // Vira o sprite
                    IsFliped = true; // Atualiza a dire��o
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
    	if (keycode != 0) {
        if (keycode == Keys.A || keycode == Keys.D || keycode == Keys.LEFT || keycode == Keys.RIGHT) {
            velocity.x = 0;  // Para a movimenta��o horizontal quando as teclas A ou D s�o soltas
        } else {
            return false;
        }
        }
        return true;
    }
}
