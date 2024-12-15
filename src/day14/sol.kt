package day14

import java.io.File

fun main() {
    val rr = RestroomRedoubt(File("src/day14/input.txt"), 103, 101)

    val safetyFactor = rr.part1()
    val minEntropyMoves = rr.part2()

    // 228690000
    println(safetyFactor)
    // 7093
    println(minEntropyMoves)
    /*
                             ^
                              ^
                                                                                     ^         ^
                                              ^                                         ^

 ^             ^

      ^     ^                                                       ^                   ^
^                                                                         ^
                                          ^

                                                                              ^                ^
                                ^   ^               ^                              ^
                    ^
                       ^                                                        ^
           ^           ^                     ^
                                                   ^          ^
                  ^           ^                                                                   ^

                 ^                           ^
                ^  ^                                ^


                            ^ ^     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 ^                                  ^                             ^
                                    ^                             ^
   ^                                ^                             ^                          ^
                          ^         ^                             ^                   ^
                                    ^              ^              ^
                               ^    ^             ^^^             ^
                                    ^            ^^^^^            ^
                                    ^           ^^^^^^^           ^                      ^        ^
                                    ^          ^^^^^^^^^          ^            ^                ^
                                    ^            ^^^^^            ^        ^
                                    ^           ^^^^^^^           ^
                     ^            ^ ^          ^^^^^^^^^          ^ ^                            ^
                                    ^         ^^^^^^^^^^^         ^
                                    ^        ^^^^^^^^^^^^^        ^
                                    ^          ^^^^^^^^^          ^ ^                  ^^
                                    ^         ^^^^^^^^^^^         ^
                                    ^        ^^^^^^^^^^^^^        ^
                      ^             ^       ^^^^^^^^^^^^^^^       ^
       ^                            ^      ^^^^^^^^^^^^^^^^^      ^
           ^  ^                     ^        ^^^^^^^^^^^^^        ^                ^
                                    ^       ^^^^^^^^^^^^^^^       ^                           ^
                                    ^      ^^^^^^^^^^^^^^^^^      ^     ^                 ^
      ^                             ^     ^^^^^^^^^^^^^^^^^^^     ^          ^
                ^                   ^    ^^^^^^^^^^^^^^^^^^^^^    ^                   ^
                                  ^ ^             ^^^             ^
                                    ^             ^^^             ^      ^
                                    ^             ^^^             ^
                                    ^                             ^
                            ^       ^                             ^
                                    ^                             ^
               ^                    ^                             ^              ^
              ^         ^           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^                     ^
                           ^                                        ^
                    ^     ^                ^                          ^
                            ^
                                                                                               ^
                         ^                                      ^
                                                                         ^   ^
                                            ^
                                                                             ^        ^


   ^                                            ^              ^


                                                                                    ^            ^
                                                                  ^
                                ^                       ^

                      ^
                              ^            ^
                                                              ^
                                                                ^
                                      ^                            ^                    ^


                                                                         ^                    ^
                                    ^       ^            ^       ^                         ^

                                   ^                                ^
                                                                          ^
                                                                     ^         ^                 ^
                                        ^           ^                   ^
                                                                 ^    ^
                                           ^

 ^    ^                                                                            ^
                                                         ^
                                                                                          ^
                                   ^                                                        ^
                        ^                                      ^                          ^ ^
       ^                                                                  ^                         ^


                                                                  ^                                ^
                                                                         ^
                          ^
                                                                 ^
           ^
     */
}