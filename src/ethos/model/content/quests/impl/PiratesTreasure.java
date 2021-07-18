package ethos.model.content.quests.impl;

import ethos.model.players.Player;

/**
 * Pirates Treasure
 * @author Andrew (Mr Extremez)
 */

public class PiratesTreasure {


	public static void showInformation(Player client) {
		for (int i = 8144; i < 8195; i++) {
			client.getPA().sendFrame126("", i);
		}
		client.getPA().sendFrame126("@dre@Pirate's Treasure", 8144);
		client.getPA().sendFrame126("", 8145);
		if (client.pirateTreasure == 0) {
			client.getPA().sendFrame126("Pirate's Treasure", 8144);
			client.getPA().sendFrame126(
					"I can start this quest by speaking to Redbeard Frank in",
					8147);
			client.getPA().sendFrame126("Port Sarim", 8148);
			client.getPA().sendFrame126("", 8149);
			client.getPA().sendFrame126(
					"There are no minimum requirements.", 8150);
		} else if (client.pirateTreasure == 1) {
			client.getPA().sendFrame126("Pirate's Treasure", 8144);
			client.getPA().sendFrame126(
					"<str>I've talked to Redbeard.</str>", 8147);
			client.getPA().sendFrame126(
					"He wants me to get him some rum", 8148);
		} else if (client.pirateTreasure == 2) {
			client.getPA().sendFrame126("Pirate's Treasure", 8144);
			client.getPA().sendFrame126(
					"<str>I talked to Redbeard.</str>", 8147);
			client.getPA().sendFrame126(
					"<str>I found a way to get the rum</str>", 8148);
			client.getPA().sendFrame126(
					"I should get the rum and return to Redbeard.", 8149);
		} else if (client.pirateTreasure == 3) {
			client.getPA().sendFrame126("Pirate's Treasure", 8144);
			client.getPA().sendFrame126(
					"<str>I talked to Redbeard</str>.", 8147);
			client.getPA().sendFrame126(
					"<str>I found a way to get the rum</str>", 8148);
			client.getPA().sendFrame126("<str>I gave him the rum</str>",
					8149);
			client.getPA().sendFrame126(
					"He told me I need to look at the chest in", 8149);
			client.getPA().sendFrame126("The blue moon inn", 8150);
		} else if (client.pirateTreasure == 4) {
			client.getPA().sendFrame126("Pirate's Treasure", 8144);
			client.getPA().sendFrame126(
					"<str>I talked to Redbeard</str>.", 8147);
			client.getPA().sendFrame126(
					"<str>I found a way to get the rum</str>", 8148);
			client.getPA().sendFrame126("<str>I gave him the rum</str>",
					8149);
			client.getPA().sendFrame126(
					"<str>I looked in the chest</str>", 8149);
			client.getPA().sendFrame126(
					"I need to go to falador and kill the gardener", 8150);
		} else if (client.pirateTreasure == 5) {
			client.getPA().sendFrame126(
					"<str>I talked to Redbeard.</str>", 8147);
			client.getPA().sendFrame126(
					"<str>I found a way to get the rum</str>", 8148);
			client.getPA().sendFrame126("<str>I gave him the rum</str>",
					8149);
			client.getPA().sendFrame126(
					"<str>I looked in the chest</str>", 8149);
			client.getPA().sendFrame126(
					"<str>I went to falador and killed the gardener</str>", 8150);
			client.getPA().sendFrame126(
					"I should find the casket now", 8151);
		} else if (client.pirateTreasure == 6) {
			client.getPA().sendFrame126(
					"<str>I talked to Redbeard.", 8147);
			client.getPA().sendFrame126(
					"<str>I found a way to get the rum</str>", 8148);
			client.getPA().sendFrame126("<str>I gave him the rum</str>",
					8149);
			client.getPA().sendFrame126(
					"<str>I looked in the chest</str>", 8149);
			client.getPA().sendFrame126(
					"<str>I went to falador and killed the gardener</str>", 8150);
			client.getPA().sendFrame126("<str>I found the casket</str>",
					8181);
			client.getPA().sendFrame126("@red@QUEST COMPLETE",
					8152);
			client.getPA().sendFrame126(
					"As a reward, I gained a casket.", 8153);
			client.getPA().sendFrame126("2 Quest Points.", 8154);
		}
		client.getPA().showInterface(8134);
	}

}

