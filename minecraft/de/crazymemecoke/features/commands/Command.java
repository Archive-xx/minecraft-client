package de.crazymemecoke.features.commands;

import de.crazymemecoke.Methods;
import de.crazymemecoke.manager.eventmanager.Event;
import de.crazymemecoke.utils.time.TimeHelper;
import net.minecraft.client.Minecraft;

public abstract class Command extends Methods {

	public TimeHelper timeHelper = new TimeHelper();

	public static Minecraft mc = Minecraft.mc();
	
	public abstract void execute(String[] args);
	
	public abstract String getName();

	public void onEvent(Event event) {

	}

	private String label;
	
	  public String[] getArguments()
	  {
	    String[] argus = this.label.split(" ");
	    return argus;
	  }
	
}