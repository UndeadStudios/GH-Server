package godzhell.model.npcs.bosses.zulrah.impl;

import java.util.Arrays;

import godzhell.event.CycleEventContainer;
import godzhell.event.CycleEventHandler;
import godzhell.model.npcs.bosses.zulrah.DangerousEntity;
import godzhell.model.npcs.bosses.zulrah.DangerousLocation;
import godzhell.model.npcs.bosses.zulrah.SpawnDangerousEntity;
import godzhell.model.npcs.bosses.zulrah.Zulrah;
import godzhell.model.npcs.bosses.zulrah.ZulrahLocation;
import godzhell.model.npcs.bosses.zulrah.ZulrahStage;
import godzhell.model.players.Player;
import godzhell.model.players.combat.CombatType;

public class RangeStageFour extends ZulrahStage {

	public RangeStageFour(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || zulrah.getNpc() == null || zulrah.getNpc().isDead || player == null || player.isDead
				|| zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		int ticks = container.getTotalTicks();
		if (ticks == 4) {
			zulrah.getNpc().setFacePlayer(false);
			CycleEventHandler.getSingleton().addEvent(player, new SpawnDangerousEntity(zulrah, player,
					Arrays.asList(DangerousLocation.EAST, DangerousLocation.SOUTH_EAST, DangerousLocation.SOUTH_WEST), DangerousEntity.TOXIC_SMOKE, 40), 1);
		} else if (ticks == 16) {
			CycleEventHandler.getSingleton().addEvent(player,
					new SpawnDangerousEntity(zulrah, player, Arrays.asList(DangerousLocation.SOUTH_EAST, DangerousLocation.SOUTH_WEST), DangerousEntity.MINION_NPC), 1);
		} else if (ticks == 26) {
			zulrah.getNpc().setFacePlayer(true);
			zulrah.changeStage(5, CombatType.MAGE, ZulrahLocation.SOUTH);
			zulrah.getNpc().totalAttacks = 0;
			container.stop();
		}
	}

}
