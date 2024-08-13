import 'package:dynamic_color/dynamic_color.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:toaster/features/common/provider/brightness_provider.dart';
import 'package:toaster/routes/router.dart';

class ToasterApp extends ConsumerWidget {
  const ToasterApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final systemTheme = ref.watch(systemThemeProvider);
    ref.read(systemThemeProvider.notifier).listen(context);

    final routerConfig = ref.watch(routerProvider);
    return DynamicColorBuilder(
      builder: (lightDynamic, darkDynamic) {
        return MaterialApp.router(
          onGenerateTitle: (context) =>
              AppLocalizations.of(context)?.appName ?? '',
          localizationsDelegates: const [
            AppLocalizations.delegate,
            GlobalMaterialLocalizations.delegate,
            GlobalWidgetsLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
          ],
          supportedLocales: const [
            Locale('en'),
            Locale('de'),
            Locale('nl'),
            Locale('ru'),
          ],
          theme: ThemeData(
            colorScheme: (systemTheme == Brightness.light
                    ? lightDynamic
                    : darkDynamic) ??
                ColorScheme.fromSeed(seedColor: Colors.brown).copyWith(
                  brightness: systemTheme,
                ),
            useMaterial3: true,
            visualDensity: VisualDensity.adaptivePlatformDensity,
          ),
          routerConfig: routerConfig,
        );
      },
    );
  }
}
