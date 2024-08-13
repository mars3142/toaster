import 'package:flutter/material.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'navigator_key_provider.g.dart';

@riverpod
GlobalKey<NavigatorState> navigator(NavigatorRef ref) {
  return GlobalKey<NavigatorState>(debugLabel: 'routerKey');
}
