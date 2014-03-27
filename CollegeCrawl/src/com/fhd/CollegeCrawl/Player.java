package com.fhd.CollegeCrawl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite{

	private Texture imgidle;
	public Sprite shadow;
	private float frame = 0;
	private int maxframes = 5;
	private Texture[] walk_ani_tex = new Texture[maxframes];
	private boolean isLookingLeft ,isLookingRight;
	boolean isWalkingRight;
	boolean isWalkingLeft;
	boolean isWalkingUp;
	boolean isWalkingDown;
	private float speed = 0.7f;
	public boolean destReached;
	private Vector2 destPos;
	public Rectangle left;
	public Rectangle right;
	public Rectangle top;
	public Rectangle bottom;
	public Rectangle rect;
	public String type = "guy";
	
	//TODO player braucht collision-rectangles

	/**
	 *@author Christoph Vogel 
	 */
	public Player ()
	{
		for (int i=0; i < maxframes; i++)
		{
			walk_ani_tex[i] = new Texture("content/"+type+"/"+i+".png");
		}

		shadow = new Sprite(new Texture("content/shadow.png"));
		shadow.flip(false, true);
		imgidle = new Texture("content/"+type+"/idle.png");

		this.set(new Sprite(imgidle));
		this.setBounds(300, 300, 64, 64);
		this.flip(true, true);
		isLookingRight = true;
		destPos = new Vector2(this.getX(),this.getY());
	
		left = new Rectangle();
		right = new Rectangle();
		top = new Rectangle();
		bottom = new Rectangle();
		rect = new Rectangle();
		
		left.set(this.getX()+0,this.getY()+16,24,32);
		right.set(this.getX()+40,this.getY()+16,24,32);
		top.set(this.getX()+16,this.getY()+0,32,24);
		bottom.set(this.getX()+16,this.getY()+40,32,24);
		rect.set(this.getX()+16,this.getY()+32,32,32);
	}
	
	
	

	public void setNav(Vector2 nav)
	{
		//nav in die mitte des sprites setzen
		nav.x = nav.x - 32;
		nav.y = nav.y - 50;
		
		this.destPos = nav;
		if(destPos.x < this.getX())
		{
//			this.setTexture(imgidle);
			if(!isLookingLeft){
				this.flip(true, false);
				isLookingLeft = true;
			}
		} 
		else 
		{
//			this.setTexture(imgidle);
			if(isLookingLeft){
				this.flip(true, false);
				isLookingLeft = false;
			}
		}


	}

	public void goToNav()
	{
		final Vector2 destVec = new Vector2(destPos.x, destPos.y);
		final Vector2 posVec = new Vector2(this.getX(), this.getY());

		if (destVec.dst(posVec) > speed)
		{
			destVec.sub(posVec).nor().mul(speed);
			translate(destVec.x, destVec.y);

		} else
		{
			destVec.sub(posVec);
		}
		translate(destVec.x, destVec.y);

		if(this.destPos.x == getX() && this.destPos.y == getY())
		{
			destReached = true;
		} else {
			destReached = false;
		}
	}


	public void update()
	{
		left.set(this.getX()+0,this.getY()+16,24,32);
		right.set(this.getX()+40,this.getY()+16,24,32);
		top.set(this.getX()+16,this.getY()+0,32,24);
		bottom.set(this.getX()+16,this.getY()+40,32,24);
		rect.set(this.getX()+16,this.getY()+32,32,32);

		this.shadow.setBounds(this.getX()+16, this.getY()+50, 32, 32);
		this.goToNav();

		if(!destReached)
		{
			frame+=0.15f;
			if(frame >= maxframes)
			{
				frame = 0;
			}
			this.setTexture(walk_ani_tex[(int) frame]);
		} 
		else 
		{
			this.setTexture(imgidle);	
		}

		//keyinput stuff
		/*
		if(!isWalkingLeft && !isWalkingRight && !isWalkingUp && !isWalkingDown)
		{
			if(isLookingLeft)
			{
				this.setTexture(imgleft);
			} else {
				this.setTexture(imgright);
			}
		} 
		else 
		{
			if(frame >= maxframes)
			{
				frame = 0;
			}
			this.setTexture(walk_ani_tex[(int) frame]);
		}
		 */
	}
	

	public void goDown(){
		frame+=0.15;
		isWalkingDown = true;
	}
	public void goUp(){
		frame+=0.15;
		isWalkingUp = true;
	}
	public void goRight(){
		frame+=0.15;

		isWalkingRight = true;

		if(isLookingLeft){
			this.flip(true, false);
			isLookingLeft = false;
		}
	}
	public void goLeft(){
		frame+=0.15;

		isWalkingLeft = true;

		if(!isLookingLeft){
			this.flip(true, false);
			isLookingLeft = true;
		}
	}
}
