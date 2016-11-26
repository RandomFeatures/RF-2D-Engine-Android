package rfi2d.engine.ui;

import rfi2d.engine.ControlBase;
import rfi2d.engine.GameBase;
import rfi2d.engine.helper.OverlapTest;
import rfi2d.engine.ui.ControlClick;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;



public class CheckControl extends ControlBase {

	private TextureRegion m_CheckOn;
	private TextureRegion m_CheckOff;
	public ControlClick ClickListner = null;
	private boolean m_pressed = false;
	public boolean Checked = false;
	public CheckControl(GameBase game, Rectangle rect, TextureRegion on, TextureRegion off) {
		super(game, rect);
		
	}

	public CheckControl(Builder build) {
		super(build.game, build.rect);
		m_CheckOn = build.checkOn;
		m_CheckOff = build.checkOff;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float deltaTime) {
		if(m_pressed)
		{
			m_Game.getSpriteBatch().setColor(0.5f, 0.5f, 0.5f, 1);
			if(Checked)
				m_Game.draw(m_CheckOn, m_X+0, m_Y+0);
			else
				m_Game.draw(m_CheckOff, m_X+0, m_Y+0);
			m_Game.getSpriteBatch().setColor(Color.WHITE.toFloatBits());
		}
		else	
			if(Checked)
				m_Game.draw(m_CheckOn, m_X, m_Y);
			else
				m_Game.draw(m_CheckOff, m_X, m_Y);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		
		if (OverlapTest.pointInRectangle(m_Rect, x, y)) 
		{
			m_pressed = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		m_pressed = false;
		if (OverlapTest.pointInRectangle(m_Rect, x, y)) {
			Checked = !Checked;
			if(ClickListner!=null)  
				ClickListner.OnClick(this);
			return true;
		}
		return false;
	}
	
	public static final class Builder {
		private GameBase game;
		private Rectangle rect;
		private TextureRegion checkOn;
		private TextureRegion checkOff;
		
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
		public Builder setOnRegion(TextureRegion value) {
			checkOn = value;
			return this;
		}
		public Builder setOffRegion(TextureRegion value) {
			checkOff = value;
			return this;
		}

		public CheckControl build() 
		{
			return new CheckControl(this);
		}
		
	}
}
