# Clean GUI — Fabric 1.21.11

Mod klientowy: klawisz P otwiera menu, P lub X zamyka je z animacją fade. Menu zawiera Settings, Modules, Cosmetics, Profile, FPS Boost i About.

## Jak uzyskać plik JAR bez instalowania Gradle na komputerze

1. Wejdź na https://github.com/ i zaloguj się (lub utwórz darmowe konto).
2. Kliknij **+** → **New repository**. Nazwij repozytorium `CleanGUI`, ustaw **Public** i kliknij **Create repository**.
3. Na stronie pustego repozytorium wybierz **Add file** → **Upload files**.
4. Rozpakuj ZIP projektu i przeciągnij CAŁĄ zawartość folderu `cleangui` (wraz z ukrytym folderem `.github`) na stronę GitHuba. Ważne: w głównym katalogu repozytorium muszą być `build.gradle`, `settings.gradle`, `gradle.properties`, `src` oraz `.github`.
5. Kliknij **Commit changes**.
6. Otwórz kartę **Actions**. Poczekaj, aż workflow **Build CleanGUI JAR** zakończy się zielonym znacznikiem.
7. Kliknij zakończony workflow, przewiń do **Artifacts** i pobierz `CleanGUI-Fabric-1.21.11`.
8. Rozpakuj pobrany artefakt ZIP. W środku będzie plik `.jar`. Włóż go do folderu `.minecraft/mods`.

Wymagania w grze: Minecraft Java 1.21.11, Fabric Loader oraz Fabric API dla 1.21.11. To mod klientowy.

## Kompilacja lokalna

Wymaga JDK 21 i Gradle 9.2.1. W katalogu projektu uruchom `gradle build`.
