import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:toaster/features/common/provider/brightness_provider.dart';
import 'package:toaster/features/common/provider/theme_color_provider.dart';
import 'package:toaster/routes/router.dart';

class ToasterApp extends ConsumerWidget {
  const ToasterApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    ref.read(systemThemeProvider.notifier).listen(context);

    final routerConfig = ref.watch(routerProvider);
    final colorScheme = ref.watch(themeColorProvider);
    return MaterialApp.router(
      onGenerateTitle: (context) => AppLocalizations.of(context)?.appName ?? '',
      localizationsDelegates: const [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: AppLocalizations.supportedLocales,
      theme: ThemeData(
        colorScheme: colorScheme,
        useMaterial3: true,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      routerConfig: routerConfig,
    );
  }
}
