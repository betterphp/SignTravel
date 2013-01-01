package uk.co.jacekk.bukkit.signlink;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v7.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission SIGN_CREATE		= new PluginPermission("signlink.sign.create",		PermissionDefault.OP,	"Allows the player to create SignLink signs");
	public static final PluginPermission SIGN_REMOVE		= new PluginPermission("signlink.sign.remove",		PermissionDefault.OP,	"Allows the player to remove SignLink signs");
	public static final PluginPermission SIGN_USE			= new PluginPermission("signlink.sign.use",			PermissionDefault.TRUE,	"Allows the player to use SignLink signs");
	public static final PluginPermission COMMAND_WARP		= new PluginPermission("signlink.command.warp",		PermissionDefault.TRUE,	"Allows the player to use the /warp command");
	public static final PluginPermission COMMAND_WARPLIST	= new PluginPermission("signlink.command.warplist",	PermissionDefault.TRUE,	"Allows the player to use the /warplist command");
	
}
