generate: update_packages translation
	fvm dart run build_runner build --delete-conflicting-outputs

watch: update_packages translation
	fvm dart run build_runner watch --delete-conflicting-outputs

update_packages:
	fvm flutter pub get

clean:
	fvm flutter clean

translation:
	fvm flutter gen-l10n
