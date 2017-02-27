package core.nodes;

import core.Constants;
import core.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;



import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/8/2017.
 */
public class AttackNode extends Node {

    private NPC object;
    private GroundItem groundItem;

    public AttackNode(MethodContext context) {
        super(context);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        object = getContext().getNpcs().closest(npc -> npc != null && npc.getName().equals(Constants.NPC_TARGET) && !npc.isInCombat());

        groundItem  = getContext().getGroundItems().closest(Constants.LOOT);

       return !getContext().getLocalPlayer().isInCombat()
               && !getContext().getLocalPlayer().isInteractedWith()
               && !canLoot()
               && Constants.BANKING == false
               && object != null;
    }



    @Override
    public int execute() {
        Constants.STATE = "AttackNode executed";
        if (object.exists()) {
            if (getContext().getMap().canReach(object)) {
                if (object.canAttack() && object.isOnScreen()) {
                    //getContext().getWalking().walk(object.getTile());
                    //Constants.STATE = "Walking to target";
                    //sleepUntil(() -> object.isOnScreen(), 1000);
                    Constants.STATE = "Attacking " + Constants.NPC_TARGET;
                    object.interact("Attack");
                    Constants.KILL_COUNTER = true;
                    sleepUntil(() -> canLoot(), 3000);
                } else {
                    getContext().getWalking().walk(object.getTile());
                    Constants.STATE = "Walking to target";
                    sleepUntil(() -> object.isOnScreen(), 1000);
                }
            } else {
                getContext().getWalking().walk(object.getTile());
                Constants.STATE = "Walking to target";
                sleepUntil(() -> getContext().getMap().canReach(object), 4000);
            }
        }
        return (int) Calculations.nextGaussianRandom(400, 200);
    }

    private boolean canLoot () {
        if (groundItem != null && groundItem.distance() <= 15) {
            return true;
        }
        return false;
    }
}
