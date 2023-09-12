import 'package:auto_route/annotations.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:toaster/features/toast_list/toast_list_provider.dart';
import 'package:toaster/features/toast_list/widgets/toast_card.dart';

@RoutePage()
class ToastListScreen extends ConsumerWidget {
  const ToastListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('All Apps'),
        actions: [
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.filter_list),
          ),
        ],
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: ref.watch(toastListProvider).when(
            loading: () => const Center(child: CircularProgressIndicator()),
            data: (data) => RefreshIndicator.adaptive(
              onRefresh: () async {
                ref.refresh(toastListProvider);
              },
              child: CustomScrollView(
                slivers: [
                  SliverList(
                    delegate: SliverChildBuilderDelegate(
                      (context, index) {
                        final item = data[index];
                        return Padding(
                          padding: EdgeInsets.symmetric(
                            vertical: 8.0,
                            horizontal: 8.0 + MediaQuery.of(context).padding.left,
                          ),
                          child: ToastCard(key: Key(item.id), toast: item),
                        );
                      },
                      childCount: data.length,
                    ),
                  ),
                  const SliverToBoxAdapter(
                    child: SizedBox(
                      height: 16.0,
                    ),
                  ),
                ],
              ),
            ),
            error: (error, stackTrace) => Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(error.toString()),
                  TextButton(onPressed: () => ref.refresh(toastListProvider), child: const Text('Retry')),
                ],
              ),
            ),
          ),
    );
  }
}
