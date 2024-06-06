# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
-renamesourcefileattribute SourceFile

-keepparameternames

-keep class com.meritumads.settings.MsAdsSdk{
	public getInstance();
	public init(android.content.Context, java.lang.String, java.lang.String);
	public init(android.content.Context, java.lang.String, java.lang.String, com.meritumads.elements.MsAdsDelegate);

	public getWebviewDroid();
	public getPopupBanners();
	public getFullScreenBanners();
	public getPrerollBanners();
	public getInListBanners();
	public getInListBannersIds();
	public getArrowBackColor();
	public setArrowBackColor(java.lang.String);
	public setActionBarColor(java.lang.String);
	public setApiLinkService(com.meritumads.settings.MsAdsOpenApiLinkService);
	public setActiveFilter(java.lang.String, java.lang.String);
	public setListOfActiveFilters(java.util.LinkedHashMap);
	public setUserId(java.lang.String);
	public getUserId();

}
-keep class com.meritumads.elements.MsAdsDelegate{
	*;
}
-keep class com.meritumads.settings.MsAdsBanners{
	public <methods>;
}

-keep class com.meritumads.settings.MsAdsFullScreen{
	public <methods>;
}

-keep class com.meritumads.settings.MsAdsPopups{
	public <methods>;

}

-keep class com.meritumads.settings.MsAdsPreRolls{
	public <methods>;

}

-keep class com.meritumads.settings.MsAdsInListPosition{
    public <methods>;
}

-keep class com.meritumads.settings.MsAdsPreRollService{
    public <methods>;
}

-keep class com.meritumads.elements.MsAdsPreRollHolder{
    public <methods>;
}

-keep class com.meritumads.settings.MsAdsPreRollStatus{
	*;
}

-dontwarn org.xmlpull.v1.**
-dontnote org.xmlpull.v1.**
-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }

 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items). 
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call 
 -keep,allowobfuscation,allowshrinking class retrofit2.Response


  
 # With R8 full mode generic signatures are stripped for classes that are not 
 # kept. Suspend functions are wrapped in continuations where the type argument 
 # is used. 
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation 
 
-keep class com.meritumads.pojo.** { *; }
-keepattributes Signature








