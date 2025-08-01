/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.kotlin.tree;

import org.junit.jupiter.api.Test;
import org.openrewrite.Issue;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.kotlin.Assertions.kotlin;

class ObjectExpressionTest implements RewriteTest {

    @Issue("https://github.com/openrewrite/rewrite-kotlin/issues/274")
    @Test
    void referenceToObjectField() {
        rewriteRun(
          kotlin(
            """
              val x = object : Iterator<Any> {
                  var elementsLeft = 1
                  override fun hasNext(): Boolean = elementsLeft > 0
                  override fun next(): String = "x"
              }
              """
          )
        );
    }

    @Test
    void objectInMethod() {
        rewriteRun(
          kotlin(
            """
              fun test() {
                  object : Runnable {
                      override fun run() {
                      }
                  }
              }
              """
          )
        );
    }

    @Test
    void anonymousObject() {
        rewriteRun(
          kotlin(
            """
              open class A
              interface B
              
              val foo: Any = object: A ( ), B { }
              """
          )
        );
    }
}
