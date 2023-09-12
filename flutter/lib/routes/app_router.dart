import 'package:auto_route/auto_route.dart';

import 'app_router.gr.dart';

@AutoRouterConfig()
class AppRouter extends $AppRouter {
  @override
  List<AutoRoute> get routes => [
        AutoRoute(
          page: HomeRoute.page,
          path: '/',
          children: [
            AutoRoute(
              page: ToastRouter.page,
              children: [
                AutoRoute(
                  page: ToastListRoute.page,
                  initial: true,
                ),
                AutoRoute(
                  page: ToastDetailRoute.page,
                ),
              ],
            ),
            AutoRoute(
              path: "settings",
              page: SettingsRoute.page,
            ),
          ],
        ),
      ];
}

@RoutePage(name: 'ToastRouter')
class ToastRouterScreen extends AutoRouter {
  const ToastRouterScreen({super.key});
}
