# Alice
> A Java-Discord remote control app.

Alice is a simple Java-Discord remote control app that lets you control and automate a seperate computer while being away from the computer.

Alice uses the [Discord API](https://discord.com/developers/docs/reference) and [Javacord](https://javacord.org/) to let users interact with the client PC from any other device that they are signed into.

## Features
Alice currently features a variety of commands that include the following:
- Configuration System: Alice features a configuration system that saves previous settings for new sessions, this is saved using Java serialization to prevent plain text tokens.
- Run Command: Run shell commands & get an output.
- Click Command: Setup clicks at an interval & manually click the screen.
- Keyboard Command: Send streams of keyboard inputs to control the host screen.

There are more features planned that may be added later, these features are currently basic features that will be used to create further features.

## Building
If you want to build the project on your own, you will need to have [JDK 17](https://adoptium.net/temurin/releases/?version=17&arch=x64&package=jdk) installed.

Navigate to the main folder of the project and run the following commands:
```bash
# If you are using a Unix-based system, you'll have to run the following once:
chmod +x ./gradlew

# Use this to build a .jar file in the /build/libs folder.
# This will generate the project with all dependencies present.
./gradlew shadowJar

# If you do not want to link the external jars, you can use the following:
./gradlew build

# Navigate to the builds folder & run:
cd ./build/libs
java -jar *.jar
```

## Roadmap
The following features are supposed to be added later:
- Screenshot Command: Send screenshots at a regular interval & manually screenshot.
- Webhook Logging: Log errors and other issues from SLF4J to a webhook for ease.
- 

## Usage
You are free to use this program as you like, I am not responsible for how you use this program. For more in depth information, refer to the **license.**