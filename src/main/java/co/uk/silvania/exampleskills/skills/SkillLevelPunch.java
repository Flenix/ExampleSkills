package co.uk.silvania.exampleskills.skills;

import co.uk.silvania.exampleskills.ExampleSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import co.uk.silvania.rpgcore.skills.SkillLevelStrength;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class SkillLevelPunch extends SkillLevelBase implements IExtendedEntityProperties {
	
	//SkillLevelPunch is very similar to Jump but I've not documented it.
	//If you want to see how the mod works and how to create a skill, see SkillLevelJump
	//If you're mad and want to guess, or want to copy-paste a template, use this.	
	
	public static String staticSkillId = "skillPunch";
	
	public SkillLevelPunch(EntityPlayer player, String skillID) {
		super(skillID);
		this.xp = 0;
		
		//This requires the Strength skill to be equipped.
		requiredSkills.add(SkillLevelStrength.staticSkillId);
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelPunch.staticSkillId, new SkillLevelPunch(player, staticSkillId));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillId + "xp");		
	}

	@Override public void init(Entity entity, World world) {}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelPunch((EntityPlayer)event.entity, skillId));
		}
	}
	
	public void openGui() {
		
	}
	
	@SubscribeEvent
	public void onPunch(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityPlayer) {
			System.out.println("onPunch skillId: " + skillId);
			SkillLevelPunch skill = (SkillLevelPunch) SkillLevelPunch.get((EntityPlayer) event.source.getEntity(), skillId);
			skill.addXP(1, (EntityPlayer) event.source.getEntity());
			
			if (isSkillEquipped((EntityPlayer) event.source.getEntity(), skillId)) {
				System.out.println("It Punched! XP: " + skill.getXP());
			}
		}
	}

	@Override
	public void activateSkill(EntityPlayer arg0, World arg1) {}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("PUNCH THING FACE");
		description.add("PUNCH FEEL GOOD");
		description.add("XP GET");
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public int iconX() {
		return 0;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(ExampleSkills.MODID, "textures/gui/skills.png");
	}

	@Override
	public String skillName() {
		return "Punch";
	}

	@Override
	public String shortName() {
		return "POW";
	}
	
	@Override
	public boolean skillUnlocked() {
		return false;
	}
	
	@Override
	public void addEquipIssues() {
		equipIssues.add("Skill not yet implemented.");
	}
}
