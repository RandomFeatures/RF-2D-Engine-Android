package rfi2d.engine;

public interface RenderInterface {
	public void dispose();
	public void update(final float deltaTime);		
	public void render(final float deltaTime);
}
