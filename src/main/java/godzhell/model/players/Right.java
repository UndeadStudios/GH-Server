package godzhell.model.players;

import godzhell.util.Misc;

import java.util.*;

/**
 * The rights of a player determines their authority. Every right can be viewed with a name and a value. The value is used to separate each right from one another.
 * 
 * @author Jason MacK
 * @date January 22, 2015, 5:23:49 PM
 */

public enum Right implements Comparator<Right> {
	PLAYER(0, "000000", ""),
	HELPER(11, "004080", "Helper"),
	MODERATOR(1, "919191", "Moderator", HELPER),
	ADMINISTRATOR(2, "F5FF0F", "Administrator", MODERATOR),
	OWNER(9, "F5FF0F", "Owner", ADMINISTRATOR),
	//UNKNOWN(4, "F5FF0F", ""),
	CONTRIBUTOR(-1, "B60818", ""),
	SPONSOR(-1, "063DCF", "", CONTRIBUTOR),
	SUPPORTER(-1, "118120", "", SPONSOR),
	
	DONATOR(3, "9E00DE", ""),
	SUPER_DONATOR(7, "9E6405", "", DONATOR),
	EXTREME_DONATOR(8, "9E6405", "", SUPER_DONATOR),
	LEGENDARY(21, "9E6405", "", EXTREME_DONATOR),
	RAINBOW_DONATOR(22, "9E6405", "", LEGENDARY),
	//MAX_DONATOR(20, "9E6405", "", RAINBOW_DONATOR),
	
	RESPECTED_MEMBER(10, "272727", ""),
	HITBOX(12, "437100", ""),
	IRONMAN(23, "3A3A3A", ""),
	ULTIMATE_IRONMAN(24, "717070", ""),
	YOUTUBER(10, "FE0018", ""),
	GAME_DEVELOPER(4, "544FBB", "Developer", OWNER),
	OSRS(-1, "437100", "");

	/**
	 * The level of rights that define this
	 */
	private final int right;

	/**
	 * The rights inherited by this right
	 */
	private final List<Right> inherited;

	/**
	 * The color associated with the right
	 */
	private final String color;

	private final String title;

	/**
	 * Creates a new right with a value to differentiate it between the others
	 * 
	 * @param right the right required
	 * @param color a color thats used to represent the players name when displayed
	 * @param inherited the right or rights inherited with this level of right
	 */
	private Right(int right, String color, String title, Right... inherited) {
		this.right = right;
		this.inherited = Arrays.asList(inherited);
		this.color = color;
		this.title = title;
	}

	/**
	 * The rights of this enumeration
	 * 
	 * @return the rights
	 */
	public int getValue() {
		return right;
	}

	/**
	 * Returns a {@link Rights} object for the value.
	 * 
	 * @param value the right level
	 * @return the rights object
	 */
	public static Right get(int value) {
		return RIGHTS.stream().filter(element -> element.right == value).findFirst().orElse(PLAYER);
	}

	public String getTitle() { return title; }

	/**
	 * A {@link Set} of all {@link Rights} elements that cannot be directly modified.
	 */
	private static final Set<Right> RIGHTS = Collections.unmodifiableSet(EnumSet.allOf(Right.class));

	/**
	 * The color associated with the right
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Determines if this level of rights inherited another level of rights
	 * 
	 * @param rights the level of rights we're looking to determine is inherited
	 * @return {@code true} if the rights are inherited, otherwise {@code false}
	 */
	public boolean isOrInherits(Right right) {
		/*if (this == right) 
			return true;
		for (int i = 0; i < inherited.size(); i++) {
			if (this == inherited.get(i))
				return true;
		}
		System.out.println("inherited.size: "+inherited.size());
		return false;*/
		return this == right || inherited.size() > 0 && inherited.stream().anyMatch(r -> r.isOrInherits(right));
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain MODERATOR}
	 * @return	true if they are of type moderator
	 */
	public boolean isModerator() {
		return equals(MODERATOR);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain HELPER}
	 * @return	true if they are of type moderator
	 */
	public boolean isHelper() {
		return equals(HELPER);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain ADMINISTRATOR}
	 * @return	true if they are of type administrator
	 */
	public boolean isAdministrator() {
		return equals(ADMINISTRATOR);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain OWNER}
	 * @return	true if they are of type owner
	 */
	public boolean isOwner() {
		return equals(OWNER);
	}

	/**
	 * Determines if the players right equal that of {@link MODERATOR}, {@link ADMINISTRATOR},
	 * and {@link OWNER}
	 * @return	true if they are any of the predefined types
	 */
	public boolean isStaff() {
		return isHelper() || isModerator() || isAdministrator() || isOwner();
	}
	
	public boolean isManagment() {
		return isAdministrator() || isOwner();
	}
	
	/**
	 * An array of {@link Right} objects that represent the order in which some rights should be prioritized over others. The index at which a {@link Right} object exists
	 * determines it's priority. The lower the index the less priority that {@link Right} has over another. The list is ordered from lowest priority to highest priority.
	 * <p>
	 * An example of this would be comparing a {@link #MODERATOR} to a {@link #ADMINISTRATOR}. An {@link #ADMINISTRATOR} can be seen as more 'powerful' when compared to a
	 * {@link #MODERATOR} because they have more power within the community.
	 * </p>
	 */

	public static final Right[] PRIORITY = { PLAYER, OSRS, IRONMAN, ULTIMATE_IRONMAN, CONTRIBUTOR, SPONSOR, SUPPORTER, DONATOR, SUPER_DONATOR, EXTREME_DONATOR, LEGENDARY, RESPECTED_MEMBER, HITBOX, YOUTUBER, HELPER,
			GAME_DEVELOPER, MODERATOR, ADMINISTRATOR, OWNER, };

	@Override
	public String toString() {
		return Misc.capitalizeJustFirst(name().replaceAll("_", " "));
	}

	@Override
	public int compare(Right arg0, Right arg1) {
		return 0;
	}

}
