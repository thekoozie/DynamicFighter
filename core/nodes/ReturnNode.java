package core.nodes;

import core.Constants;
import core.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/14/2017.
 */
public class ReturnNode extends Node {
    public ReturnNode(MethodContext context) {
        super(context);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        return Constants.BANK_OPTION == true
                && Constants.BANK_RETURN == true
                && Constants.BANKING == true;
    }

    @Override
    public int execute() {
        Constants.STATE = "Return executed";
        if (Constants.BANK_RETURN == true) {
            if (getContext().getLocalPlayer().distance(Constants.RETURN_TILE) > 5) {
                getContext().getWalking().walk(Constants.RETURN_TILE);
                Constants.STATE = "Walking back";
                sleepUntil(() -> !getContext().getLocalPlayer().getTile().equals(Constants.RETURN_TILE), 1000);
            } else if (getContext().getLocalPlayer().distance(Constants.RETURN_TILE) <= 5) {
                Constants.BANK_RETURN = false;
                Constants.BANKING = false;
                Constants.STATE = "Returned";
            }
        }
        return (int) Calculations.nextGaussianRandom(400, 200);
    }
}
