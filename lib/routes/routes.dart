import 'package:flutter/widgets.dart';
import 'package:go_router/go_router.dart';
import 'package:toaster/features/settings/setting_screen.dart';
import 'package:toaster/features/toast_detail/toast_detail_screen.dart';
import 'package:toaster/features/toast_list/toast_list_screen.dart';

part 'routes.g.dart';

@TypedGoRoute<ToastListRoute>(
  path: '/',
  routes: [
    TypedGoRoute<ToastDetailRoute>(
      path: 'toast/:id',
    ),
  ],
)
class ToastListRoute extends GoRouteData {
  const ToastListRoute();

  @override
  Widget build(BuildContext context, GoRouterState state) =>
      const ToastListScreen();
}

@TypedGoRoute<SettingsRoute>(
  path: '/setting',
)
class SettingsRoute extends GoRouteData {
  const SettingsRoute();

  @override
  Widget build(BuildContext context, GoRouterState state) =>
      const SettingsScreen();
}

class ToastDetailRoute extends GoRouteData {
  const ToastDetailRoute({required this.id});

  final String id;

  @override
  Widget build(BuildContext context, GoRouterState state) =>
      ToastDetailScreen(id: id);
}
