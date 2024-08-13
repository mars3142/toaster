import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'brightness_provider.g.dart';

@Riverpod(keepAlive: true)
class SystemTheme extends _$SystemTheme {
  @override
  Brightness build() {
    return SchedulerBinding.instance.platformDispatcher.platformBrightness;
  }

  void listen(BuildContext context) {
    var window = View.of(context).platformDispatcher;
    window.onPlatformBrightnessChanged = () {
      WidgetsBinding.instance.handlePlatformBrightnessChanged();
      state = window.platformBrightness;
    };
  }
}
