import core.Constants;
import core.Methods;
import core.handlers.ScriptGui;
import core.handlers.StartHandler;
import core.nodes.*;
import core.Node;
import org.dreambot.api.methods.ViewportTools;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.event.impl.PaintEvent;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 7804364 on 2/8/2017.
 */
@ScriptManifest(name = "DynaFighter", author = "7804364", version = 1.11D, description = "AIO Dynamic Fighter", category = Category.COMBAT)
public class Main extends AbstractScript implements InventoryListener {
    private StartHandler startHandler;
    private ScriptGui scriptGui = new ScriptGui();

    private final List<Node> open = new ArrayList<>();
    private final List<Node> cache = new ArrayList<>();

    public int startAttackLevel = 0, startStrengthLevel = 0, startDefenceLevel = 0, startConstitutionLevel = 0;
    public int startAttackExp = -1, startStrengthExp = -1, startDefenceExp = -1, startConstitutionExp = -1;
    public int attackExpGained = -1, strengthExpGained = -1, defenceExpGained = -1, constitutionExpGained = -1;

    public static long startTime;
    public static final Timer runTime = new Timer();

    @Override
    public void onStart() {
        cache.add(new AttackNode(this));
        cache.add(new LootNode(this));
        cache.add(new EatNode(this));
        cache.add(new BankNode(this));
        cache.add(new ReturnNode(this));
        cache.add(new ReplenishFoodNode(this));
        scriptGui.setVisible(true);

        getSkillTracker().start(Skill.ATTACK);
        getSkillTracker().start(Skill.STRENGTH);
        getSkillTracker().start(Skill.DEFENCE);

        startTime = 0;

        startAttackExp = getSkillTracker().getStartExperience(Skill.ATTACK);
        startStrengthExp = getSkillTracker().getStartExperience(Skill.STRENGTH);
        startDefenceExp = getSkillTracker().getStartExperience(Skill.DEFENCE);

        startAttackLevel = getSkillTracker().getStartLevel(Skill.ATTACK);
        startStrengthLevel = getSkillTracker().getStartLevel(Skill.STRENGTH);
        startDefenceLevel = getSkillTracker().getStartLevel(Skill.DEFENCE);

        startTime = System.currentTimeMillis();
    }

    @Override
    public int onLoop() {
        int delay = 420;
        if(!cache.isEmpty() && Constants.INITIALIZE == true) {
            open.clear();
            open.addAll(cache.stream().filter(Node::accept).collect(Collectors.toList()));
            if(!open.isEmpty()){
                delay = getSuitableOpenNode().execute();
            }
        } else if (Constants.INITIALIZE == false) {
            startHandler = new StartHandler(this);
        }
        return delay;
    }

    public Node getSuitableOpenNode () {
        Node node = null;
        if(!open.isEmpty()) {
            node = open.get(0);
            if(open.size() > 1) {
                for (Node possible : open) {
                    if (node.priority() < possible.priority()) {
                        node = possible;
                    }
                }
            }
        }
        return node;
    }

    public List<Node> getNodeCache() {
        return cache;
    }

    private final Color color1 = new Color(0, 0, 0);
    private final Color color2 = new Color(51, 153, 255);
    private final Color color3 = new Color(255, 255, 255);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Arial", 0, 22);
    private final Font font2 = new Font("Arial", 0, 16);
    private final Font font3 = new Font("Arial", 1, 12);
    private final Font font4 = new Font("Arial", 0, 12);

    @Override
    public void onPaint(Graphics2D g){
        if (getLocalPlayer().getInteractingCharacter() != null) {
            if (getLocalPlayer().getInteractingCharacter().getHealth() == 0 && Constants.KILL_COUNTER == true) {
                Constants.KILL_COUNTER = false;
                Constants.KILLS++;
            }
        }

        attackExpGained = getSkills().getExperience(Skill.ATTACK) - startAttackExp;
        strengthExpGained = getSkills().getExperience(Skill.STRENGTH) - startStrengthExp;
        defenceExpGained = getSkills().getExperience(Skill.DEFENCE) - startDefenceExp;

        if (attackExpGained > 0) {
            Constants.FIGHTER_SKILL = "Attack";
        }
        if (strengthExpGained > 0) {
            Constants.FIGHTER_SKILL = "Strength";
        }
        if (defenceExpGained > 0) {
            Constants.FIGHTER_SKILL = "Defence";
        }

        Skill fighterSkill = null;
        if (Constants.FIGHTER_SKILL != null) {
            if (Constants.FIGHTER_SKILL.equals("Attack")) {
                fighterSkill = Skill.ATTACK;
            }
            if (Constants.FIGHTER_SKILL.equals("Defence")) {
                fighterSkill = Skill.DEFENCE;
            }
            if (Constants.FIGHTER_SKILL.equals("Strength")) {
                fighterSkill = Skill.STRENGTH;
            }
        }

        //fighterGui
        g.setColor(color1);
        g.fillRoundRect(8, 344, 505, 129, 16, 16);
        g.setStroke(stroke1);
        g.drawRoundRect(8, 344, 505, 129, 16, 16);
        g.setFont(font1);
        g.setColor(color2);
        g.drawString("DynaFighter", 154, 364);
        g.setFont(font2);
        g.drawString("---------------------------------------------------------------------------------------", 39, 380);
        g.setFont(font3);
        g.drawString("Status:", 56, 392);
        g.setFont(font4);
        g.drawString("RunTime: " + org.dreambot.api.utilities.Timer.formatTime(System.currentTimeMillis() - startTime), 40, 412);
        g.drawString(Constants.STATE, 40, 424);
        g.drawString("Skill: " + Constants.FIGHTER_SKILL, 40, 436);
        g.setFont(font3);
        g.drawString("Core:", 246, 392);
        g.setFont(font4);
        g.drawString("Exp/hr: " + Methods.getHourly((int) Methods.getExpGained(fighterSkill, this), runTime.elapsed()), 232, 412);
        g.drawString("Exp gained: " + (int) Methods.getExpGained(fighterSkill, this), 232, 424);
        g.drawString("Levels gained: " + (Methods.getCurrentLevel(fighterSkill, this) - Methods.getStartLevel(fighterSkill, this)), 232, 436);
        g.setFont(font3);
        g.drawString("Info:", 440, 392);
        g.setFont(font4);
        g.drawString("Kills: " + Constants.KILLS, 420, 412);
        g.drawString("Kills/hr: " + Methods.getHourly(Constants.KILLS, runTime.elapsed()), 420, 424);
        if (Constants.LOOT_OPTION) {
            g.drawString("Looted: " + Constants.ITEMS_LOOTED + "(" + Methods.getHourly(Constants.ITEMS_LOOTED, runTime.elapsed()) + ")", 420, 436);
        }
        g.setFont(font3);

        g.setColor(color2);
        g.fillRect(10, 443,(int) ((Methods.getPercent(fighterSkill, this) * 502)), 26);
        g.setColor(color3);
        g.setStroke(stroke1);
        g.drawRect(10, 443, 502, 26);
        g.setColor(color3);
        g.drawString((int) (Methods.getPercent(fighterSkill, this) * 100) + "%" + " ("+Methods.getExpTnl(fighterSkill, this)+")", 245, 460);
    }

    @Override
    public void onItemChange(Item[] items) {
        Constants.ITEMS_LOOTED++;
    }
}