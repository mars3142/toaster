// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// AutoRouterGenerator
// **************************************************************************

// ignore_for_file: type=lint
// coverage:ignore-file

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'package:auto_route/auto_route.dart' as _i5;
import 'package:flutter/material.dart' as _i6;
import 'package:toaster/features/home/home_screen.dart' as _i1;
import 'package:toaster/features/settings/setting_screen.dart' as _i2;
import 'package:toaster/features/toast_detail/toast_detail_screen.dart' as _i3;
import 'package:toaster/features/toast_list/toast_list_screen.dart' as _i4;

abstract class $AppRouter extends _i5.RootStackRouter {
  $AppRouter({super.navigatorKey});

  @override
  final Map<String, _i5.PageFactory> pagesMap = {
    HomeRoute.name: (routeData) {
      return _i5.AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const _i1.HomeScreen(),
      );
    },
    SettingsRoute.name: (routeData) {
      return _i5.AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const _i2.SettingsScreen(),
      );
    },
    ToastDetailRoute.name: (routeData) {
      final pathParams = routeData.inheritedPathParams;
      final args = routeData.argsAs<ToastDetailRouteArgs>(
          orElse: () => ToastDetailRouteArgs(id: pathParams.getString('id')));
      return _i5.AutoRoutePage<dynamic>(
        routeData: routeData,
        child: _i3.ToastDetailScreen(
          key: args.key,
          id: args.id,
        ),
      );
    },
    ToastListRoute.name: (routeData) {
      return _i5.AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const _i4.ToastListScreen(),
      );
    },
  };
}

/// generated route for
/// [_i1.HomeScreen]
class HomeRoute extends _i5.PageRouteInfo<void> {
  const HomeRoute({List<_i5.PageRouteInfo>? children})
      : super(
          HomeRoute.name,
          initialChildren: children,
        );

  static const String name = 'HomeRoute';

  static const _i5.PageInfo<void> page = _i5.PageInfo<void>(name);
}

/// generated route for
/// [_i2.SettingsScreen]
class SettingsRoute extends _i5.PageRouteInfo<void> {
  const SettingsRoute({List<_i5.PageRouteInfo>? children})
      : super(
          SettingsRoute.name,
          initialChildren: children,
        );

  static const String name = 'SettingsRoute';

  static const _i5.PageInfo<void> page = _i5.PageInfo<void>(name);
}

/// generated route for
/// [_i3.ToastDetailScreen]
class ToastDetailRoute extends _i5.PageRouteInfo<ToastDetailRouteArgs> {
  ToastDetailRoute({
    _i6.Key? key,
    required String id,
    List<_i5.PageRouteInfo>? children,
  }) : super(
          ToastDetailRoute.name,
          args: ToastDetailRouteArgs(
            key: key,
            id: id,
          ),
          rawPathParams: {'id': id},
          initialChildren: children,
        );

  static const String name = 'ToastDetailRoute';

  static const _i5.PageInfo<ToastDetailRouteArgs> page =
      _i5.PageInfo<ToastDetailRouteArgs>(name);
}

class ToastDetailRouteArgs {
  const ToastDetailRouteArgs({
    this.key,
    required this.id,
  });

  final _i6.Key? key;

  final String id;

  @override
  String toString() {
    return 'ToastDetailRouteArgs{key: $key, id: $id}';
  }
}

/// generated route for
/// [_i4.ToastListScreen]
class ToastListRoute extends _i5.PageRouteInfo<void> {
  const ToastListRoute({List<_i5.PageRouteInfo>? children})
      : super(
          ToastListRoute.name,
          initialChildren: children,
        );

  static const String name = 'ToastListRoute';

  static const _i5.PageInfo<void> page = _i5.PageInfo<void>(name);
}
