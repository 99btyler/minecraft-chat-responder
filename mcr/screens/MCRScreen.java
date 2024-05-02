package mcr.screens;

import java.io.IOException;

import mcr.MCR;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MCRScreen extends GuiScreen {
	
	private final int colorText = 0xFFFFFF;
	private final String colorCodeYellow = "§e";
	
	private final int MCRwidth = 182;
	private int MCRstartingX;
	private int MCRstartingY;
	
	@Override
	public void initGui() {
		
		MCRstartingX = (width / 2) - 91;
		MCRstartingY = 3;
		
		// CREATE BUTTONS
		this.buttonList.add(new GuiButton(0, MCRstartingX, MCRstartingY, MCRwidth / 2, 20, MCR.getInstance().isEnabled() ? "Enabled" : "Disabled"));
		this.buttonList.add(new GuiButton(1, MCRstartingX + (MCRwidth / 2), MCRstartingY, MCRwidth / 2, 20, colorCodeYellow + "Add Keyword"));
		
		int keywordButtonY = MCRstartingY + 25;
		for (int i = 0; i < MCR.getInstance().getKeywords().size(); i++) {
			this.buttonList.add(new GuiButton(this.buttonList.size(), MCRstartingX, keywordButtonY, MCRwidth, 20, MCR.getInstance().getKeywords().get(i).getTextPreview()));
			keywordButtonY += 19;
		}
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		// DRAW BUTTONS
		for (GuiButton guiButton : this.buttonList) {
			guiButton.drawButton(mc, mouseX, mouseY);
		}
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		switch (button.id) {
		
			case 0:
				MCR.getInstance().togle();
				mc.displayGuiScreen(new MCRScreen()); // update screen
				break;
				
			case 1:
				mc.displayGuiScreen(new MCRScreenAddKeyword(this));
				break;
			
			default:
				mc.displayGuiScreen(new MCRScreenEditKeyword(MCR.getInstance().getKeywords().get(button.id-2), this));
				break;
		
		}
		
	}

}
