plugins {
    id("scalaquest.libraries-conventions")
}

dependencies {
    // the Scalaquest Shell version depends on the Core module of the project.
    api(project(":core"))

    api("dev.zio:zio_2.13:_")
    testImplementation("dev.zio:zio-test_2.13:_")
    testImplementation("dev.zio:zio-test-junit_2.13:_")
}
