package ycp.edu.cs496project.mobileApp;

import ycp.edu.cs496project.mobileApp.graphics.Panel;
import android.os.Bundle;
import android.view.Window;
import android.app.Activity;

public class MarbleMadness extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
	}

}
