# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\programming\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings
  #指定代码的压缩级别
    -optimizationpasses 5

    #包明不混合大小写
    -dontusemixedcaseclassnames

    #不去忽略非公共的库类
    -dontskipnonpubliclibraryclasses

     #优化  不优化输入的类文件
    -dontoptimize

#     #预校验
#    -dontpreverify

     #混淆时是否记录日志
    -verbose
     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    #保护注解
    -keepattributes *Annotation*
    # 保持哪些类不被混淆

    -keepattributes LineNumberTable,SourceFile

-keepattributes LineNumberTable,SourceFile

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
long producerIndex;
long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
@butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
@butterknife.* <methods>;
}

-dontwarn android.support.**

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-dontwarn java.lang.invoke.*

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keep class **.R
-keep  class com.spc.sedentary.tips.ui.activity.** {*;}
-keep class com.spc.sedentary.tips.fragment.** {*;}