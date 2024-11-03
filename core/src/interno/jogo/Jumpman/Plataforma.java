package interno.jogo.Jumpman;

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
    private Sprite sprite;  // Sprite que representa a imagem da plataforma
    private Vector2 position;  // Vetor que armazena a posição (x, y) da plataforma
    private Vector2 velocity;  // Vetor que armazena a posição (x, y) da plataforma
    private boolean movimentoAtivo = false;  // Controla se o movimento está ativado


    // Construtor da classe Plataforma
    public Plataforma(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  // Inicializa o sprite com a textura passada como argumento
        this.position = new Vector2(x, y);  // Define a posição inicial da plataforma
        this.velocity = new Vector2(0, 0);
        // Posiciona o sprite na tela com base na posição definida
        sprite.setPosition(position.x, position.y);
    }

    
    // Verifica se o jogador está em uma plataforma
    public boolean isOnPlatform(Player player) {
    	float playerBottom = player.position.y; // Posição atual do  pé do jogador
    	float playerPrevBottom = player.position.y - player.velocity.y * Gdx.graphics.getDeltaTime(); // Previsão so pé do jogador
    	if (!player.IsFliped) {
    		if(playerPrevBottom <= getPosition().y + getSprite().getHeight() &&
    				playerBottom > getPosition().y && 
    				player.position.x + player.sprite.getWidth()- 25> getPosition().x &&  // Verifica sobreposição no eixo X
    				player.position.x   < getPosition().x + getSprite().getWidth() && player.velocity.y < 0 ) {// Verifica sobreposição no eixo X       
                // O jogador pula automaticamente ao pousar em uma plataforma
                player.jump();
                return true;
                }
    	}
    	else {
    		if( playerPrevBottom <= getPosition().y + getSprite().getHeight() && // Check if the player was above the platform
    				playerBottom > getPosition().y && //
    				player.position.x + player.sprite.getWidth()  > getPosition().x &&  // Verifica sobreposição no eixo X
    				player.position.x  + 25  < getPosition().x + getSprite().getWidth() && player.velocity.y < 0 ) { // Verifica sobreposição no eixo X       
                
    			player.jump();
                return true;

    		}
    	}
    	return false;
    }
    
    // Método update: atualiza a lógica da plataforma a cada frame
    public void update(float deltaTime) {
    	 if (movimentoAtivo) {
             position.add(velocity.x * deltaTime, velocity.y * deltaTime);
         }
    	 else
    		 velocity.y = 0;

            // Atualiza a posição do sprite para a nova posição calculada
            sprite.setPosition(position.x, position.y);

    
   }
    
    public void ativarMovimento(boolean ativo) {
        this.movimentoAtivo = ativo;
    }

    // Métodos getters para o sprite e a posição
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
