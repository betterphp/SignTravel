package uk.co.jacekk.bukkit.signlink.command;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v8.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v8.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.v8.util.ListUtils;
import uk.co.jacekk.bukkit.signlink.Permission;
import uk.co.jacekk.bukkit.signlink.SignLink;
import uk.co.jacekk.bukkit.signlink.event.SignLinkTeleportEvent;

public class WarpCommandExecutor extends BaseCommandExecutor<SignLink> {
	
	public WarpCommandExecutor(SignLink plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"warp"}, description = "Teleports you or another player to a warp", usage = "<player/warp> <warp>")
	public void warp(CommandSender sender, String label, String[] args){
		if (!Permission.COMMAND_WARP.has(sender)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to use this command"));
			return;
		}
		
		if (args.length != 1 && args.length != 2){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Usage: /" + label + " <player_name/warp> <warp>"));
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Example: /" + label + " spawn_town"));
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Example: /" + label + " Notch spawn_town"));
			return;
		}
		
		String destination = null;
		Player player = null;
		
		if (args.length == 1){
			if (!(sender instanceof Player)){
				sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You must specify a player name when using this command from the console."));
				return;
			}
			
			destination = args[0];
			player = (Player) sender;
		}else{
			destination = args[1];
			player = plugin.server.getPlayer(args[0]);
			
			if (player == null){
				sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The player must be online."));
				return;
			}
		}
		
		if (!plugin.locations.contains(destination)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The destination " + destination + " could not be found"));
		}else{
			Location dest = plugin.locations.get(destination);
			
			SignLinkTeleportEvent signTeleportEvent = new SignLinkTeleportEvent(player, dest, destination);
			plugin.pluginManager.callEvent(signTeleportEvent);
			
			if (!signTeleportEvent.isCancelled()){
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Teleporting to " + destination));
				player.teleport(dest);
			}
		}
	}
	
	@CommandHandler(names = {"warplist"}, description = "Lists all of the available warp locations")
	public void warplist(CommandSender sender, String label, String[] args){
		if (!Permission.COMMAND_WARPLIST.has(sender)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to use this command"));
			return;
		}
		
		Set<String> destinations = plugin.locations.getKeys();
		
		sender.sendMessage(plugin.formatMessage(ChatColor.GREEN + "There are " + destinations.size() + " available warps:"));
		sender.sendMessage(ChatColor.AQUA + ListUtils.implode(", ", Arrays.asList(destinations.toArray(new String[0]))));
	}
	
}
