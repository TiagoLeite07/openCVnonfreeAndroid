# OpenCV nonfree module in Android

This application uses Android-ndk and Java jni in order to be able to use methods in the nonfree module of OpenCV in Android.

## Compiling nonfree module for Android

The method used to cross-compile the nonfree module is based on the tutorial available in this [link](http://web.guohuiwang.com/technical-notes/sift_surf_opencv_android).

### Preparing the project

First create a folder that will contain our porting project, here it will be named 'nonfree_opencv_android'. Inside that folder create another called 'jni'.

Then go to your OpenCV installation and copy to this folder the following files:

* \<openCV_DIR\>/build/cvconfig.h
* \<openCV_DIR\>/modules/nonfree/src/nonfree_init.cpp
* \<openCV_DIR\>/modules/nonfree/src/precomp.hpp
* \<openCV_DIR\>/modules/nonfree/src/sift.cpp
* \<openCV_DIR\>/modules/nonfree/src/surf.cpp

Now create the Android build files: Android.mk and Application.mk

#### Android.mk

	LOCAL_PATH:= $(call my-dir)
	include $(CLEAR_VARS)
	OPENCV_INSTALL_MODULES:=on
	OPENCV_CAMERA_MODULES:=off
	include <openCV_ANDROID_SDK_DIR>/sdk/native/jni/OpenCV.mk

	LOCAL_C_INCLUDES:= 	<openCV_ANDROID_SDK_DIR>/sdk/native/jni/include
	LOCAL_MODULE    := nonfree
	LOCAL_CFLAGS    := -Werror -O3
	LOCAL_LDLIBS    += -llog

	LOCAL_SRC_FILES := 	nonfree_init.cpp \
						sift.cpp \
						surf.cpp
	include $(BUILD_SHARED_LIBRARY)

#### Application.mk

	APP_ABI := armeabi 
	#APP_ABI += armeabi-v7a # you can do either armeabi or armeabi-v7a, steps are the same.
	APP_STL := gnustl_static
	APP_CPPFLAGS := -frtti -fexceptions
	APP_PLATFORM := android-8

### Bulding the project

Change Android.mk and Application.mk files, replacing \<openCV_ANDROID_SDK_DIR\> with the path applicable to your environment.
Navigate to project dir in terminal and run ndk-build. This will create two shared libraries compatible with Android: libnonfree.so and libopencv_java.so.

## Running the application

Alter the file Android.mk in the jni folder, line 14, in order to be applicable to your environment. Copy the libraries built in the previous section, libnonfree.so and libopencv_java.so, to the jni folder of the Android application.
