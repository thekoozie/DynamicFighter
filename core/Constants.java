package core;


import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.items.Item;

/**
 * Created by 7804364 on 2/8/2017.
 */
public class Constants {
    public static String NPC_TARGET = "Man";
    public static String LOOT = "Bones";
    public static String FOOD = "Bread";
    public static double EAT_PERCENT = 60.00;
    public static int FOOD_AMOUNT = 1;
    public static Tile RETURN_TILE = null;

    public static boolean BANK_RETURN = false;
    public static boolean BANKING = false;
    public static boolean LOOT_OPTION = true;
    public static boolean EAT_OPTION = false;
    public static boolean BANK_OPTION = true;
    public static boolean INITIALIZE = false;

    public static String STATE = "Starting up...";

    public static boolean KILL_COUNTER;
    public static int ITEMS_LOOTED = 0;
    public static int KILLS = 0;
    public static String FIGHTER_SKILL;
}
