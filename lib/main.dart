import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:toaster/sl.dart';
import 'package:toaster/toaster_app.dart';

void main() {
  configureDependencies();

  runApp(
    const ProviderScope(child: ToasterApp()),
  );
}
