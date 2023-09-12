import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import 'toast_detail_provider.dart';

@RoutePage()
class ToastDetailScreen extends ConsumerWidget {
  const ToastDetailScreen({
    super.key,
    @pathParam required this.id,
  });

  final String id;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final details = ref.watch(toastDetailProvider(id));
    return details.when(
      loading: () => const _DetailPage(
        title: 'Loading',
        child: Center(
          child: CircularProgressIndicator(),
        ),
      ),
      data: (data) => _DetailPage(
        title: 'Detail ${data.id}',
        actions: [
          IconButton(
            onPressed: () {},
            icon: Icon(Icons.delete),
          ),
        ],
        child: const Center(
          child: Text('Detail'),
        ),
      ),
      error: (error, stackTrace) => _DetailPage(
        title: 'Error',
        child: Center(
          child: Text(error.toString()),
        ),
      ),
    );
  }
}

class _DetailPage extends StatelessWidget {
  const _DetailPage({
    required this.title,
    required this.child,
    this.actions,
  });

  final String title;
  final Widget child;
  final List<Widget>? actions;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
        actions: actions,
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: child,
    );
  }
}
