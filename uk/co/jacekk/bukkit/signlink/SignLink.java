package uk.co.jacekk.bukkit.signlink;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.signlink.listeners.SignBreakListener;
import uk.co.jacekk.bukkit.signlink.listeners.SignCreateListener;
import uk.co.jacekk.bukkit.signlink.listeners.TeleportListener;
import uk.co.jacekk.bukkit.util.LocationStore;
import uk.co.jacekk.bukkit.util.PluginLogger;

public class SignLink extends JavaPlugin {
	
	protected PluginLogger log;
	private PluginDescriptionFile pdFile;
	
	protected Server server;
	protected PluginManager pluginManager;
	
	public LocationStore locations;
	
	public void onEnable(){
		this.log = new PluginLogger(this);
		this.pdFile = this.getDescription();
		
		String pluginDir = this.getDataFolder().getAbsolutePath();
		(new File(pluginDir)).mkdirs();
		
		File locationFile = new File(pluginDir + File.separator + "sign-locations.bin");
		
		this.locations = new LocationStore(locationFile);
		this.locations.load();
		
		if (locationFile.canWrite() == false){
			this.log.fatal("Unable to write to the location storage file.");
		}
		
		this.server = this.getServer();
		this.pluginManager = this.server.getPluginManager();
		
		this.pluginManager.registerEvents(new TeleportListener(this), this);
		this.pluginManager.registerEvents(new SignCreateListener(this), this);
		this.pluginManager.registerEvents(new SignBreakListener(this), this);
	}
	
	public void onDisable(){
		this.locations.save();
	}
	
	public String formatMessage(String message, boolean colour, boolean version){
		StringBuilder line = new StringBuilder();
		
		if (colour){
			line.append(ChatColor.BLUE);
		}
		
		line.append("[");
		line.append(this.pdFile.getName());
		
		if (version){
			line.append(" v");
			line.append(this.pdFile.getVersion());
		}
		
		line.append("] ");
		line.append(message);
		
		return line.toString();
	}
	
	public String formatMessage(String message, boolean colour){
		return this.formatMessage(message, colour, !colour);
	}
	
	public String formatMessage(String message){
		return this.formatMessage(message, true, false);
	}
	
}
