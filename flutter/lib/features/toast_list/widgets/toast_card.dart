import 'dart:math';

import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:toaster/routing/app_router.gr.dart';

class ToastCard extends StatelessWidget {
  const ToastCard({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        context.router.push(ToastDetailRoute(id: '${Random().nextInt(10)}'));
      },
      child: Card(
        child: Stack(
          children: [
            Positioned(
              right: 0.0,
              child: SizedBox(
                width: MediaQuery.of(context).size.width - 72.0,
                height: 50.0,
                child: const ColoredBox(color: Colors.red),
              ),
            ),
            const Padding(
              padding: EdgeInsets.all(16.0),
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
          ],
        ),
      ),
    );
  }
}
