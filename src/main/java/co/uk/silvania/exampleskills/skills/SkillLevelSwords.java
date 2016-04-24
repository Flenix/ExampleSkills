package co.uk.silvania.exampleskills.skills;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class SkillLevelSwords extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public SkillLevelSwords(EntityPlayer player, String skillID) {
		super(skillID);
		staticSkillId = skillID;
		this.xp = 0;
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
		xp = nbt.getInteger(skillId + "xp");		
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelSwords.staticSkillId, new SkillLevelSwords(player, staticSkillId));
	}

	@Override public void init(Entity entity, World world) {}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelSwords((EntityPlayer)event.entity, skillId));
		}
	}

	@Override
	public boolean hasGui() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String skillName() {
		return "Swords";
	}

	@Override
	public void openGui() {}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("\u00A7oExample skill, has no function currently.");
		description.add("\u00A7oWill not be included in release.");
		description.add("One-handed sword skill. Levelled by using");
		description.add("one-handed swords.");
		description.add("Later unlocks other skills.");
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {}

	@Override
	public int iconX() {
		return 90;
	}

	@Override
	public int iconZ() {
		return 0;
	}
	
	@Override
	public String shortName() {
		return "SRD";
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
