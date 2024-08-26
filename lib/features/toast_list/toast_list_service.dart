import 'dart:async';

import 'package:injectable/injectable.dart';
import 'package:intl/intl.dart';
import 'package:toaster/features/common/models/app.dart';
import 'package:toaster/features/common/models/toast.dart';

@injectable
class ToastListService {
  Future<List<Toast>> getToasts(App? app) async {
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
  }
}
