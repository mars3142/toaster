import 'dart:math';

import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:toaster/features/common/models/app.dart';
import 'package:toaster/features/common/models/toast.dart';
import 'package:toaster/features/toast_list/toast_list_service.dart';
import 'package:toaster/sl.dart';

part 'toast_list_provider.g.dart';

@riverpod
Future<List<Toast>> toastList(
  ToastListRef ref, {
  App? app,
}) async {
  await Future.delayed(Duration(seconds: Random().nextInt(5)));
  if (Random().nextInt(100) >= 70) {
    throw Exception('Error while fetching data');
  }
  return getIt<ToastListService>().getToasts(app);
}
