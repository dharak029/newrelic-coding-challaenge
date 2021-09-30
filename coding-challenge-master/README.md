# coding-challenge

Coding Challenge Build Framework

## Starter build framework for the coding challenge

First, you do not need to use this starter framework for your project.
If you would rather use a different build system (maven, javac, ...)
you are free to so long as you provide clear commands to build your
project and start your com.newrelic.codingchallenge.server.  Failure to do so will invalidate your
submission.


## Install Java

This coding challenge is in Java so it is recommended you install Java
1.8 from Oracle.


## Gradle

The build framework provided here uses gradle to build your project
and manage your dependencies.  The `gradlew` command used here will
automatically download gradle for you so you shouldn't need to install
anything other than java.


### How to Run application

1. Open project as existing gradle project in IntelliJ 
2. Start the server from Main.java by providing localhost in program arguements under run configuration
3. Start the client from ClientSocket.java under client package by providing localhost in program arguments under run configuration
4. Once the client is connected you can provide 9 digits number on console. Make sure to hit enter after each 9 digits number
5. Go to server console to check the report of unique numbers. It'll print report on console every 10 seconds
6. Once you stop the client the log file numbers.log will be generated in logfile folder
7. Once you start the server again the old log file will be deleted from the logfile folder



