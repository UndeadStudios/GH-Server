package godzhell.model.content;

import java.util.*;

import godzhell.event.CycleEvent;
import godzhell.event.CycleEventContainer;
import godzhell.event.CycleEventHandler;
import godzhell.model.players.Player;
import godzhell.model.players.PlayerHandler;
import godzhell.model.items.GameItem;
import godzhell.model.items.ItemAssistant;
import godzhell.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Robbie
 *
 */
public class GalvekBag extends CycleEvent {

	/**
	 * The item id of the Pursuit Crate required to trigger the event
	 */
	public static final int GALVEK_BAG = 10834;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
					new GameItem(565, 1000),
					new GameItem(12746),
					new GameItem(560, 1000), 
					new GameItem(11937, 150),
					new GameItem(9242, 200), 
					new GameItem(9244, 200),
					new GameItem(19685), 
					new GameItem(4153), 
					new GameItem(4087), 
					new GameItem(1128, 5), 
					new GameItem(1080, 5),
					new GameItem(4588, 5),
					new GameItem(11730),
					new GameItem(13307, Misc.random(50) + 50), 
					new GameItem(12696, 15), 
					new GameItem(990, 3))
					
			);
			
		items.put(Rarity.UNCOMMON,
				Arrays.asList(
						new GameItem(11818),
						new GameItem(12748),
						new GameItem(11820), 
						new GameItem(11822), 
						new GameItem(20113), 
						new GameItem(20116), 
						new GameItem(20119), 
						new GameItem(20122), 
						new GameItem(20125), 
						new GameItem(19841), 
						new GameItem(20773), 
						new GameItem(20775),
						new GameItem(19677),
						new GameItem(20775),
						new GameItem(6816, 20),
						new GameItem(13307, Misc.random(100) + 100),
						new GameItem(20777))
						
		);
			
			items.put(Rarity.RARE,
					Arrays.asList(
							new GameItem(11840), 
							new GameItem(12749),
							new GameItem(4675), 
							new GameItem(20595), 
							new GameItem(20517), 
							new GameItem(20520), 
							new GameItem(20838),
							new GameItem(20840),
							new GameItem(20842),
							new GameItem(20844), 		
							new GameItem(20846),
							new GameItem(20272),
							new GameItem(20775),
							new GameItem(4151),
							new GameItem(20526),
							new GameItem(11826),
							new GameItem(11828),
							new GameItem(11830),
							new GameItem(6585),
							new GameItem(19553),
							new GameItem(13237),
							new GameItem(13346),
							new GameItem(11806),
							new GameItem(19677, 3),
							new GameItem(11230, Misc.random(200) + 50),
							new GameItem(13307, Misc.random(100) + 200),
							new GameItem(995, 1000000)));	
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new bag to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public GalvekBag(Player player) {
		this.player = player;
	}

	/**
	 * Opens a PvM Casket if possible, and ultimately triggers and event, if possible.
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open an ancient loot bag.");
			return;
		}
		if (!player.getItems().playerHasItem(GALVEK_BAG)) {
			player.sendMessage("You need an ancient loot bag to do this.");
			return;
		}
		player.getItems().deleteItem(GALVEK_BAG, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@SuppressWarnings("unused")
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int coins = 50000 + Misc.random(50000);
		int coinsDouble = 100000 + Misc.random(100000);
		int random = Misc.random(100);
		int rights = player.getRights().getPrimary().getValue() - 1;
		List<GameItem> itemList = random < 55 ? items.get(Rarity.COMMON) : random >= 55 && random <= 90 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(500) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ancient Bag</col>] <img="+ rights +"> <col=255>"+ Misc.capitalizeJustFirst(player.playerName)
					+ "</col> hit the jackpot and got an <col=CC0000>Arcane Spirit Shield</col>!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(12825, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(12825, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(12825, 1);
				break;
			}
		}

		if (Misc.random(750) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ancient Bag</col>] <img="+ rights +"> <col=255>"+ Misc.capitalizeJustFirst(player.playerName)
					+ "</col> hit the jackpot and got a <col=CC0000>Galvek Pet</col>!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(13511, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(13511, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(13511, 1);
				break;
			}
		}

		if (Misc.random(10) == 0) {
			player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			//player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("@red@You receive double coins!");
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>gp.");
			/*player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " just got very lucky and hit double coins!");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
					+ "</col> and <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col> from a Pursuit Crate.");*/
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			//PlayerHandler.executeGlobalMessage(
			//		"<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col> from a PvM Casket.");
		}
		container.stop();
	}

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}