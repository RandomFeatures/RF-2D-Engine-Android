package rfi2d.engine.ui;

import rfi2d.engine.ControlBase;
import rfi2d.engine.GameBase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Marquee extends ControlBase{
	private TextureRegion m_rgnImage;
	private float m_ElapsedTime; 
	private int m_Direction;
	private float m_red;
	private float m_green;
	private float m_blue;
	private float m_alpha;
	private float m_speed = 0.03f;
	/**
	 * 
	 */
	public Marquee(final GameBase game, TextureRegion region, float speed, int dir, float startx, float starty, float red, float green, float blue, float alpha) {
		super(game, new Rectangle(startx,starty,0,0));
		m_rgnImage = region;
		m_Direction = dir;
		m_speed = speed;
		m_red = red;
		m_green = green;
		m_blue = blue;
		m_alpha = alpha;
	}
	
	public Marquee(Builder build){
		super(build.game, new Rectangle(build.x,build.y,0,0));
		m_rgnImage = build.region;
		m_Direction = build.direction;
		m_speed = build.speed;
		m_red = build.red;
		m_green = build.green;
		m_blue = build.blue;
		m_alpha = build.alpha;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime) {
		m_ElapsedTime += deltaTime;
		if(m_ElapsedTime >= m_speed)
		{
			m_ElapsedTime = 0.0f;
			switch(m_Direction)
			{
			case 0://Left
				m_X++;
				if (m_X >= m_Game.getCameraWidth())
					m_X = (0 - m_rgnImage.getRegionWidth());
				break;
			case 1://Right
				m_X--;
				if (m_X <= (0 - m_rgnImage.getRegionWidth()))
					m_X = m_Game.getCameraWidth();
				break;
			case 2://Up
				m_Y++;
				if (m_Y >= m_Game.getCameraHeight())
					m_Y = (0 - m_rgnImage.getRegionHeight());
				break;
			case 3://Down
				m_Y--;
				if (m_Y <= (0 - m_rgnImage.getRegionHeight()))
					m_Y = m_Game.getCameraHeight();
				break;
			}
		}
		
	}
	
	@Override
	public void render(float deltaTime) {
		m_Game.getSpriteBatch().setColor(m_red, m_green, m_blue, m_alpha);
		m_Game.draw(m_rgnImage, m_X, m_Y);
		m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());
	}
	
	public static final class Builder {
		private GameBase game;
		private TextureRegion region;
		private int direction = 0;
		private float x = 0f;
		private float y = 0f;
		private float red = 1f;
		private float green = 1f;
		private float blue = 1f;
		private float alpha = 1f;
		private float speed = 1f;
		
		public Builder(GameBase value) {
			game = value;
		}
		
		public Builder setX(float value) {
			x = value;
			return this;
		}
		public Builder setY(float value) {
			 y = value;
			return this;
		}
		public Builder setRed(float value) {
			red = value;
			return this;
		}
		public Builder setGreen(float value) {
			green = value;
			return this;
		}
		public Builder setBlue(float value) {
			blue = value;
			return this;
		}
		public Builder setAlpha(float value) {
			alpha = value;
			return this;
		}
		public Builder setSpeed(float value) {
			speed = value;
			return this;
		}
		public Builder setDirection(int value) {
			direction = value;
			return this;
		}
		public Builder setRegion(TextureRegion value) {
			region = value;
			return this;
		}

		public Marquee build() 
		{
			return new Marquee(this);
		}
		
	}

	
}
