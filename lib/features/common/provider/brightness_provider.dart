import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'brightness_provider.g.dart';

@riverpod
class SystemTheme extends _$SystemTheme {
  @override
  Brightness build() {
    return SchedulerBinding.instance.platformDispatcher.platformBrightness;
  }

  void listen(BuildContext context) {
    var window = View.of(context).platformDispatcher;
    window.onPlatformBrightnessChanged = () => _setState(window);
  }

  void _setState(PlatformDispatcher window) {
    WidgetsBinding.instance.handlePlatformBrightnessChanged();
    state = window.platformBrightness;
  }
}
