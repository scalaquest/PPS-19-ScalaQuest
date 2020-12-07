plugins {
    id("scalaquest.libraries-conventions")
}

dependencies {
    // the Scalaquest Shell version depends on the Core module of the project.
    api(project(":core"))

    // todo more module-specific dependencies here

    // library for functional IO.
    implementation("dev.zio:zio_2.13:_")
}
