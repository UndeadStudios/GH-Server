package godzhell.model.players.packets.objectoptions;

import godzhell.Server;
import godzhell.clip.ObjectDef;
import godzhell.clip.Region;
import godzhell.model.players.Player;
import godzhell.model.players.Right;
import godzhell.model.content.skills.construction.Construction;
import godzhell.util.Misc;

public class ObjectOptionFive {
	
	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (!Region.objectExists(objectType, obX, obY, c.heightLevel)) {
			return;
		}
		c.getPA().resetVariables();
		c.clickObjectType = 0;
		c.turnPlayerTo(obX, obY);
		ObjectDef def = ObjectDef.getObjectDef(objectType);
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 5:  "+objectType+", Object name: "+def.getName());
		Construction.handleConstructionClick(c, objectType, obX, obY);
		switch (objectType) {
			case 5492:
				if (c.getItems().playerHasItem(1523, 1)
						&& Misc.random(4) < 3) {
					c.getPA().movePlayer(3149, 9652, 0);
					c.sendMessage(
							"You go down the trapdoor.");
					c.startAnimation(827);
					c.getPA().addSkillXP(.5,
							c.playerThieving);
					c.getPA().closeAllWindows();
				} else if (!c.getItems().playerHasItem(1523, 1)
						&& Misc.random(5) < 2) {
					c.getPA().movePlayer(3149, 9652, 0);
					c.sendMessage(
							"You go down the trapdoor.");
					c.startAnimation(827);
					c.getPA().addSkillXP(.5,
							c.playerThieving);
					c.getPA().closeAllWindows();
					c.resetWalkingQueue();
				} else if (c.getItems().playerHasItem(1523, 1)
						&& Misc.random(4) > 3) {
					c.sendMessage(
							"You fail to pick the lock.");
					c
							.sendMessage(
									"Your thieving has been drained, your fingers feel numb.");
					c.playerLevel[17] = c.getPA()
							.getLevelForXP(c.playerXP[17]) - 1;
					c.getPA().refreshSkill(17);
					c.getItems().deleteItem(1523, 1);
				} else if (!c.getItems().playerHasItem(1523, 1)
						&& Misc.random(5) > 2) {
					c.sendMessage(
							"You fail to pick the lock.");
					c
							.sendMessage(
									"Your thieving has been drained, your fingers feel numb.");
					c.playerLevel[17] = c.getPA()
							.getLevelForXP(c.playerXP[17]) - 1;
					c.getPA().refreshSkill(17);
				}
				break;
		case 12309:
			c.getShops().openShop(14);
			break;
		}
	}

}
