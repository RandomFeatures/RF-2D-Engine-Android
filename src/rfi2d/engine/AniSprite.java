package rfi2d.engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import rfi2d.engine.types.AniType;

public class AniSprite {
	private float m_ElapsedTime;
	private final TextureRegion[] m_KeyFrames;
	private final float m_FrameDuration;
	private int m_CurrentFrame = -1;
	private AniType m_AniType = AniType.FORWARD;
	private boolean m_Forward = true;
	private boolean m_Playing = false;
	private boolean m_Visible = true;
	private boolean m_Flipped = false;
	/**
	 * Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration
	 *            the time between frames in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames.
	 */
	public AniSprite(float frameDuration, AniType anitype, TextureRegion... keyFrames) {
		this.m_FrameDuration = frameDuration;
		this.m_KeyFrames = keyFrames;
		this.m_AniType = anitype;
	}

	public int getCurrentFrame() {
		return m_CurrentFrame;
	}

	public AniSprite setFrame(int value) {
		if(value <=  m_KeyFrames.length - 1)
			m_CurrentFrame = value;
		return this;
	}

	public AniSprite play(int from){
		m_Playing = true;
		m_Visible = true;
		setFrame(from);
		return this;
	}
	
	public AniSprite stop(){
		m_Playing = false;
		return this;
	}

	public AniSprite hide(){
		m_Visible = false;
		return this;
	}
	public AniSprite show(){
		m_Visible = false;
		return this;
	}
	public AniSprite flip(boolean x, boolean y){
		m_Flipped =  x || y;
		for(TextureRegion reg: m_KeyFrames)
			reg.flip(x, y);
		return this;
	}
	
	public boolean isFlipped(){
		return m_Flipped;
	}
	
	public AniSprite setAniType(AniType value)
	{
		this.m_AniType = value;
		return this;
	}
	
	public TextureRegion getFrame() {
		return m_KeyFrames[m_CurrentFrame];
	}

	public void update(float deltaTime) {
		if (!m_Playing) return;
		
		m_ElapsedTime += deltaTime;
		
		if (m_ElapsedTime >= m_FrameDuration) {
			m_ElapsedTime = 0;
			switch (m_AniType) {
			case FORWARD:
				if (m_CurrentFrame < m_KeyFrames.length - 1)
					m_CurrentFrame++;
				break;
			case BACK:
				if (m_CurrentFrame > 0)
					m_CurrentFrame--;
				break;
			case LOOP:
				if (m_CurrentFrame == m_KeyFrames.length - 1)
					m_CurrentFrame = 0;
				else
					m_CurrentFrame++;
				break;
			case BACKLOOP:
				if (m_CurrentFrame == 0)
					m_CurrentFrame = m_KeyFrames.length - 1;
				else
					m_CurrentFrame--;
				break;
			case PINGPONG:
				if (m_Forward) {
					if (m_CurrentFrame == m_KeyFrames.length - 1) {
						// at the end of the line so stop and back up
						m_Forward = false;
						m_CurrentFrame--;
					} else
						m_CurrentFrame++;
				} else if (m_CurrentFrame == 0) {
					// at tehe begining so start forward
					m_Forward = true;
					m_CurrentFrame++;
				} else
					m_CurrentFrame--;
				break;

			}

		}

	}

	public void draw(GameBase game, float x, float y) {
		if(m_CurrentFrame > -1 && m_Visible)
			game.draw(this.getFrame(), x, y);
	}

	public void draw(GameBase game, float x, float y, float width, float height) {
		if(m_CurrentFrame > -1 && m_Visible)
			game.draw(this.getFrame(), x, y, width, height);
	}

}
