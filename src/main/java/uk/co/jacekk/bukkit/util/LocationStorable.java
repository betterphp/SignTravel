package uk.co.jacekk.bukkit.util;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationStorable implements Serializable {
	
	private static final long serialVersionUID = 4957491030028893851L;
	
	private String worldName;
	private Double x, y, z;
	private Float pitch, yaw; 
	
	public LocationStorable(String worldName, Double x, Double y, Double z, Float yaw, Float pitch){
		this.worldName = worldName;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public LocationStorable(Location location){
		this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
	
	public Location toLocation(){
		return new Location(Bukkit.getWorld(this.worldName), this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
}
