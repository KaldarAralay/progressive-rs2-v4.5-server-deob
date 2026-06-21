# Easy RS2 Progressive Singleplayer Deob

## About the Project

This is a deobfuscated source release of Mige5's Easy RS2 Progressive Singleplayer server.

The original project was abandoned by Mige5 years ago. The goal of this deob is to make the server easier to read, build, debug, and continue developing so people can maintain their own forks instead of being stuck with only the original obfuscated jar.

Original Rune-Server release thread:
https://rune-server.org/threads/easyrs2-progressive-singleplayer.701962/

No ownership of the original project is claimed here. This deob is meant as a preservation and development aid for people who already use the original release.

## Project Layout

- `deobfuscated_source/` - the editable Java source tree.
- `Server_lib/` - required dependency jars.
- `data/` - server data, settings, saves, definitions, and runtime files.
- `Config.cfg` - main server config.
- `Server-deob-compiled.jar` - current rebuilt runnable jar.
- `Server-deob-pass1.jar` - intermediate deob jar/reference artifact; do not use this as the normal runtime jar.
- `tools/` - decompile, repair, and rebuild helper scripts.
- `qa-output/` - local compile/test output and verification artifacts.

## IDE Setup

Open the `Server` folder as a Java project in your IDE.

Use `deobfuscated_source` as the source root, then add every jar in `Server_lib` as project libraries. Use a full JDK, not just a JRE, because rebuilding needs both `javac` and `jar`.

Recommended run configuration:

- Main class: `com.rs2.launcher.ControlPanel`
- Working directory: the `Server` folder
- VM options: `-Xmx1024m`
- Classpath: compiled `deobfuscated_source` output plus all jars in `Server_lib`

The working directory matters because the server loads `Config.cfg`, `data/`, and other files by relative path.

## Running the Server

The known-good runtime jar is:

```bat
java -Xmx1024m -jar "Server-deob-compiled.jar"
```

`LAUNCH.bat` already points at this jar. After the control panel opens, press `Start Server`.

The original client should be run from the sibling `Client` folder. The current deob expects the same cache/build/config path as the original server setup.

## Rebuilding the Jar

From PowerShell in the `Server` folder:

```powershell
New-Item -ItemType Directory -Force .\qa-output\full-compile | Out-Null
Get-ChildItem .\deobfuscated_source -Recurse -Filter *.java |
    ForEach-Object { ".\" + (Resolve-Path $_.FullName -Relative) } |
    Set-Content -Encoding ASCII .\qa-output\all-sources.txt

javac -Xmaxerrs 5000 -classpath ".\Server-deob-pass1.jar;.\Server_lib\*" -d .\qa-output\full-compile "@.\qa-output\all-sources.txt"
powershell -ExecutionPolicy Bypass -File .\tools\build-compiled-server-jar.ps1
```

The build script packages `qa-output/full-compile` into `Server-deob-compiled.jar` with the correct launcher main class and library manifest.

## Notes for Development

This source is deobfuscated, not fully hand-restored. Some class, method, and local variable names may still be rough, and some methods still reflect CFR decompiler recovery patterns.

Keep behavior changes small and test against the original server whenever possible. Good first parity checks are login, saving/loading, movement, NPC combat, shops, banking, trading, quests, minigames, and restart persistence.

When changing network, login, cache, or config code, test with the stock client and the real launcher path. Those areas are sensitive to small mismatches.
