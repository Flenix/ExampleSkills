package co.uk.silvania.exampleskills.skills;

import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class SkillLevelJump extends SkillLevelBase implements IExtendedEntityProperties {
	
	//Some methods require static, while static breaks others.
	//As such, you should keep this here (it should be in every class that extends SkillLevelBase)
	public static String staticSkillId; 
	
	public SkillLevelJump(EntityPlayer player, String skillID) {
		skillName = "Jump";
		skillId = skillID;
		staticSkillId = skillID;
		this.xp = 0;
	}
	
	//This registers your skill with the player.
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelJump.staticSkillId, new SkillLevelJump(player, staticSkillId));
	}

	//This saves data to the player regarding your skill. Usually you'll just save XP
	//However if your skill has a cooldown or something, you may want to save that here too.
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(skillName, xp);
		compound.setTag(skillId, nbt);
	}

	//Loads it back - see save above.
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getInteger(skillName);		
	}

	//Called when your constructor is called. You don't need anything here, but it's required.
	@Override public void init(Entity entity, World world) {}
	
	//Can either go in this class (Register it as a EventHandler if so!) or in your EventHandler class.
	//This applies the skill to the entity when they are created.
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelJump((EntityPlayer)event.entity, skillId));
		}
	}
	
	//This is an example of a skill in action. In this case, every time the player jumps
	//they are awarded an XP point. How nice!
	//You can add XP in almost any way you can imagine - All you need is an instance of EntityPlayer.
	//use the get method as shown below, then call addXP(int). Number can be anything you like, so long as it's an int.
	//If you use negative numbers, it will take the XP away instead - there's no negative check, so be careful if you're doing that.
	@SubscribeEvent
	public void onJump(LivingJumpEvent event) {
		//TODO Check if player has this skill equipped
		if (event.entity instanceof EntityPlayer) {
			SkillLevelJump skill = (SkillLevelJump) SkillLevelJump.get((EntityPlayer) event.entity, skillId);
			skill.addXP(1);
			System.out.println("It jumped! XP: " + skill.getXP());
		}
	}
}
