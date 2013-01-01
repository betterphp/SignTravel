package uk.co.jacekk.bukkit.signlink;

import java.io.File;

import uk.co.jacekk.bukkit.baseplugin.v7.BasePlugin;
import uk.co.jacekk.bukkit.signlink.listeners.SignBreakListener;
import uk.co.jacekk.bukkit.signlink.listeners.SignCreateListener;
import uk.co.jacekk.bukkit.signlink.listeners.TeleportListener;
import uk.co.jacekk.bukkit.util.LocationStore;

public class SignLink extends BasePlugin {
	
	public LocationStore locations;
	
	public void onEnable(){
		super.onEnable(true);
		
		File locationFile = new File(this.baseDirPath + File.separator + "sign-locations.bin");
		
		this.locations = new LocationStore(locationFile);
		this.locations.load();
		
		if (locationFile.canWrite() == false){
			this.log.fatal("Unable to write to the location storage file.");
		}
		
		this.pluginManager.registerEvents(new TeleportListener(this), this);
		this.pluginManager.registerEvents(new SignCreateListener(this), this);
		this.pluginManager.registerEvents(new SignBreakListener(this), this);
	}
	
	public void onDisable(){
		this.locations.save();
	}
	
}
