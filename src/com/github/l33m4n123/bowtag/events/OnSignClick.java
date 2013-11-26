package com.github.l33m4n123.bowtag.events;

import me.JPG.Tester.ArenaManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnSignClick implements Listener  {

	private ArenaManager manager;

	public OnSignClick(ArenaManager arenaManager) {
		this.manager = arenaManager;
	}

	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && b.getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			if (sign.getLine(0).equalsIgnoreCase("BowTag")) {
				Player p = e.getPlayer();
				p.sendMessage(sign.getLine(1));
				manager.addPlayers(p, sign.getLine(1));
			}
			
		}
		
	}

}
