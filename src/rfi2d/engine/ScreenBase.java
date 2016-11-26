package rfi2d.engine;


import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public abstract class ScreenBase extends InputAdapter {


	protected final GameBase m_Game;
	protected boolean m_Dying = false;
	protected boolean m_Paused = false;
	protected final GUIManager m_guimanager;
	protected boolean AcceptInput = true; 
	protected TextureRegion m_Borders;
	private Vector3 touchPos = new Vector3();
	@Override
	protected void finalize() throws Throwable {
		try {
			m_guimanager.dispose();
			m_Borders = null;
	    } finally {
	        super.finalize();
	    }
	}

	
	public ScreenBase(final GameBase game) {
		this.m_Game = game;
		m_guimanager = new GUIManager(game);
		AcceptInput = m_Game.AcceptInput;
		if(!m_Game.AcceptInput) m_guimanager.DisableAll();
		
		
	}
	
	public void AddControl(final ControlBase control) {
		m_guimanager.AddControl(control);
	}
	
	public GUIManager getGUIManager() {
		return m_guimanager;
	}
	
	public void EnableInput(boolean value) {
		AcceptInput = value;
		if(value)
			m_guimanager.EnableAll();
		else
			m_guimanager.DisableAll();
	}

	
	
	@Override
	public boolean keyDown(int keycode) {
		return m_guimanager.keyDown(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return m_guimanager.keyTyped(character);
	}

	@Override
	public boolean keyUp(int keycode) {
		return m_guimanager.keyUp(keycode);
	}

	@Override
	public boolean scrolled(int amount) {
		return m_guimanager.scrolled(amount);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		
		
        m_Game.Camera.unproject(touchPos.set(x, y, 0));

		return m_guimanager.touchDown((int)touchPos.x,m_Game.m_CameraHeight-(int)touchPos.y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		m_Game.Camera.unproject(touchPos.set(x, y, 0));
        
		return m_guimanager.touchDragged((int)touchPos.x,m_Game.m_CameraHeight-(int)touchPos.y, pointer);
	}

	@Override
	public boolean touchMoved(int x, int y) {
		
		m_Game.Camera.unproject(touchPos.set(x, y, 0));
        
		return m_guimanager.touchMoved((int)touchPos.x,m_Game.m_CameraHeight-(int)touchPos.y);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		
		m_Game.Camera.unproject(touchPos.set(x, y, 0));
		
		return m_guimanager.touchUp((int)touchPos.x,m_Game.m_CameraHeight-(int)touchPos.y, pointer, button);
	}

	
	public abstract void init();
	
	public abstract void update(float deltaTime);		
	
	public abstract void render(float deltaTime);
	
	public abstract void pause();
	
	public abstract void resume();
	
	public abstract void dispose();


}
