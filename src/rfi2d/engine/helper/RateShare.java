package rfi2d.engine.helper;

import rfi2d.engine.ControlBase;
import rfi2d.engine.GameBase;
import rfi2d.engine.ScreenBase;
import rfi2d.engine.ui.ButtonControl;
import rfi2d.engine.ui.ControlClick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RateShare extends ScreenBase {

	
	private final ButtonControl m_btnRate;
	private final ButtonControl m_btnShare;
	private final ButtonControl m_btnNotNow;
	private final TextureRegion m_dialog;
	private final Texture DialogTextTexture;
	
	
	public RateShare(GameBase game) {
		super(game);

		DialogTextTexture = new Texture("gfx/rateit.png");
		m_dialog = new TextureRegion(DialogTextTexture, 0, 0, 512, 214);
		
		m_btnRate = new ButtonControl.Builder(game)
		.setX(212)
		.setY(304)
		.setWidth(100)
		.setHeight(30)
		.setRegion(new TextureRegion(DialogTextTexture, 68, 171, 100, 30))
		.build();
		m_btnRate.ClickListner = new ControlClick() {
			@Override
			public void OnClick(ControlBase sender) {
				m_Game.rateIt();
				m_Game.LogEvent("Rated");
				m_Game.popScreens(1);//this screen
			}
		};
		
		m_guimanager.AddControl(m_btnRate);
	
	
		m_btnShare = new ButtonControl.Builder(game)
		.setX(350)
		.setY(304)
		.setWidth(100)
		.setHeight(30)
		.setRegion(new TextureRegion(DialogTextTexture, 206, 171, 100, 30))
		.build();
		m_btnShare.ClickListner = new ControlClick() {
			@Override
			public void OnClick(ControlBase sender) {
				m_Game.shareIt();
				m_Game.LogEvent("Shared");
				m_Game.popScreens(1);//this screen
			}
		};
		
		m_guimanager.AddControl(m_btnShare);
	
		m_btnNotNow = new ButtonControl.Builder(game)
		.setX(488)
		.setY(304)
		.setWidth(100)
		.setHeight(30)
		.setRegion(new TextureRegion(DialogTextTexture, 344, 171, 100, 30))
		.build();
		m_btnNotNow.ClickListner = new ControlClick() {
			@Override
			public void OnClick(ControlBase sender) {
				m_Game.LogEvent("Not Now");
				m_Game.popScreens(1);//this screen
			}
		};
		
		m_guimanager.AddControl(m_btnNotNow);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float deltaTime) {

		m_Game.getSpriteBatch().begin();
		
		m_Game.draw(m_dialog, 144, 133);
		m_guimanager.render(deltaTime);
		m_Game.getSpriteBatch().end();

	}

	@Override
	public void update(float deltaTime) {
		m_guimanager.update(deltaTime);

	}

	@Override
	public void init() {
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
