package godzhell.model.players.combat.specials;

import godzhell.model.entity.Entity;
import godzhell.model.players.Player;
import godzhell.model.players.combat.Damage;
import godzhell.model.players.combat.Special;
import godzhell.model.players.combat.melee.CombatPrayer;

public class DragonSword extends Special {

	public DragonSword() {
		super(4, 2.50, 2.50, new int[] { 21009 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx100(1369);
		player.startAnimation(7515);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
			if (damage.getAmount() > 0) {
				CombatPrayer.resetOverHeads((Player) target);
			}
		}

	}

}
