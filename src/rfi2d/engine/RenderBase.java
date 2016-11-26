/**
 * 
 */
package rfi2d.engine;


/**
 * @author allen
 *
 */
public abstract class RenderBase implements RenderInterface{

	protected final GameBase m_Game;
	
	public RenderBase(final GameBase game) {
		m_Game = game;
	}

	
}
