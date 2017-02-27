package core;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;

/**
 * Created by 7804364 on 2/20/2017.
 */
public class Methods {
    public static int getHourly (final int amount, final long elapsed) {
        return (int) (amount * 3600000.0D / elapsed);
    }

    public static double getCurrentExp (Skill skill, MethodContext context) {
        return context.getSkills().getExperience(skill);
    }

    public static double getExpGained (Skill skill, MethodContext context) {
        return getCurrentExp(skill, context) - context.getSkillTracker().getStartExperience(skill);
    }

    public static double getCurrentLevel (Skill skill, MethodContext context) {
        return context.getSkills().getRealLevel(skill);
    }

    public static double getStartLevel (Skill skill, MethodContext context) {
        return context.getSkillTracker().getStartLevel(skill);
    }

    public static double getExpTnl (Skill skill, MethodContext context) {
        return context.getSkills().getExperienceToLevel(skill);
    }

    public static double getPercent (Skill skill, MethodContext context) {
        return getCurrentExp(skill, context)/(getExpTnl(skill, context)+getCurrentExp(skill, context));
    }
}
