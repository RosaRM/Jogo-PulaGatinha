package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatVertical extends Plataforma  {
	 private Vector2 direction;  // Direção do movimento
	    private float speed;        // Velocidade do movimento
	    private float distance;     // Distância máxima que a plataforma deve percorrer
	    private float originalY;    // Posição original Y da plataforma
		private float fall;

	    // Construtor da classe PlataformaMovel
	    public PlatVertical(Texture texture, float x, float y) {
	        super(texture, x, y);  // Chama o construtor da classe base Plataforma
	        this.direction = new Vector2(0, 1);  // Direção padrão (para cima)
	        this.speed = 175f;        // Velocidade padrão
	        this.distance = 50;     // Distância padrão
	        this.originalY = y;
	        this.fall = 0;// Armazena a posição Y original para referência
	    }

	    // Método update: atualiza a lógica da plataforma móvel
	    @Override
	    public void update(float deltaTime) {
	    	if (movimentoAtivo) {
	            // Atualiza a posição da plataforma com base na direção e na velocidade
	            position.add(0, direction.y * deltaTime * speed + fall * deltaTime);
		        this.originalY += fall * deltaTime;

	    	}
	    	else {
	            // Atualiza a posição da plataforma com base na direção e na velocidade
	            position.add(0, direction.y * deltaTime * speed);
	    	}
	            // Verifica se a plataforma ultrapassou a distância máxima
	            if (Math.abs(position.y - originalY) >= distance -1) {
	                // Inverte a direção do movimento
	                direction.scl(-1);
	                
	            }
	        

	        // Atualiza a posição do sprite
	        sprite.setPosition(position.x, position.y);
	    }
		public void setVel(Vector2 velocity) {
			this.fall = -velocity.y;
		}
	    public Vector2 getVel() {
	        return direction;
	    }
	}

