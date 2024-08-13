// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'toast_detail_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$toastDetailHash() => r'9f4f4823253c25b53703f0cfb1d184383d3fb867';

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

/// See also [toastDetail].
@ProviderFor(toastDetail)
const toastDetailProvider = ToastDetailFamily();

/// See also [toastDetail].
class ToastDetailFamily extends Family<AsyncValue<Toast>> {
  /// See also [toastDetail].
  const ToastDetailFamily();

  /// See also [toastDetail].
  ToastDetailProvider call(
    String id,
  ) {
    return ToastDetailProvider(
      id,
    );
  }

  @override
  ToastDetailProvider getProviderOverride(
    covariant ToastDetailProvider provider,
  ) {
    return call(
      provider.id,
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
  String? get name => r'toastDetailProvider';
}

/// See also [toastDetail].
class ToastDetailProvider extends AutoDisposeFutureProvider<Toast> {
  /// See also [toastDetail].
  ToastDetailProvider(
    String id,
  ) : this._internal(
          (ref) => toastDetail(
            ref as ToastDetailRef,
            id,
          ),
          from: toastDetailProvider,
          name: r'toastDetailProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$toastDetailHash,
          dependencies: ToastDetailFamily._dependencies,
          allTransitiveDependencies:
              ToastDetailFamily._allTransitiveDependencies,
          id: id,
        );

  ToastDetailProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.id,
  }) : super.internal();

  final String id;

  @override
  Override overrideWith(
    FutureOr<Toast> Function(ToastDetailRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ToastDetailProvider._internal(
        (ref) => create(ref as ToastDetailRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        id: id,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<Toast> createElement() {
    return _ToastDetailProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ToastDetailProvider && other.id == id;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, id.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ToastDetailRef on AutoDisposeFutureProviderRef<Toast> {
  /// The parameter `id` of this provider.
  String get id;
}

class _ToastDetailProviderElement
    extends AutoDisposeFutureProviderElement<Toast> with ToastDetailRef {
  _ToastDetailProviderElement(super.provider);

  @override
  String get id => (origin as ToastDetailProvider).id;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
