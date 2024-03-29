package godzhell.model.players.packets;

import godzhell.Server;
import godzhell.clip.ObjectDef;
import godzhell.event.CycleEvent;
import godzhell.event.CycleEventContainer;
import godzhell.event.CycleEventHandler;
import godzhell.model.content.skills.agility.AgilityHandler;
import godzhell.model.content.skills.farming.FarmingConstants;
import godzhell.model.content.skills.hunter.impling.PuroPuro;
import godzhell.model.multiplayer_session.MultiplayerSessionFinalizeType;
import godzhell.model.multiplayer_session.MultiplayerSessionStage;
import godzhell.model.multiplayer_session.MultiplayerSessionType;
import godzhell.model.multiplayer_session.duel.DuelSession;
import godzhell.model.players.Boundary;
import godzhell.model.players.PacketType;
import godzhell.model.players.Player;

import java.util.Objects;

/**
 * Click Object
 */
public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132,
			SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234, FIFTH_CLICK = 228;

	@Override
	public void processPacket(final Player c, int packetType, int packetSize) {

		c.clickObjectType = c.objectX = c.objectId = c.objectY = 0;
		c.objectYOffset = c.objectXOffset = 0;
		c.getPA().resetFollow();
		c.getCombat().resetPlayerAttack();

		if (c.isForceMovementActive()) {
			return;
		}
		if (c.teleTimer > 0) {
			return;
		}
		switch (packetType) {
		case FIRST_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndianA();
			c.objectId = c.getInStream().readInteger();
			c.objectY = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
			ObjectDef def = ObjectDef.getObjectDef(c.objectId);
			if (c.isForceMovementActive()) {
				return;
			}

			if (c.viewingLootBag || c.addingItemsToLootBag || c.viewingRunePouch) {
				return;
			}

			if (c.objectId == 11834 && Boundary.isIn(c, Boundary.FIGHT_CAVE)) {
				c.getFightCave().leaveGame();
				return;
			}
			// if (!Region.isWorldObject(c.objectId, c.objectX, c.objectY, c.heightLevel) &&
			// !Boundary.isIn(c, Boundary.ICE_DEMON) && !Boundary.isIn(c,
			// Boundary.GODWARS_BOSSROOMS) && !Boundary.isIn(c, Boundary.LIGHTHOUSE)
			// && !Boundary.isIn(c, Boundary.RFD)) {
			// c.sendMessage("Warning: The object could not be verified by the server. If
			// you feel this is");
			// c.sendMessage("incorrect, please contact a staff member to have this
			// resolved.");
			// if (c.getRights().isOrInherits(Right.OWNER)) {
			// c.sendMessage("Object Option One: " + c.objectId + ", " + c.objectX + ", " +
			// c.objectY + ", " + c.heightLevel);
			// }
			// return;
			// }
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			if (c.objectId == 9357) {
				if (Boundary.isIn(c, Boundary.FIGHT_CAVE)) {
					c.getFightCave().leaveGame();
				}
				return;
			}
			DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.debugMessage) {
				c.sendMessage("Clicked Object Option 1:  "+c.objectId+", Object name: "+def.getName()+", SizeX "+def.xLength()+", SizeY "+def.yLength());
			}
			if (Math.abs(c.getX() - c.objectX) > 25 || Math.abs(c.getY() - c.objectY) > 25) {
				c.resetWalkingQueue();
				break;
			}
			switch (c.objectId) {
			case 30282:
			case 30352:
				if(!c.hasSacrificedFcape) {
					c.sendMessage("I must earn TzHaar-Ket-Keh's trust before entering the Inferno.");
					break;
				}
				if(c.getY() >= 5114) {
					c.createTzkalzukInstance();
					c.getInferno().jumpIntoInferno();
					/*c.setForceMovement(2496, 5122, 0, 85, "NORTH", 6723);
						c.getAgilityHandler().stopEmote(c);
						c.getPA().sendScreenFade("", 1, 5);
						c.createTzkalzukInstance();
						c.getInferno().initiateTzkalzuk();*/
						
					break;
				} else {
					c.getPlayerAssistant().playerWalk(2496, 5115);
				}
				
				break;
			case 30283:
				c.getInfernoMinigame().leaveGame();
				break;
			case 25016:
			case 25017:
			case 25018:
			case 25029:
				PuroPuro.magicalWheat(c);
				break;
			case 21728:
				if (c.playerLevel[16] < 34) {
					c.sendMessage("You need an Agility level of 34 to pass this.");
					return;
				}
				if (c.absY == 9566) {
					AgilityHandler.delayEmote(c, "CRAWL", 2655, 9573, 0, 2);
				} else {
					AgilityHandler.delayEmote(c, "CRAWL", 2655, 9566, 0, 2);
				}
				break;
				case 31556:
					c.objectDistance=3;
					break;
			case 11701:
				c.getPA().startTeleport(2202, 3056, 0, "modern", false);
				break;
			case 31627:
				c.getPA().movePlayer(2457, 2839, 1);
				break;
			case 31609:
				c.getPA().movePlayer(2452, 2847, 2);
				break;
			case 32206:
				c.getPA().movePlayer(2457, 2839, 0);
				break;
			case 31610:
				c.getPA().movePlayer(2449, 2847, 1);
				break;
			case 9398:// deposit
				c.getPA().sendFrame126("The Bank of GodzHell - Deposit Box", 7421);
				c.getPA().sendFrame248(4465, 197);// 197 just because you can't
				// see it =\
				c.getItems().resetItems(7423);
				break;
			case 29735:// Basic training
				if (c.objectX != 3277 && c.objectY != 5169) {
					c.getPA().movePlayer(2634, 5069, 0);
					c.sendMessage("Welcome to the Basic training dungeon, you can find basic monsters here!");
				}
				break;
			case 6450:// Basic training ladder
				c.getPA().movePlayer(2910, 2717, 0);
				break;
			case 30386:
				c.getPA().movePlayer(2440, 3089, 0);
				break;
			case 27785:
				c.getDH().sendDialogues(70300, -1);
				break;
			case 26709:// strongholdslayer cave
				c.getPA().movePlayer(2429, 9825, 0);
				c.sendMessage("Welcome to the Stronghold slayer cave, you can find many slayer monsters here!");
				break;
			case 26710:// strongholdslayer caveexit
			case 27258:
				c.getPA().movePlayer(2430, 3425, 0);
				break;
			case 28892:// catacomb agility
				if (c.playerLevel[16] < 34) {
					c.sendMessage("You need an Agility level of 34 to pass this.");
					return;
				}
				if (c.absX == 1648) {
					AgilityHandler.delayEmote(c, "CRAWL", 1646, 10000, 0, 2);
				} else if (c.absX == 1716) {
					AgilityHandler.delayEmote(c, "CRAWL", 1706, 10078, 0, 2);
				} else if (c.absX == 1706) {
					AgilityHandler.delayEmote(c, "CRAWL", 1716, 10056, 0, 2);
				} else if (c.absX == 1646) {
					AgilityHandler.delayEmote(c, "CRAWL", 1648, 10009, 0, 2);
				}
				break;
			case 30175:// Stronghold short
				if (c.playerLevel[16] < 72) {
					c.sendMessage("You need an Agility level of 72 to pass this.");
					return;
				}
				if (c.absX == 2429) {
					AgilityHandler.delayEmote(c, "CRAWL", 2435, 9806, 0, 2);
				} else if (c.absX == 2435) {
					AgilityHandler.delayEmote(c, "CRAWL", 2429, 9806, 0, 2);
				}
				break;
			case 535:// Smoke Devil Entrance
				if (c.absX == 2379) {
					AgilityHandler.delayEmote(c, "CRAWL", 2376, 9452, 0, 2);
				}
				break;
			case 536:// Smoke Devil Exit
				if (c.absX == 2376) {
					AgilityHandler.delayEmote(c, "CRAWL", 2379, 9452, 0, 2);
				}
				break;
			
			case 1738:// Ice Dungeon in Port Sarim Enterance
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3009, 9552, 0, 2);
				break;
			case 2123:// relleka entrance
				AgilityHandler.delayFade(c, "CRAWL", 2808, 10002, 0, "You crawl into the entrance.",
						"and you end up in a dungeon.", 3);
				c.sendMessage("Welcome to the Relleka slayer dungeon, find many slayer tasks here.");
				break;
			case 2268:// ice dung exit
				AgilityHandler.delayEmote(c, "CLIMB_UP", 1651, 3619, 0, 2);
				break;
			case 2141:// relleka exit
				c.getPA().movePlayer(1259, 3502, 0);
				break;
			/*
			 * case 29734://dgorillas if (c.objectX == 1349 && c.objectY == 3591) {
			 * c.getPA().movePlayer(2130, 5646, 0); c.
			 * sendMessage("Welcome to the Demonic Gorilla's Dungeon, try your luck for a heavy frame!"
			 * ); } break;
			 */
			case 28687:// dgexit
				c.getPA().movePlayer(1348, 3590, 0);
				break;
			case 4153:// corpexit
				c.getPA().movePlayer(1547, 3571, 0);
				break;
			case 2544:// daggentrence
				c.getPA().movePlayer(2446, 10147, 0);
				break;
			case 8966:// dagexit
				c.getPA().movePlayer(1547, 3571, 0);
				break;
			case 2823:// mdragsentrance
				AgilityHandler.delayFade(c, "CRAWL", 1746, 5323, 0, "You crawl into the entrance.",
						"and you end up in a dungeon.", 3);
				c.sendMessage("Welcome to the Mithril Dragon Cave");
				break;
			case 25337:// mdragsexit
				c.getPA().movePlayer(1792, 3709, 0);
				break;
			case 4150:// warriors guild
				c.sendMessage("Welcome to the Warriors Guild");
				break;
			case 4151:// barrows
				c.getPA().movePlayer(3565, 3308, 0);
				c.sendMessage("Welcome to Barrows");
				break;
				case 31561:
				case 5014:
					c.objectDistance=2;
					break;
			case 15477:
			case 13405:
				c.objectDistance = 5;
				break;
				case 1733:
				case 10779:
					c.objectYOffset = 2;
					break;
				case 41438:
					c.objectDistance=2;
					break;
				case 11756:
				case 4551:
				case 4553:
				case 4555:
				case 4557:
				case 23556:
				case 677:
				case 21578:
				case 6461:
				case 6462:
				case 26645:
				case 26646:
				case 26762:
				case 26763:
				case 11374:
				case 26567:
				case 26568:
				case 26569:
				case 11406:
				case 11430:
				case 26461:
				case 26766:
				case 1759:
				case 1761:
				case 1753:
				case 14918:
				case 1754:
				case 11819:
				case 11826:
				case 1751:
				case 7471:
				case 11377:
				case 11375:
				case 11376:
				case 10817:
				case 10595:
				case 10596:
				case 29082:
				case 7811:
				case 28893:
					c.objectDistance = 4;
					break;
				case 25336:
				case 25338:
				case 25339:
				case 25340:
					c.objectDistance = 3;
					break;
				case 14938:
				case 14402:
				case 14403:
				case 14404:
				case 11634:
				case 14409:
				case 14398:
				case 14399:
				case 14413:
				case 14414:
				case 14832:
					c.objectDistance = 4;
					break;
				case 14833:
				case 14834:
				case 14835:
				case 14836:
				case 14841:
				case 14844:
				case 14845:
				case 14848:
				case 14846:
				case 14894:
				case 14847:
				case 14897:
				case 14899:
				case 14901:
				case 14903:
				case 14904:
				case 14905:
				case 14911:
				case 14919:
				case 14920:
				case 14921:
				case 14922:
				case 14923:
				case 14924:
				case 14928:
				case 14932:
				case 14929:
				case 14930:
				case 14931:
					c.objectDistance = 4;
					break;
				case 14936:
				case 14937:
				case 14939:
				case 14940:
				case 14941:
				case 14944:
				case 14945:
				case 6260:
				case 14947:
				case 14987:
				case 14990:
				case 14991:
				case 14992:
				case 14994:
				case 15609:
				case 26635:
				case 15610:
				case 15611:
				case 28912:
				case 15612:
					c.objectDistance = 4;
				case 31925:
				case 30189:
					c.objectDistance = 2;
					break;
				case 31858:
					c.objectYOffset = 1;
					break;
			case 29150:
				c.objectYOffset = 1;
				break;
			case 23131:
			case 26366: // Godwars altars
			case 26365:
			case 26364:
			case 26363:
			case 678:
			case 16466:
			case 23569:
			case 23568:
			case 23132:
			case 29730:
			case 29729:
			case 29728:
				c.objectDistance = 5;
				break;
			case 3044:
			case 21764:
			case 17010:
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
			case FarmingConstants.GRASS_OBJECT:
			case FarmingConstants.HERB_OBJECT:
			case FarmingConstants.HERB_PATCH_DEPLETED:
			case 16671:
			case 17068:
			case 27057:
				c.objectDistance = 6;
				break;
			case 5094:
			case 5096:
			case 5097:
			case 5098:
			case 14912:
			case 16511:
				c.objectDistance = 7;
				break;
			case 26562:
				c.objectDistance = 2;
				break;
			case 26503:
				c.objectDistance = 1;
				break;
			case 26518:
				c.objectDistance = 1;
				break;
			case 26380:
				c.objectDistance = 9;
				break;
			case 26502:
				c.objectDistance = 1;
				break;
			case 20720:
			case 20721:
			case 20722:
			case 20770:
			case 20771:
			case 20772:
			case 10777:
			case 10778:
			case 27255:
				c.objectDistance = 3;
				break;
			case 7674:
				c.objectDistance = 2;
				break;
			case 11758:
			case 11764:
			case 11762:
			case 11759:
			case 1756:
			case 26724:
				c.objectDistance = 5;
				break;
			case 23271:
				c.objectDistance = 5;
				break;
			case 2491:
				c.objectDistance = 10;
				break;
			case 245:
				c.objectYOffset = -1;
				c.objectDistance = 0;
				break;
			case 272:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;
			case 273:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;
			case 246:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;
			case 4493:
			case 4494:
			case 4496:
			case 4495:
				c.objectDistance = 5;
				break;
			case 12536:
			case 12537:
			case 12538:
				c.objectDistance = 2;
				break;
			case 20667: // Barrows tomb staircases
			case 20668:
			case 20669:
			case 20670:
				c.objectDistance = 1;
				break;
			case 20671:
			case 20672:
			case 16664:
				c.objectDistance = 3;
				break;
			case 10229:
			case 6522:
			case 11734:
			case 11833:
			case 11834:
				c.objectDistance = 7;
				break;
			case 29681:
			case 29682:
				if (c.objectX == 1570 && c.objectY == 3484)
					c.objectYOffset = 1;
				break;
			case 8959:
				c.objectYOffset = 1;
				break;
			case 4417:
				if (c.objectX == 2425 && c.objectY == 3074)
					c.objectYOffset = 2;
				break;
			case 4420:
				if (c.getX() >= 2383 && c.getX() <= 2385) {
					c.objectYOffset = 1;
				} else {
					c.objectYOffset = -2;
				}
				break;
			case 31861:
			case 13182:
			case 6552:
			case 409:
			case 28900:
			case 12941:
				c.objectDistance = 3;
				break;
			case 2114:
			case 2118:
			case 2119:
			case 2120:
			case 16509:
			case 21725:
			case 21727:
			case 23104:
				c.objectDistance = 4;
				break;
			case 2879:
			case 2878:
				c.objectDistance = 3;
				break;
			case 29668:
			case 29670:
				if (c.objectX == 1572)
					c.objectXOffset = 1;
				else if (c.objectY == 3494)
					c.objectYOffset = 1;
				break;
			case 2558:
				c.objectDistance = 0;
				if (c.absX > c.objectX && c.objectX == 3044)
					c.objectXOffset = 1;
				if (c.absY > c.objectY)
					c.objectYOffset = 1;
				if (c.absX < c.objectX && c.objectX == 3038)
					c.objectXOffset = -1;
				break;
			case 9356:
				c.objectDistance = 2;
				break;
			case 5959:
			case 1815:
			case 5960:
			case 1816:
				c.objectDistance = 0;
				break;
			case 9293:
				c.objectDistance = 2;
				break;
			case 4418:
				if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2380 && c.objectY == 3127)
					c.objectYOffset = 2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				break;
			case 9706:
				c.objectDistance = 0;
				c.objectXOffset = 1;
				break;
			case 9707:
				c.objectDistance = 0;
				c.objectYOffset = -1;
				break;
			case 4419:
			case 6707: // verac
				c.objectYOffset = 3;
				break;
			case 6823:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
			case 6706: // torag
				c.objectXOffset = 2;
				break;
			case 6772:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
			case 6705: // karils
				c.objectYOffset = -1;
				break;
			case 6822:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
			case 6704: // guthan stairs
				c.objectYOffset = -1;
				break;
			case 6773:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
			case 6703: // dharok stairs
				c.objectXOffset = -1;
				break;
			case 6771:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
			case 6702: // ahrim stairs
				c.objectXOffset = -1;
				break;
			case 6821:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
			case 3192:
				c.objectDistance = 7;
				break;
				case 1276:
				case 1278:// trees
				case 1279:
				case 1281: // oak
				case 1758:
				case 1760:
				case 1750:
				case 9036:
				case 3037:
				case 29763:
				case 11761:
				case 11763:
				case 11755:
				case 1308: // willow
				case 1307: // maple
				case 1309: // yew
				case 1306: // yew
				case 10820:
				case 10833:
				case 10834:
				case 10832:
				case 10822:
				case 10829: // willow
				case 10819: // willow
				case 10831: // willow
				case 10828:
				case 8513:
				case 8512:
				case 8511:
				case 8510:
				case 8509:
				case 8508:
				case 8507:
				case 8506:
				case 8504:
				case 8503:
				case 5121:
				case 4536:
				case 8441:
				case 8442:
				case 8443:
				case 8444:
				case 8440:
				case 8439:
				case 8438:
				case 8437:
				case 8436:
				case 8435:
				case 5126:
				case 4674:
				case 4535:
					c.objectDistance = 3;
					break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(),
					c.objectDistance)) {
				c.getActions().firstClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 1;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {

						if (c.clickObjectType == 1 && c.goodDistance(c.objectX + c.objectXOffset,
								c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().firstClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType > 1 || c.clickObjectType == 0) {
							container.stop();
						}
					}

					@Override
					public void stop() {

						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
		case SECOND_CLICK:
			c.objectId = c.getInStream().readInteger();
			c.objectY = c.getInStream().readSignedWordBigEndian();
			c.objectX = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
			// if (!Region.isWorldObject(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
			// c.sendMessage("Warning: The object could not be verified by the server. If
			// you feel this is");
			// c.sendMessage("incorrect, please contact a staff member to have this
			// resolved.");
			// return;
			// }
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.debugMessage) {
				c.sendMessage("Object Option Two: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: " + c.objectY
						+ " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}
			if (c.objectId == 12309) {
				c.getShops().openShop(14);
			}
			switch (c.objectId) {
			case 20877:
			case 2030: // Allows for the furnace to be used from the other side too
				c.objectDistance = 4;
				c.objectXOffset = 3;
				c.objectYOffset = 3;
				break;

			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
			case 2478:
			case 2483:
			case 2484:
			case 11734:
			case 11731:
			case 11732:
			case 4874:
			case 23104:
			case 24009:
			case 4876:
			case 14011:
			case 4875:
			case 7811:
			case 28900:
				c.objectDistance = 3;
				break;
			case 6163:
			case 6165:
			case 6166:
			case 6164:
			case 6162:
				c.objectDistance = 2;
				break;
			case 3192:
				c.objectDistance = 7;
				break;
			case 12536:
			case 12537:
			case 12538:
				c.objectDistance = 2;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;

			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(),
					c.objectDistance)) {
				c.getActions().secondClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 2;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {

						if (c.clickObjectType == 2 && c.goodDistance(c.objectX + c.objectXOffset,
								c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().secondClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 2 || c.clickObjectType > 2)
							container.stop();
					}

					@Override
					public void stop() {

						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
		case THIRD_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndian();
			c.objectY = c.getInStream().readUnsignedWord();
			c.objectId = c.getInStream().readUnsignedWord();
			// if (!Region.isWorldObject(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
			// c.sendMessage("Warning: The object could not be verified by the server. If
			// you feel this is");
			//// c.sendMessage("incorrect, please contact a staff member to have this
			// resolved.");
			// return;
			// }
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.debugMessage) {
				c.sendMessage("Object Option Three: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: "
						+ c.objectY + " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}
			switch (c.objectId) {
			case 7811:
				c.objectDistance = 3;
				break;
			case 12536:
			case 12537:
			case 12538:
				c.objectDistance = 2;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(),
					c.objectDistance)) {
				c.getActions().thirdClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 3;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {

						if (c.clickObjectType == 3 && c.goodDistance(c.objectX + c.objectXOffset,
								c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().thirdClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 3)
							container.stop();
					}

					@Override
					public void stop() {

						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
		case FOURTH_CLICK:
			c.objectX = c.getInStream().readUnsignedWordBigEndianA();
			c.objectId = c.getInStream().readInteger();
			c.objectY = c.getInStream().readUnsignedWordBigEndianA();
			// if (!Region.isWorldObject(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
			// c.sendMessage("Warning: The object could not be verified by the server. If
			// you feel this is");
			// c.sendMessage("incorrect, please contact a staff member to have this
			// resolved.");
			/// return;
			// }
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.debugMessage) {
				c.sendMessage("Object Option Four: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: "
						+ c.objectY + " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}
			switch (c.objectId) {
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(),
					c.objectDistance)) {
				c.getActions().fourthClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 4;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {

						if (c.clickObjectType == 4 && c.goodDistance(c.objectX + c.objectXOffset,
								c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().fourthClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 4)
							container.stop();
					}

					@Override
					public void stop() {

						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
			
		case FIFTH_CLICK:
			c.objectId = c.getInStream().readInteger();
			c.objectY = c.getInStream().readUnsignedWordA();
			c.objectX = c.getInStream().readUnsignedWord();
			//client.face(client.objectX, client.objectY);
			//client.objectDistance = 1;
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.debugMessage) {
				c.sendMessage("Object Option Fifth: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: "
						+ c.objectY + " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}
			switch (c.objectId) {
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(),
					c.objectDistance)) {
				c.getActions().fifthClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 5;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {

						if (c.clickObjectType == 5 && c.goodDistance(c.objectX + c.objectXOffset,
								c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().fifthClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 5)
							container.stop();
					}

					@Override
					public void stop() {

						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;

		}

	}

	public void handleSpecialCase(Player c, int id, int x, int y) {

	}

}
