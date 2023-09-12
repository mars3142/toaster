generate: update_packages
	fvm dart run build_runner build --delete-conflicting-outputs

watch: update_packages
	fvm dart run build_runner watch --delete-conflicting-outputs

update_packages:
	fvm flutter pub get

clean:
	fvm flutter clean
