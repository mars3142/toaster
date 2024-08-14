import 'package:flutter/foundation.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:toaster/features/common/provider/navigator_key_provider.dart';
import 'package:toaster/features/home/home_screen.dart';
import 'package:toaster/routes/routes.dart';

part 'router.g.dart';

@riverpod
GoRouter router(RouterRef ref) {
  final navigatorKey = ref.watch(navigatorProvider);

  final router = GoRouter(
    navigatorKey: navigatorKey,
    debugLogDiagnostics: kDebugMode,
    initialLocation: const ToastListRoute().location,
    routes: <RouteBase>[
      StatefulShellRoute.indexedStack(
        builder: (context, state, body) {
          return HomeScreen(body: body);
        },
        branches: <StatefulShellBranch>[
          StatefulShellBranch(
            routes: [
              $toastListRoute,
            ],
          ),
          StatefulShellBranch(
            routes: [
              $settingsRoute,
            ],
          ),
        ],
      ),
    ],
  );

  return router;
}
