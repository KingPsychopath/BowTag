package com.github.l33m4n123.bowtag;

import me.JPG.Tester.ArenaManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.l33m4n123.bowtag.commands.CommandCreateArena;
import com.github.l33m4n123.bowtag.configs.SimpleConfig;
import com.github.l33m4n123.bowtag.configs.SimpleConfigManager;
import com.github.l33m4n123.bowtag.events.OnSignClick;

public class BowTag extends JavaPlugin {

	public SimpleConfigManager manager;

	public SimpleConfig config;
	public SimpleConfig arena;

    public static Economy economy = null;

	private ArenaManager arenaManager = new ArenaManager(this);

	 private boolean setupEconomy() {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }

	@Override
	public void onEnable() {
		Bukkit.getLogger().info(
				BowTagInfo.NAME + " version: " + BowTagInfo.VERSION
						+ " by Leeman enabled");

		String[] comments = { "Multiple lines", "Of nice comments",
				"are supported!" };
		String[] header = { "This is super simple", "and highly customizable",
				"new and fresh SimpleConfig!" };

		this.manager = new SimpleConfigManager(this);
		this.config = manager.getNewConfig("config.yml", header);
		this.arena = manager.getNewConfig("arenas.yml");

		this.config.set("path1", "value1", comments);
		this.config.set("path2", "value2", "This is second comment !");
		this.config.saveConfig();

    	arenaManager.loadArenas(this.arena);

		// Registering Commands
		getCommand("bowTag").setExecutor(
				new CommandCreateArena(this.arenaManager, this.arena));
		// Registering Events
		Bukkit.getServer().getPluginManager().registerEvents(new OnSignClick(this.arenaManager), this);

	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info(
				BowTagInfo.NAME + " version:" + BowTagInfo.VERSION
						+ " by Leeman disabled");
	}
}
