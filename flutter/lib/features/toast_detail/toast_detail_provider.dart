import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:toaster/features/common/models/app.dart';
import 'package:toaster/features/common/models/toast.dart';

final toastDetailProvider = FutureProvider.family<Toast, String>((ref, id) async {
  await Future.delayed(const Duration(seconds: 5));
  return Toast(id: id, app: const App(packageName: 'com.example.toaster', name: 'Toaster'));
});
