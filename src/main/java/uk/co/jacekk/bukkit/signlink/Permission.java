package uk.co.jacekk.bukkit.signlink;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v7.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission SIGN_CREATE	= new PluginPermission("signlink.create", PermissionDefault.OP, "Allows the player to create SignLink signs");
	public static final PluginPermission SIGN_REMOVE	= new PluginPermission("signlink.remove", PermissionDefault.OP, "Allows the player to remove SignLink signs");
	public static final PluginPermission SIGN_USE		= new PluginPermission("signlink.use", PermissionDefault.TRUE, "Allows the player to use SignLink signs");
	
}
