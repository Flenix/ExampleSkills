package co.uk.silvania.exampleskills.skills;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class SkillLevelPunch extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId = "skillPunch";
	
	public SkillLevelPunch(EntityPlayer player, String skillID) {
		skillId = "skillPunch";
		skillName = "Punch";
		this.xp = 0;
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelPunch.staticSkillId, new SkillLevelPunch(player, staticSkillId));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(skillName, xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getInteger(skillName);		
	}

	@Override public void init(Entity entity, World world) {}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelPunch((EntityPlayer)event.entity, skillId));
		}
	}
	
	@SubscribeEvent
	public void onPunch(LivingAttackEvent event) {
		//TODO Check if player has this skill equipped
		if (event.source.getEntity() instanceof EntityPlayer) {
			System.out.println("onPunch skillId: " + skillId);
			SkillLevelPunch skill = (SkillLevelPunch) SkillLevelPunch.get((EntityPlayer) event.source.getEntity(), skillId);
			skill.addXP(1);
			System.out.println("It Punched! XP: " + skill.getXP());
		}
	}
}
