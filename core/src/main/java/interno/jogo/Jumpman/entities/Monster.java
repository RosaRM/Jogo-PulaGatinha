package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster {
	// Atributos da classe Monstro
    private float Countdown = 0;
    protected Sprite sprite;  
    protected Vector2 position; 
    protected boolean movimentoAtivo = false;  
    public boolean IsDead = false;  
    public int width = 100, height = 64;  
    private Vector2 direction;  
    private float speed;       
    private float distance;     // Dist�ncia m�xima que a plataforma deve percorrer
    private float originalX;  
    private Sound moan = Gdx.audio.newSound(Gdx.files.internal("sounds/mean.wav"));

    // Construtor da classe Plataforma
    public Monster(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  
        this.position = new Vector2(x, y);  
        this.direction = new Vector2(1, 0);
        this.speed = MathUtils.random(150f, 200f);        
    	distance = MathUtils.random(75f,150f);
        this.originalX = x;
        sprite.setPosition(position.x, position.y);
        sprite.setSize(width, height);

    }

    
    
    public void update(float deltaTime) {
    	Countdown += deltaTime;
    	float distanceL = distance/2;
    	float distanceR = distanceL *-1;
    	if (movimentoAtivo) {
            // Atualiza a posi��o da plataforma com base na dire��o e na velocidade
            position.add(direction.x * speed * deltaTime, direction.y * deltaTime);
    	}
    	else {
            position.add(direction.x * speed * deltaTime, 0);
    	}
            // Verifica se a plataforma ultrapassou a dist�ncia m�xima
        if (Math.abs(position.x - originalX) >= distanceL && direction.x == 1) {
        		direction.scl(-1);
        }
        else if ((Math.abs(position.x - originalX))*-1 <= distanceR && direction.x == -1) {
          
        		direction.scl(-1);
        }
            sprite.setPosition(position.x, position.y);
        // Faz o som do morcego após o tempo
        if (position.y < Gdx.graphics.getHeight() *0.9f && Countdown > 5 && position.y > 0 ) {
        	moan.play();
        	Countdown = 0;
        	}
        
    }
    
    
    //Verifica se o jogador esta na mesma posição do inimigo
    public boolean isOnEnemy(Player player) {
        float playerFootLeft = 0;
        float playerFootRight = 0;
        float playerBottom = player.position.y; 
        float playerPrevBottom = player.position.y - player.velocity.y * Gdx.graphics.getDeltaTime(); // Previsão da posição anterior dos pés do jogador
        
        if (player.IsFliped) {
            playerFootLeft = player.position.x + player.sprite.getWidth() - 25;
            playerFootRight = player.position.x;
        } else {
            playerFootLeft = player.position.x + player.sprite.getWidth();
            playerFootRight = player.position.x + 25;
        }
        //Verifica se o jogador pisa no inimigo
        boolean isTouchingFromTop = 
            playerPrevBottom <= getPosition().y + getSprite().getHeight() &&
            playerBottom > getPosition().y &&
            playerFootLeft > getPosition().x &&
            playerFootRight < getPosition().x + getSprite().getWidth() &&
            player.IsFalling;
        
        if (isTouchingFromTop) {
        	IsDead = true;
            player.jump();
            return true;
        }
        
        //Verifica se o jogador morre pro inimigo
        boolean isTouchingOther = 
            player.position.x + player.sprite.getWidth() > getPosition().x &&
            player.position.x < getPosition().x + getSprite().getWidth() &&
            player.position.y + player.sprite.getHeight() > getPosition().y &&
            player.position.y + 10 < getPosition().y + getSprite().getHeight()/1.5f;
        
        if (isTouchingOther) {
            player.IsDead = true;
        }
        
        return false;
    }

    
    public void ativarMovimento(boolean ativo) {
        this.movimentoAtivo = ativo;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
    
	public void setVel(Vector2 velocity) {
		this.direction.y = -velocity.y;
	}
	
    public Vector2 getVel() {
        return direction;
    }
}
