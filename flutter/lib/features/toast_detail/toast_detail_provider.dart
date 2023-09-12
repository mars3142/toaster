import 'dart:math';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:intl/intl.dart';
import 'package:toaster/features/common/models/app.dart';
import 'package:toaster/features/common/models/toast.dart';

final toastDetailProvider = FutureProvider.family<Toast, String>((ref, id) async {
  await Future.delayed(Duration(seconds: Random().nextInt(5)));
  if (Random().nextInt(100) >= 70) {
    throw Exception('Error while fetching data');
  }
  final formatter = DateFormat.yMd().add_Hms();
  return Toast(
    id: id,
    app: const App(packageName: 'com.example.toaster', name: 'Toaster'),
    message: 'Toast $id',
    createdAt: formatter.format(DateTime.now()),
  );
});
