plugins {
    id("scalaquest.libraries-conventions")
}

dependencies {
    // The library comprehends the Core module of the project.
    api(project(":core"))

    // Enables ZIO test framework.
    api("dev.zio:zio_2.13:_")
    testImplementation("dev.zio:zio-test_2.13:_")
    testImplementation("dev.zio:zio-test-junit_2.13:_")
}
