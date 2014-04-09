package opencvnonfreeandroid.jni;

import org.opencv.core.Mat;

public class NonfreeJNILib
{
	
	public void drawSiftKeyPoints( Mat image )
	{
		nativeDrawSiftKp( image.getNativeObjAddr() );
	}

	public static native void nativeDrawSiftKp( long inputImage );

}
