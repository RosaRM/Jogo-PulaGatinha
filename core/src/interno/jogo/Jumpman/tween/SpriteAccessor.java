package interno.jogo.Jumpman.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite>{
	
	public static final int ALPHA = 0;
	
	public int getValues(Sprite target, int tweetType, float[] returnValues) {
		switch(tweetType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
			default:
				assert false;
				return -1;
		}
	}
	
	@Override
	public void setValues(Sprite target, int tweetType, float[] newValues) {
		switch(tweetType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
	}

}
