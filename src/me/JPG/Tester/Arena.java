package me.JPG.Tester;
 
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
 
/**
 *
 * @Author Jake
 */
public class Arena {
   
    //A list of all the Arena Objects
    public static ArrayList<Arena> arenaObjects = new ArrayList<Arena>();
   
    //Some fields we want each Arena object to store:
    private Location joinLocation, spawnLocationOne, spawnLocationTwo, endLocation; //Some general arena locations
   
    private String name; //Arena name
    private ArrayList<String> players = new ArrayList<String>(); //And arraylist of players name
   
    private int maxPlayers;

    private ArrayList<String> bowMen, survivor = new ArrayList<>();
   
    private boolean inGame = false; //Boolean to determine if an Arena is ingame or not, automaticly make it false
   
   
    //Now for a Constructor:
    public Arena (String arenaName, Location joinLocation, Location spawnLocationOne, Location spawnLocationTwo, Location endLocation, int maxPlayers) { //So basicly: Arena myArena = new Arena("My Arena", joinLocation, startLocation, endLocation, 17)
        //Lets initalize it all:
        this.name = arenaName;
        this.joinLocation = joinLocation;
        this.spawnLocationOne = spawnLocationOne;
        this.spawnLocationTwo = spawnLocationTwo;
        this.endLocation = endLocation;
        this.maxPlayers = maxPlayers;
       
        //Now lets add this object to the list of objects:
        arenaObjects.add(this);
       
    }
   
    //Now for some Getters and Setters, so with our arena object, we can use special methods:
    public Location getJoinLocation() {
        return this.joinLocation;
    }
   
    public void setSurvivor(String playerName) {
        this.survivor.add(playerName);
    }

    public void setBowMen(String playerName) {
        this.bowMen.add(playerName);
    }

    public ArrayList<String> getBowMen() {
        return this.bowMen;
    }


    public ArrayList<String> getSurvivor() {
        return this.survivor;
    }
    
    public void setJoinLocation(Location joinLocation) {
        this.joinLocation = joinLocation;
    }
   
    public Location getSpawnLocationOne() {
        return this.spawnLocationOne;
    }

    public Location getSpawnLocationTwo() {
        return this.spawnLocationTwo;
    }
   
    public void setSpawnLocationOne (Location pSpawnLocationOne) {
        this.spawnLocationOne = pSpawnLocationOne;
    }

    public void setSpawnLocationTwo (Location pSpawnLocationTwo) {
        this.spawnLocationTwo = pSpawnLocationTwo;
    }
   
    public Location getEndLocation() {
        return this.endLocation;
    }
   
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
   
    public String getName() {
        return this.name;
    }
   
    public void setName(String name) {
        this.name = name;
    }
   
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
   
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
   
    public ArrayList<String> getPlayers() {
        return this.players;
    }
   
   
   
    //And finally, some booleans:
   public boolean isFull() { //Returns weather the arena is full or not
       if (players.size() >= maxPlayers) {
           return true;
       } else {
           return false;
       }
   }
   
   
   public boolean isInGame() {
       return inGame;
   }
   
   public void setInGame(boolean inGame) {
       this.inGame = inGame;
   }
   
   //To send each player in the arena a message
   public void sendMessage(String message) {
       for (String s: players) {
           Bukkit.getPlayer(s).sendMessage(message);
       }
   }
   
 
}