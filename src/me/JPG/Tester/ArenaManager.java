package me.JPG.Tester;
 
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.github.l33m4n123.bowtag.BowTag;
import com.github.l33m4n123.bowtag.configs.SimpleConfig;
 
/**
 *
 * @Author Jake
 */
public class ArenaManager {

    private BowTag myPlugin;
   
    //constructor
    public ArenaManager(BowTag plugin) {
    	this.myPlugin = plugin;
    }

    //Usefull for getting the ArenaManager, like so: ArenaManager.getManager()
//    public static ArenaManager getManager() {
//        return am;
//    }
   
   
    //A method for getting one of the Arenas out of the list by name:
    public Arena getArena(String name) {
        for (Arena a: Arena.arenaObjects) {
        	//For all of the arenas in the list of objects
            if (a.getName().equals(name)) { 
            	//If the name of an arena object 
            	//in the list is equal to the one in the parameter..
                return a; //Return that object
                
            }
        }
        return null; //No objects were found, return null
    }
   
   
    //A method for adding players
    public void addPlayers(Player player, final String arenaName) {
    	player.sendMessage(arenaName);
        if (getArena(arenaName) != null) { //If the arena exsists
           
            Arena arena = getArena(arenaName);
            //Create an arena for using in this method
           
            if (!arena.isFull()) { //If the arena is not full
               
                if (!arena.isInGame()) {
                   
                    //Every check is complete, arena is joinable
                    player.getInventory().clear(); //Clear the players inventory
                    player.setHealth(player.getMaxHealth()); //Heal the player
                    player.setFireTicks(0); //Heal the player even more ^ ^ ^
                   
                    //Teleport to the arena's join location aka lobby
                    player.teleport(arena.getJoinLocation());
                   
                    //Add the player to the arena list
                    arena.getPlayers().add(player.getName()); 
                    //Add the players name to the arena
                   
                    int playersLeft = arena.getMaxPlayers() - arena.getPlayers().size(); 
                    //How many players needed to start
                    //Send the arena's players a message
                    arena.sendMessage(ChatColor.BLUE + player.getName() + " has joined the arena! We only need " + playersLeft + " to start the game!");
                   
                   
                    if (playersLeft == 0) { 
                    	//IF there are 0 players needed to start the game
                    	
                    	// Selecting randomly who will be Bowmen
                    	// and adding it to the list in the arena object
                		int firstBowMen = (int) (Math.random()*8);
                		ArrayList<String> survivor = arena.getPlayers();
                		ArrayList<String> bowMen = arena.getBowMen();

                		bowMen.add(survivor.get(firstBowMen));
                		survivor.remove(firstBowMen);

                		int secondBowMen = (int) (Math.random()*7);
                		bowMen.add(survivor.get(secondBowMen));
                		survivor.remove(secondBowMen);

                		for (String name : survivor) {
                			arena.setSurvivor(name);
                		}

                		for (String s : arena.getPlayers()) {
                			if (bowMen.contains(s)) {
                				Bukkit.getPlayer(s).sendMessage(ChatColor.GOLD + "[BowTag] You are a BowMen! Prepare");
                			}
                			Bukkit.getPlayer(s).sendMessage(ChatColor.GOLD + "[BowTag] Game will start in 40 Seconds.");
                			Bukkit.getPlayer(s).sendMessage(ChatColor.GOLD + "[BowTag] Last chance to pick your Kit.");
                		}
                		this.myPlugin.getServer().getScheduler().scheduleSyncDelayedTask(this.myPlugin, new Runnable() {
							
                            //Start the arena, see the method way below :)
                			//Starts it with a 40 second delay
							@Override
							public void run() {
								startArena(arenaName);
							}
						}, 800L);
                    }
                   
               
            } else {
            	//Specifiend arena is in game, send the player an error message
                    player.sendMessage(ChatColor.RED + "The arena you are looking for is currently full!");
                   
                }
            } else { //Specified arena is full, send the player an error message
                player.sendMessage(ChatColor.RED + "The arena you are looking for is currently full!");
            }
           
        } else { //The arena doesn't exsist, send the player an error message
            player.sendMessage(ChatColor.RED + "The arena you are looking for could not be found!");
        }
       
    }
   
   
    //A method for removing players
    public void removePlayer(Player player, String arenaName) {
       
        if (getArena(arenaName) != null) { //If the arena exsists
           
             Arena arena = getArena(arenaName); //Create an arena for using in this method
           
            if (arena.getPlayers().contains(player.getName())) { 
            	//If the arena has the player already
               
                //Every check is complete, arena is leavable
                player.getInventory().clear(); //Clear the players inventory
                player.setHealth(player.getMaxHealth()); //Heal the player
                player.setFireTicks(0); //Heal the player even more ^ ^ ^
                   
                    //Teleport to the arena's join location
                    player.teleport(arena.getEndLocation());
                   
                     //remove the player to the arena list
                    arena.getPlayers().remove(player.getName());
                    //Removes the players name to the arena
                   
                    //Send the arena's players a message
                    arena.sendMessage(ChatColor.BLUE + player.getName() + " has left the Arena! There are " + arena.getPlayers().size() + "players currently left!");
                   
               
               
               
            } else { 
            	//Specified arena doesn't have the player, send the player an error message
                player.sendMessage(ChatColor.RED + "Your not in the arena your looking for!");
               
            }
           
           
        } else { //The arena doesn't exsist, send the player an error message
            player.sendMessage(ChatColor.RED + "The arena you are looking for could not be found!");
        }
    }
       
       
        //A method for starting an Arena:
        public void startArena(String arenaName) {
           
            if (getArena(arenaName) != null) { //If the arena exsists
                 
                 final Arena arena = getArena(arenaName); 
                 //Create an arena for using in this method
                 
                 arena.sendMessage(ChatColor.GOLD + "The arena has BEGUN!");
                 
                 //Set ingame
                 arena.setInGame(true);
                 
                 for (String s: arena.getPlayers()) {
                	 //Loop through every player in the arena
                	 if (arena.getBowMen().contains(s)) {
                		 Bukkit.getPlayer(s).teleport(arena.getSpawnLocationOne());
                	 } else if (arena.getSurvivor().contains(s)) {
                		 Bukkit.getPlayer(s).teleport(arena.getSpawnLocationTwo());
                	 }
                     //Teleports the player to the arena start location
                     
                     //Do custom stuff here, like give weapons etc, 
                     //but for the purpose of this tutorial, i'll do nothing
                     
                	 // check every Tick if there are still survivors alive
                    this.myPlugin.getServer().getScheduler().scheduleSyncDelayedTask(this.myPlugin, new Runnable() {
						int time = 0;
						@Override
						public void run() {
							time++;
							ArrayList<String> remainingSurvivors = arena.getSurvivor();
							if (remainingSurvivors.size() == 0 || time == 300) {
								endArena(arena.getName());
							}
							
						}
					}, 20L);
                     
                     
                 }
                 
                 
             }
           
        }
       
       
        //A method for ending an Arena:
        public void endArena(String arenaName) {
           
            if (getArena(arenaName) != null) { //If the arena exsists
                 
                 Arena arena = getArena(arenaName); 
                 //Create an arena for using in this method
                 
                 //Send them a message
                 arena.sendMessage(ChatColor.GOLD + "The Game is over");

                 if (arena.getSurvivor().size() == 0) {
                	 arena.sendMessage(ChatColor.GOLD + "[BowTag] The Bowmen have won and earned xx");
                	 ArrayList<String> bowMen = arena.getBowMen();
                	 for (String s: bowMen) {
                		 BowTag.economy.depositPlayer(s, 10.0);
                		 Bukkit.getPlayer(s).sendMessage("You earnerd 10");
                	 }
                 } else {
                	 arena.sendMessage(ChatColor.GOLD + "[BowTag] The Survivors have won and earned xx");
                	 ArrayList<String> survivor = arena.getSurvivor();
                	 for (String s: survivor) {
                		 BowTag.economy.depositPlayer(s, 10.0);
                		 Bukkit.getPlayer(s).sendMessage("You earnerd 10");
                	 }
                 }
                 
                 //Set ingame
                 arena.setInGame(false);
                 
                 for (String s: arena.getPlayers()) {
                	 //Loop through every player in the arena
                     
                     //Teleport them:
                     
                     Player player = Bukkit.getPlayer(s); //Create a player by the name
                player.teleport(arena.getEndLocation());
                     
                player.getInventory().clear(); //Clear the players inventory
                player.setHealth(player.getMaxHealth()); //Heal the player
                player.setFireTicks(0); //Heal the player even more ^ ^ ^
               
                //Remove them all from the list
                arena.getPlayers().remove(player.getName());
                if (arena.getBowMen().contains(player.getName())) {
                	arena.getBowMen().remove(player.getName());
                } else if (arena.getSurvivor().contains(player.getName())) {
                	arena.getSurvivor().remove(player.getName());
                }
                     
                 }
               
               
            }
        }
           
           
            //And our final method, loading each arena
            //This will be resonsible for creating each arena 
            //from the config, and creating an object to represent it
            //Call this method in your main class, onEnable
           
       
        public void loadArenas(SimpleConfig arenaFile) {
           
            //I just create a quick Config Variable, obviously don't do this.
            //Use your own config file
        	SimpleConfig fc = arenaFile;
            //If you just use this code, it will erorr,
            // its null. Read the notes above, USE YOUR OWN CONFIGURATION FILE
           
            //Youll get an error here, FOR THE LOVE OF GAWD READ THE NOTES ABOVE!!!
            for (String keys: fc.getConfigurationSection("arenas").getKeys(false)) { 
            	//For each arena name in the arena file
               
                //Now lets get all of the values, and make an Arena object for each:
                //Just to help me remember: Arena myArena = 
            	//new Arena("My Arena", joinLocation, startLocation, endLocation, 17)
                String worldName = fc.getString("arenas."+ keys +".world");
                World world = Bukkit.getWorld(worldName);
               
                //Arena's name is keys
               
                double joinX = fc.getDouble("arenas." + keys + ".joinX");
                double joinY = fc.getDouble("arenas." + keys + ".joinY");
                double joinZ = fc.getDouble("arenas." + keys + ".joinZ");
                Location joinLocation = new Location(world, joinX, joinY, joinZ);
               
                double spawnPointOneX = fc.getDouble("arenas." + keys + ".spawnPointOneX");
                double spawnPointOneY = fc.getDouble("arenas." + keys + ".spawnPointOneY");
                double spawnPointOneZ = fc.getDouble("arenas." + keys + ".spawnPointOneZ");
               
                Location spawnLocationOne = new Location(world, spawnPointOneX, spawnPointOneY, spawnPointOneZ);

                double spawnPointTwoX = fc.getDouble("arenas." + keys + ".spawnPointTwoX");
                double spawnPointTwoY = fc.getDouble("arenas." + keys + ".spawnPointTwoY");
                double spawnPointTwoZ = fc.getDouble("arenas." + keys + ".spawnPointTwoZ");
               
                Location spawnLocationTwo = new Location(world, spawnPointTwoX, spawnPointTwoY, spawnPointTwoZ);
               
                double endX = fc.getDouble("arenas." + keys + ".endX");
                double endY = fc.getDouble("arenas." + keys + ".endX");
                double endZ = fc.getDouble("arenas." + keys + ".endX");
               
                Location endLocation = new Location(world, endX, endY, endZ);
               
                int maxPlayers = fc.getInt("arenas." + keys + ".maxPlayers");
               
                //Now lets create an object to represent it:
                Arena arena = new Arena(keys, joinLocation, spawnLocationOne, spawnLocationTwo, endLocation, maxPlayers);
               
            }
           
 
    }
       
        //Our final method, create arena!
        public void createArena(String arenaName, Location joinLocation, Location spawnLocationOne, Location spawnLocationTwo, Location endLocation, int maxPlayers, SimpleConfig arenaFile) {
           
            //Now, lets create an arena object to represent it:
            Arena arena = new Arena(arenaName, joinLocation, spawnLocationOne, spawnLocationTwo, endLocation, maxPlayers);
           
            //Now here is where you would save it all to a file, again,
            //im going to create a null FileConfiguration, USE YOUR OWN!!!
            SimpleConfig fc = arenaFile; //USE YOUR OWN PUNK
           
            fc.set("arenas." + arenaName, null); //Set its name
            //Now sets the other values
           
            String path = "arenas." + arenaName + "."; //Shortcut
            //Sets the paths
            fc.set(path + "world", joinLocation.getWorld().getName());
            fc.set(path + "joinX", joinLocation.getX());
            fc.set(path + "joinY", joinLocation.getY());
            fc.set(path + "joinZ", joinLocation.getZ());
           
            fc.set(path + "spawnPointOneX", spawnLocationOne.getX());
            fc.set(path + "spawnPointOneY", spawnLocationOne.getY());
            fc.set(path + "spawnPointOneZ", spawnLocationOne.getZ());

            fc.set(path + "spawnPointTwoX", spawnLocationTwo.getX());
            fc.set(path + "spawnPointTwoY", spawnLocationTwo.getY());
            fc.set(path + "spawnPointTwoZ", spawnLocationTwo.getZ());
           
            fc.set(path + "endX", endLocation.getX());
            fc.set(path + "endY", endLocation.getY());
            fc.set(path + "endZ", endLocation.getZ());
           
            fc.set(path + "maxPlayers", maxPlayers);
           
            //Now save it up down here
            fc.saveConfig();
           
        }
}