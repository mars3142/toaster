import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

final brightnessProvider = FutureProvider<Brightness>(
  (ref) => SchedulerBinding.instance.platformDispatcher.platformBrightness,
);
