package core.nodes;

import core.Constants;
import core.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

import static org.dreambot.api.methods.MethodProvider.sleepUntil;

/**
 * Created by 7804364 on 2/14/2017.
 */
public class BankNode extends Node {
    public BankNode(MethodContext context) {
        super(context);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override //Inventory is full of food or loot it does not tell the difference fix later
    public boolean accept() {
        return Constants.BANK_OPTION == true
                && getContext().getInventory().isFull()
                && getContext().getInventory().contains(Constants.LOOT)
                && !getContext().getInventory().contains(Constants.FOOD);
    }

    @Override
    public int execute() {
        Constants.STATE = "Banking executed";
        if (Constants.BANKING == false) {
            Constants.RETURN_TILE = getContext().getLocalPlayer().getTile();
        }
        Constants.BANKING = true;
        if (getContext().getBank().openClosest()) {
            Constants.STATE = "Banking";
            sleepUntil(() -> getContext().getBank().isOpen(), 600000);
            if (getContext().getBank().isOpen()) {
                if (!getContext().getInventory().isEmpty()) {
                    getContext().getBank().depositAllItems();
                }
                sleepUntil(() -> getContext().getBank().depositAllItems(), 1000);
                if (Constants.EAT_OPTION == true && getContext().getBank().contains(Constants.FOOD)) {
                    getContext().getBank().withdraw(Constants.FOOD, Constants.FOOD_AMOUNT);
                    sleepUntil(() -> getContext().getInventory().contains(Constants.FOOD), 1000);
                }
                getContext().getBank().close();
                sleepUntil(() -> !getContext().getBank().isOpen(), 1000);
                Constants.BANK_RETURN = true;
            }
        }
        return (int) Calculations.nextGaussianRandom(400, 200);
    }

    private boolean foodOutage () {
        if (Constants.BANK_OPTION == true && !getContext().getInventory().contains(Constants.FOOD) && Constants.EAT_OPTION) {
            return true;
        }
        return false;
    }
}
