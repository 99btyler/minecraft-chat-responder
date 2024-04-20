# minecraft-chat-responder
Automatically respond to chat messages. Plus, a Hypixel Bedwars friend requester

# Getting Started
Get this project set up locally
### Prerequisites
* Download the decompiled mcp for 1.8.9 [here](https://github.com/Marcelektro/MCP-919)
* Create a natives folder in mcp919>jars>libraries>org>lwjgl -- download the natives [here](https://legacy.lwjgl.org)
* (In Eclipse) Open the eclipse folder and set client>properties>libraries>lwjgl>native-library-location to [path to natives folder]
### Setting up
* Copy the mcr folder from this repository to mcp919/src/minecraft
* Using your Java IDE, open mcp919/eclipse
* Manually add this code to the Minecraft code:

```java
// ----> inside Minecraft.java

// In the runTick() method after 'if (Keyboard.getEventKeyState())' after 'if (this.currentScreen != null) { } else {':
if (k == 24) {
  MCR.getInstance().openGUI();
}
```
```java
// ----> inside GuiNewChat.java

// At the start of printChatMessage() method:
MCR.getInstance().handleChatMessage(chatComponent.getUnformattedText().replaceAll("§.", ""));
```
