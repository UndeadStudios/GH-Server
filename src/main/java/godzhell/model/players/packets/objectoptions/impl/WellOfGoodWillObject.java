package godzhell.model.players.packets.objectoptions.impl;

import godzhell.model.content.wogw.Wogw;
import godzhell.model.content.wogw.Wogwitems;
import godzhell.model.players.Player;
import godzhell.util.Misc;

public class WellOfGoodWillObject {

	private static void sendInterfaces(Player c) {

		c.getPA().showInterface(38000);
		c.getPA().sendFrame171(1, 38020);
		c.getPA().sendChangeSprite(38006, (byte) 1);
		c.getPA().sendChangeSprite(38007, (byte) 1);
		c.getPA().sendChangeSprite(38008, (byte) 1);
		c.getPA().sendChangeSprite(38013, (byte) 1);
		c.getPA().sendFrame126(""+Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE)+"/50M", 38015);
		c.getPA().sendFrame126(""+Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_MINIGAME_BONUS)+"/50M", 38016);
		c.getPA().sendFrame126(""+Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS)+"/100M", 38017);
		c.getPA().sendFrame126(""+Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS)+"/100M", 38018);

	}

	private static boolean canInteract(Player c, int itemId) {

		if (itemId==12926) {
			c.sendMessage("Please empty your blowpipe before donating it!");
			return false;
		}
		return true;
	}

	public static void handleInteraction(Player c, int itemId) {

		if (canInteract(c, itemId)) {
			if (itemId==995) {
				sendInterfaces(c);
				for (int i=0; i<5; i++) {
					if (Wogw.lastDonators[i]==null) {
						c.getPA().sendFrame126("None", 38033+i);
						continue;
					}
					c.getPA().sendFrame126(Wogw.lastDonators[i], 38033+i);
				}
			}
			for (final Wogwitems.itemsOnWell t : Wogwitems.itemsOnWell.values()) {
				if (itemId==t.getItemId()) {
					c.wellItem=itemId;
					c.wellItemPrice=t.getItemWorth();
					c.getDH().sendDialogues(784, -1);
				}
			}
		}
	}
}
