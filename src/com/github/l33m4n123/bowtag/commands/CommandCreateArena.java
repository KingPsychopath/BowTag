package com.github.l33m4n123.bowtag.commands;

import me.JPG.Tester.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.l33m4n123.bowtag.configs.SimpleConfig;

public class CommandCreateArena implements CommandExecutor {

	private ArenaManager arenaManager;
	private SimpleConfig arenaFile;

	Location lobby = null;
	Location spawnPointOne = null; // Spawnpoint for BowMen
	Location spawnPointTwo = null; // Spawnpoint for Survivors
	Location endLocation = null; // Where they spawn if they leave or game is over
	String arenaName = null; // Arena Name

	public CommandCreateArena(final ArenaManager manager,
			final SimpleConfig file) {
		this.arenaManager = manager;
		this.arenaFile = file;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command should only be run by a player!");
			return true;
		} else {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("bowTag")) {
				switch(args.length) {
				case 0:
					p.sendMessage(ChatColor.GOLD + "[BowTag] is a MiniGame created by L33m4n123 for GalaxyNetwork");
					break;
				case 1:
					switch(args[0]) {
					case "setLobby":
						this.lobby = p.getLocation();
						p.sendMessage(ChatColor.GOLD + "[BowTag] Lobby set");
						return true;
					case "setBowMenSpawn":
						this.spawnPointOne = p.getLocation();
						p.sendMessage(ChatColor.GOLD + "[BowTag] Bowmen Spawn set");
						return true;
					case "setSurvivorSpawn":
						this.spawnPointTwo = p.getLocation();
						p.sendMessage(ChatColor.GOLD + "[BowTag] Survivor Spawn set");
						return true;
					case "setExit":
						this.endLocation = p.getLocation();
						p.sendMessage(ChatColor.GOLD + "[BowTag] Exit set");
						return true;
					case "createArena":
						if (this.lobby == null || this.spawnPointOne == null || this.spawnPointTwo == null || this.arenaName == null || this.endLocation == null) {
							p.sendMessage(ChatColor.RED + "[BowTag] Remember to set SpawnPoints, Lobby, Exit and Name first");
						} else {
							this.arenaManager.createArena(this.arenaName, this.lobby, this.spawnPointOne, this.spawnPointTwo, this.endLocation, 8, arenaFile);
							p.sendMessage(ChatColor.GOLD + "[BowTag] Arena sucessfully created");
						}
						return true;
					default:
						p.sendMessage(ChatColor.GOLD + "[BowTag] Invalid arguments");
						return true;						
					}
				}

				if (args.length > 2 && args[0].equalsIgnoreCase("setName")) {
					String nameOfArena = "";
					for (int i = 1; i < args.length; i++) {
						nameOfArena += args[i] + " ";
					}
					this.arenaName = nameOfArena.trim();
					p.sendMessage(ChatColor.GOLD + "[BowTag] Name set to " + nameOfArena.trim());
					return true;
				}
			}
		}
		return false;
	}
}
