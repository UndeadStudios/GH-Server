package godzhell.model.players.packets.objectoptions;

import godzhell.Server;
import godzhell.clip.Region;
import godzhell.model.players.Player;
import godzhell.model.players.Right;
import godzhell.model.content.skills.construction.Construction;
import godzhell.model.content.skills.construction.Objects;

public class ObjectOptionFour {
	
	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (!Region.objectExists(objectType, obX, obY, c.heightLevel)) {
			return;
		}
		c.getPA().resetVariables();
		if (Objects.isObject(objectType)) {
			Objects.handleObjectClick(c, objectType, obX, obY);
		}
		Construction.handleConstructionClick(c, objectType, obX, obY);
		c.clickObjectType = 0;
		c.turnPlayerTo(obX, obY);
		
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 4:  "+objectType+"");
		
		switch (objectType) {
		case 12309:
			c.getShops().openShop(126);
			break;
		case 8356://streehosidius
			c.getPA().movePlayer(1679, 3541, 0);
			break;
		}
	}

}