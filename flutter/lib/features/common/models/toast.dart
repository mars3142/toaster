import 'package:toaster/features/common/models/app.dart';

class Toast {
  final String id;
  final String? message;
  final App app;

  const Toast({
    required this.id,
    required this.app,
    this.message,
  });
}
