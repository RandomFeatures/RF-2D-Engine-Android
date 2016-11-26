package rfi2d.engine;

import java.util.ArrayList;

import rfi2d.engine.helper.CameraHelper;
import rfi2d.engine.types.DeployType;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GameBase implements ApplicationListener {
	private ArrayList<ScreenBase> screens;
	private float m_CoinTimer = 0f;
	protected SpriteBatch m_SpriteBatch;
	private Application.ApplicationType m_Platfrom;
	protected int m_CameraWidth = 800;
	protected int m_CameraHeight = 480;
	protected int m_Coins = 0;
	protected int m_DisplayCoins = 0;
	protected int m_CoinInc = 1;
	public OrthographicCamera Camera;

	protected DeployType m_DeployType = DeployType.FREE;
	public RenderText FontSmallWhite;
	public RenderText FontSmallBlue;
	public RenderText FontSmallGreen;
	public boolean AcceptInput = true;
	public ScreenBase CurrentScreen;
	public float viewportX = 800.0f;
	public float viewportY = 480.0f;
	public float decal_viewportX = 0.0f;
	public float decal_viewportY = 0.0f;
	public int ScreenX = 0;
	public int ScreenY = m_CameraHeight;
	public int ScreenTopY = 0;
	protected boolean m_Scaled = false;
	protected boolean m_Paused = false;
	protected boolean m_Dying = false;
	public abstract void LogEvent(String eventID);
	public abstract boolean rateCheck();
	public abstract void rateIt();
	public abstract void shareIt();
	public abstract boolean showHighScore();
	public abstract void recordScore(int board, long score, long time);
	public abstract void recordAchievement(String id);
	public abstract void ready();
	public abstract void showAds(boolean show);
	public abstract void addCoins(int count);
	public abstract void removeCoins(int count);
	public abstract void purchaseProduct(String sku);
	public abstract void refundProduct(String sku);
	public abstract void showFullAd();
	public abstract void openShop();
	public abstract void openFacebook();
	public abstract void openTwitter();
	
	public void EnableInput(boolean value) {
		AcceptInput = value;
		if (CurrentScreen != null)
			CurrentScreen.EnableInput(value);
	}

	/**
	 * @param m_Platfrom
	 *            the m_Platfrom to set
	 */
	public void setPlatfrom(Application.ApplicationType m_Platfrom) {
		this.m_Platfrom = m_Platfrom;
	}

	/**
	 * @return the m_Platfrom
	 */
	public Application.ApplicationType getPlatfrom() {
		return m_Platfrom;
	}

	public DeployType getDeployType() {
		return m_DeployType;
	}

	/**
	 * @return the m_CameraWidth
	 */
	public int getCameraWidth() {
		return m_CameraWidth;
	}

	public int getCameraHeight() {
		return m_CameraHeight;
	}
	
	public int getCoins(){
		return m_Coins;
	}

	public int getDisplayCoins(){
		return m_DisplayCoins;
	}

	
	/**
	 * @return the m_SpriteBatch
	 */
	public SpriteBatch getSpriteBatch() {
		return m_SpriteBatch;
	}

	public void pushScreen(ScreenBase newscreen) {
		screens.add(0, newscreen);
		screens.get(0).init();
		CurrentScreen = screens.get(0);
	}

	public void popScreen() {
		if (screens.size() > 0) {
			screens.get(0).dispose();
			screens.remove(0);
			if (screens.size() > 0) {
				screens.get(0).init();
				CurrentScreen = screens.get(0);
			} else
				CurrentScreen = null;
		}
	}

	public void popScreens(int count) {
		for (int i = 0; i < count; i++)
			if (screens.size() > 0) {
				screens.get(0).dispose();
				screens.remove(0);
			}

		if (screens.size() > 0) {
			screens.get(0).init();
			CurrentScreen = screens.get(0);
		} else
			CurrentScreen = null;
	}

	public void popAll() {
		while (screens.size() > 0) {
			screens.get(0).dispose();
			screens.remove(0);
			CurrentScreen = null;
		}
		screens.clear();
	}

	public void draw(TextureRegion region, float x, float y) {
		m_SpriteBatch.draw(region, ScreenX + x, ScreenY - y - region.getRegionHeight());
		
	}

	public void draw(TextureRegion region, float x, float y, float width, float height) {
		//m_SpriteBatch.draw(region, ScreenX + x,  ScreenY - y - region.getRegionHeight(), width, height);
		m_SpriteBatch.draw(region, ScreenX + x,  ScreenY - y, width, height);
	}

	public void draw(Sprite sprite, float x, float y) {
		sprite.setPosition(ScreenX + x, ScreenY - y - sprite.getRegionHeight());
		sprite.draw(m_SpriteBatch);
	}

	public abstract ScreenBase getStartScreen();
	public abstract void ExitGame();
	public abstract boolean LoadAssets();

	@Override
	public void create() {
		m_SpriteBatch = new SpriteBatch();
		
		/*
		Matrix4 projection_normal = new Matrix4();
		
		// we will curb the viewport based on the actual size of the screen, so sprites looks good
		// we review the height actually used on the screen depending on the width
		viewportY = Gdx.graphics.getWidth() * 0.6f; // by a factor of 1.666666667f as the game was done in 800x480 (480/800 = 1.5)
		
		// but if the screen is not high enough (1 case (HTC Tattoo): Low density (120), LDPI; Small screen QVGA (240x320))
		//if (viewportY > Gdx.graphics.getHeight()) {
		//     // Then we review the contrary the width depending on the height
		//     viewportX = Gdx.graphics.getHeight() * (m_CameraWidth/m_CameraHeight); // by a factor of 0.6666666 .. expressed precisely by 320/480 (that's why we used float)
		//     viewportY = Gdx.graphics.getHeight();
		//     decal_viewportX = (Gdx.graphics.getWidth() - viewportX)/2; // we center the view
		//} else {
		     // here the screen is high enough
		viewportX = Gdx.graphics.getWidth();
		decal_viewportY = (Gdx.graphics.getHeight() - viewportY)/2;
		//}
		// Now the viewport is adjusted according to these calculations
		Gdx.gl.glViewport((int)decal_viewportX, (int)decal_viewportY, (int)viewportX, (int)viewportY);
		
		//m_CameraWidth = (int)viewportX;
		//m_CameraHeight = (int)viewportY;

		ScreenX = (m_CameraWidth - 800)/2;
		ScreenY = m_CameraHeight -((m_CameraHeight - 480)/2);
		ScreenTopY = ((m_CameraHeight - 480)/2);
		
		
		projection_normal.setToOrtho(0, m_CameraWidth, 0, m_CameraHeight, -1, 1);
	
		m_SpriteBatch.setProjectionMatrix(projection_normal);
		*/
		
		Camera = CameraHelper.createCamera(m_Scaled, m_CameraWidth, m_CameraHeight);
		
		m_SpriteBatch.setProjectionMatrix(Camera.combined);
		
		screens = new ArrayList<ScreenBase>();
		screens.add(0, getStartScreen());
		if(screens.get(0)!=null)
			screens.get(0).init();
	}
	
	@Override
	public void resume() {
		m_Paused = false;
		if (screens.size() > 0)
			if(screens.get(0) != null)
			{
				screens.get(0).m_Paused = false;
				screens.get(0).resume();
			}
	}

	@Override
	public void render() {
		if(m_Dying) return;
		if(m_Paused) return;
		
		m_CoinTimer += Gdx.graphics.getDeltaTime();
		if(m_CoinTimer >= 0.05f)
		{
			m_CoinTimer = 0f;
			
			
			if(m_DisplayCoins < m_Coins)
			{
				m_DisplayCoins += m_CoinInc;
				if(m_Coins < m_DisplayCoins)
					m_DisplayCoins = m_Coins;
			}
			if(m_DisplayCoins > m_Coins)
			{
				m_DisplayCoins-=m_CoinInc;
				if(m_Coins > m_DisplayCoins)
					m_DisplayCoins = m_Coins;
			}
		}
		
		if (screens.size() > 0) {
			if(screens.get(0) != null)
			{
				screens.get(0).update(Gdx.graphics.getDeltaTime());
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
				screens.get(0).render(Gdx.graphics.getDeltaTime());
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		m_Paused = true;
		if (screens.size() > 0)
			if(screens.get(0) != null)
			{
				screens.get(0).m_Paused = true;
				screens.get(0).pause();
			}
	}

	@Override
	public void dispose() {
		m_Dying = true;
		
		while (screens.size() > 0) {
			if(screens.get(0) != null)
			{
				screens.get(0).m_Dying = true;
				screens.get(0).dispose();
				screens.remove(0);
			}
		}
		screens.clear();
		m_SpriteBatch.dispose();
	}
}
