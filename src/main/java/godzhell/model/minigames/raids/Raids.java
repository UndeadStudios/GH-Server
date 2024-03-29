package godzhell.model.minigames.raids;

import godzhell.Server;
import godzhell.clip.doors.Location;
import godzhell.model.content.achievement.AchievementType;
import godzhell.model.content.achievement.Achievements;
import godzhell.model.items.Item;
import godzhell.model.items.ItemDefinition;
import godzhell.model.npcs.NPCDef;
import godzhell.model.npcs.NPCDefinitions;
import godzhell.model.npcs.NPCHandler;
import godzhell.model.players.Boundary;
import godzhell.model.players.Player;
import godzhell.model.players.PlayerHandler;
import godzhell.util.Misc;
import godzhell.world.objects.GlobalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author @ Goon_
 * www.rune-server.com
 */

public class Raids {
	/**
	 * The player
	 */
	Player player;

	/**
	 * The raid leader
	 */
	public Player raidLeader;


	/**
	 * Raid points
	 */
	private int raidPoints;

	/**
	 * Group points
	 */
	public int groupPoints;

	/**
	 * Raid Group
	 */
	Player[] raidGroup;

	/**
	 * The current path
	 */
	private int path;

	/**
	 * The current way
	 */
	private int way;

	/**
	 * Current room
	 */
	public int currentRoom;


	/**
	 * Reached room
	 */
	public int reachedRoom;
	/**
	 * Monster spawns (No Double Spawning)
	 */
	public boolean lizards = false;
	public boolean vasa = false;
	public boolean vanguard = false;
	public boolean ice = false;
	public boolean chest = false;
	public boolean skilling = false;
	public boolean mystic = false;
	public boolean tekton = false;
	public boolean mutta = false;
	public boolean archers = false;
	public boolean olm = false;
	public boolean olmDead = false;
	public boolean rightHand = false;
	public boolean leftHand = false;
	public int activeBraziers;
	public boolean brazier1 = false;
	public boolean brazier2 = false;
	public boolean brazier3 = false;
	public boolean brazier4 = false;

	/**
	 *Handles special attacks of Ice Demon and Olm (Acid pool and Ice Storm)
	 * @return
	 */
	public boolean isInAcid() {
		return Server.getGlobalObjects().get(30032, player.getX(), player.getY(), player.getHeight()) != null;
	}

	public boolean isInStorm() {
		return Server.getGlobalObjects().get(29876, player.getX(), player.getY(), player.getHeight()) != null;
	}

	public void createStorm() {
		GlobalObject object = new GlobalObject(29876, player.getX(), player.getY(), player.getHeight(), 3, 10, 50, -1);
		Server.getGlobalObjects().add(object);
	}

	public void createAcidPool() {
		GlobalObject object = new GlobalObject(30032, player.getX(), player.getY(), player.getHeight(), 3, 10, 50, -1);
		Server.getGlobalObjects().add(object);
	}

	/**
	 * Braziers
	 * @param value
	 */
	public void lightBrazier1(boolean value) {
		this.brazier1 = value;
	}
	public void lightBrazier2(boolean value) {
		this.brazier2 = value;
	}
	public void lightBrazier3(boolean value) {
		this.brazier3 = value;
	}
	public void lightBrazier4(boolean value) {
		this.brazier4 = value;
	}

	public boolean isBraziersLit() {
		return brazier1 && brazier2 && brazier3 && brazier4 && brazier4;
	}

	/**
	 * The door location of the current paths
	 */
	private ArrayList<Location> roomPaths= new ArrayList<Location>();

	/**
	 * The names of the current rooms in path
	 */
	private  ArrayList<String> roomNames = new ArrayList<String>();

	/**
	 * Current monsters needed to kill
	 */
	private int mobAmount = 0;

	/**
	 * Constructs the raids class for the player
	 * @param player The player
	 */
	public Raids(Player player) {
		this.player=player;
	}

	/**
	 * Gets the height for the raid
	 * @return the height
	 */
	public int getHeight(Player player) {
		return raidLeader.getIndex()*4;
	}

	/**
	 * Get points
	 */
	public int getPoints() {
		return raidPoints;
	}
	/**
	 * Add points
	 */
	public void addPoints(int points) {
		raidPoints += points;
	}

	/**
	 * Gets the current path
	 * @return the path
	 */
	public int getPath() {
		return path;
	}

	/**
	 * Gets the current way
	 * @return the way
	 */
	public int getWay() {
		return way;
	}

	/**
	 * Sets the current path
	 * @param path
	 */
	public void setPath(int path) {
		this.path=path;
	}

	/**
	 * Gets the start location for the path
	 * @return path
	 */
	public Location getStartLocation() {
		switch(path) {
			case 0:
				return RaidRooms.STARTING_ROOM_2.doorLocation;
		}
		return RaidRooms.STARTING_ROOM.doorLocation;
	}
	/**
	 * Handles raid rooms
	 * @author Goon
	 *
	 */
	public enum RaidRooms{
		STARTING_ROOM("start_room",1,0,new Location(3299,5189)),
		LIZARDMEN_SHAMANS("lizardmen",1,0,new Location(3308,5208)),
		SKELETAL_MYSTIC("skeletal",1,0,new Location(3312,5217,1)),
		VASA_NISTIRIO("vasa",1,0,new Location(3312,5279)),
		VANGUARDS("vanguard",1,0,new Location(3312,5311)),
		ICE_DEMON("ice",1,0,new Location(3313,5346)),
		CHEST_ROOM("chest",1,0,new Location(3311,5374)),
		//SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		//ARCHERS_AND_MAGERS("archer",1,0,new Location(3309,5340,1)),
		MUTTADILE("muttadile",1,0,new Location(3311,5309,1)),
		//SKILLING("skilling",1,0,new Location(3311,5442)),
		TEKTON("tekton",1,0,new Location(3310,5277,1)),
		ENERGY_ROOM("energy",1,0,new Location(3275,5159)),
		OLM_ROOM_WAIT("olm_wait",1,0,new Location(3232,5721)),
		OLM_ROOM("olm",1,0,new Location(3232,5730)),

		STARTING_ROOM_2("start_room",1,1,new Location(3299,5189)),
		MUTTADILE_2("muttadile",1,1,new Location(3311,5309,1)),
		VASA_NISTIRIO_2("vasa",1,1,new Location(3312,5279)),
		VANGUARDS_2("vanguard",1,1,new Location(3312,5311)),
		//SKILLING_2("skilling",1,1,new Location(3311,5442)),
		ICE_DEMON_2("ice",1,1,new Location(3313,5346)),
		//ARCHERS_AND_MAGERS_2("archer",1,1,new Location(3309,5340,1)),
		CHEST_ROOM_2("chest",1,1,new Location(3311,5374)),
		//SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		SKELETAL_MYSTIC_2("skeletal",1,1,new Location(3312,5217,1)),
		TEKTON_2("tekton",1,1,new Location(3310,5277,1)),
		LIZARDMEN_SHAMANS_2("lizardmen",1,1,new Location(3308,5208)),
		ENERGY_ROOM_2("energy",1,1,new Location(3275,5159)),
		OLM_ROOM_WAIT_2("olm_wait",1,1,new Location(3232,5721)),
		OLM_ROOM_2("olm",1,1,new Location(3232,5730));

		/**
		 STARTING_ROOM_2("start_room",1,new Location(3299,5189)),
		 LIZARDMEN_SHAMANS_2("lizardmen",1,new Location(3308,5208)),
		 VASA_NISTIRIO_2("vasa",1,new Location(3312,5279)),
		 VANGUARDS_2("vanguard",1,new Location(3312,5311)),
		 ICE_DEMON_2("ice",1,new Location(3313,5346)),
		 CHEST_ROOM_2("chest",1,new Location(3311,5374)),
		 //SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		 SKELETAL_MYSTIC("skeletal",1,new Location(3312,5217,1)),
		 TEKTON_2("tekton",1,new Location(3310,5277,1)),
		 MUTTADILE_2("muttadile",1,new Location(3311,5309,1)),
		 ARCHERS_AND_MAGERS_2("archer",1,new Location(3309,5340,1)),
		 ENERGY_ROOM_2("energy",1,new Location(3275,5159)),
		 OLM_ROOM_WAIT_2("olm_wait",1,new Location(3232,5721)),
		 OLM_ROOM_2("olm",1,new Location(3232,5730));
		 **/

		private Location doorLocation;
		private int path;
		private int way;
		private String roomName;

		private RaidRooms(String name,int path1,int way1,Location door) {
			doorLocation=door;
			roomName=name;
			path=path1;
			way=way1;

		}

		public Location getDoor() {
			return doorLocation;
		}

		public int getPath() {
			return path;
		}
		public int getWay() {
			return way;
		}
		public String getRoomName() {
			return roomName;
		}


	}

	public void updateRaidPoints() {
		player.getPA().sendFrame126("Total: @whi@"+groupPoints,17502);
		player.getPA().sendFrame126(player.playerName+": @whi@"+raidPoints,17503);
	}

	public static void lightBrazer(Player player, int id, int objX, int objY) {
		Player raidLeader = player.getRaids().raidLeader;

		if (raidLeader == null) {
			return;
		}

		if (!player.getItems().playerHasItem(20799, 1)) {
			player.sendMessage("You need some kindling to light this brazier!");
			return;
		}

		int height = raidLeader.getIndex()*4;

		Server.getGlobalObjects().replace(
				new GlobalObject(29747, objX, objY, height, 0, 10, 200, -1),
				new GlobalObject(29748, objX, objY, height, 0, 10, 200, 29747));

		player.getItems().deleteItem(20799, 1);

		if (objX == 3307 && objY == 5368)
			raidLeader.getRaids().lightBrazier1(true);
		if (objX == 3309 && objY == 5365)
			raidLeader.getRaids().lightBrazier2(true);
		if (objX == 3312 && objY == 5365)
			raidLeader.getRaids().lightBrazier3(true);
		if (objX == 3314 && objY == 5368)
			raidLeader.getRaids().lightBrazier4(true);

		raidLeader.sendMessage("Someone has lit one of the braziers!");
	}

	/**
	 * Kill all spawns for the raid leader if left
	 * @param player
	 */
	public void killAllSpawns(Player player) {
		NPCHandler.kill(394, player.getRaids().getHeight(player.getRaids().raidLeader)); // banker
		NPCHandler.kill(3341, player.getRaids().getHeight(player.getRaids().raidLeader)); //healer
		NPCHandler.kill(3258, player.getRaids().getHeight(player.getRaids().raidLeader)); // master farmer
		NPCHandler.kill(7563, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7566, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7585, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7544, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7573, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7573, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7573, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7604, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7606, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7605, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7605, player.getRaids().getHeight(player.getRaids().raidLeader));
		NPCHandler.kill(7559, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly ranger
		NPCHandler.kill(7559, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly ranger
		NPCHandler.kill(7559, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly ranger
		NPCHandler.kill(7560, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly mager
		NPCHandler.kill(7560, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly mager
		NPCHandler.kill(7560, player.getRaids().getHeight(player.getRaids().raidLeader)); // deathly mager
		NPCHandler.kill(7527, player.getRaids().getHeight(player.getRaids().raidLeader)); // melee vanguard
		NPCHandler.kill(7528, player.getRaids().getHeight(player.getRaids().raidLeader)); // range vanguard
		NPCHandler.kill(7529, player.getRaids().getHeight(player.getRaids().raidLeader)); // magic vanguard
		NPCHandler.kill(7553, player.getRaids().getHeight(player.getRaids().raidLeader)); // magic vanguard
		NPCHandler.kill(7554, player.getRaids().getHeight(player.getRaids().raidLeader)); // magic vanguard
		NPCHandler.kill(7555, player.getRaids().getHeight(player.getRaids().raidLeader)); // magic vanguard
	}

	/**
	 * Leaves the raid.
	 * @param player
	 */
	public void leaveGame(Player player) {
		if (System.currentTimeMillis() - player.infernoLeaveTimer < 15000) {
			player.sendMessage("You cannot leave yet, wait a couple of seconds and try again.");
			return;
		}
		player.sendMessage("@red@You have left the Chambers of Xeric.");
		player.getPA().movePlayer(1245, 3561, 0);
		killAllSpawns(player);
		roomNames = new ArrayList<String>();
		roomPaths= new ArrayList<Location>();
		activeBraziers = 0;
		currentRoom = 0;
		mobAmount=0;
		reachedRoom = 0;
		raidLeader=null;
		lizards = false;
		vasa = false;
		vanguard = false;
		ice = false;
		chest = false;
		skilling = false;
		mystic = false;
		tekton = false;
		mutta = false;
		archers = false;
		olm = false;
		olmDead = false;
		rightHand = false;
		leftHand = false;

	}
	/**
	 * Starts the raid.
	 */
	public void startRaid() {
		if (player.clan == null || !player.clan.isFounder(player.playerName)) {
			player.sendMessage("You're not in a clan that you own, and can not pass the door.");
			return;
		}

		int memberCount = player.clan.activeMembers.size();

		if (memberCount < 1) {
			player.sendMessage("You don't have enough people in your clan to start a raid.");
			return;
		}

		if (memberCount > 22) {
			player.sendMessage("Your clan exceeds the max limit of 22 players in Raids.");
			return;
		}

		activeBraziers = 0;
		raidLeader=player;
		int path1 = 1;
		int way1=Misc.random(1);
		path = path1;
		way=way1;
		raidPoints = 0;
		for (String username : player.clan.activeMembers) {
			Player p = PlayerHandler.getPlayer(username);
			if (p == null || !p.inRaidsMountain()) {
				continue;
			}
			if(p.combatLevel < 75 && p.totalLevel < 750){
				p.sendMessage("You need at least 75 combat or 750 total level to attend raids.");
				continue;
			}
			p.getRaids().raidLeader = player;
			p.getRaids().path = path1;
			p.getRaids().way= way1;
			p.getRaids().raidPoints=0;
			for(RaidRooms room : RaidRooms.values()) {
				if(room.getWay() == way) {
					p.getRaids().roomNames.add(room.getRoomName());
					p.getRaids().roomPaths.add(room.getDoor());
				}
			}
			p.getRaids().updateRaidPoints();
			p.getPA().movePlayer(getStartLocation().getX(),getStartLocation().getY(),getHeight(player));
			p.sendMessage("@red@Welcome to the Chambers of Xeric!");
		}
	}

	int[] rarerewards = {22296, 20517, 20520, 20595, 20784, 21000, 21006, 21009, 21012, 21015, 21018, 21021, 21024, 21028, 20849};
	int[] commonrewards = {560,565,566,892,11212,3050,208,210,212,214,3052,216,2486,218,220,443, 454, 445, 448, 450, 452, 1624, 1622, 1620, 1618, 13391, 7937, 2722}; //{item, maxAmount}

	/**
	 * Handles giving the raid reward
	 */
	public void giveReward() {
		int rewardChance = Misc.random(300);
		if(rewardChance >= 296) {
			giveRareReward();
		}else {
			giveCommonReward();
		}
		if (player.raidCount == 24) {
			player.getItems().addItemUnderAnyCircumstance(22388, 1);
			player.getItems().addItemUnderAnyCircumstance(22380, 1);
		}
		if (player.raidCount == 49) {
			player.getItems().addItemUnderAnyCircumstance(22390, 1);
		}
		if (player.raidCount == 74) {
			player.getItems().addItemUnderAnyCircumstance(22392, 1);
			player.getItems().addItemUnderAnyCircumstance(22384, 1);
		}
		if (player.raidCount == 99) {
			player.getItems().addItemUnderAnyCircumstance(22394, 1);
		}
		if (player.raidCount == 149) {
			player.getItems().addItemUnderAnyCircumstance(22396, 1);
			player.getItems().addItemUnderAnyCircumstance(22376, 1);
		}
		if (player.raidCount == 199) {
			player.getItems().addItemUnderAnyCircumstance(22382, 1);
		}
		if (player.raidCount == 249) {
			player.getItems().addItemUnderAnyCircumstance(22378, 1);
		}
	}

	/**
	 * Handles giving a rare reward.
	 */

	public void giveRareReward() {
		//p.gfx0(1368);
		int rareitem = 0;
		rareitem = Misc.random(rarerewards.length-1);
		if(rareitem < 0) {
			rareitem = Misc.random(rarerewards.length);
		}
		player.raidReward[0][0] = rarerewards[rareitem];
		PlayerHandler.executeGlobalMessage("@red@" + player.playerName + " has received a rare item @red@"+ ItemDefinition.forId(player.raidReward[0][0]).getName() + " from raids!");
		if(player.raidReward[0][0] == 20849) {
			player.raidReward[0][1] = 500;
		}else {
			player.raidReward[0][1] = 1;
		}

		//p.getItems().addItem(player.raidReward[0][0], player.raidReward[0][1]);
	}
	/**
	 * Handles giving a common reward
	 */
	public void giveCommonReward() {
		//p.gfx0(277);
		int commonitem = 0;
		commonitem = Misc.random(commonrewards.length-1);
		player.raidReward[0][0] = commonrewards[commonitem];

		switch(player.raidReward[0][0]) {
			case 560://death rune
				player.raidReward[0][1] = Misc.random(1000);
				break;
			case 565://blood rune
				player.raidReward[0][1] = Misc.random(1000);
				break;
			case 566://soul rune
				player.raidReward[0][1] = Misc.random(1000);
				break;
			case 892://rune arrow
				player.raidReward[0][1] = Misc.random(1000);
				break;
			case 11212://dragon arrow
				player.raidReward[0][1] = Misc.random(1000);
				break;
			case 3050://grimy toadflax
				player.raidReward[0][1] = Misc.random(750);
				break;
			case 208://grimy rannar
				player.raidReward[0][1] = Misc.random(350);
				break;
			case 210://grimy irit
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 212://grimy avantoe
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 214://grimy kwuarm
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 3052://grimy snapdragon
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 216://grimy cadatine
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 2486://grimy lantadyme
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 218://grimy dwarf weed
				player.raidReward[0][1] = Misc.random(400);
				break;
			case 220://grimy torsol
				player.raidReward[0][1] = Misc.random(500);
				break;
			case 443://silver ore
				player.raidReward[0][1] = Misc.random(1500);
				break;
			case 454://coal
				player.raidReward[0][1] = Misc.random(1200);
				break;
			case 445://gold ore
				player.raidReward[0][1] = Misc.random(100);
				break;
			case 448://mithril ore
				player.raidReward[0][1] = Misc.random(750);
				break;
			case 450://adamant ore
				player.raidReward[0][1] = Misc.random(500);
				break;
			case 452://runite ore
				player.raidReward[0][1] = Misc.random(300);
				break;
			case 1624://uncut sapphire
				player.raidReward[0][1] = Misc.random(350);
				break;
			case 1622://uncut emerald
				player.raidReward[0][1] = Misc.random(350);
				break;
			case 1620://uncut ruby
				player.raidReward[0][1] = Misc.random(350);
				break;
			case 1618: //uncut diamond
				player.raidReward[0][1] = Misc.random(400);
				break;
			case 13391: //lizard fang
				player.raidReward[0][1] = Misc.random(3000);
				break;
			case 7937://pure ess
				player.raidReward[0][1] = Misc.random(3000);
				break;
			case 2722://hard casket
				player.raidReward[0][1] = 1;
				break;
			default:
				player.raidReward[0][1]=1;
				break;

		}
	}

	final int OLM = 7554;
	final int OLM_RIGHT_HAND= 7553;
	final int OLM_LEFT_HAND = 7555;

	public void handleMobDeath(int npcType) {
		player.getRaids().raidLeader.getRaids().mobAmount-=1;
		switch(npcType) {
			case OLM:
				Server.getGlobalObjects().add(new GlobalObject(-1, 3232, 5749, player.getHeight(), 10, 3));
				Server.getGlobalObjects().add(new GlobalObject(29885, 3220, 5743, player.getHeight(), 10, 3));
				Server.getGlobalObjects().add(new GlobalObject(29888, 3220, 5733, player.getHeight(), 10, 3));
				Server.getGlobalObjects().add(new GlobalObject(29882, 3220, 5738, player.getHeight(), 10, 3));
				Server.getGlobalObjects().add(new GlobalObject(30028, 3233, 5751, player.getHeight(), 10, 4));

				for (String username : player.getRaids().raidLeader.clan.activeMembers) {
					Player p = PlayerHandler.getPlayer(username);
					if (!p.inRaids()) {
						continue;
					}
					p.getRaids().giveReward();
					p.raidCount+=1;
					//Achievements.increase(player, AchievementType.RAIDS, 1);
					p.getPA().movePlayer(1255,3562,0);
					p.sendMessage("@red@Congratulations you have defeated The Great Olm and completed the raid!");
					p.sendMessage("@red@You have completed "+p.raidCount+" raids." );
					p.getItems().addItemUnderAnyCircumstance(p.raidReward[0][0], p.raidReward[0][1]);
					p.getRaids().roomNames = new ArrayList<String>();
					p.getRaids().roomPaths= new ArrayList<Location>();
					p.getRaids().currentRoom = 0;
					p.getRaids().mobAmount=0;
					p.getRaids().reachedRoom = 0;
					p.getRaids().activeBraziers = 0;
					p.getRaids().raidLeader=null;
					p.getRaids().lizards = false;
					p.getRaids().vasa = false;
					p.getRaids().vanguard = false;
					p.getRaids().ice = false;
					p.getRaids().chest = false;
					p.getRaids().skilling = false;
					p.getRaids().mystic = false;
					p.getRaids().tekton = false;
					p.getRaids().mutta = false;
					p.getRaids().archers = false;
					p.getRaids().olm = false;
					p.getRaids().olmDead = false;
					p.getRaids().rightHand = false;
					p.getRaids().leftHand = false;
				}

				return;
			case OLM_RIGHT_HAND:
				player.getRaids().raidLeader.getRaids().rightHand = true;
				if(player.getRaids().raidLeader.getRaids().leftHand == true) {
					player.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable.");
				}else {
					player.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!");
				}
				return;
			case OLM_LEFT_HAND:
				player.getRaids().raidLeader.getRaids().leftHand = true;
				if(player.getRaids().raidLeader.getRaids().rightHand == true) {
					player.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable.");
				}else {
					player.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!");
				}
				return;
		}
		//	int randomPoints = Misc.random(500);
		//	player.getRaids().addPoints(randomPoints);
		//	player.sendMessage("@red@You receive "+randomPoints+" points from killing this monster.");
		//	player.sendMessage("@red@You now have "+player.raidPoints+" points.");
		if(player.getRaids().raidLeader.getRaids().mobAmount <= 0) {
			player.sendMessage("@red@The room has been cleared and you are free to pass.");
		}else {
			player.sendMessage("@red@There are "+player.getRaids().raidLeader.getRaids().mobAmount+" enemies remaining.");
		}
		//player.sendMessage("test");
		player.getRaids().updateRaidPoints();
	}

	/**
	 * Spawns npc for the current room
	 * @param currentRoom The room
	 */
	public void spawnNpcs(int currentRoom) {

		int height = player.getRaids().getHeight(player);

		switch(player.getRaids().roomNames.get(currentRoom)) {
			case "lizardmen":
				if(lizards) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7573, 3274, 5262, height, 1, 350, 25, 300, 300,true);
						NPCHandler.spawn(7573, 3282, 5266, height, 1, 350, 25, 300, 300,true);
						NPCHandler.spawn(7573, 3275, 5269, height, 1, 350, 25, 300, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7573, 3274, 5262, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
						NPCHandler.spawn(7573, 3282, 5266, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
						NPCHandler.spawn(7573, 3275, 5269, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7573, 3274, 5262, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
						NPCHandler.spawn(7573, 3282, 5266, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
						NPCHandler.spawn(7573, 3275, 5269, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7573, 3274, 5262, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
						NPCHandler.spawn(7573, 3282, 5266, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
						NPCHandler.spawn(7573, 3275, 5269, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
					}
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7573, 3307, 5265, height, 1, 350, 25, 300, 300,true);
						NPCHandler.spawn(7573, 3314,5265, height, 1, 350, 25, 300, 300,true);
						NPCHandler.spawn(7573, 3314,5261, height, 1, 350, 25, 300, 300,true);
					} else if (player.clan.activeMembers.size() <= 3) {
						NPCHandler.spawn(7573, 3307,5265, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
						NPCHandler.spawn(7573, 3314,5265, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
						NPCHandler.spawn(7573, 3314,5261, height, 1, (int) (350 * 1.10), (int) (25 * 1.10), (int) (300 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7573, 3307,5265, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
						NPCHandler.spawn(7573, 3314,5265, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
						NPCHandler.spawn(7573, 3314,5261, height, 1, (int) (350 * 1.20), (int) (25 * 1.20), (int) (300 * 1.20), (int) (300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 7 ) {
						NPCHandler.spawn(7573, 3307,5265, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
						NPCHandler.spawn(7573, 3314,5265, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
						NPCHandler.spawn(7573, 3314,5261, height, 1, (int) (350 * 1.32), (int) (25 * 1.32), (int) (300 * 1.32), (int) (300 * 1.32),true);
					}

					//NPCHandler.spawn(7573, 3307,5265, height, 1, 350, 25, 300, 300,true);
					//NPCHandler.spawn(7573, 3314,5265, height, 1, 350, 25, 300, 300,true);
					//NPCHandler.spawn(7573, 3314,5261, height, 1, 350, 25, 300, 300,true);
				}

				lizards = true;
				mobAmount+=3;
				break;
			case "vasa":
				if(vasa) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7566, 3280,5295, height, -1, 650, 25, 250, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7566, 3280,5295, height, -1, (int) (650 * 1.10), (int) (25 * 1.10), (int) (250 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7566, 3280,5295, height, -1, (int) (650 * 1.20), (int) (25 * 1.20), (int) (250 * 1.20), (int) (300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7566, 3280,5295, height, -1, (int) (650 * 1.32), (int) (25 * 1.32), (int) (250 * 1.32), (int) (300 * 1.32),true);
					}
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7566, 3311,5295, height, -1, 650, 25, 250, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7566, 3311,5295, height, -1, (int) (650 * 1.10), (int) (25 * 1.10), (int) (250 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7566, 3311,5295, height, -1, (int) (650 * 1.20), (int) (25 * 1.20), (int) (250 * 1.20), (int) (300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7566, 3311,5295, height, -1, (int) (650 * 1.32), (int) (25 * 1.32), (int) (250 * 1.32), (int) (300 * 1.32),true);
					}
					//NPCHandler.spawn(7566, 3311,5295, height, -1, 650, 25, 250, 300,true);
				}
				vasa = true;
				mobAmount+=1;
				break;
			case "vanguard":
				if(vanguard) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7527, 3277,5326, height, -1, 300, 25, 140, 200,true); // melee vanguard
						NPCHandler.spawn(7528, 3277,5332, height, -1, 300, 25, 140, 200,true); // range vanguard
						NPCHandler.spawn(7529, 3285,5329, height, -1, 300, 25, 140, 200,true); // magic vanguard
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7527, 3277,5326, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
						NPCHandler.spawn(7528, 3277,5332, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
						NPCHandler.spawn(7529, 3285,5329, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7527, 3277,5326, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
						NPCHandler.spawn(7528, 3277,5332, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
						NPCHandler.spawn(7529, 3285,5329, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7527, 3277,5326, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
						NPCHandler.spawn(7528, 3277,5332, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
						NPCHandler.spawn(7529, 3285,5329, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
					}
					//NPCHandler.spawn(7527, 3277,5326, height, -1, 300, 25, 140, 200,true); // melee vanguard
					//NPCHandler.spawn(7528, 3277,5332, height, -1, 300, 25, 140, 200,true); // range vanguard
					//NPCHandler.spawn(7529, 3285,5329, height, -1, 300, 25, 140, 200,true); // magic vanguard
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7527, 3310,5324, height, -1, 300, 25, 140, 200,true); // melee vanguard
						NPCHandler.spawn(7528, 3310,5331, height, -1, 300, 25, 140, 200,true); // range vanguard
						NPCHandler.spawn(7529, 3316,5331, height, -1, 300, 25, 140, 200,true); // magic vanguard
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7527, 3310,5324, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
						NPCHandler.spawn(7528, 3310,5331, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
						NPCHandler.spawn(7529, 3316,5331, height, -1, (int) (300 * 1.10), (int) (25 * 1.10), (int) (140 * 1.10), (int) (200 * 1.10),true); // melee vanguard
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7527, 3310,5324, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
						NPCHandler.spawn(7528, 3310,5331, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
						NPCHandler.spawn(7529, 3316,5331, height, -1, (int) (300 * 1.20), (int) (25 * 1.20), (int) (140 * 1.20), (int) (200 * 1.20),true); // melee vanguard
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7527, 3310,5324, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
						NPCHandler.spawn(7528, 3310,5331, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
						NPCHandler.spawn(7529, 3316,5331, height, -1, (int) (300 * 1.32), (int) (25 * 1.32), (int) (140 * 1.32), (int) (200 * 1.32),true); // melee vanguard
					}
					//NPCHandler.spawn(7527, 3310,5324, height, -1, 300, 25, 140, 200,true); // melee vanguard
					//NPCHandler.spawn(7528, 3310,5331, height, -1, 300, 25, 140, 200,true); // range vanguard
					//NPCHandler.spawn(7529, 3316,5331, height, -1, 300, 25, 140, 200,true);// magic vanguard
				}
				vanguard = true;
				mobAmount+=3;
				break;
			case "ice":
				if(ice) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7585, 3273,5365, height, -1, 750, 45, 350, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7585, 3273,5365, height, -1, (int)(750 * 1.10), (int)(45 * 1.10), (int)(350 * 1.10), (int)(300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7585, 3273,5365, height, -1, (int)(750 * 1.20), (int)(45 * 1.20), (int)(350 * 1.20), (int)(300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7585, 3273,5365, height, -1, (int)(750 * 1.32), (int)(45 * 1.32), (int)(350 * 1.32), (int)(300 * 1.32),true);
					}
					//NPCHandler.spawn(7585, 3273,5365, height, -1, 750, 45, 350, 300,true);
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7585, 3310,5367, height, -1, 750, 45, 350, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7585, 3310,5367, height, -1, (int)(750 * 1.10), (int)(45 * 1.10), (int)(350 * 1.10), (int)(300 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7585, 3310,5367, height, -1, (int)(750 * 1.20), (int)(45 * 1.20), (int)(350 * 1.20), (int)(300 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7585, 3310,5367, height, -1, (int)(750 * 1.32), (int)(45 * 1.32), (int)(350 * 1.32), (int)(300 * 1.32),true);
					}
					//NPCHandler.spawn(7585, 3310,5367, height, -1, 750, 45, 350, 300,true);
				}
				ice = true;
				mobAmount+=1;
				break;
			case "chest":

				break;
			case "skilling":
				NPCHandler.spawn(3258, 3318,5454, height, 1, -1, -1, -1, -1,false);

				break;
			case "scavenger":

				break;
			case "skeletal":
				if(mystic) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7604, 3279,5271, height+1, -1, 250, 25, 400, 250,true);
						NPCHandler.spawn(7605, 3290,5268, height+1, -1, 250, 25, 500, 250,true);
						NPCHandler.spawn(7606, 3279,5264, height+1, -1, 250, 25, 400, 250,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7604, 3279,5271, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
						NPCHandler.spawn(7605, 3290,5268, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
						NPCHandler.spawn(7606, 3279,5264, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7604, 3279,5271, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
						NPCHandler.spawn(7605, 3290,5268, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
						NPCHandler.spawn(7606, 3279,5264, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7604, 3279,5271, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
						NPCHandler.spawn(7605, 3290,5268, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
						NPCHandler.spawn(7606, 3279,5264, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
					}
					//NPCHandler.spawn(7604, 3279,5271, height+1, -1, 250, 25, 400, 250,true);
					//NPCHandler.spawn(7605, 3290,5268, height+1, -1, 250, 25, 500, 250,true);
					//NPCHandler.spawn(7606, 3279,5264, height+1, -1, 250, 25, 400, 250,true);
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7604, 3318,5262, height+1, -1, 250, 25, 400, 250,true);
						NPCHandler.spawn(7605, 3307,5258, height+1, -1, 250, 25, 500, 250,true);
						NPCHandler.spawn(7606, 3301,5262, height+1, -1, 250, 25, 400, 250,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7604, 3318,5262, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
						NPCHandler.spawn(7605, 3307,5258, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
						NPCHandler.spawn(7606, 3301,5262, height+1, -1, (int)(250 * 1.05), (int) (25 * 1.05), (int) (400 * 1.05), (int) (250 * 1.05),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7604, 3318,5262, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
						NPCHandler.spawn(7605, 3307,5258, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
						NPCHandler.spawn(7606, 3301,5262, height+1, -1, (int)(250 * 1.10), (int) (25 * 1.10), (int) (400 * 1.10), (int) (250 * 1.10),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7604, 3318,5262, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
						NPCHandler.spawn(7605, 3307,5258, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
						NPCHandler.spawn(7606, 3301,5262, height+1, -1, (int)(250 * 1.20), (int) (25 * 1.20), (int) (400 * 1.20), (int) (250 * 1.20),true);
					}
					//NPCHandler.spawn(7604, 3318,5262,height+1, -1, 250, 25, 400, 250,true);
					//NPCHandler.spawn(7605, 3307,5258, height+1, -1, 250, 25, 500, 250,true);
					//NPCHandler.spawn(7606, 3301,5262, height+1, -1, 250, 25, 400, 250,true);
				}
				mobAmount+=3;
				mystic = true;
				break;
			case "tekton":
				if(tekton) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7544, 3280,5295, height+1, -1, 1200, 45, 450, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7544, 3280,5295, height+1, -1, (int) (1200 * 1.05), (int) (45 * 1.05), (int) (450 * 1.05), (int) (300 * 1.05),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7544, 3280,5295, height+1, -1, (int) (1200 * 1.10), (int) (45 * 1.10), (int) (450 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7544, 3280,5295, height+1, -1, (int) (1200 * 1.20), (int) (45 * 1.20), (int) (450 * 1.20), (int) (300 * 1.20),true);
					}
					//NPCHandler.spawn(7544, 3280,5295, height+1, -1, 1200, 45, 450, 300,true);
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7544, 3310, 5293, height+1, -1, 1200, 45, 450, 300,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7544, 3310, 5293, height+1, -1, (int) (1200 * 1.05), (int) (45 * 1.05), (int) (450 * 1.05), (int) (300 * 1.05),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7544, 3310, 5293, height+1, -1, (int) (1200 * 1.10), (int) (45 * 1.10), (int) (450 * 1.10), (int) (300 * 1.10),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7544, 3310, 5293, height+1, -1, (int) (1200 * 1.20), (int) (45 * 1.20), (int) (450 * 1.20), (int) (300 * 1.20),true);
					}
					//NPCHandler.spawn(7544, 3310, 5293, height+1, -1, 1200, 45, 450, 300,true);
				}
				mobAmount+=1;
				tekton = true;
				break;
			case "muttadile":
				if(mutta) {
					return;
				}
				if(path == 0) {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7563, 3276,5331, height + 1, 1, 750, 25, 400, 400,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7563, 3276,5331, height + 1, 1, (int)(750 * 1.10), (int)(25 * 1.10), (int)(400 * 1.10), (int)(400 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7563, 3276,5331, height + 1, 1, (int)(750 * 1.20), (int)(25 * 1.20), (int)(400 * 1.20), (int)(400 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7563, 3276,5331, height + 1, 1, (int)(750 * 1.32), (int)(25 * 1.32), (int)(400 * 1.32), (int)(400 * 1.32),true);
					}
					//	NPCHandler.spawn(7563, 3276,5331, height + 1, 1, 750, 25, 400, 400,true);
				}else {
					if (player.clan.activeMembers.size() == 1)  {
						NPCHandler.spawn(7563, 3308,5331, height + 1, 1, 750, 25, 400, 400,true);
					} else if (player.clan.activeMembers.size() <= 3 ) {
						NPCHandler.spawn(7563, 3308,5331, height + 1, 1, (int)(750 * 1.10), (int)(25 * 1.10), (int)(400 * 1.10), (int)(400 * 1.10),true);
					} else if (player.clan.activeMembers.size() <= 5 ) {
						NPCHandler.spawn(7563, 3308,5331, height + 1, 1, (int)(750 * 1.20), (int)(25 * 1.20), (int)(400 * 1.20), (int)(400 * 1.20),true);
					} else if (player.clan.activeMembers.size() >= 6 ) {
						NPCHandler.spawn(7563, 3308,5331, height + 1, 1, (int)(750 * 1.32), (int)(25 * 1.32), (int)(400 * 1.32), (int)(400 * 1.32),true);
					}
					//NPCHandler.spawn(7563, 3308,5331, height + 1, 1, 750, 25, 400, 400,true);
				}
				mobAmount+=1;
				mutta = true;
				break;
			case "archer":
				if(archers) {
					return;
				}
				if(path == 0) {
					NPCHandler.spawn(7559, 3287,5364, height + 1, -1, 150, 25, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3287,5363, height + 1, -1, 150, 25, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3285,5363, height + 1, -1, 150, 30, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3285,5364, height + 1, -1, 150, 30, 100, 100,true); // deathly ranger

					NPCHandler.spawn(7560, 3286,5369, height + 1, -1, 150, 25, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3284,5369, height + 1, -1, 150, 25, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3286,5370, height + 1, -1, 150, 30, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3284,5370, height + 1, -1, 150, 30, 100, 100,true); // deathly mager
				}else {
					NPCHandler.spawn(7559, 3319,5363, height + 1, -1, 150, 25, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3317,5363, height + 1, -1, 150, 25, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3317,5364, height + 1, -1, 150, 30, 100, 100,true); // deathly ranger
					NPCHandler.spawn(7559, 3319,5364, height + 1, -1, 150, 30, 100, 100,true); // deathly ranger

					NPCHandler.spawn(7560, 3318,5370, height + 1, -1, 150, 25, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3318,5369, height + 1, -1, 150, 25, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3316,5369, height + 1, -1, 150, 30, 100, 100,true); // deathly mager
					NPCHandler.spawn(7560, 3316,5370, height + 1, -1, 150, 30, 100, 100,true); // deathly mager
				}
				archers = true;
				mobAmount+=8;
				break;
			case "olm":
				if(olm) {
					return;
				}
				if (player.clan.activeMembers.size() == 1)  {
					NPCHandler.spawn(7553, 3223, 5733, height, -1, 500, 33, 272, 272,false); // left claw
					NPCHandler.spawn(7554, 3223, 5737, height, -1, 1600, 33, 272, 272,true); // olm head
					NPCHandler.spawn(7555, 3223, 5742, height, -1, 500, 33, 272, 272,false); // right claw
				} else if (player.clan.activeMembers.size() <= 3 ) {
					NPCHandler.spawn(7553, 3223, 5733, height, -1, (int) (500 * 1.10), (int) (33 * 1.10), (int) (272 * 1.10), (int) (272 * 1.10),false); // left claw
					NPCHandler.spawn(7554, 3223, 5737, height, -1, (int) (1600 * 1.10), (int) (33 * 1.10), (int) (272 * 1.10), (int) (272 * 1.10),false); // olm head
					NPCHandler.spawn(7555, 3223, 5742, height, -1, (int) (500 * 1.10), (int) (33 * 1.10), (int) (272 * 1.10), (int) (272 * 1.10),false); // right claw
				} else if (player.clan.activeMembers.size() <= 5 ) {
					NPCHandler.spawn(7553, 3223, 5733, height, -1, (int) (500 * 1.20), (int) (33 * 1.20), (int) (272 * 1.20), (int) (272 * 1.20),false); // left claw
					NPCHandler.spawn(7554, 3223, 5737, height, -1, (int) (1600 * 1.20), (int) (33 * 1.20), (int) (272 * 1.20), (int) (272 * 1.20),false); // olm head
					NPCHandler.spawn(7555, 3223, 5742, height, -1, (int) (500 * 1.20), (int) (33 * 1.20), (int) (272 * 1.20), (int) (272 * 1.20),false); // right claw
				} else if (player.clan.activeMembers.size() >= 6 ) {
					NPCHandler.spawn(7553, 3223, 5733, height, -1, (int) (500 * 1.30), (int) (33 * 1.30), (int) (272 * 1.30), (int) (272 * 1.30),false); // left claw
					NPCHandler.spawn(7554, 3223, 5737, height, -1, (int) (1600 * 1.30), (int) (33 * 1.30), (int) (272 * 1.30), (int) (272 * 1.30),false); // olm head
					NPCHandler.spawn(7555, 3223, 5742, height, -1, (int) (500 * 1.30), (int) (33 * 1.30), (int) (272 * 1.30), (int) (272 * 1.30),false); // right claw
				}
				//NPCHandler.spawn(7553, 3223, 5733, height, -1, 500, 33, 272, 272,false); // left claw
				//NPCHandler.spawn(7554, 3223, 5737, height, -1, 1600, 33, 272, 272,true); // olm head
				//NPCHandler.spawn(7555, 3223, 5742, height, -1, 500, 33, 272, 272,false); // right claw

				Server.getGlobalObjects().add(new GlobalObject(29884, 3220, 5743, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(29887, 3220, 5733, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(29881, 3220, 5737, getHeight(player), 3, 10));

				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5732, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5733, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5734, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5735, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5736, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5737, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5738, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5739, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5740, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5741, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5742, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5743, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5744, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5745, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5746, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5747, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5748, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5749, getHeight(player), 3, 10));
				Server.getGlobalObjects().add(new GlobalObject(2376, 3227, 5750, getHeight(player), 3, 10));
				olm = true;
				mobAmount+=3;
				break;
		}
		reachedRoom+=1;
	}
	/**
	 * Handles object clicking for raid objects
	 * @param player The player
	 * @param objectId The object id
	 * @return
	 */
	public boolean handleObjectClick(Player player, int objectId) {
		switch(objectId) {
			//case 29789:
			case 29734:
			case 29879:
				player.getRaids().nextRoom();
				return true;
			case 29789:
				if (player.objectX == 3318 && player.objectY == 5400) { // thieving room exit to Mages
					if (!player.getItems().playerHasItem(20895, 1)) {
						player.sendMessage("@red@You need the Vanguard Judgement book!");
						return true;
					}
				}
				if (player.objectX == 3318 && player.objectY == 5400) { // thieving room exit to Mages
					if (player.getItems().playerHasItem(20895, 1)) {
						player.getRaids().nextRoom();
						player.getItems().deleteItem(20895, 1);
						player.sendMessage("@red@You are permitted to pass through but the book vanishes..");
					}
					return true;
				}
				if (player.objectX != 3318 && player.objectY != 5400) {
					player.getRaids().nextRoom();
				}
				return true;
			case 29777:
				player.getRaids().startRaid();
				return true;
			case 30066:
				if (player.specRestore > 0) {
					int seconds = ((int)Math.floor(player.specRestore * 0.6));
					player.sendMessage("@red@You have to wait another " + seconds + " seconds to use the energy well.");
					return true;
				}

				player.specRestore = 120;
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.playerLevel[5] = player.getPA().getLevelForXP(player.playerXP[5]);
				player.getHealth().removeAllStatuses();
				player.getHealth().reset();
				player.getPA().refreshSkill(5);
				player.sendMessage("@red@You touch the energy well");
				return true;

			case 29778:
				if (!player.clan.isFounder(player.playerName)) {
					player.getRaids().roomNames = new ArrayList<String>();
					player.getRaids().roomPaths= new ArrayList<Location>();
					player.getRaids().currentRoom = 0;
					player.getRaids().mobAmount=0;
					player.getRaids().reachedRoom = 0;
					player.getRaids().activeBraziers = 0;
					player.getRaids().raidLeader=null;
					player.getPA().movePlayer(1245, 3561, 0);
					player.sendMessage("@red@You abandon your team and leave the raid.");
					return true;
				}
				if (player.clan.isFounder(player.playerName)) {
					for (String username : player.clan.activeMembers) {
						Player p = PlayerHandler.getPlayer(username);
						if (p == null) {
							continue;
						}
						if (Boundary.isIn(p, Boundary.RAIDS) || (Boundary.isIn(p, Boundary.OLM))) {
							p.getPA().movePlayer(1255,3562,0);
							p.getRaids().roomNames = new ArrayList<String>();
							p.getRaids().roomPaths= new ArrayList<Location>();
							p.getRaids().currentRoom = 0;
							p.getRaids().mobAmount=0;
							p.getRaids().reachedRoom = 0;
							p.getRaids().activeBraziers = 0;
							p.getRaids().raidLeader=null;
							p.getRaids().lizards = false;
							p.getRaids().vasa = false;
							p.getRaids().vanguard = false;
							p.getRaids().ice = false;
							p.getRaids().chest = false;
							p.getRaids().skilling = false;
							p.getRaids().mystic = false;
							p.getRaids().tekton = false;
							p.getRaids().mutta = false;
							p.getRaids().archers = false;
							p.getRaids().olm = false;
							p.getRaids().olmDead = false;
							p.getRaids().rightHand = false;
							p.getRaids().leftHand = false;
							p.getRaids().updateRaidPoints();
							p.sendMessage("@red@The leader of the clan has ended the raid!");
						}
					}
					//	player.getRaids().leaveGame(player);
					//player.sendMessage("@red@You have ended the raid.");
				}
				return true;

			case 30028:
				player.getPA().showInterface(57000);
				return true;
		}
		return false;
	}
	/**
	 * Goes to the next room, Handles spawning etc.
	 */
	public void nextRoom() {
		//player.sendMessage("nextroom1");
		if(player.getRaids().raidLeader.playerName != player.playerName) {
			if (player.getRaids().currentRoom + 1 > player.getRaids().raidLeader.getRaids().reachedRoom && currentRoom != 0) {
				if (player.getRaids().raidLeader.getRaids().mobAmount > 0 && player.getRaids().currentRoom == player.getRaids().raidLeader.getRaids().currentRoom) {
					player.sendMessage("@red@Please defeat all the monsters before going to the next room.");
					return;
				}
			}

		}else{
			if (player.getRaids().currentRoom == player.getRaids().raidLeader.getRaids().reachedRoom) {
				if (player.getRaids().raidLeader.getRaids().mobAmount > 0) {
					player.sendMessage("@red@Please defeat all the monsters before going to the next room.");
					return;
				}
			}
		}

		//player.sendMessage("nextroom2");
		/**
		 if(player.getRaids().raidLeader.playerName != player.playerName) {
		 if(player.getRaids().currentRoom + 1 < player.getRaids().raidLeader.getRaids().reachedRoom){
		 return;
		 }
		 }

		 if(player.getRaids().raidLeader.getRaids().mobAmount > 0 && currentRoom != 0 && player.getRaids().currentRoom < player.getRaids().raidLeader.getRaids().reachedRoom) {
		 player.sendMessage("@red@Please defeat all the monsters before going to the next room.");
		 return;
		 }
		 **/


		//player.sendMessage("nextroom3");
		player.getPA().movePlayer(roomPaths.get(currentRoom+1).getX(),roomPaths.get(currentRoom+1).getY(),roomPaths.get(currentRoom+1).getZ() == 1 ? getHeight(player) + 1 :getHeight(player));

		player.getRaids().updateRaidPoints();
		currentRoom+=1;

		if(player.getRaids().raidLeader.playerName != player.playerName) {
			return;
		}
		spawnNpcs(currentRoom);
	}
}

