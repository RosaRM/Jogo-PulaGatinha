package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Plataforma {
    // Atributos da classe Plataforma
    protected Sprite sprite;  // Sprite que representa a imagem da plataforma
    protected Vector2 position;  // Vetor que armazena a posi��o (x, y) da plataforma
    private Vector2 velocity;  // Vetor que armazena a posi��o (x, y) da plataforma
    protected boolean movimentoAtivo = false;  // Controla se o movimento est� ativado
    public int width = 65, height = 35;  // Tamanho do sprite do jogador


    // Construtor da classe Plataforma
    public Plataforma(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  // Inicializa o sprite com a textura passada como argumento
        this.position = new Vector2(x, y);  // Define a posi��o inicial da plataforma
        this.velocity = new Vector2(0, 0);
        // Posiciona o sprite na tela com base na posi��o definida
        sprite.setPosition(position.x, position.y);
        sprite.setSize(width, height);

    }

    
    // Verifica se o jogador est� em uma plataforma
    public boolean isOnPlatform(Player player) {
    	float playerFootLeft = 0;
		float playerFootRight = 0;
    	float playerBottom = player.position.y; // Posi��o atual do  p� do jogador
    	float playerPrevBottom = player.position.y - player.velocity.y * Gdx.graphics.getDeltaTime(); // Previs�o so p� do jogador
    	if (player.IsFliped) {
    		playerFootLeft = player.position.x + player.sprite.getWidth()- 25;
    		playerFootRight = player.position.x; }
		else {
    		playerFootLeft = player.position.x + player.sprite.getWidth();
    		 playerFootRight = player.position.x  + 25 ;    			
		}
    		if(playerPrevBottom <= getPosition().y + getSprite().getHeight() &&
    				playerBottom > getPosition().y && 
    				playerFootLeft > getPosition().x &&  // Verifica sobreposi��o no eixo X
    				playerFootRight  < getPosition().x + getSprite().getWidth() && player.velocity.y < 0 ) {// Verifica sobreposi��o no eixo X       
    	        
    			
    			// O jogador pula automaticamente ao pousar em uma plataforma
                player.jump();
                return true;
                }
    	return false;
    }
    
    // M�todo update: atualiza a l�gica da plataforma a cada frame
    public void update(float deltaTime) {
    	 if (movimentoAtivo) {
             position.add(velocity.x * deltaTime, velocity.y * deltaTime);
         }
    	 else
    		 velocity.y = 0;

            // Atualiza a posi��o do sprite para a nova posi��o calculada
            sprite.setPosition(position.x, position.y);

    
   }
    
    public void ativarMovimento(boolean ativo) {
        this.movimentoAtivo = ativo;
    }

    // M�todos getters para o sprite e a posi��o
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
	public void setVel(Vector2 velocity) {
		this.velocity.y = -velocity.y;
	}
    public Vector2 getVel() {
        return velocity;
    }
}
