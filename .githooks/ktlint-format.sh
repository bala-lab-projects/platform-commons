#!/bin/bash
# Pre-commit hook to format Kotlin files with ktlint

# Use existing ktlint jar
KTLINT_VERSION="1.7.1"
CACHE_DIR="${HOME}/.cache/pre-commit"
KTLINT_JAR="${CACHE_DIR}/ktlint${KTLINT_VERSION}.jar"

# Download ktlint if not present
if [ ! -f "$KTLINT_JAR" ]; then
    mkdir -p "$CACHE_DIR"
    echo "Downloading ktlint ${KTLINT_VERSION}..."
    curl -sSL "https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION}/ktlint" \
        -o "$KTLINT_JAR" && chmod +x "$KTLINT_JAR"
fi

# Format all passed files
exit_code=0
for file in "$@"; do
    if [[ "$file" == *.kt || "$file" == *.kts ]]; then
        java -jar "$KTLINT_JAR" --format "$file" || exit_code=1
    fi
done

exit $exit_code
