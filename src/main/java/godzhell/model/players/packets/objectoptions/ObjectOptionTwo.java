package godzhell.model.players.packets.objectoptions;

import godzhell.Server;
import godzhell.clip.ObjectDef;
import godzhell.clip.Region;
import godzhell.definitions.ObjectID;
import godzhell.model.content.skills.FlaxPicking;
import godzhell.model.content.skills.thieving.StallData;
import godzhell.model.objects.functions.Pickable;
import godzhell.model.players.Player;
import godzhell.model.players.Position;
import godzhell.model.players.Right;
import godzhell.model.players.packets.objectoptions.impl.DarkAltar;
import godzhell.util.Location3D;
import godzhell.util.Misc;

/*
 * @author Matt
 * Handles all 2nd options for objects.
 */

public class ObjectOptionTwo {

	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (!Region.objectExists(objectType, obX, obY, c.heightLevel)) {
			return;
		}
		c.clickObjectType = 0;
		c.getFarming().patchObjectInteraction(objectType, -1, obX, obY);
		if (Server.getHolidayController().clickObject(c, 2, objectType, obX, obY)) {
			return;
		}
		Location3D location = new Location3D(obX, obY, c.heightLevel);
		ObjectDef def = ObjectDef.getObjectDef(objectType);
		if ((def!=null ? def.name : null)!= null && def.name.toLowerCase().contains("bank")) {
			c.getPA().openUpBank();
		}
		if((def!=null ? def.name : null)!= null && def.name.toLowerCase().equals("spinning wheel")){
			c.getPA().showInterface(33155);
		}
		if((def!=null ? def.name : null)!= null && def.name.toLowerCase().equals("ladder")) {
			if(def.actions[1].equals("Climb-up")) {
				if(obX == 3069 && obY == 10256) { // custom locations
					c.getPA().movePlayer(3017, 3850, 0);
					return;
				}
				if(obX == 3017 && obY == 10249) { // custom locations
					c.getPA().movePlayer(3069, 3857, 0);
					return;
				}
				if(c.getX() > 6400) {
					c.getPA().movePlayer(c.getX(), c.getX()-6400, c.heightLevel);
					return;
				} else {
					c.getPA().movePlayer(c.absX, c.absY, c.heightLevel+1);
					return;
				}
			}
			if(def.actions[1].equals("Climb-down")) {
				if(obX == 3017 && obY == 3849) { // custom locations
					c.getPA().movePlayer(3069, 10257, 0);
					return;
				}
				if(obX == 3069 && obY == 3856) { // custom locations
					c.getPA().movePlayer(3017, 10248, 0);
					return;
				}
				if(obX == 1570 && obY == 2829 && c.heightLevel == 1) {
					c.getPA().movePlayer(1570, 2830, 0);
					return;
				}
				if(obX == 1560 && obY == 2829 && c.heightLevel == 1) {
					c.getPA().movePlayer(1560, 2830, 0);
					return;
				}
				if(c.getX() < 6400 && (c.heightLevel & 3) == 0) {
					c.getPA().movePlayer(c.getX(), c.getX()+6400, c.heightLevel);
					return;
				} else {
					c.getPA().movePlayer(c.absX, c.absY, c.heightLevel-1);
					return;
				}
			}
		}
		if((def!=null ? def.name : null)!= null && def.name.toLowerCase().equals("staircase")) {
			if(def.actions[1].equals("Climb-up")) {
				if(obX == 3103 && obY == 3159) { // Wizard tower
					c.getPA().movePlayer(3104, 3161, 2);
					return;
				}
				c.getPA().movePlayer(c.absX, c.absY, c.heightLevel+1);
				return;
				
			}
			if(def.actions[1].equals("Climb-down")) {	
				c.getPA().movePlayer(c.absX, c.absY, c.heightLevel-1);
				return;
		}
		}
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 2:  "+objectType+", Object name: "+def.getName());
		switch (objectType) {
		case 29778:
			c.sendMessage("hello");
			break;
		case 28900:
			DarkAltar.handleRechargeInteraction(c);
			break;
		case 2272:
			if(obX == 2984 && obY == 3336) {
				c.getPA().object(2271, obX, obY, c.heightLevel, 1, 10);
			}
			
			break;

		case 4525:
			c.getHouse().setLocked(!c.getHouse().isLocked());
			break;
		case 1161:
		case 2646:
		case 313:
		case 5585:
		case 5584:
		case 312:
		case 3366:
		case 5583:
		case 15506:
			Pickable.pickObject(c, objectType, obX, obY);
			break;
		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getShops().openShop(115);
			break;
		/**
		 * Iron Winch - peek
		 */
			case 9157:
				c.getPA().showInterface(51000);
				c.getTeleport().selection(c, 0);
				break;
		case 23104:
			c.getDH().sendDialogues(110, 5870);
			break;
			
		case 2118:
			c.getPA().movePlayer(3434, 3537, 0);
			break;

		case 2114:
			c.getPA().movePlayer(3433, 3537, 1);
			break;
		case 25824:
			c.turnPlayerTo(obX, obY);
			c.getDH().sendDialogues(40, -1);
			break;
		case 26260:
			c.getDH().sendDialogues(55874, -1);
			break;
		case 14896:
			c.turnPlayerTo(obX, obY);
			FlaxPicking.getInstance().pick(c, new Location3D(obX, obY, c.heightLevel));
			break;
		
		case 3840: // Compost Bin
			c.getFarming().handleCompostRemoval();
		break;
			case ObjectID.SILK_STALL:
			case ObjectID.SILK_STALL_6568:
			case ObjectID.SILK_STALL_11729:
			case ObjectID.SILK_STALL_20344:
			case ObjectID.SILK_STALL_36569:
			case ObjectID.SILK_STALL_41755:
			c.getThieving().steal(StallData.Silk, objectType, location);
			break;
			case ObjectID.BAKERS_STALL:
			case ObjectID.BAKERY_STALL_6163:
			case ObjectID.BAKERY_STALL_6569:
			case ObjectID.BAKERY_STALL_20345:
			c.getThieving().steal(StallData.Baker, objectType, location);
			break;
			case ObjectID.GEM_STALL_11731:
			c.getThieving().steal(StallData.Gem2, objectType, location);
			break;
			case ObjectID.FUR_STALL:
			case ObjectID.FUR_STALL_4278:
			case ObjectID.FUR_STALL_6571:
			case ObjectID.FUR_STALL_11732:
			case ObjectID.FUR_STALL_20347:
			case ObjectID.FUR_STALL_37405:
			c.getThieving().steal(StallData.FUR, objectType, location);
			break;
			case ObjectID.VEG_STALL:
			case ObjectID.VEG_STALL_4708:
				c.getThieving().steal(StallData.VEG, objectType, location);
				break;
			case ObjectID.FISH_STALL:
			case ObjectID.FISH_STALL_4705:
			case ObjectID.FISH_STALL_4707:
			case ObjectID.FISH_STALL_31712:
			case ObjectID.FISH_STALL_37404:
				c.getThieving().steal(StallData.FISH, objectType, location);
				break;
			case ObjectID.SPICE_STALL:
			case ObjectID.SPICE_STALL_6572:
			case ObjectID.SPICE_STALL_11733:
			case ObjectID.SPICE_STALL_20348:
			case ObjectID.SPICE_STALL_36572:
			c.getThieving().steal(StallData.Spice, objectType, location);
			break;
			case ObjectID.SILVER_STALL:
			case ObjectID.SILVER_STALL_6164:
			case ObjectID.SILVER_STALL_11734:
			case ObjectID.SILVER_STALL_36570:
			case ObjectID.SILVER_STALL_41757:
			c.getThieving().steal(StallData.Silver, objectType, location);
			break;
		case ObjectID.GENERAL_STALL:
			c.getThieving().steal(StallData.General, objectType, location);
			break;
			case ObjectID.SCIMITAR_STALL:
			c.getThieving().steal(StallData.Scimitar, objectType, location);
			break;
			case ObjectID.FOOD_STALL:
			c.getThieving().steal(StallData.food, objectType, location);
			break;
			case 6:
				c.getCannon().pickup(new Position(c.objectX, c.objectY, c.heightLevel));
				break;
		case 23609:
			c.getPA().movePlayer(3507, 9494, 0);
			break;
			
		case 2558:
		case 8356://streequid
			c.getPA().movePlayer(1255, 3568, 0);
			break;
		case 2557:
			if (System.currentTimeMillis() - c.lastLockPick < 1000 || c.freezeTimer > 0) {
				return;
			}
			c.lastLockPick = System.currentTimeMillis();
			if (c.getItems().playerHasItem(1523, 1)) {

				if (Misc.random(10) <= 2) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (c.objectX == 3044 && c.objectY == 3956) {
					if (c.absX == 3045) {
						c.getPA().walkTo(-1, 0);
					} else if (c.absX == 3044) {
						c.getPA().walkTo(1, 0);
					}

				} else if (c.objectX == 3038 && c.objectY == 3956) {
					if (c.absX == 3037) {
						c.getPA().walkTo(1, 0);
					} else if (c.absX == 3038) {
						c.getPA().walkTo(-1, 0);
					}
				} else if (c.objectX == 3041 && c.objectY == 3959) {
					if (c.absY == 3960) {
						c.getPA().walkTo(0, -1);
					} else if (c.absY == 3959) {
						c.getPA().walkTo(0, 1);
					}
				} else if (c.objectX == 3191 && c.objectY == 3963) {
					if (c.absY == 3963) {
						c.getPA().walkTo(0, -1);
					} else if (c.absY == 3962) {
						c.getPA().walkTo(0, 1);
					}
				} else if (c.objectX == 3190 && c.objectY == 3957) {
					if (c.absY == 3957) {
						c.getPA().walkTo(0, 1);
					} else if (c.absY == 3958) {
						c.getPA().walkTo(0, -1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		case 7814:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 838);
				c.sendMessage("An ancient wisdomin fills your mind.");
			} else if (c.playerMagicBook == 1) {
				c.sendMessage("You switch to the lunar spellbook.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
			} else if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
			}
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 838);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
		/*
		 * One stall that will give different amount of money depending on your thieving level, also different amount of xp.
		 */
		case 2781:
		case 26814:
		case 11666:
		case 3044:
		case 16469:
		case 2030:
		case 24009:
		case 26300:
		case 10082:
			c.getSmithing().sendSmelting();
			break;
			
			/**
		 * Opening the bank.
		 */
		case 24101:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
		case 25808:
		case 11744:
		case 11748:
		case 10060:
		case 24347:
		case 16700:
			c.getPA().openUpBank();
			break;

		}
	}
}
