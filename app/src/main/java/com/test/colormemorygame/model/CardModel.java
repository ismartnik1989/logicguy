package com.test.colormemorygame.model;

import android.widget.Button;

import java.io.Serializable;


public class CardModel implements Serializable{

	public int x;
	public int y;
	public Button button;
	
	public CardModel(Button button, int x, int y) {
		this.x = x;
		this.y=y;
		this.button=button;
	}
	

}
