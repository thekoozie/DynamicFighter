package core.nodes;

import core.Constants;
import core.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/12/2017.
 */
public class LootNode extends Node {
    private GroundItem loot;

    public LootNode(MethodContext context) {
        super(context);
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean accept() {
        //loot = getContext().getGroundItems().closest(Constants.LOOT);

        loot = getContext().getGroundItems().closest(groundItem -> groundItem != null && groundItem.distance() <= 15 && groundItem.exists() && groundItem.getName().equals(Constants.LOOT));

        return loot != null && !getContext().getLocalPlayer().isInCombat() && loot.exists()
                && !getContext().getInventory().isFull()
                && Constants.LOOT_OPTION == true
                && Constants.BANKING == false;
    }

    @Override
    public int execute() {
        Constants.STATE = "LootNode executed";
        if (loot != null) {
            loot.interact("Take");
            Constants.STATE = "Collecting loot";
            sleepUntil(() -> getContext().getLocalPlayer().getTile().equals(loot.getTile()), 4000);
        }
        return (int) Calculations.nextGaussianRandom(400, 200);
    }
}
