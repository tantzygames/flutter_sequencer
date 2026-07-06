package com.michaeljperri.flutter_sequencer

import android.content.Context
import android.content.res.AssetManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterSequencerPlugin */
public class FlutterSequencerPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android

  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel: MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext

    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_sequencer")
    channel.setMethodCallHandler(this);

  }

  override fun onMethodCall(call: MethodCall,result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "setupAssetManager") {
      setupAssetManager(context.assets)
      result.success(null)
    } else if (call.method == "listAssetDir") {
      val assetDir = call.argument<String>("assetDir")!!
      val extension = call.argument<String>("extension")
      val paths =
        context.assets
          .list("flutter_assets/$assetDir")!!
          .filter { fileName -> fileName.endsWith(".$extension") }
          .map { path -> "$assetDir/$path" }

      result.success(paths)
    } else if (call.method == "listAudioUnits") {
      result.success(emptyList<String>())
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  companion object {
    // 5. Removed registerWith and static context. Only keep the C++ library loader.
    init {
      System.loadLibrary("flutter_sequencer")
    }
  }

  private external fun setupAssetManager(assetManager: AssetManager)
}
