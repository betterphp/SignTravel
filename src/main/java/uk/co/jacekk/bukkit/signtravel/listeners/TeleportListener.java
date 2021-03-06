package uk.co.jacekk.bukkit.signtravel.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.signtravel.Permission;
import uk.co.jacekk.bukkit.signtravel.SignTravel;
import uk.co.jacekk.bukkit.signtravel.event.SignLinkTeleportEvent;

public class TeleportListener extends BaseListener<SignTravel> {
	
	private List<Material> safeBlocks;
	
	public TeleportListener(SignTravel plugin){
		super(plugin);
		
		this.safeBlocks = Arrays.asList(
			Material.AIR,
			Material.WATER,
			Material.STATIONARY_WATER,
			Material.PORTAL,
			Material.RAILS,
			Material.DETECTOR_RAIL,
			Material.POWERED_RAIL,
			Material.VINE,
			Material.LADDER,
			Material.TORCH,
			Material.SIGN_POST,
			Material.WALL_SIGN,
			Material.SNOW,
			Material.WOODEN_DOOR,
			Material.IRON_DOOR_BLOCK,
			Material.WOOD_PLATE,
			Material.STONE_PLATE
		);
	}
	
	private boolean safeBlock(Block block){
		return this.safeBlocks.contains(block.getType());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		Location to = event.getTo().clone();
		
		int yMax = to.getWorld().getMaxHeight();
		
		Chunk chunk = to.getChunk();
		Block block = to.getBlock();
		Block above = block.getRelative(BlockFace.UP); 
		
		if (chunk.isLoaded() == false){
			chunk.load();
		}
		
		while (this.safeBlock(block) == false || this.safeBlock(above) == false){
			to.add(0D, 1D, 0D);
			
			block = above;
			above = above.getRelative(BlockFace.UP);
			
			if (above.getY() >= yMax){
				break;
			}
		}
		
		event.setTo(to);
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event){
		Block clicked = event.getClickedBlock();
		Material type = clicked.getType();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (type == Material.SIGN_POST || type == Material.WALL_SIGN)){
			Sign sign = (Sign) clicked.getState();
			Player player = event.getPlayer();
			
			String[] lines = sign.getLines();
			
			String destination = null;
			
			for (int i = 3; i >= 0; --i){
				if (lines[i].startsWith("[") && lines[i].endsWith("]")){
					destination = lines[i].substring(1, lines[i].length() - 1);
					break;
				}
			}
			
			if (destination != null && Permission.SIGN_USE.has(player)){
				if (!plugin.locations.contains(destination)){
					player.sendMessage(plugin.formatMessage(ChatColor.RED + "The destination " + destination + " could not be found"));
				}else{
					Location dest = plugin.locations.get(destination);
					
					SignLinkTeleportEvent signTeleportEvent = new SignLinkTeleportEvent(player, dest, destination);
					plugin.pluginManager.callEvent(signTeleportEvent);
					
					if (!signTeleportEvent.isCancelled()){
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Teleporting to " + destination));
						player.teleport(dest);
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
}
