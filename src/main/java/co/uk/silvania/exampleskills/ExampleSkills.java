package co.uk.silvania.exampleskills;

import co.uk.silvania.exampleskills.skills.SkillLevelJump;
import co.uk.silvania.exampleskills.skills.SkillLevelPunch;
import co.uk.silvania.rpgcore.RegisterSkill;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ExampleSkills.MODID, version = ExampleSkills.VERSION)
public class ExampleSkills {
    public static final String MODID = "exampleskills";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	SkillLevelJump skillJump = new SkillLevelJump(null, "skillJump");
    	SkillLevelPunch skillPunch = new SkillLevelPunch(null, "skillPunch");

    	RegisterSkill.register(skillJump);
    	RegisterSkill.register(skillPunch);

    	MinecraftForge.EVENT_BUS.register(new SkillLevelJump(null, "skillJump"));
    	MinecraftForge.EVENT_BUS.register(new SkillLevelPunch(null, "skillPunch"));
    }
}
