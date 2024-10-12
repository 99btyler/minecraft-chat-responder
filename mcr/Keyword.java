package mcr;

import java.util.Random;

public class Keyword {
	
	private String text;
	private String responses; // Separated by ;
	private String delay; // min-max
	
	private final Random random = new Random();
	
	public Keyword(String text, String responses, String delay) {
		this.text = text;
		this.responses = responses;
		this.delay = delay;
	}
	
	public final String getText() {
		return text;
	}
	
	public final String getTextPreview() {
		return text.length() >= 25 ? this.text.substring(0, 25) + "..." : text;
	}
	
	public final void setText(String text) {
		this.text = text;
	}
	
	public final String getResponses() {
		return responses;
	}
	
	public final String getRandomResponse() {
		final String[] split = responses.split(";");
		return split[random.nextInt(split.length - 0) + 0];
	}
	
	public final void setResponses(String responses) {
		this.responses = responses;
	}
	
	public final String getDelay() {
		return delay;
	}
	
	public final int getRandomDelay() {
		
		final String[] delaySplit = delay.split("-");
		
		final int delayMin = Integer.parseInt(delaySplit[0]);
		final int delayMax = Integer.parseInt(delaySplit[1]);
		
		return random.nextInt(delayMax-delayMin+1)+delayMin;
		
	}
	
	public final void setDelay(String delay) {
		this.delay = delay;
	}

}
