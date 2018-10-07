#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_c_foodie_foodographer_HomePage_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
