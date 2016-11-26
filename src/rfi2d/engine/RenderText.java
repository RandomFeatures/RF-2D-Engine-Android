package rfi2d.engine;

import rfi2d.engine.GameBase;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class RenderText {
	
	
	private String[] m_chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\<[]>% "};
	//private String[] m_chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!?'\"+-=/\\<[]>% "};
	private final TextureRegion[][] TextTexture;
	private final GameBase m_Game;
	private int m_CellWidth;
	public void setCharacters(String[] m_chars) {
		this.m_chars = m_chars;
	}

	public RenderText(GameBase game, Texture texture, int startx, int starty, int cellwidth, int cellheight, int width, int height ) {
		m_Game = game;
		m_CellWidth = cellwidth;
		int xSlices = width / cellwidth;
		int ySlices = height / cellheight;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, startx + (x * cellwidth), starty + (y * cellheight), cellwidth, cellheight);
			}
		}
		
		TextTexture = res;
	}
	
	public void draw (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < m_chars.length; ys++) {
				int xs = m_chars[ys].indexOf(ch);
				if (xs >= 0) {
					m_Game.draw(TextTexture[xs][ys], x + (i * (m_CellWidth)), y);
					
				}
			}
		}
	}
	
	public void draw (SpriteBatch batch, String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < m_chars.length; ys++) {
				int xs = m_chars[ys].indexOf(ch);
				if (xs >= 0) {
					batch.draw(TextTexture[xs][ys], x + i * (m_CellWidth), y);
				}
			}
		}
	}
}
