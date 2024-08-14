import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:toaster/routes/routes.dart';

class SettingsScreen extends StatelessWidget {
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: GestureDetector(
        onTap: () => context.go(const ToastDetailRoute(id: "9999").location),
        child: const Text("Click here"),
      ),
    );
  }
}
