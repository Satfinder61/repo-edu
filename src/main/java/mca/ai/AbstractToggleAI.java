package mca.ai;

import java.util.UUID;

import mca.entity.EntityVillagerMCA;
import mca.enums.EnumPersonality;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import radixcore.modules.RadixLogic;

/**
 * Defines an AI that can be "toggled" on or off. It does not run consistently.
 */
public abstract class AbstractToggleAI extends AbstractAI
{
	/** The UUID of the player that triggered this AI. */
	protected UUID assigningPlayer = new UUID(0,0);
	
	public AbstractToggleAI(EntityVillagerMCA owner)
	{
		super(owner);
	}
	
	/** Sets this AI as active and begins calling the update methods. */
	public abstract void setIsActive(boolean value);
	
	/** @returns True if this AI is currently running. */
	public abstract boolean getIsActive();
	
	/** @returns The user-friendly name of this AI. Displays above the actor's head. */
	protected abstract String getName();
	
	/** @returns The player who triggered this AI. Looks up the player by UUID. */
	public final EntityPlayer getAssigningPlayer()
	{
		return owner.worldObj.getPlayerEntityByUUID(assigningPlayer);
	}
	
	/** Adds a chat message to the assigning player's chat log. */
	public final void notifyAssigningPlayer(String message)
	{
		final EntityPlayer player = getAssigningPlayer();
		
		if (player != null)
		{
			player.addChatMessage(new TextComponentString(message));
		}
	}
	
	/** Handles duplicating stacks added to the inventory, or stacks ignored due to personality. */
	protected final boolean addItemStackToInventory(ItemStack stack)
	{
		if (owner.getPersonality() == EnumPersonality.CURIOUS && RadixLogic.getBooleanWithProbability(10))
		{
			owner.getVillagerInventory().addItem(stack);
			return owner.getVillagerInventory().addItem(stack.copy()) == null;
		}
		
		else if (owner.getPersonality() == EnumPersonality.GREEDY && RadixLogic.getBooleanWithProbability(10))
		{
			return false;
		}
		
		else if (owner.getPersonality() != EnumPersonality.GREEDY)
		{
			return owner.getVillagerInventory().addItem(stack) == null;
		}
		
		return false;
	}
}
