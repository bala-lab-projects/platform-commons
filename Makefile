.PHONY: help build test clean publish-local check-modules format check-format spotless-apply spotless-check pre-commit-install pre-commit-run

help:
	@echo "Available Commands:"
	@echo "  build               - Build all modules"
	@echo "  test                - Run tests"
	@echo "  clean               - Clean build artifacts"
	@echo "  publish-local       - Publish to Maven Local"
	@echo "  check-modules       - List all modules"
	@echo "  format              - Format Kotlin code (spotless + ktlint)"
	@echo "  check-format        - Check code formatting"
	@echo "  spotless-apply      - Auto-fix spotless violations"
	@echo "  spotless-check      - Check for spotless violations"
	@echo "  pre-commit-install  - Install pre-commit hooks (includes Spotless + ktlint)"
	@echo "  pre-commit-run      - Run pre-commit on all files (Spotless, ktlint, linting)"

build:
	./gradlew build

test:
	./gradlew test

clean:
	./gradlew clean

publish-local:
	./gradlew publishToMavenLocal
	@echo "✅ Published to Maven Local: ~/.m2/repository/io/github/platform/"

check-modules:
	./gradlew projects

format:
	@echo "Formatting Kotlin code with spotless..."
	@./gradlew spotlessApply
	@echo "✅ Formatting complete"

check-format:
	@echo "Checking code formatting..."
	@./gradlew spotlessCheck

spotless-apply:
	@echo "Applying spotless formatting..."
	@./gradlew spotlessApply
	@echo "✅ Spotless formatting applied"

spotless-check:
	@echo "Checking spotless formatting..."
	@./gradlew spotlessCheck

pre-commit-install:
	@echo "Installing pre-commit hooks..."
	@command -v pre-commit >/dev/null 2>&1 || { echo "❌ pre-commit not found. Install with: pip install pre-commit"; exit 1; }
	@pre-commit install
	@pre-commit install --hook-type commit-msg
	@echo "✅ Pre-commit hooks installed"

pre-commit-run:
	@echo "Running pre-commit on all files..."
	@pre-commit run --all-files
