package uk.co.jacekk.bukkit.signlink.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import uk.co.jacekk.bukkit.signlink.SignLink;

public class SignBreakListener implements Listener {
	
	private SignLink plugin;
	
	public SignBreakListener(SignLink plugin){
		this.plugin = plugin;
	}
	
	private boolean checkSignBreak(Block block, Player player){
		Sign sign = (Sign) block.getState();
		
		String[] lines = sign.getLines();
		
		String line;
		String location = null;
		String destination = null;
		
		for (int i = 3; i >= 0; --i){
			line = lines[i]; 
			
			if (line.startsWith("[") && line.endsWith("]")){
				if (destination == null){
					destination = line.substring(1, line.length() - 1);
				}else{
					location = line.substring(1, line.length() - 1);
				}
			}
		}
		
		if (plugin.locations.contains(location)){
			if (player.hasPermission("signlink.remove") == false){
				return false;
			}else{
				plugin.locations.remove(location);
			}
		}else if (destination != null && plugin.locations.contains(destination)){
			if (player.hasPermission("signlink.remove") == false){
				return false;
			}
		}
		
		return true;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		Block broken = event.getBlock();
		
		Material brokenType = broken.getType();
		
		if ((brokenType == Material.SIGN_POST || brokenType == Material.WALL_SIGN) && this.checkSignBreak(broken, player) == false){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to remove this sign"));
			event.setCancelled(true);
			return;
		}
		
		Block above = broken.getRelative(BlockFace.UP);
		
		if (above.getType() == Material.SIGN_POST && this.checkSignBreak(above, player) == false){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to remove this sign"));
			event.setCancelled(true);
			return;
		}
		
		Block[] surrounding = new Block[4];
		
		surrounding[0] = broken.getRelative(BlockFace.NORTH);
		surrounding[1] = broken.getRelative(BlockFace.SOUTH);
		surrounding[2] = broken.getRelative(BlockFace.EAST);
		surrounding[3] = broken.getRelative(BlockFace.WEST);
		
		for (Block surroundingBlock : surrounding){
			if (surroundingBlock.getType() == Material.WALL_SIGN && this.checkSignBreak(surroundingBlock, player) == false){
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to remove this sign"));
				event.setCancelled(true);
				return;
			}
		}
		
		player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Location removed"));
	}
	
}
