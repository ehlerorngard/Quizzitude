resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.0")

// this plugin allows the application.conf file to read env variables from the .env file
addSbtPlugin("au.com.onegeek" %% "sbt-dotenv" % "2.0.117")
