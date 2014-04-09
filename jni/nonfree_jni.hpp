#include <jni.h>

// JNI interface functions, be careful about the naming.
extern "C"
{
    JNIEXPORT void JNICALL Java_opencvnonfreeandroid_jni_NonfreeJNILib_nativeDrawSiftKp( JNIEnv*, jclass, jlong );
}
