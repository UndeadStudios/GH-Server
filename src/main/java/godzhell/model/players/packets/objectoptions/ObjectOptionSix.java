package godzhell.model.players.packets.objectoptions;

import godzhell.Server;
import godzhell.clip.Region;
import godzhell.model.players.Player;

public class ObjectOptionSix {
	
	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (!Region.objectExists(objectType, obX, obY, c.heightLevel)) {
			return;
		}
		c.clickObjectType = 0;
		
		switch (objectType) {
		
		case 8356://piscdocks
			c.getPA().movePlayer(1806, 3689, 0);
			break;
		}
	}

}
