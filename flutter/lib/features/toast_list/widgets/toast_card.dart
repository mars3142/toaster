import 'dart:math';

import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:riverpod_context/riverpod_context.dart';
import 'package:toaster/features/common/models/toast.dart';
import 'package:toaster/features/toast_detail/toast_detail_provider.dart';
import 'package:toaster/routes/app_router.gr.dart';

class ToastCard extends ConsumerWidget {
  const ToastCard({
    super.key,
    required this.toast,
  });

  final Toast toast;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return GestureDetector(
      onTap: () {
        if (toast.message == 'Toast 1') {
          context.router.push(ToastDetailRoute(id: Random().nextInt(20).toString()));
          return;
        }
        context.router.navigate(const SettingsRoute());
      },
      child: Card(
        elevation: 8.0,
        child: Stack(
          children: [
            Positioned(
              right: 0.0,
              top: 32.0,
              left: 48.0,
              child: SizedBox(
                height: 32.0 + 64.0,
                child: ClipRRect(
                  borderRadius: const BorderRadius.only(
                    bottomRight: Radius.circular(12.0),
                  ),
                  child: ColoredBox(
                    color: Theme.of(context).colorScheme.primary,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 48.0, right: 8.0, top: 8.0, bottom: 8.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            toast.message ?? '',
                            style: TextStyle(color: Theme.of(context).colorScheme.onPrimary),
                          ),
                          const Spacer(),
                          Align(
                            alignment: Alignment.centerRight,
                            child: Text(
                              toast.app.name,
                              style: TextStyle(color: Theme.of(context).colorScheme.onPrimary),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ),
            const Padding(
              padding: EdgeInsets.only(left: 16.0, right: 16.0, bottom: 16.0, top: 32.0 + 16.0),
              child: SizedBox(
                width: 64.0,
                height: 64.0,
                child: Stack(
                  fit: StackFit.expand,
                  children: [
                    ColoredBox(color: Colors.white),
                    Placeholder(),
                  ],
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(left: 16.0, top: 4.0),
              child: Text(
                toast.createdAt,
                style: Theme.of(context).textTheme.bodyLarge?.copyWith(color: Theme.of(context).colorScheme.primary),
              ),
            )
          ],
        ),
      ),
    );
  }
}
