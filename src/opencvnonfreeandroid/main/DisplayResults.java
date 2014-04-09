package opencvnonfreeandroid.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

public class DisplayResults extends Activity
{

	private OpencvNonfreeAndroid app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		app = (OpencvNonfreeAndroid) getApplication();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_display_results);

		ImageView imv = (ImageView) findViewById(R.id.imageView1);
		imv.setImageBitmap(app.results);
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	public void onDestroy()
	{
		super.onDestroy();
	}

}
