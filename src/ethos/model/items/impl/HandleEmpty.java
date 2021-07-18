package ethos.model.items.impl;

import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;

public class HandleEmpty {

	public static boolean canEmpty(Player c, int id) {
		return filledToEmpty(c, id) != -1;
	}

	public static int filledToEmpty(Player c, int id) {
		String itemName = ItemAssistant.getItemName(id);
		if (!itemName.contains("Ring") && !itemName.contains("necklace")) {
		if (itemName.contains("(3)") || itemName.contains("(4)") || itemName.contains("(2)") || itemName.contains("(1)")  || itemName.contains("Weapon poison")) {
			if (id != 1712 && id != 1710 && id != 1708 && id != 1706) {
					c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
					c.getItems().addItem(229, 1);
					c.sendMessage("You empty the vial.");
				}
			}
		}
		switch (id) {
		case 1937: // Jugs
		case 1989:
		case 1991:
		case 1993:
		case 3729:
			return 1935;
		case 227: // Vial of Water
			return 229;
		case 1927: // Buckets
		case 1929:
		case 4687:
		case 4286:
		case 1784:
		case 4693:
		case 6712:
		case 7471:
		case 7622:
		case 7624:
		case 7626:
			return 1925;
		}
		return -1;
	}

	public static void handleEmptyItem(Player c, int itemId, int giveItem) {
		final String name = ItemAssistant.getItemName(itemId);
		c.sendMessage("You empty your " + name + ".");
		c.getItems().deleteItem(itemId, 1);
		c.getItems().addItem(giveItem, 1);
	}

}