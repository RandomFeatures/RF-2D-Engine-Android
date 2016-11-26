package rfi2d.engine.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderMethods {

	
	public static void tileImage(SpriteBatch batch, TextureRegion region, int startX, int startY, int width, int height, int imgWidth, int imgHeight)
	{
		 int tx = startX;
         int ty = startY;

         // if tiled, compute visible background region and draw tiles
         int w = width,  iw = imgWidth;
         int h = height, ih = imgHeight;

         int sx = startX>0 ? 0 : tx%iw;
         int sy = startY>0 ? 0 : ty%ih;
         if ( sx > 0 ) sx -= iw;
         if ( sy > 0 ) sy -= ih;

         for ( int x=sx; x<w-sx; x+=iw ) {
             for ( int y=sy; y<h-sy; y+=ih )
            	 batch.draw(region, x, y);
         }
	}
}
