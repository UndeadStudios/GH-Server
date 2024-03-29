package godzhell.model.content.traveling;

import godzhell.event.CycleEvent;
import godzhell.event.CycleEventContainer;
import godzhell.event.CycleEventHandler;
import godzhell.model.items.Item;
import godzhell.model.players.Boundary;
import godzhell.model.players.Player;
import godzhell.model.players.combat.Hitmark;
import godzhell.util.Misc;

public class DesertHeat {

    /**
     * Damage dealt to player
     */
    private static int DAMAGE = 1+ Misc.random(8);
    /**
     * Waterskin animation
     */
    private static final int ANIMATION = 829;
    /**
     * Time player has if they don't have protection (90 seconds)
     */
    private static int REGULAR_TIMER = 90000;
    /**
     * Integer to check if player has waterskins
     */
    private static int waterskin = -1;
    /**
     * Waterskins before and after
     */
    private static int[][] WATERSKINS = {
            {1825, 1823},//waterskin 3
            {1827, 1825},//waterskin 2
            {1829, 1827},//waterskin 1
            {1831, 1829}//waterskin 0
    };
    /**
     * Desert clothes
     */
    private static final int[][] CLOTHES = {
            {1833, Item.CHEST}, {1835, Item.LEGS}, {1837, Item.FEET}
    };

    private static void doDamage(Player player) {
        player.sendMessage("You should get a waterskin for any traveling in the desert.");
player.appendDamage(DAMAGE, Hitmark.HIT);
        player.getPlayerAssistant().refreshSkill(player.playerHitpoints);
    }

    private static int getClothes(Player player) {
        int temp = 0;
        for (int element[] : CLOTHES) {
            if (player.playerEquipment[element[1]] == element[0]) {
                temp += 1;
            }
        }
        return temp;
    }

    private static int getTimer(Player player) {
        /**
         * 10 secs extra for each desert clothing item
         */
        int heat = 10000 * getClothes(player);
        return REGULAR_TIMER + heat;
    }

    private static boolean preventHeat(Player player) {
        return (Boundary.isIn(player, Boundary.NO_HEAT));
    }

    public static void callHeat(final Player player) {
        if (!Boundary.isIn(player, Boundary.DESERT)
                || player.playerLevel[player.playerHitpoints] < 0
                || preventHeat(player)) {
            return;
        }
        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                if (!Boundary.isIn(player, Boundary.DESERT)
                        || player.playerLevel[player.playerHitpoints] < 0
                        || player.disconnected
                        || preventHeat(player)) {
                    container.stop();
                    return;
                }
                if (System.currentTimeMillis() - player.lastDesert > getTimer(player)) {
                    player.lastDesert = System.currentTimeMillis();
                    if (!checkWaterskin(player)) {
                        doDamage(player);
                    }
                    container.stop();
                } else if (player.playerLevel[player.playerHitpoints] < 0) {
                    player.isDead = true;
                    container.stop();
                }
            }
            @Override
            public void stop() {

            }
        }, 1);
    }

    public static boolean checkWaterskin(final Player player) {
        for (int i = 0; i < WATERSKINS.length; i++) {
            if (player.getItems().playerHasItem(WATERSKINS[i][1])) {
                waterskin = i;
            }
        }
        if (waterskin == -1) {//empty waterskin
            return false;
        }
        if (waterskin >= 0) {
            player.getItems().deleteItem(WATERSKINS[waterskin][1], 1);
            player.getItems().addItem(WATERSKINS[waterskin][0], 1);
            player.startAnimation(ANIMATION);
            return true;
        }
        return false;
    }

    public static void showWarning(Player player) {
        for (int i = 8144; i < 8195; i++) {
            player.getPA().sendString("", i);
        }
        player.getPA().sendString("@dre@DESERT WARNING", 8144);
        player.getPA().sendString("", 8145);
        player.getPA().sendString("The intense heat of the desert reduces your health.", 8147);
        player.getPA().sendString("Bring 2-5 waterskins to avoid receiving any damage.", 8148);
        player.getPA().sendString("", 8149);
        player.getPA().sendString("Wearing desert robes will not prevent the damage, but", 8150);
        player.getPA().sendString("will reduce it significantly.", 8151);
        player.getPA().sendString("", 8152);
        player.getPA().sendString("The waterskins however need to be re-filled. Bring a", 8153);
        player.getPA().sendString("knife and cut healthy cacti to re-fill the waterskins.", 8154);
        player.getPA().sendString("@red@Any water vessels will evaporate, such as jug of water.", 8155);
        player.getPA().showInterface(8134);
    }
}
