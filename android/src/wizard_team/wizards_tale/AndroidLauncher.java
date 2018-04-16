package wizard_team.wizards_tale;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import wizard_team.wizards_tale.WizardsTaleGame;

public class AndroidLauncher extends AndroidApplication{

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WizardsTaleGame(), config);
	}

}
