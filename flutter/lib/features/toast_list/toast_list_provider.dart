import 'dart:math';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:intl/intl.dart';
import 'package:toaster/features/common/models/app.dart';
import 'package:toaster/features/common/models/toast.dart';

final toastListProvider = FutureProvider<List<Toast>>(
  (ref) async {
    await Future.delayed(Duration(seconds: Random().nextInt(5)));
    if (Random().nextInt(100) >= 70) {
      throw Exception('Error while fetching data');
    }
    final formatter = DateFormat.yMd().add_Hms();
    return List.generate(
      20,
      (index) => Toast(
        id: '$index',
        app: const App(packageName: '', name: 'Toaster'),
        message: 'Toast $index',
        createdAt: formatter.format(DateTime.now()),
      ),
    );
  },
);
