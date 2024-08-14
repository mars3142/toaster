import 'package:flutter/material.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:toaster/features/common/provider/brightness_provider.dart';

part 'theme_color_provider.g.dart';

@riverpod
class ThemeColor extends _$ThemeColor {
  @override
  ColorScheme? build() {
    final systemTheme = ref.watch(systemThemeProvider);

    return ColorScheme.fromSeed(seedColor: Colors.blue).copyWith(
      brightness: systemTheme,
    );
  }
}
