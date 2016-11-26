/**
 * 
 */
package rfi2d.engine;


/**
 * @author allen
 *
 */
public interface PlatformListener {
	public void onExit();
	public void onEvent(String eventID);
	public void showAds(boolean show);
	public void onReady();
	public boolean rateCheck();
	public void recordScore(int board, long score, long time);
	public void recordAchievement(String id);
	public void onRateIt();
	public void onShareIt();
	public void onShowOpenFeint();
	public boolean onShowHighScore();
	public void showFullAd();
	public void openShop();
	public void openFacebook(int mode);
	public void openTwitter(int mode);
}
