package core.nodes;

import core.Constants;
import core.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/14/2017.
 */
public class EatNode extends Node {
    public EatNode(MethodContext context) {
        super(context);
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean accept() {
        return getContext().getInventory().contains(Constants.FOOD)
                && getContext().getLocalPlayer().getHealthPercent() <= Constants.EAT_PERCENT
                && Constants.EAT_OPTION == true;
    }

    @Override
    public int execute() {
        Constants.STATE = "EatingNode executed";
        if (getContext().getInventory().contains(Constants.FOOD)) {
            getContext().getInventory().interact(Constants.FOOD, "Eat");
            Constants.STATE = "Eating";
            sleepUntil(() -> getContext().getLocalPlayer().getAnimation() == -1, 2400);
        }
        return (int) Calculations.nextGaussianRandom(400, 200);
    }
}
