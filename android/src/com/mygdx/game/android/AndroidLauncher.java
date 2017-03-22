package com.mygdx.game.android;

import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mystic.game.GlobalController;
import com.mystic.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {

	static GlobalController c;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		c = new GlobalController() {
			@Override
			public void move() {
				startActivity(new Intent(getBaseContext(), MainActivity.class));
				//AndroidLauncher.super.finish();
			}
			public void setScore(int s){

			}
		};
		initialize(new MyGdxGame(c), config);
	}
}
