plugins {
    id("scalaquest.libraries-conventions")
}

dependencies {
    // todo more module-specific dependencies here

    // prolog
    implementation("it.unibo.alice.tuprolog:tuprolog:_")

    // lens?
    implementation("org.typelevel:cats-effect_2.13:_")
}