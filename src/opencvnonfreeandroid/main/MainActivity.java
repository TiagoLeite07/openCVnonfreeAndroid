package opencvnonfreeandroid.main;

import opencvnonfreeandroid.jni.NonfreeJNILib;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class MainActivity extends Activity implements CvCameraViewListener2
{
	private static final String TAG = "OpenCV nonfree Android test";
	private OpencvNonfreeAndroid app;

	private CameraBridgeViewBase _mOpenCvCameraView;
	private NonfreeJNILib _cvnonfree;
	
	private Mat _cRgba;
	private boolean _setSaveNextFrame;
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this)
	{
		@Override
		public void onManagerConnected(int status)
		{
			switch(status)
			{
				case LoaderCallbackInterface.SUCCESS:
				{
					Log.i( TAG, "OpenCV loaded successfully" );
					
					// Load native library after(!) OpenCV initialization
					try
					{
						System.loadLibrary("opencv_java");
						System.loadLibrary("nonfree");
						System.loadLibrary("nonfree_jni");
					}
					catch( UnsatisfiedLinkError e )
					{
						System.err.println("Native code library failed to load.\n" + e); 
					}
					
					_mOpenCvCameraView.enableView();
				} break;
				default:
				{
					super.onManagerConnected(status);
				} break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.i( TAG, "called onCreate" );
		super.onCreate(savedInstanceState);
		
		app = (OpencvNonfreeAndroid) getApplication();
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		
		_mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
		_mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		_mOpenCvCameraView.setCvCameraViewListener(this);		
		_mOpenCvCameraView.setOnTouchListener(new CustomOnTouchListener(this));
		
		_cvnonfree = new NonfreeJNILib();
		_setSaveNextFrame = false;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		OpenCVLoader.initAsync( OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback );
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if( _mOpenCvCameraView != null )
		{
			_mOpenCvCameraView.disableView();
		}
	}

	public void onDestroy()
	{
		super.onDestroy();
		if( _mOpenCvCameraView != null )
		{
			_mOpenCvCameraView.disableView();
		}
	}

	@Override
	public void onCameraViewStarted(int width, int height) {}

	@Override
	public void onCameraViewStopped() {}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame)
	{
		if(_setSaveNextFrame)
		{
			_cRgba = inputFrame.rgba();
			drawKeyPoints(_cRgba);

			_setSaveNextFrame = false;
		}

		return inputFrame.rgba();
	}
	
	public void drawKeyPoints( Mat image )
	{
		_cvnonfree.drawSiftKeyPoints(_cRgba);
		
		Bitmap bimage = Bitmap.createBitmap( _cRgba.cols(), _cRgba.rows(), Bitmap.Config.ARGB_8888 );
		Utils.matToBitmap( _cRgba, bimage );
		app.results = bimage;
		
		Intent intent = new Intent( this, DisplayResults.class );		
		startActivity(intent);
	}

	private class CustomOnTouchListener implements OnTouchListener
	{
		MainActivity _act;

		public CustomOnTouchListener( MainActivity act )
		{
			this._act = act;
		}

		@Override
		public boolean onTouch( View v, MotionEvent event )
		{
			_act._setSaveNextFrame = true;

			return false;
		}
	}

}
