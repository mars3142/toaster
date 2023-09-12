import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:toaster/routes/app_router.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:dynamic_color/dynamic_color.dart';

class ToasterApp extends StatelessWidget {
  ToasterApp({super.key});

  final appRouter = AppRouter();

  @override
  Widget build(BuildContext context) {
    return ProviderScope(
      child: DynamicColorBuilder(
        builder: (lightDynamic, darkDynamic) {
          return MaterialApp.router(
            onGenerateTitle: (context) => AppLocalizations.of(context)?.appName ?? '',
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
              colorScheme: lightDynamic ?? ColorScheme.fromSeed(seedColor: Colors.brown),
              brightness: lightDynamic?.brightness ?? Brightness.light,
              useMaterial3: true,
            ),
            routerConfig: appRouter.config(),
          );
        },
      ),
    );
  }
}
