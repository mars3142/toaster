import 'package:auto_route/annotations.dart';
import 'package:flutter/material.dart';
import 'package:toaster/features/toast_list/widgets/toast_card.dart';

@RoutePage()
class ToastListScreen extends StatelessWidget {
  const ToastListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('All Apps'),
        actions: [
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.filter_list),
          ),
        ],
      ),
      body: CustomScrollView(
        slivers: [
          SliverList(
            delegate: SliverChildBuilderDelegate(
              (context, index) {
                return const Padding(
                  padding: EdgeInsets.all(8.0),
                  child: ToastCard(),
                );
              },
              childCount: 100,
            ),
          ),
          const SliverToBoxAdapter(
            child: SizedBox(
              height: 16.0,
            ),
          ),
        ],
      ),
    );
  }
}
