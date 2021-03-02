<div align="center">
    <img src="https://raw.githubusercontent.com/scalaquest/PPS-19-ScalaQuest/gh-pages/images/logo.png" width="80">
</div>
<h1 align="center">ScalaQuest</h1>

<div align="center">

![Release](https://img.shields.io/github/v/release/scalaquest/PPS-19-ScalaQuest?label=Release)
![CI](https://img.shields.io/github/workflow/status/scalaquest/PPS-19-ScalaQuest/CI?label=CI)
![Repo website](https://img.shields.io/website?label=Project%20website&url=https%3A%2F%2Fscalaquest.github.io%2FPPS-19-ScalaQuest%2F)

![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=sqale_rating)
![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=reliability_rating)
![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=security_rating)
![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=alert_status)
![Coverage](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=coverage)
![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=scalaquest_PPS-19-ScalaQuest&metric=sqale_index)

ScalaQuest is an exam project for PPS and LSS courses in UNIBO, made by
[Riccardo Maldini](https://www.riccardomaldini.it),
[Filippo Nardini](https://github.com/lippo97),
[Jacopo Corina](https://github.com/corinz97),
[Francesco Gorini](https://github.com/francescogorini),
[Thomas Angelini](https://github.com/ThomasAngeliniUnibo).

</div>

![gameplay](https://raw.githubusercontent.com/scalaquest/PPS-19-ScalaQuest/gh-pages/images/gameplay.gif)

## The idea behind the project

The basic idea is to create a framework for the implementation of games of the
[Interactive Fiction](https://en.wikipedia.org/wiki/Interactive_fiction) genre,
in which the player can use text commands to influence the environment and
continue in the game (e.g. [Zork](https://en.wikipedia.org/wiki/Zork)).

## Official documentation

The official documentation of the project consists in:
- [PPS Report](https://scalaquest.github.io/Reports/docs/pps-report.html) and
  [LSS Report](https://scalaquest.github.io/Reports/docs/lss-report.html). The
  previous links shows a web version of reports, auto-generated from the
  [report sources](https://github.com/scalaquest/Reports). You can also
  consult them in a [LaTeX PDF](https://github.com/scalaquest/Reports/releases/latest)
  format;
- An [appendix document](https://scalaquest.github.io/Reports/docs/appendix.html)
  including the Scrum Overview, a summary of what has been done in each Scrum,
  with an index of linked issues and PR for each one. The document has been
  incrementally updated at each Scrum Review meeting. The document is also
  available as a [LaTeX PDF](https://github.com/scalaquest/Reports/releases/latest);
- The [project backlog](https://github.com/orgs/scalaquest/projects/1),
  publicly available as a GitHub Projects board;
- The official Scaladoc of the [Core](https://scalaquest.github.io/PPS-19-ScalaQuest/scaladoc/core/index.html)
  and [CLI](https://scalaquest.github.io/PPS-19-ScalaQuest/scaladoc/cli/index.html) modules;
- The official Coverage reports for the [Core](https://scalaquest.github.io/PPS-19-ScalaQuest/coverage/core/index.html)
  and [CLI](https://scalaquest.github.io/PPS-19-ScalaQuest/coverage/cli/index.html) modules.

## How to use the game creation framework?


Including the `cli` jar is sufficient to start programming your CLI game, as the
module includes `core` as an internal dependency. Importing the `core` solely is recommended only
if you want to define your personal game interface, different from the standard 
CLI (a web interface, for example).

You can use `core` or `cli` libraries by including them as dependencies for your Gradle project.
Add this lines to the `build.gradle.kts`:

```kotlin
dependencies {

  // Add the cli as dependency. This is sufficient to start 
  // building your game. Change the version to the latest available.
  implementation("io.github.scalaquest:cli:0.3.0")
  
  // Add the core as dependency. Change the version to the latest available.
  implementation("io.github.scalaquest:core:0.3.0")
}
```


You can find [here](https://mvnrepository.com/artifact/io.github.scalaquest/core) the latest version 
for the `core`, and [here](https://mvnrepository.com/artifact/io.github.scalaquest/cli) the latest 
version for the `cli`.

Alternatively, you download the libraries from the
[release page](https://github.com/scalaquest/PPS-19-ScalaQuest/releases/latest),
and include them as project dependencies.


## How to play the example games?

ScalaQuest includes some examples that shows how to correctly use the library.
You can use them as your game template, if you don't want to start from scratch.
Download the [latest EscapeRoom, WizardQuest or PokeQuest releases](https://github.com/scalaquest/PPS-19-ScalaQuest/releases/latest),
unzip the chosen game and run it from your terminal (`bin/gamename` for Unix
devices, `bin/gamename.bat` for Windows ones).
