package godzhell.event.impl;

import godzhell.event.Event;
import godzhell.model.players.Player;
import godzhell.model.players.Right;
import godzhell.model.players.mode.Mode;
import godzhell.model.players.mode.ModeType;

public class IronmanRevertEvent extends Event<Player> {

	public IronmanRevertEvent(Player attachment, int ticks) {
		super(attachment, ticks);
	}

	@Override
	public void execute() {
		if (attachment == null || attachment.disconnected) {
			super.stop();
			return;
		}

		final long delay = attachment.getRevertModeDelay();

		if (delay == 0) {
			super.stop();
			return;
		}

		if (attachment.getMode() == null) {
			super.stop();
			return;
		}

		if (attachment.getMode().isRegular()) {
			super.stop();
			return;
		}

		if (System.currentTimeMillis() > attachment.getRevertModeDelay()) {
			attachment.getRights().remove(Right.IRONMAN);
			attachment.getRights().remove(Right.ULTIMATE_IRONMAN);
			attachment.setMode(Mode.forType(ModeType.REGULAR));
			attachment.sendMessage("Your mode has been reverted to regular upon request.");
			attachment.getPA().requestUpdates();
			super.stop();
		}
	}

}
