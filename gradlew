#!/bin/sh
# GitHub Actions uses Gradle installed by the workflow.
exec gradle "$@"
