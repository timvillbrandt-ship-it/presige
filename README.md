# Prestige Client (Fabric, MC 26.2)

Voraussetzungen: JDK 25, Gradle >= 9.1.

    gradle wrapper      # einmalig, falls kein gradlew vorhanden
    ./gradlew build     # Jar liegt in build/libs/
    ./gradlew runClient # zum Testen

Steuerung: Rechts-Shift öffnet die ClickGUI, Linksklick auf ein Modul schaltet es um.

Neues Modul: Klasse in `module/impl/` von `Module` ableiten, in `ModuleManager` registrieren.

Hinweis: Der Code wurde ohne Compiler geschrieben. Wenn eine 26.2-Signatur nicht passt,
ist das meist ein Einzeiler (siehe Chat).
