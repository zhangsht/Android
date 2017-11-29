# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# 保留行号，方便跟踪
-keepattributes SourceFile,LineNumberTable

# 混淆文件名
-renamesourcefileattribute SourceFile

-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
    public static *** e(...);
}

# 混淆时不使用大小写混写的类名
-dontusemixedcaseclassnames
# 不跳过 libraray 中的非 public 类
-dontskipnonpubliclibraryclasses
# 保留方法上的异常属性
-keepattributes Exceptions
# 打印处理过程的信息
-verbose

# 保留清单文件类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

#关闭优化功能，因为优化可能会造成一些潜在的风险，无法保证在所有版本的 Dalvik 都正常运行
-dontoptimize
# 关闭预校验功能，Android 平台上不需要，所以默认是关闭的
-dontpreverify
# 保留注解属性、泛型、内部类、封闭方法，后面都是三个属性都是为了反射正常运行
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
#不混淆 native 方法名和包含 native 方法的类名，如果 native 方法未使用，还是会被移除
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留继承自 View 的 setXx() 和 getXx() 方法，因为属性动画会用到相应的 setter 和 getter
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}

# 保留 Activity 中参数是 View 的方法，因为在 XML 中配置 android:onClick="buttonClick" 属性时，点击该按钮时就会调用 Activity 中的 buttonClick(View view) 方法
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保留 enum 中的静态 values() 和 valueOf 方法，因为这些静态方法可能会在运行时通过内省调用
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留 Parcelable 子类中的 CREATOR 字段
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# 保留 R 文件中的所有静态字段，这些静态字段是用来记录资源 ID 的
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 保留 JavascriptInterface 注解标记的方法，不然 js 调用时就会找不到方法
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 不对 android.support 包下的代码警告，因为 support 包中一些代码是高版本才能使用，但是在低版本中有兼容性判断，是安全，所以在低版本打包时也不要警告
-dontnote android.support.**
-dontwarn android.support.**

#保留 Keep 注解
-keep class android.support.annotation.Keep

#标记类时，保留类及其所有成员
-keep @android.support.annotation.Keep class * {*;}

 #标记方法时，保留标注的方法和包含它的类名
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

 # 标记字段时，保留标记的字段和包含它的类名
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

 # 标记构造函数时，保留标记的构造函数和包含它的类名
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-keep public class * extends android.view.View {
      public <init>(android.content.Context);
      public <init>(android.content.Context, android.util.AttributeSet);
      public <init>(android.content.Context, android.util.AttributeSet, int);
      public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}

# 混淆包名
-repackageclasses ''
-allowaccessmodification

# 保留实体类
-keep class com.**.entity.** {
    void set*(***);
    void set*(int, ***);
    boolean is*();
    boolean is*(int);
    *** get*();
    *** get*(int);
}

# 不混淆 Serializable 相关内容
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}










