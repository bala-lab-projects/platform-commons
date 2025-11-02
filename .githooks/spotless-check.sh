#!/bin/bash
# Pre-commit hook to apply Spotless formatting
# Simply runs spotlessApply - pre-commit will handle file staging

./gradlew spotlessApply --quiet 2>&1
exit 0
