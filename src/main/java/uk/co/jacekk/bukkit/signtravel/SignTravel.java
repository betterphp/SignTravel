package uk.co.jacekk.bukkit.signtravel;

import java.io.File;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.signtravel.command.WarpCommandExecutor;
import uk.co.jacekk.bukkit.signtravel.listeners.SignBreakListener;
import uk.co.jacekk.bukkit.signtravel.listeners.SignCreateListener;
import uk.co.jacekk.bukkit.signtravel.listeners.TeleportListener;
import uk.co.jacekk.bukkit.util.LocationStore;

public class SignTravel extends BasePlugin {
	
	public LocationStore locations;
	
	public void onEnable(){
		super.onEnable(true);
		
		File locationFile = new File(this.baseDirPath + File.separator + "sign-locations.bin");
		
		this.locations = new LocationStore(locationFile);
		this.locations.load();
		
		if (!locationFile.canWrite()){
			this.log.fatal("Unable to write to the location storage file.");
		}
		
		this.pluginManager.registerEvents(new TeleportListener(this), this);
		this.pluginManager.registerEvents(new SignCreateListener(this), this);
		this.pluginManager.registerEvents(new SignBreakListener(this), this);
		
		this.permissionManager.registerPermissions(Permission.class);
		this.commandManager.registerCommandExecutor(new WarpCommandExecutor(this));
	}
	
	public void onDisable(){
		this.locations.save();
	}
	
}
