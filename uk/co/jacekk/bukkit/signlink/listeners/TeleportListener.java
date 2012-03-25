package uk.co.jacekk.bukkit.signlink.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.signlink.SignLink;

public class TeleportListener implements Listener {
	
	private SignLink plugin;
	
	public TeleportListener(SignLink plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event){
		Block clicked = event.getClickedBlock();
		Material type = clicked.getType();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (type == Material.SIGN_POST || type == Material.WALL_SIGN)){
			Sign sign = (Sign) clicked.getState();
			Player player = event.getPlayer();
			
			String[] lines = sign.getLines();
			
			String line;
			String destination = null;
			
			for (int i = 3; i >= 0; --i){
				line = lines[i]; 
				
				if (line.startsWith("[") && line.endsWith("]")){
					destination = line.substring(1, line.length() - 1);
					break;
				}
			}
			
			if (destination != null && player.hasPermission("signlink.use")){
				if (plugin.locations.contains(destination) == false){
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "The destination " + destination + " could not be found"));
				}else{
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Teleporting to " + destination));
					player.teleport(plugin.locations.get(destination));
					event.setCancelled(true);
				}
			}
		}
	}
	
}
