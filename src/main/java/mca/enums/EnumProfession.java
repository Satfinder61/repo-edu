package mca.enums;

import java.util.ArrayList;
import java.util.List;

import mca.core.MCA;
import mca.entity.EntityVillagerMCA;
import radixcore.modules.RadixMath;

public enum EnumProfession 
{
	Unassigned	 (-1, EnumProfessionSkinGroup.Unassigned, -1),
	Farmer       (0,  EnumProfessionSkinGroup.Farmer,   1),
	Fisherman    (1,  EnumProfessionSkinGroup.Farmer,   2),
	Shepherd     (2,  EnumProfessionSkinGroup.Farmer,   3),
	Fletcher     (3,  EnumProfessionSkinGroup.Farmer,   4),
	Librarian    (4,  EnumProfessionSkinGroup.Librarian,1),
	Cleric       (5,  EnumProfessionSkinGroup.Priest,   1),
	Armorer      (6,  EnumProfessionSkinGroup.Smith,    1),
	WeaponSmith  (7,  EnumProfessionSkinGroup.Smith,    2),
	ToolSmith    (8,  EnumProfessionSkinGroup.Smith,    3),
	Butcher      (9,  EnumProfessionSkinGroup.Butcher,  1),
	Baker        (10, EnumProfessionSkinGroup.Baker,    1),
	Leatherworker(11, EnumProfessionSkinGroup.Butcher,  2),
	Guard        (12, EnumProfessionSkinGroup.Guard,    1),
	Archer       (13, EnumProfessionSkinGroup.Guard,    1),
	Miner        (14, EnumProfessionSkinGroup.Miner,    1),
	Mason        (15, EnumProfessionSkinGroup.Miner,    1),
	Child        (16, EnumProfessionSkinGroup.Child,    1),
	Warrior      (17, EnumProfessionSkinGroup.Warrior,  3);

	private int id;
	private int vanillaCareerId;
	private EnumProfessionSkinGroup skinGroup;

	private EnumProfession(int id, EnumProfessionSkinGroup skinGroup, int vanillaCareerId)
	{
		this.id = id;
		this.vanillaCareerId = vanillaCareerId;
		this.skinGroup = skinGroup;
	}

	public int getId()
	{
		return id;
	}

	public static EnumProfession getNewProfessionFromVanilla(int id)
	{
		switch (id)
		{
		case 0: return getRandomByGroup(EnumProfessionSkinGroup.Farmer, EnumProfessionSkinGroup.Miner);
		case 1: return getRandomByGroup(EnumProfessionSkinGroup.Librarian);
		case 2: return getRandomByGroup(EnumProfessionSkinGroup.Priest);
		case 3: return getRandomByGroup(EnumProfessionSkinGroup.Smith);
		case 4: return getRandomByGroup(EnumProfessionSkinGroup.Butcher, EnumProfessionSkinGroup.Baker);
		default:
			return getRandomByGroup(EnumProfessionSkinGroup.Farmer);
		}
	}

	public static EnumProfession getRandomByGroup(EnumProfessionSkinGroup... groups)
	{
		List<EnumProfession> groupProfessions = new ArrayList<EnumProfession>();

		for (EnumProfession profession : EnumProfession.values())
		{
			for (EnumProfessionSkinGroup group : groups)
			{
				if (profession.skinGroup == group)
				{
					groupProfessions.add(profession);
				}
			}
		}

		return groupProfessions.get(RadixMath.getNumberInRange(0, groupProfessions.size() - 1));
	}

	public static EnumProfession getProfessionById(int id)
	{
		for (EnumProfession profession : EnumProfession.values())
		{
			if (profession.getId() == id)
			{
				return profession;
			}
		}

		return null;
	}

	public static EnumProfession getAtRandom()
	{
		EnumProfession[] values = EnumProfession.values();
		int returnIndex = RadixMath.getNumberInRange(0, EnumProfession.values().length - 1);
		return values[returnIndex];
	}

	public String getUserFriendlyForm(EntityVillagerMCA human) 
	{
		//Player children have the "child" profession. Change their display title to "Villager" if they're grown up.
		if (this == Child && !human.getIsChild())
		{
			return MCA.getLanguageManager().getString("profession.villager");
		}
		
		//All children will show the "Child" title, regardless of underlying profession. 
		//When grown, their actual profession title will be shown.
		else if (human.getIsChild())
		{
			return MCA.getLanguageManager().getString("profession.child");			
		}
		
		else
		{
			return MCA.getLanguageManager().getString(getLocalizationId());
		}
	}

	public EnumProfessionSkinGroup getSkinGroup()
	{
		return skinGroup;
	}

	public static List<Integer> getListOfIds()
	{
		List<Integer> returnList = new ArrayList<Integer>();
		
		for (EnumProfession profession : EnumProfession.values())
		{
			returnList.add(profession.id);
		}
		
		return returnList;
	}
	
	public String getLocalizationId()
	{
		switch (this)
		{
		case Archer:
			return "profession.archer";
		case Armorer:
			return "profession.armorer";
		case Butcher:
			return "profession.butcher";
		case Cleric:
			return "profession.cleric";
		case Farmer:
			return "profession.farmer";
		case Fisherman:
			return "profession.fisherman";
		case Fletcher:
			return "profession.fletcher";
		case Guard:
			return "profession.guard";
		case Leatherworker:
			return "profession.leatherworker";
		case Librarian:
			return "profession.librarian";
		case Shepherd:
			return "profession.shepherd";
		case ToolSmith:
			return "profession.toolsmith";
		case WeaponSmith:
			return "profession.weaponsmith";
		case Miner:
			return "profession.miner";
		case Mason:
			return "profession.mason";
		case Baker:
			return "profession.baker";
		case Child:
			return "profession.child";
		case Warrior:
			return "profession.warrior";
		}

		return "";
	}
	
	public int getVanillaCareerId() 
	{
		 return vanillaCareerId;
	}
}
