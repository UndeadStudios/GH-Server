package ethos.model.players.skills.construction;

import ethos.model.content.dialogue.Dialogue;
import ethos.model.content.dialogue.DialogueConstants;
import ethos.model.content.dialogue.DialogueManager;
import ethos.model.content.dialogue.Emotion;
import ethos.model.players.skills.construction.rooms.Default;

public class RoomDialogue2 extends Dialogue {

	@Override
	public void execute() {
		switch(getNext()) {
		case 0:
			DialogueManager.sendOption(getPlayer(), "Replace it", "Rotate it", "Keep it as it is", "Remove it");
			break;

		}
		// TODO Auto-generated method stub

	}
	@Override
	public boolean clickButton(int id) {
		switch(id) {
		case DialogueConstants.OPTIONS_4_1:
			getPlayer().getPA().showInterface(30946); 
			break;
		case DialogueConstants.OPTIONS_4_2:
			try {
				getPlayer().replaceWith = (Room) Class.forName(getPlayer().toReplace.getClassName()).newInstance();
				getPlayer().replaceWith.setCustomObjects(getPlayer().toReplace.getCustomObjects());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			getPlayer().start(new RoomDialogue());
			break;
		case DialogueConstants.OPTIONS_4_4:
			try {
				getPlayer().replaceWith = (Room) Class.forName(getPlayer().toReplace.getClassName()).newInstance();
			getPlayer().replaceWith = new Default();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			getPlayer().getPA().closeAllWindows();
			break;
			
		case DialogueConstants.OPTIONS_4_3:
			getPlayer().getPA().closeAllWindows();
			getPlayer().toReplace = getPlayer().replaceWith = null;
			break;
		}
		return false;
	}
}
