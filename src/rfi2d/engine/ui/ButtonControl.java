package rfi2d.engine.ui;

import rfi2d.engine.ControlBase;
import rfi2d.engine.GameBase;
import rfi2d.engine.helper.OverlapTest;
import rfi2d.engine.ui.ControlClick;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;




public class ButtonControl extends ControlBase{

	private final TextureRegion m_ImageRegion;
	public ControlClick ClickListner = null;
	private boolean m_pressed = false;
	private float m_ProtectedCountDown = 0;
	private boolean m_Protected = false;
	private float m_upColor = Color.WHITE.toFloatBits();
	private float m_downColor = Color.toFloatBits(0.5f, 0.5f, 0.5f, 1);
	private float m_ScaleWidth = 0f;
	private float m_ScaleHeight = 0f;
	/**
	 * @param game
	 * @param rect
	 * @param region
	 */
	public ButtonControl(final GameBase game, Rectangle rect, final TextureRegion region) {
		super(game, rect);
		m_ImageRegion = region;
	}

	public ButtonControl(Builder build) {
		super(build.game, build.rect);
		m_ImageRegion = build.region;
		m_upColor = build.upColor;
		m_downColor = build.downColor;
		m_ScaleWidth = build.ScaleWidth;
		m_ScaleHeight = build.ScaleHeight;
	}
	
	public ButtonControl protect(float timeout)
	{
		m_Protected = true;
		m_ProtectedCountDown = timeout;
		return this;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(m_Protected) return false;
		if (OverlapTest.pointInRectangle(m_Rect, x, y)) {
			m_pressed = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(m_Protected) return false;
		
		m_pressed = false;
		if (OverlapTest.pointInRectangle(m_Rect, x, y)) {
			if(ClickListner!=null)  
				ClickListner.OnClick(this);
			return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime) {
		if(m_Visible)
		{
			if(m_ProtectedCountDown > 0)
			{
				m_ProtectedCountDown -= deltaTime;
				if(m_ProtectedCountDown <= 0)
					m_Protected = false;
			}
		}
	}

	@Override
	public void render(float deltaTime) {
		
		if(!m_Enabled)
		{
			m_Game.getSpriteBatch().setColor(0.25f, 0.25f, 0.25f, 1);
			if(m_ScaleWidth > 0 && m_ScaleHeight > 0)
				m_Game.draw(m_ImageRegion, m_X, m_Y+m_ScaleHeight, m_ScaleWidth, m_ScaleHeight);
			else
				m_Game.draw(m_ImageRegion, m_X, m_Y);
			m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());	
		}
		else
		if(m_pressed)
		{
			m_Game.getSpriteBatch().setColor(m_downColor);
			if(m_ScaleWidth > 0 && m_ScaleHeight > 0)
				m_Game.draw(m_ImageRegion, m_X, m_Y+m_ScaleHeight, m_ScaleWidth, m_ScaleHeight);
				else
					m_Game.draw(m_ImageRegion, m_X, m_Y);
			m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());
		}
		else{
			m_Game.getSpriteBatch().setColor(m_upColor);
			if(m_ScaleWidth > 0 && m_ScaleHeight > 0)
					m_Game.draw(m_ImageRegion, m_X, m_Y+m_ScaleHeight, m_ScaleWidth, m_ScaleHeight);
				else
					m_Game.draw(m_ImageRegion, m_X, m_Y);
			m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());
			}
	}
	
	
	public static final class Builder {
		private final GameBase game;
		private final Rectangle rect;
		private TextureRegion region;
		private float ScaleWidth = 0f;
		private float ScaleHeight = 0f;
		private float upColor = Color.WHITE.toFloatBits();
		private float downColor = Color.toFloatBits(0.5f, 0.5f, 0.5f, 1);
		
		public Builder(final GameBase value) {
			game = value;
			rect = new Rectangle();
		}
		
		public Builder setX(float value) {
			rect.setX(value);
			return this;
		}
		public Builder setY(float value) {
			rect.setY(value);
			return this;
		}
		public Builder setWidth(float value) {
			rect.setWidth(value);
			return this;
		}
		public Builder setHeight(float value) {
			rect.setHeight(value);
			return this;
		}
		public Builder setScale(float width, float height) {
			ScaleWidth = width;
			ScaleHeight = height;
			return this;
		}
		
		public Builder setRegion(TextureRegion value) {
			region = value;
			return this;
		}
		
		public Builder setColor (float r, float g, float b, float a) {
			int intBits = (int)(255 * a) << 24 | (int)(255 * b) << 16 | (int)(255 * g) << 8 | (int)(255 * r);
			upColor = Float.intBitsToFloat(intBits & 0xfeffffff);
			return this;
		}
		
		public Builder setDownColor (float r, float g, float b, float a) {
			int intBits = (int)(255 * a) << 24 | (int)(255 * b) << 16 | (int)(255 * g) << 8 | (int)(255 * r);
			downColor = Float.intBitsToFloat(intBits & 0xfeffffff);
			return this;
		}

		public ButtonControl build() 
		{
			return new ButtonControl(this);
		}
		
	}
	
	
}
