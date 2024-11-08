package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatVertical extends Plataforma  {
	 private Vector2 direction;  // Dire��o do movimento
	    private float speed;        // Velocidade do movimento
	    private float distance;     // Dist�ncia m�xima que a plataforma deve percorrer
	    private float originalY;    // Posi��o original Y da plataforma
		private float fall;

	    // Construtor da classe PlataformaMovel
	    public PlatVertical(Texture texture, float x, float y) {
	        super(texture, x, y);  // Chama o construtor da classe base Plataforma
	        this.direction = new Vector2(0, 1);  // Dire��o padr�o (para cima)
	        this.speed = 175f;        // Velocidade padr�o
	        this.distance = 50;     // Dist�ncia padr�o
	        this.originalY = y;
	        this.fall = 0;// Armazena a posi��o Y original para refer�ncia
	    }

	    // M�todo update: atualiza a l�gica da plataforma m�vel
	    @Override
	    public void update(float deltaTime) {
	    	if (movimentoAtivo) {
	            // Atualiza a posi��o da plataforma com base na dire��o e na velocidade
	            position.add(0, direction.y * deltaTime * speed + fall * deltaTime);
		        this.originalY += fall * deltaTime;

	    	}
	    	else {
	            // Atualiza a posi��o da plataforma com base na dire��o e na velocidade
	            position.add(0, direction.y * deltaTime * speed);
	    	}
	            // Verifica se a plataforma ultrapassou a dist�ncia m�xima
	            if (Math.abs(position.y - originalY) >= distance -1) {
	                // Inverte a dire��o do movimento
	                direction.scl(-1);
	                
	            }
	        

	        // Atualiza a posi��o do sprite
	        sprite.setPosition(position.x, position.y);
	    }
		public void setVel(Vector2 velocity) {
			this.fall = -velocity.y;
		}
	    public Vector2 getVel() {
	        return direction;
	    }
	}

