// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'toast_list_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$toastListHash() => r'cfa16c4fb97b1e5f5a8b8255077170f0223fd70c';

/// Copied from Dart SDK
class _SystemHash {
  _SystemHash._();

  static int combine(int hash, int value) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + value);
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x0007ffff & hash) << 10));
    return hash ^ (hash >> 6);
  }

  static int finish(int hash) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x03ffffff & hash) << 3));
    // ignore: parameter_assignments
    hash = hash ^ (hash >> 11);
    return 0x1fffffff & (hash + ((0x00003fff & hash) << 15));
  }
}

/// See also [toastList].
@ProviderFor(toastList)
const toastListProvider = ToastListFamily();

/// See also [toastList].
class ToastListFamily extends Family<AsyncValue<List<Toast>>> {
  /// See also [toastList].
  const ToastListFamily();

  /// See also [toastList].
  ToastListProvider call({
    App? app,
  }) {
    return ToastListProvider(
      app: app,
    );
  }

  @override
  ToastListProvider getProviderOverride(
    covariant ToastListProvider provider,
  ) {
    return call(
      app: provider.app,
    );
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'toastListProvider';
}

/// See also [toastList].
class ToastListProvider extends AutoDisposeFutureProvider<List<Toast>> {
  /// See also [toastList].
  ToastListProvider({
    App? app,
  }) : this._internal(
          (ref) => toastList(
            ref as ToastListRef,
            app: app,
          ),
          from: toastListProvider,
          name: r'toastListProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$toastListHash,
          dependencies: ToastListFamily._dependencies,
          allTransitiveDependencies: ToastListFamily._allTransitiveDependencies,
          app: app,
        );

  ToastListProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.app,
  }) : super.internal();

  final App? app;

  @override
  Override overrideWith(
    FutureOr<List<Toast>> Function(ToastListRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ToastListProvider._internal(
        (ref) => create(ref as ToastListRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        app: app,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<Toast>> createElement() {
    return _ToastListProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ToastListProvider && other.app == app;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, app.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ToastListRef on AutoDisposeFutureProviderRef<List<Toast>> {
  /// The parameter `app` of this provider.
  App? get app;
}

class _ToastListProviderElement
    extends AutoDisposeFutureProviderElement<List<Toast>> with ToastListRef {
  _ToastListProviderElement(super.provider);

  @override
  App? get app => (origin as ToastListProvider).app;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
