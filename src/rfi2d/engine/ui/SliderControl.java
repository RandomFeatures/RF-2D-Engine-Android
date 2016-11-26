package rfi2d.engine.ui;

import rfi2d.engine.ControlBase;
import rfi2d.engine.GameBase;
import rfi2d.engine.helper.OverlapTest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class SliderControl extends ControlBase {

	private TextureRegion m_ImageRegion;
	public ControlChange ChangeListner = null;
	private boolean m_pressed = false;
	private Rectangle m_SliderRec;
	private float m_SlideX = 0;
	private float m_SlideY = 0;
	private float m_Position = 0.0f; 
	public SliderControl(final GameBase game, Rectangle rect, TextureRegion region) {
		super(game, rect);
		m_ImageRegion = region;
		m_SlideX = m_X;
		m_SlideY = m_Y + (rect.getHeight()-region.getRegionHeight())/2;
		m_SliderRec = new Rectangle(m_X-21,m_SlideY,region.getRegionWidth(),region.getRegionHeight());


	}

	public SliderControl(Builder build) {
		super(build.game, build.rect);
		m_ImageRegion = build.region;
		m_SlideX = m_X;
		m_SlideY = m_Y + ((build.rect.getHeight()-build.region.getRegionHeight())/2)-4;
		m_SliderRec = new Rectangle(m_X-21,m_SlideY,m_ImageRegion.getRegionWidth(),m_ImageRegion.getRegionHeight());

	}
	
	public SliderControl setPosition(float value)
	{
		m_Position = value;

		m_SlideX = m_X + (value*m_Width);
		m_SliderRec.setX(m_SlideX-21);
		
		return this;
	}
	
	public float getPosition()
	{
		return m_Position;
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (OverlapTest.pointInRectangle(m_SliderRec, x, y)) {
			m_pressed = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		m_pressed = false;
		if (OverlapTest.pointInRectangle(m_Rect, x, y)) {
			
			m_Position = ((x - m_X)/m_Width);
			m_SlideX = x;
			m_SliderRec.setX(x-21);
			if(ChangeListner!=null)  
				ChangeListner.OnChange(this, m_Position);
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		
		if (m_pressed && OverlapTest.pointInRectangle(m_Rect, x, y)) {
			
			m_Position = ((x - m_X)/m_Width);
			m_SlideX = x;
			m_SliderRec.setX(x-21);
			if(ChangeListner!=null)  
				ChangeListner.OnChange(this, m_Position);
			return true;
		}
		
		return false;
	}

	
	@Override
	public void render(float deltaTime) {
		if(m_pressed)
		{
			m_Game.getSpriteBatch().setColor(0.5f, 0.5f, 0.5f, 1);
			m_Game.draw(m_ImageRegion, m_SlideX - 21, m_SlideY);
			m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());
		}
		else	
			m_Game.draw(m_ImageRegion, m_SlideX - 21, m_SlideY);

	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	public static final class Builder {
		private GameBase game;
		private Rectangle rect;
		private TextureRegion region;
		
		public Builder(GameBase value) {
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
		public Builder setRect(Rectangle value) {
			rect = value;
			return this;
		}
		public Builder setRegion(TextureRegion value) {
			region = value;
			return this;
		}

		public SliderControl build() 
		{
			return new SliderControl(this);
		}
	}
}
