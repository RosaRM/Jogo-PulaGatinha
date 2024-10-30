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

    // Método update: atualiza a lógica da plataforma a cada frame
    public void update(float deltaTime) {
    	 if (movimentoAtivo) {
             position.add(velocity.x * deltaTime, velocity.y * deltaTime);
         }
    	 else
    		 velocity.y = 0;
/*
        // Se a plataforma sair da parte inferior da tela (y < 0)
        if (position.y + sprite.getHeight() < 0) {
            // Reposiciona a plataforma no topo da tela em uma nova posição x aleatória
            
            position.y = Gdx.graphics.getHeight() * 1.4f + MathUtils.random(0, 15);  // Define uma posição x aleatória dentro da largura da tela
            position.x = MathUtils.random(0, Gdx.graphics.getWidth() - sprite.getWidth());  // Define uma posição x aleatória dentro da largura da tela
        }
*/
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
