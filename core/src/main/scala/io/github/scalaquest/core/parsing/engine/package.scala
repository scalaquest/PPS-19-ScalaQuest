package io.github.scalaquest.core.parsing

package object engine {
  type Library = BaseLibrary with TuProlog

  type Theory = BaseTheory with TuPrologConverter
}
