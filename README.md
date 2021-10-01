# Mareu
# Building Java Projects with Gradle

##### This guide walks you through using Gradle to build and execute this app.

### What you’ll need
+ A favorite text editor or IDE
+ JDK 6 or later
+ Install Gradle / Build the project with gradle wrapper

### Build the project using the gradle wrapper without installing gradle

+ You can build the project using the gradle wrapper by running the cmd: "gradlew build" in the app directory
+ This can be done without having gradle installed on your system as it will download and cache the Gradle binaries to build the project
+ You will however need to have JDK 6 or later installed on your system: https://www.oracle.com/java/technologies/java-platform.html

### Build the project using gradle

### Install Gradle
+ **On Unix**

```
$ sudo add-apt-repository ppa:cwchien/gradle
$ sudo apt-get update
$ sudo apt-get install gradle
```


+ **On Mac OS X**
    + `brew install gradle`

    + [Install Homebrew](http://brew.sh/).


+ **On Windows**

  + [Download from Gradle site](https://docs.gradle.org/current/userguide/installation.html).

  + Unzip the Gradle download to the folder to which you would like to install Gradle, eg. “C:\Program Files”. The subdirectory gradle-x.x will be created from the archive, where x.x is the version.

  + Add location of your Gradle “bin” folder to your path. Open the system properties (WinKey + Pause), select the “Advanced” tab, and the “Environment Variables” button, then add “C:\Program Files\gradle-x.x\bin” (or wherever you unzipped Gradle) to the end of your “Path” variable under System Properties. Be sure to omit any quotation marks around the path even if it contains spaces. Also make sure you separated from previous PATH entries with a semicolon “;”.

  + In the same dialog, make sure that JAVA_HOME exists in your user variables or in the system variables and it is set to the location of your JDK, e.g. C:\Program Files\Java\jdk1.7.0_06 and that %JAVA_HOME%\bin is in your Path environment variable.

  + Open a new command prompt (type cmd in Start menu) and run gradle –version to verify that it is correctly installed.
  
### To test the Gradle installation, run Gradle from the command-line: `gradle`
+ If all goes well, you see a welcome message:
```
:help

Welcome to Gradle 2.8.

To run a build, run gradle <task> ...

To see a list of available tasks, run gradle tasks

To see a list of command-line options, run gradle --help

To see more detail about a task, run gradle help --task <task>

BUILD SUCCESSFUL

Total time: 6.317 secs
```

+  You now have Gradle installed.

### Build the project using gradle

+ Now you just need to run the cmd "gradle build" in the app directory

### Execute the app on a mobile device

+ Once the project has been built, you can find a new subdirectory "build" in the app directory
+ You can then copy the ".apk" file (debug) and transfer it on your android mobile device to install the app

### Build the project on Android Studio

+ If you utilize Android Studio, you just need to copy the source code of the app and open it as an existing project
+ After syncing the files with gradle, you can build the project or launch it on your favorite emulated device
