# Development

## Prerequisites

- JDK 23 (`java -version` and `javac -version`). The source uses Java 23 language features.
- JavaFX SDK 25.0.2 for your OS and CPU architecture.
- Gson JAR, tested locally with 2.11.0.
- Python 3 for the optional portable command helper.

The helper invokes `javac`, `java`, and `javadoc` directly. It does not download dependencies or use a build framework. Set `JAVA_HOME` if several JDKs are installed.

## macOS / Linux

From the repository root:

```sh
export JAVAFX_LIB="/path/to/javafx-sdk-25.0.2/lib"
export GSON_JAR="/path/to/gson-2.11.0.jar"
python3 scripts/build.py run
```

## Windows PowerShell

```powershell
$env:JAVAFX_LIB = "C:\Libraries\javafx-sdk-25.0.2\lib"
$env:GSON_JAR = "C:\Libraries\gson-2.11.0.jar"
py -3 scripts/build.py run
```

Use the same environment variables with `build`, `test`, `smoke`, `junit`, or `docs` instead of `run`.

## IntelliJ IDEA

1. Open the repository and select JDK 23 as the project SDK and language level.
2. Mark `EchoShift/src` as a source root and `EchoShift/resources` as a resource root.
3. Add the JavaFX SDK's `lib` directory and Gson JAR as project libraries.
4. Exclude the three `*Test.java` files from application compilation, or add JUnit 5 to compile them.
5. Create an Application run configuration for `echoshift.App`.
6. Set the working directory to the **repository root**, which contains `data/`.
7. Add these VM options, adjusting the SDK path:

```text
--module-path "/path/to/javafx-sdk-25.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.media
```

The helper is also usable in IntelliJ's terminal and consistently copies all resources.

## Direct Java commands

For a POSIX shell, the following is equivalent to the application build. Run from the repository root, with the variables above set. Paths containing spaces are quoted in the argument file.

```sh
mkdir -p build/classes
find EchoShift/src -name '*.java' ! -name '*Test.java' | sed 's/.*/"&"/' > build/sources.txt
javac --release 23 -encoding UTF-8 --module-path "$JAVAFX_LIB" \
  --add-modules javafx.controls,javafx.fxml,javafx.media \
  -cp "$GSON_JAR" -d build/classes @build/sources.txt
cp -R EchoShift/src/echoshift/images EchoShift/src/echoshift/sounds \
  EchoShift/src/echoshift/styles EchoShift/src/echoshift/text build/classes/echoshift/
cp -R EchoShift/resources/. build/classes/
java --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml,javafx.media \
  -cp "build/classes:$GSON_JAR" echoshift.App
```

## Tests

`python3 scripts/build.py test` runs dependency-free regression checks for typing, word-bank resources, statistics averages, night completion, accounts, saves, and inventory. All save writes occur in a temporary directory.

`python3 scripts/build.py smoke` opens the actual JavaFX menu and gameplay screen, ends a round, verifies one-time saving and return navigation, then closes. It requires a graphical desktop and native JavaFX libraries. Screenshots are written to `build/screenshots/`.

The original JUnit 5 tests remain in `EchoShift/src/echoshift/backend` and `EchoShift/src/echoshift/nightscripts`. Supply a JUnit Platform Console Standalone 1.x JAR:

```sh
export JUNIT_JAR="/path/to/junit-platform-console-standalone-1.11.4.jar"
python3 scripts/build.py junit
```

The night tests initialize JavaFX and require a display. On headless Linux, run UI tests under `xvfb-run -a`. The GitHub workflow uses the same plain compiler helper.

## API documentation

```sh
python3 scripts/build.py docs
```

Open `build/docs/index.html` locally. `EchoShift/docs/` is the preserved historical version and does not reflect subsequent source fixes.

## Source map

| Directory | Responsibility |
| --- | --- |
| `EchoShift/src/echoshift/UI` | JavaFX views and map rendering |
| `EchoShift/src/echoshift/controllers` | Screen navigation and event handlers |
| `EchoShift/src/echoshift/nightscripts` | Rounds, enemy timing, typing actions |
| `EchoShift/src/echoshift/backend` | Factory map and enemy behavior |
| `EchoShift/src/graph` | Graph data structures |
| `EchoShift/src/typing` | Word selection, character matching, typing metrics |
| `EchoShift/src/echoshift/services` | JSON account, statistics, and inventory storage |
| `EchoShift/src/echoshift/models` | Account and session data |

## Local data and troubleshooting

The game reads and writes `data/` relative to its working directory. The helper always launches the app from the repository root; it launches tests from temporary directories. Back up `data/` before resetting saves. Existing tracked demo saves can change during play; review them before committing. Newly created local save files are ignored.

- **Missing JavaFX classes:** check that `JAVAFX_LIB` points to the SDK's `lib` directory.
- **Native-library error:** use a JavaFX SDK matching both your OS and the architecture of your JDK.
- **Missing background or word bank:** use the helper to copy non-Java resources into the build output.
- **Accounts not found in an IDE:** correct the working directory to the repository root.
- **Unchecked-operation compiler note:** the original graph implementation uses unchecked generics; this is a warning, not a failed build.
