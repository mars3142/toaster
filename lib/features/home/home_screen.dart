import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final items = <BottomNavigationBarItem>[
      BottomNavigationBarItem(
        icon: const Icon(Icons.home),
        label: AppLocalizations.of(context)?.toasts ?? '',
      ),
      BottomNavigationBarItem(
        icon: const Icon(Icons.settings),
        label: AppLocalizations.of(context)?.settings ?? '',
      ),
    ];

    return Scaffold(
      body: const Placeholder(),
      bottomNavigationBar:
          (Theme.of(context).platform == TargetPlatform.android)
              ? BottomNavigationBar(
                  currentIndex: 1,
                  onTap: (i) {},
                  items: items,
                )
              : CupertinoTabBar(
                  currentIndex: 1,
                  onTap: (i) {},
                  items: items,
                ),
    );
  }
}
