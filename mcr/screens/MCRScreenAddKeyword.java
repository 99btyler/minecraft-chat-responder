package mcr.screens;

import java.io.IOException;

import mcr.Keyword;
import mcr.MCR;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class MCRScreenAddKeyword extends GuiScreen {
	
	private final GuiScreen guiScreenPrevious;
	
	private final int colorText = 0xFFFFFF;
	private final String colorCodeYellow = "§e";
	private final String colorCodeGray = "§7";
	
	private final int MCRwidth = 182;
	private int MCRstartingX;
	private int MCRstartingY;
	
	private GuiTextField guiTextFieldText;
	private GuiTextField guiTextFieldResponses;
	private GuiTextField guiTextFieldDelay;
	
	public MCRScreenAddKeyword(GuiScreen guiScreenPrevious) {
		this.guiScreenPrevious = guiScreenPrevious;
	}
	
	@Override
	public void initGui() {
		
		MCRstartingX = (width / 2) - 91;
		MCRstartingY = 3;
		
		// CREATE BUTTONS
		this.buttonList.add(new GuiButton(0, MCRstartingX, MCRstartingY, MCRwidth / 2, 20, "Back"));
		this.buttonList.add(new GuiButton(1, MCRstartingX + (MCRwidth / 2), MCRstartingY, MCRwidth / 2, 20, colorCodeYellow + "Save"));
		
		// CREATE GUITEXTFIELDS
		int guiTextFieldY = MCRstartingY + 26;
		
		guiTextFieldText = new GuiTextField(0, fontRendererObj, MCRstartingX + 1, guiTextFieldY, MCRwidth - 2, 20);
		guiTextFieldText.setMaxStringLength(999);
		guiTextFieldText.setText(colorCodeGray + "keyword");
		guiTextFieldY += 20;
		
		guiTextFieldResponses = new GuiTextField(1, fontRendererObj, MCRstartingX + 1, guiTextFieldY, MCRwidth - 2, 20);
		guiTextFieldResponses.setMaxStringLength(999);
		guiTextFieldResponses.setText(colorCodeGray + "responses: separated by ;");
		guiTextFieldY += 20;
		
		guiTextFieldDelay = new GuiTextField(2, fontRendererObj, MCRstartingX + 1, guiTextFieldY, MCRwidth - 2, 20);
		guiTextFieldDelay.setMaxStringLength(999);
		guiTextFieldDelay.setText(colorCodeGray + "delay: min-max");
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		// DRAW BUTTONS
		for (GuiButton guiButton : this.buttonList) {
			guiButton.drawButton(mc, mouseX, mouseY);
		}
		
		// DRAW GUITEXTFIELDS
		guiTextFieldText.drawTextBox();
		guiTextFieldResponses.drawTextBox();
		guiTextFieldDelay.drawTextBox();
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		switch (button.id) {
		
			case 0:
				mc.displayGuiScreen(guiScreenPrevious);
				break;
				
			case 1:
				
				final String text = guiTextFieldText.getText();
				final String responses = guiTextFieldResponses.getText();
				final String delay = guiTextFieldDelay.getText();
				
				if (text.contains(colorCodeGray) || responses.contains(colorCodeGray) || delay.contains(colorCodeGray)) {
					return;
				}
				
				final String[] delaySplit = delay.split("-");
				if (delaySplit.length == 1) {
					
					try {
						Integer.parseInt(delay);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
					
				} else {
					
					try {
						
						final int min = Integer.parseInt(delaySplit[0]);
						final int max = Integer.parseInt(delaySplit[1]);
						
						if (min > max) {
							return;
						}
						
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return;
					}
					
				}
				
				MCR.getInstance().addKeyword(new Keyword(text, responses, delay));
				mc.displayGuiScreen(guiScreenPrevious);
				
				break;
		
		}
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		guiTextFieldText.mouseClicked(mouseX, mouseY, mouseButton);
		guiTextFieldResponses.mouseClicked(mouseX, mouseY, mouseButton);
		guiTextFieldDelay.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		super.keyTyped(typedChar, keyCode);
		guiTextFieldText.textboxKeyTyped(typedChar, keyCode);
		guiTextFieldResponses.textboxKeyTyped(typedChar, keyCode);
		guiTextFieldDelay.textboxKeyTyped(typedChar, keyCode);
		
		// Remove gray text
		if (guiTextFieldText.isFocused() && guiTextFieldText.getText().contains(colorCodeGray)) {
			guiTextFieldText.setText("" + typedChar);
		}
		if (guiTextFieldResponses.isFocused() && guiTextFieldResponses.getText().contains(colorCodeGray)) {
			guiTextFieldResponses.setText("" + typedChar);
		}
		if (guiTextFieldDelay.isFocused() && guiTextFieldDelay.getText().contains("§7")) {
			guiTextFieldDelay.setText("" + typedChar);
		}
		
	}

}
