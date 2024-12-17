package interno.jogo.Jumpman.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {
    
    public static final int ALPHA = 0; // Tipo de tween para opacidade
    public static final int Y_POSITION = 1; // Tipo de tween para posição Y

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a; // Obtém a opacidade atual do sprite
                return 1; // Retorna 1 valor
            case Y_POSITION:
                returnValues[0] = target.getY(); // Obtém a posição Y atual do sprite
                return 1; // Retorna 1 valor
            default:
                assert false; // Indica um erro se tweenType não for reconhecido
                return -1; // Indica um erro
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                // Define a nova opacidade do sprite
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            case Y_POSITION:
                // Define a nova posição Y do sprite
                target.setY(newValues[0]);
                break;
            default:
                assert false; // Indica um erro se tweenType não for reconhecido
        }
    }
}