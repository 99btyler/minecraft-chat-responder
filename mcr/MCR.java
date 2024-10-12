package mcr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import mcr.screens.MCRScreen;
import net.minecraft.client.Minecraft;

public class MCR {
	
	private static final MCR instance = new MCR();
	
    private String keybind = "O";
    private boolean enabled;
    
    private final List<Keyword> keywords = new ArrayList<Keyword>();
    private final String saveFile = "mcr-keywords-data.txt";
    
    public MCR() {
    	loadKeywords();
	}
    
    public static final MCR getInstance() {
        return instance;
    }
    
    public final String getKeybind() {
    	return keybind;
    }
    
    public final boolean isEnabled() {
    	return enabled;
    }
    
    public final void togle() {
    	enabled = !enabled;
    }
    
    public final List<Keyword> getKeywords() {
    	return keywords;
    }
    
    public final void addKeyword(Keyword keyword) {
    	keywords.add(keyword);
    	saveKeywords();
    }
    
    public final void removeKeyword(Keyword keyword) {
    	keywords.remove(keyword);
    	saveKeywords();
    }
    
    public final void editKeyword(Keyword keyword, String text, String responses, String delay) {
    	keyword.setText(text);
    	keyword.setResponses(responses);
    	keyword.setDelay(delay);
    	saveKeywords();
    }
    
    private void loadKeywords () {
    	
    	File file = new File(saveFile);
    	
    	if (!file.exists()) {
    		
    		try {
    			
				file.createNewFile();
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write("test,successful,1000-1500\n");
				bufferedWriter.write("hey " + Minecraft.getMinecraft().thePlayer.getName() + ",hey;hi;hello,1000-1500\n");
				bufferedWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
    		
    	}
    	
    	try {
    		
    		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    		
    		String line;
			while ((line = bufferedReader.readLine()) != null) {
				
				final String[] keywordSplit = line.split(",");
				
				final String text = keywordSplit[0];
				final String responses = keywordSplit[1];
				final String delay = keywordSplit[2];
				
				keywords.add(new Keyword(text, responses, delay));
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private void saveKeywords() {
    	
    	File file = new File(saveFile);
    	
    	if (!file.exists()) {
    		
    		try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
    		
    	}
    	
    	try {
    		
    		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			
			for (Keyword keyword : keywords) {
				
				final String text = keyword.getText();
				final String responses = keyword.getResponses();
				final String delay = keyword.getDelay();
				
				bufferedWriter.write(text + "," + responses + "," + delay + "\n");
				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public final void openGUI() {
    	Minecraft.getMinecraft().displayGuiScreen(new MCRScreen());
    }
    
    public final void handleChatMessage(String message) {
    	
    	for (Keyword keyword : keywords) {
    		
    		boolean usernameInKeyword = false;
    		String username = null;
    		for (String word : keyword.getText().split(" ")) {
    			if (Minecraft.getMinecraft().theWorld.getPlayerEntityByName(word) != null) {
    				usernameInKeyword = true;
    				username = word;
    			}
    		}
    		if (usernameInKeyword) {
    			if (message.contains(username)) {
    				final char charAfterUsername = message.charAt(message.indexOf(username) + username.length());
    				System.out.println(charAfterUsername);
    				if (charAfterUsername == '>' || charAfterUsername == ':') {
    					final String intro = (username + charAfterUsername + " ");
    					final String content = message.split(intro)[1];
    					if (!content.toLowerCase().contains(username.toLowerCase())) {
    						continue;
    					}
    				}
    				
    			}
    		}
    		
    		// Check for keyword
    		if (message.toLowerCase().contains(keyword.getText().toLowerCase())) {
    			
    			// Send response after a delay
    			final String response = keyword.getRandomResponse();
    			final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    			scheduledExecutorService.schedule(new Runnable() {
					@Override
					public void run() {
						
						if (!enabled) {
							return;
						}
						
						Minecraft.getMinecraft().thePlayer.sendChatMessage(response);
						Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1.0f, 1.0f);
						
					}
				}, keyword.getRandomDelay(), TimeUnit.MILLISECONDS);
    			
    		}
    		
    	}
    	
    }

}
