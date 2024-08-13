import 'package:toaster/features/common/models/app.dart';

class Toast {
  final String id;
  final String createdAt;
  final App app;
  final String? message;

  const Toast({
    required this.id,
    required this.createdAt,
    required this.app,
    this.message,
  });
}
