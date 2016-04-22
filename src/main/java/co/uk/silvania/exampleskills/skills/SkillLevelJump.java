package co.uk.silvania.exampleskills.skills;

import co.uk.silvania.exampleskills.ExampleSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class SkillLevelJump extends SkillLevelBase implements IExtendedEntityProperties {
	
	//Some methods require static, while static breaks others.
	//As such, you should keep this here (it should be in every class that extends SkillLevelBase)
	public static String staticSkillId; 
	
	public SkillLevelJump(EntityPlayer player, String skillID) {
		super(skillID);
		//skillId -must- be unique! I'm not as clever as old item ID's from 1.6 and before.
		//If your skillId clashes with that of another mod, it's crash time.
		//Maybe make it configurable, or prefix your name or modid. It's hidden from users - just used for data storage.
		//(Although, this is also used for the give XP command; so some users might see it.)
		staticSkillId = skillID;
		
		//Leave this alone, it's used for storing your XP. You need it, but you shouldn't change it.
		//Unless, for some weird reason you want them to start with more than 0.
		this.xp = 0;
		
		//All other methods are annotated in SkillLevelBase. The abstract ones are generally required or highly recommended.
		//There are others available in SkillLevelBase, check github :)
	}
	
	//This registers your skill with the player.
	//*** REQUIRED ***
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelJump.staticSkillId, new SkillLevelJump(player, staticSkillId));
	}

	//This saves data to the player regarding your skill. Usually you'll just save XP
	//However if your skill has a cooldown or something, you may want to save that here too.
	//*** REQUIRED ***
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xp);
		compound.setTag(skillId, nbt);
	}

	//Loads it back - see save above.
	//*** REQUIRED ***
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillId + "xp");		
	}

	//Called when your constructor is called. You don't need anything here.
	//*** REQUIRED ***
	@Override public void init(Entity entity, World world) {}
	
	//Can either go in this class (Register it as a EventHandler if so!) or in your EventHandler class.
	//This applies the skill to the entity when they are created.
	//*** REQUIRED ***
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelJump((EntityPlayer)event.entity, skillId));
		}
	}
	
	//This is an example of a skill in action. In this case, every time the player jumps
	//they are awarded a single XP point. How nice!
	//You can add XP in almost any way you can imagine - All you need is an instance of EntityPlayer.
	//use the get method as shown below, then call addXP(int, player). Number can be anything you like, so long as it's an int.
	//The mod will automatically check if the player has the skill equipped, and will only award XP if they do - so don't worry about that.
	//If you use negative numbers, it will take the XP away instead - there's no negative check, so be careful if you're doing that.
	//(If you take XP, use getXP() > 0 first, if it's false, setXP(0).)
	@SubscribeEvent
	public void onJump(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			//Get the skill in order to modify it
			SkillLevelJump skill = (SkillLevelJump) SkillLevelJump.get((EntityPlayer) event.entity, skillId);
			//XP is a float, so you can use decimals if you really really want to.
			//It's always trimmed to an int for player views though, so if they have 30.99 xp, it'll still show 30.
			skill.addXP(1, (EntityPlayer) event.entity);
			
			//There is a "forceAddXP(xp)" too - This will add XP even if the skill isn't equipped.
			//I dunno why you'd ever want to do that, but you can if you really want to.
		}
	}

	
	//Displayed when the player hovers over the skill's icon in the skill list.
	@Override
	public void addDescription() {
		description.add("This is an example skill. Hooray!");
		description.add("Jump up and down to gain XP.");
		description.add("What can you do with that XP? \u00A7lNOTHING!");
	}
	
	//Call this when you activate the skill. Eventually Core will have functionality which can call this for you.
	@Override public void activateSkill(EntityPlayer arg0, World arg1) {}
	
	//Whether or not your skill has a custom GUI for skill information, configs etc.
	@Override public boolean hasGui() { return false; }
	
	//Override this to open your GUI, if you have one. Called when player clicks skill detail buttons in the main GUI.
	//Those buttons are disabled if hasGui is false.
	@Override public void openGui() {}
	
	//The X-position of your skill's icon in the file.
	//Both large and small icons should be at the same X position.
	@Override public int iconX() { return 30; }
	
	//Your Z-position of your icon in the file.
	//The small icon should be exactly 128 pixels (scaled - so half the file size) below X.
	@Override public int iconZ() { return 0; }
	
	//The location of your skill sprite sheet.
	@Override public ResourceLocation skillIcon() { return new ResourceLocation(ExampleSkills.MODID, "textures/gui/skills.png"); }
	
	//The name of your skill to display to the player
	@Override public String skillName() { return "Jump"; }
	
	//Formatting for the skill. Used anywhere the name, XP etc are displayed to recolour and so on.
	@Override public String nameFormat() { return "\u00A7b"; }

	//The shorthand name for your skill, used in places where space is an issue.
	@Override public String shortName() { return "JMP"; }
}
