import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:riverpod_context/riverpod_context.dart';
import 'package:toaster/features/common/provider/brightness_provider.dart';
import 'package:toaster/routes/app_router.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:dynamic_color/dynamic_color.dart';

class ToasterApp extends ConsumerWidget {
  ToasterApp({super.key});

  final appRouter = AppRouter();

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return ref.read(brightnessProvider).when(
          data: (data) {
            return DynamicColorBuilder(
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
                    colorScheme: (data == Brightness.light ? lightDynamic : darkDynamic) ??
                        ColorScheme.fromSeed(seedColor: Colors.brown).copyWith(
                          brightness: data,
                        ),
                    useMaterial3: true,
                    visualDensity: VisualDensity.adaptivePlatformDensity,
                  ),
                  routerConfig: appRouter.config(),
                );
              },
            );
          },
          error: (error, stackTrace) => const SizedBox.shrink(),
          loading: () => const SizedBox.shrink(),
        );
  }
}
