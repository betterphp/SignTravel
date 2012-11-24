package uk.co.jacekk.bukkit.signlink.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player is teleported after clicking on a sign
 */
public class SignLinkTeleportEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Location to;
	private String destinationName;
	
	private boolean isCancelled;
	
	public SignLinkTeleportEvent(Player player, Location to, String destinationName){
		this.player = player;
		this.to = to;
		this.destinationName = destinationName;
		
		this.isCancelled = false;
	}
	
	/**
	 * Gets the player being teleported
	 * 
	 * @return The player.
	 */
	public Player getPlayer(){
		return this.player;
	}
	
	/**
	 * Gets the location that the player is teleporting from
	 * 
	 * @return The location
	 */
	public Location getFrom(){
		return this.player.getLocation().clone();
	}
	
	/**
	 * Gets the location that the player is teleporting to
	 * 
	 * @return The location
	 */
	public Location getTo(){
		return this.to;
	}
	
	/**
	 * Gets the name of the destination that the player is teleporting to
	 * 
	 * @return The name of the destination
	 */
	public String getDestinationName(){
		return this.destinationName;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public boolean isCancelled(){
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancelled){
		this.isCancelled = cancelled;
	}
	
}
