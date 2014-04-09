#include "nonfree_jni.hpp"

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/nonfree/nonfree.hpp>

int drawSiftKp( cv::Mat image )
{
	cv::Mat rgb = cv::Mat(image.cols, image.rows, CV_8UC3);
	cv::cvtColor(image, rgb, cv::COLOR_RGBA2RGB );

	std::vector< cv::KeyPoint > keypoints;

	cv::SiftFeatureDetector detector;
	detector.detect( rgb, keypoints );

	cv::drawKeypoints( rgb, keypoints, rgb, cv::Scalar(255,0,0,255),
			cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS );

	cv::cvtColor(rgb, image, cv::COLOR_RGB2RGBA);

	return 0;
}

JNIEXPORT void JNICALL Java_opencvnonfreeandroid_jni_NonfreeJNILib_nativeDrawSiftKp(JNIEnv * env, jclass claz, jlong mat)
{
	drawSiftKp( *((cv::Mat*)mat) );
}
