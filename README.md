# minecraft-chat-responder
Automatically respond to keywords in the chat

# Getting Started
Get this project set up locally
### Prerequisites
* Download the decompiled mcp919 for 1.8.9 [here](https://github.com/Marcelektro/MCP-919)
* Create a natives folder in mcp919>jars>libraries>org>lwjgl -- download the natives [here](https://legacy.lwjgl.org)
* (In Eclipse) Open the eclipse folder and set client>properties>java-build-path>libraries>lwjgl>native-library-location to [path to natives folder]
### Setting up
* Copy the mcr folder from this repository to mcp919/src/minecraft
* Using your Java IDE, open mcp919/eclipse
* Manually add this code to the Minecraft code:

```java
// ----> inside Minecraft.java

// In the runTick() method after 'if (Keyboard.getEventKeyState())' after 'if (this.currentScreen != null) { } else {':
if (Keyboard.getKeyName(k).equals(MCR.getInstance().getKeybind())) {
  MCR.getInstance().openGUI();
}
```
```java
// ----> inside GuiNewChat.java

// At the start of printChatMessage() method:
MCR.getInstance().handleChatMessage(chatComponent.getUnformattedText().replaceAll("§.", ""));
```
### Exporting
* Get the jar file
  * (In Eclipse) Click file>export then java>jar_file. Select client>src only and then give the file a name
  * Open the jar (unzip example.jar -d examplefolder) and delete start.class, meta.inf
  * Copy/paste the io folder from mcp919>jars>libraries>io>netty>netty_all>final
  * Copy/paste the assets folder and pack.png from minecraft>versions>1.8.9
  * Close the jar (jar cvf example.jar -C examplefolder/ .)
* Get the json file
    * Must match the version and name of the jar file
* Create a folder with the same name as the jar and json file, put the jar and json file inside it, and put the folder inside minecraft>versions
