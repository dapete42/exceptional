[![Maven](https://github.com/dapete42/exceptional/actions/workflows/maven-verify.yml/badge.svg)](https://github.com/dapete42/locks/actions/workflows/maven-verify.yml)
[![CodeQL](https://github.com/dapete42/exceptional/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/dapete42/locks/actions/workflows/codeql-analysis.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/dapete42/exceptional/badge)](https://www.codefactor.io/repository/github/dapete42/locks)
[![Codecov](https://codecov.io/gh/dapete42/exceptional/graph/badge.svg?token=HzTCLFKSYD)](https://codecov.io/gh/dapete42/locks)

# Exceptional!

**TODO**

Requires JRE 17 or later to use and JDK 23 or later to build.

## Example

**TODO**

## Links

**TODO** not working yet

* [Maven Central](https://central.sonatype.com/artifact/net.dapete/exceptional)/[Maven Repository](https://mvnrepository.com/artifact/net.dapete/exceptional)
* [Javadoc](https://dapete42.github.io/exceptional/apidocs/)
* [Maven Site](https://dapete42.github.io/exceptional/)

## For Developers

### How to Release

Required permissions on GitHub:
* Push to `main`
* Push to `release`
* Create releases in GitHub

1. Create a new release using the gitflow-maven-plugin:
   ```
   ./mvnw gitflow:release
   ```
   You will be prompted for a version number. This will update the `main` and `release` branches and create a tag in the form `v1.2.3`.

2. Create a release in GitHub from this tag.

3. The release will be built and deployed to Maven Central.

### How to Create and Push Maven Site

**Note:** This happens automatically after a release has been created.

Required permissions on GitHub:
* Push to `gh-pages`

1. Check out current `release` branch.

2. Create and push the Maven Site to GitHub pages:
   ```
   ./mvnw site-deploy
   ```
