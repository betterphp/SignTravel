package uk.co.jacekk.bukkit.signtravel;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v8.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission SIGN_CREATE		= new PluginPermission("signtravel.sign.create",		PermissionDefault.OP,	"Allows the player to create SignTravel signs");
	public static final PluginPermission SIGN_REMOVE		= new PluginPermission("signtravel.sign.remove",		PermissionDefault.OP,	"Allows the player to remove SignTravel signs");
	public static final PluginPermission SIGN_USE			= new PluginPermission("signtravel.sign.use",			PermissionDefault.TRUE,	"Allows the player to use SignTravel signs");
	public static final PluginPermission COMMAND_WARP		= new PluginPermission("signtravel.command.warp",		PermissionDefault.TRUE,	"Allows the player to use the /warp command");
	public static final PluginPermission COMMAND_WARPLIST	= new PluginPermission("signtravel.command.warplist",	PermissionDefault.TRUE,	"Allows the player to use the /warplist command");
	
}
