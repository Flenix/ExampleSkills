package co.uk.silvania.exampleskills;

import co.uk.silvania.exampleskills.skills.SkillLevelJump;
import co.uk.silvania.exampleskills.skills.SkillLevelPunch;
import co.uk.silvania.exampleskills.skills.SkillLevelSwords;
import co.uk.silvania.rpgcore.RegisterSkill;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

//Make sure you have rpgcore as a dependency!!!!!
@Mod(modid = ExampleSkills.MODID, version = ExampleSkills.VERSION, dependencies = "required-after:rpgcore")
public class ExampleSkills {
    public static final String MODID = "exampleskills";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	//Skills are created much like blocks and items, you initialize them...
    	SkillLevelJump skillJump = new SkillLevelJump(null, "skillJump");
    	SkillLevelPunch skillPunch = new SkillLevelPunch(null, "skillPunch");
    	SkillLevelSwords skillSwords = new SkillLevelSwords(null, "skillSwords");

    	//...and then register them.
    	RegisterSkill.register(skillJump);
    	RegisterSkill.register(skillPunch);
    	RegisterSkill.register(skillSwords);

    	//99% of the time you need to register each as an EventHandler too.
    	//The only exception is if you handle your events in a single global handler.
    	//Neither is "better" than the other performance-wise, but to me it's logical to keep events relevant to skills within the skills.
    	MinecraftForge.EVENT_BUS.register(new SkillLevelJump(null, "skillJump"));
    	MinecraftForge.EVENT_BUS.register(new SkillLevelPunch(null, "skillPunch"));
    	MinecraftForge.EVENT_BUS.register(new SkillLevelSwords(null, "skillSwords"));
    }
}
