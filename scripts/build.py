#!/usr/bin/env python3
"""Plain javac/java build. Python only handles portable file paths and resource copying."""
import argparse
import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / 'EchoShift/src'
OUT = ROOT / 'build/classes'


def run(args, cwd=ROOT):
    subprocess.run([str(arg) for arg in args], cwd=cwd, check=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('command', choices=['build', 'run', 'test', 'junit', 'docs', 'smoke'], nargs='?', default='build')
    args = parser.parse_args()
    fx = Path(os.environ.get('JAVAFX_LIB', 'lib/javafx/lib')).expanduser().resolve()
    gson = Path(os.environ.get('GSON_JAR', 'lib/gson.jar')).expanduser().resolve()
    if not (fx / 'javafx.controls.jar').is_file() or not gson.is_file():
        parser.error('Set JAVAFX_LIB to your JavaFX SDK lib folder and GSON_JAR to your Gson JAR. See docs/DEVELOPMENT.md.')
    java_home = os.environ.get('JAVA_HOME')
    def tool(name):
        return str(Path(java_home) / 'bin' / name) if java_home else name
    modules = ['--module-path', fx, '--add-modules', 'javafx.controls,javafx.fxml,javafx.media,javafx.swing']
    sources = sorted(p for p in SRC.rglob('*.java') if not p.name.endswith('Test.java'))
    if OUT.exists():
        shutil.rmtree(OUT)
    OUT.mkdir(parents=True)
    # Argument files avoid Windows command-length limits. javac expects forward slashes.
    def compile_files(files, destination, classpath):
        argfile = ROOT / 'build/sources.txt'
        argfile.write_text('\n'.join('"' + p.as_posix() + '"' for p in files), encoding='utf-8')
        run([tool('javac'), '--release', '23', '-encoding', 'UTF-8', *modules,
             '-cp', classpath, '-d', destination, '@' + str(argfile)])
    compile_files(sources, OUT, str(gson))
    for source_root in [SRC, ROOT / 'EchoShift/resources']:
        for source in source_root.rglob('*'):
            if source.is_file() and source.suffix != '.java':
                target = OUT / source.relative_to(source_root)
                target.parent.mkdir(parents=True, exist_ok=True)
                shutil.copy2(source, target)
    print(f'Compiled {len(sources)} Java files and copied resources.', flush=True)
    classpath = os.pathsep.join([str(OUT), str(gson)])
    if args.command == 'run':
        run([tool('java'), *modules, '-cp', classpath, 'echoshift.App'])
    elif args.command in ('test', 'smoke'):
        tests = ROOT / 'build/tests'
        tests.mkdir(exist_ok=True)
        compile_files(sorted((ROOT / 'tests').glob('*.java')), tests, classpath)
        with tempfile.TemporaryDirectory(prefix='echoshift-tests-') as isolated:
            run([tool('java'), *modules, '-ea', '-cp', classpath + os.pathsep + str(tests), 'SmokeTest' if args.command == 'smoke' else 'RegressionTests'], cwd=isolated)
    elif args.command == 'junit':
        junit = Path(os.environ.get('JUNIT_JAR', 'lib/junit-platform-console-standalone.jar')).expanduser().resolve()
        if not junit.is_file():
            parser.error('Set JUNIT_JAR to the JUnit Platform Console Standalone JAR (JUnit 5).')
        tests = ROOT / 'build/junit'
        tests.mkdir(exist_ok=True)
        compile_files(sorted(SRC.rglob('*Test.java')), tests, classpath + os.pathsep + str(junit))
        with tempfile.TemporaryDirectory(prefix='echoshift-junit-') as isolated:
            run([tool('java'), *modules, '-jar', junit, 'execute', '--class-path', classpath + os.pathsep + str(tests), '--scan-class-path'], cwd=isolated)
    elif args.command == 'docs':
        argfile = ROOT / 'build/sources.txt'
        run([tool('javadoc'), *modules, '-classpath', gson,
             '-d', ROOT / 'build/docs', '-Xdoclint:none', '@' + str(argfile)])



if __name__ == '__main__':
    try:
        main()
    except (subprocess.CalledProcessError, OSError) as error:
        print(f'Build failed: {error}', file=sys.stderr)
        sys.exit(1)
