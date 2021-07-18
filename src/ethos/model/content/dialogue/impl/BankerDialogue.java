package ethos.model.content.dialogue.impl;

import ethos.model.content.BankPin;
import ethos.model.content.dialogue.Dialogue;
import ethos.model.content.dialogue.DialogueConstants;
import ethos.model.content.dialogue.DialogueManager;
import ethos.model.content.dialogue.Emotion;

public class BankerDialogue extends Dialogue {


	@Override
	public void execute() {
		switch (getNext()) {
		case 0:
			DialogueManager.sendNpcChat(getPlayer(), getPlayer().getNpcClickIndex(), Emotion.DEFAULT, "Good day, how may I help you?");
			setNext(1);
			break;
		case 1:
			DialogueManager.sendOption(getPlayer(), "I'd like to access my bank account, please.", "I'd like to check my PIN settings.", "I'd like to collect items.", "What is this place?");
			break;
		}

	}
	@Override
	public boolean clickButton(int id) {
		switch(id) {
		case DialogueConstants.OPTIONS_4_1:
			getPlayer().getPA().openUpBank();
			break;
		case DialogueConstants.OPTIONS_4_2:
			getPlayer().getBankPin().bankPinSettings();
			break;
		}
		return false;
	}
}
