## Quizzitude

*NOTE –– this project is still under active construction*

This is a quiz app that allows users to quiz themselves with their own custom questions and short (multi-word) answers.  In quiz mode, the user's input answer is compared against the correct answer by an average of the percentage of exact words included (in any order) and the percentage correct determined by Longest Common Subsequence (ordered).  This allows some tolerance for spelling errors, syntax variance, and word omission. The user can set their preferred rigidity of answer evaluation.


### Technologies utilized
* Scala
* Play
* GraphQL
* Sangria
* Postgres
* React
* Redux



The prerequisites for running locally are [sbt](http://www.scala-sbt.org/download.html), [npm](https://www.npmjs.com/get-npm), [postgres](https://www.postgresql.org/download/), and [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
