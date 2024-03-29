package godzhell.clip;

import java.io.IOException;

public final class ObjectDef {

	private int opcode61;
	private boolean field3621;
	private boolean interactive;
	private String opcode150;

	public static ObjectDef getObjectDef(int id) {
		if (id > streamIndices.length) {
			id = streamIndices.length - 1;
		}
		
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == id) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef class46 = cache[cacheIndex];

		if (id > streamIndices.length - 1 || id < 0) {
			return null;
		}
		
		stream.currentOffset = streamIndices[id];

		class46.type = id;
		class46.setDefaults();
		class46.readValues(stream);

		switch (id) {
			case 10060:
			case 10061:
			case 30390:
				class46.name = "Trading Post booth";
				break;


		}

		return class46;
	}

	private void setDefaults() {
		anIntArray773 = null;
		anIntArray776 = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		anInt744 = 1;
		anInt761 = 1;
		aBoolean767 = true;
		isWalkable = true;
		hasActions = false;
		aBoolean762 = false;
		aBoolean769 = false;
		aBoolean764 = false;
		interactive = false;
		anInt781 = -1;
		anInt775 = 16;
		aByte737 = 0;
		aByte742 = 0;
		actions = null;
		anInt746 = -1;
		anInt758 = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		anInt748 = 128;
		anInt772 = 128;
		anInt740 = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		anInt760 = -1;
		varbit = -1;
		varp = -1;
		morphisms = null;
	}

	static ByteStreamExt stream;

	private static int[] streamIndices;

	public static void loadConfig() throws IOException {
		stream = new ByteStreamExt(getBuffer("loc.dat"));
		ByteStreamExt stream = new ByteStreamExt(getBuffer("loc.idx"));
		int objects = stream.readUnsignedWord();
		System.out.println("Total objects: " + objects);
		totalObjects = objects;
		streamIndices = new int[objects];
		int i = 2;
		for (int j = 0; j < objects; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}
		cache = new ObjectDef[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDef();
		}
		System.out.println(objects + " Object definitions loaded.");
	}

	public static byte[] getBuffer(String s) {
		try {
			java.io.File f = new java.io.File("./data/cache/" + s);
			if (!f.exists())
				return null;
			byte[] buffer = new byte[(int) f.length()];
			try (java.io.DataInputStream dis = new java.io.DataInputStream(new java.io.FileInputStream(f))) {
				dis.readFully(buffer);
				dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buffer;
		} catch (Exception e) {
		}
		return null;
	}

	/*
	 * private void readValues(ByteStreamExt buffer) { int i = -1; label0: do {
	 * int opcode; do { opcode = buffer.readUnsignedByte(); if (opcode == 0)
	 * break label0; if (opcode == 1) { int modelLength =
	 * buffer.readUnsignedByte(); anIntArray773 = new int[modelLength];
	 * anIntArray776 = new int[modelLength]; for(int i2 = 0; i2 < modelLength;
	 * i2++) { anIntArray773[i2] = buffer.readUnsignedWord(); anIntArray776[i2]
	 * = buffer.readUnsignedByte(); } } else if (opcode == 2) name =
	 * buffer.readString(); else if (opcode == 3) description =
	 * buffer.readBytes(); else if (opcode == 5) { int length =
	 * buffer.readUnsignedByte(); anIntArray776 = null; anIntArray773 = new
	 * int[length]; for (int l1 = 0; l1 < length; l1++) anIntArray773[l1] =
	 * buffer.readUnsignedWord(); } else if (opcode == 14) anInt744 =
	 * buffer.readUnsignedByte(); else if (opcode == 15) anInt761 =
	 * buffer.readUnsignedByte(); else if (opcode == 17) aBoolean767 = false;
	 * else if (opcode == 18) isWalkable = false; else if (opcode == 19) { i =
	 * buffer.readUnsignedByte(); if (i == 1) hasActions = true; } else if
	 * (opcode == 21) aBoolean762 = true; else if (opcode == 22) aBoolean769 =
	 * true; else if (opcode == 23) aBoolean764 = true; else if (opcode == 24) {
	 * anInt781 = buffer.readUnsignedWord(); if (anInt781 == 65535) anInt781 =
	 * -1; } else if (opcode == 28) anInt775 = buffer.readUnsignedByte(); else
	 * if (opcode == 29) aByte737 = buffer.readSignedByte(); else if (opcode ==
	 * 39) aByte742 = buffer.readSignedByte(); else if (opcode >= 30 && opcode <
	 * 39) { if (actions == null) actions = new String[10]; actions[opcode - 30]
	 * = buffer.readString(); if (actions[opcode -
	 * 30].equalsIgnoreCase("hidden")) actions[opcode - 30] = null; } else if
	 * (opcode == 40) { int i1 = buffer.readUnsignedByte(); originalModelColors
	 * = new int[i1]; modifiedModelColors = new int[i1]; for (int i2 = 0; i2 <
	 * i1; i2++) { modifiedModelColors[i2] = buffer.readUnsignedWord();
	 * originalModelColors[i2] = buffer.readUnsignedWord(); } } else if (opcode
	 * == 60) anInt746 = buffer.readUnsignedWord(); else if (opcode == 62)
	 * aBoolean751 = true; else if (opcode == 64) aBoolean779 = false; else if
	 * (opcode == 65) anInt748 = stream.readUnsignedWord(); else if (opcode ==
	 * 66) anInt772 = stream.readUnsignedWord(); else if (opcode == 67) anInt740
	 * = stream.readUnsignedWord(); else if (opcode == 68) anInt758 =
	 * buffer.readUnsignedWord(); else if (opcode == 69) anInt768 =
	 * buffer.readUnsignedByte(); else if (opcode == 70) anInt738 =
	 * buffer.readSignedWord(); else if (opcode == 71) anInt745 =
	 * buffer.readSignedWord(); else if (opcode == 72) anInt783 =
	 * buffer.readSignedWord(); else if (opcode == 73) aBoolean736 = true; else
	 * if (opcode == 74) { aBoolean766 = true; } else { if (opcode != 75)
	 * continue; anInt760 = buffer.readUnsignedByte(); } continue label0; }
	 * while (opcode != 77); anInt774 = buffer.readUnsignedWord(); if (anInt774
	 * == 65535) anInt774 = -1; anInt749 = buffer.readUnsignedWord(); if
	 * (anInt749 == 65535) anInt749 = -1; int j1 = buffer.readUnsignedByte();
	 * childrenIDs = new int[j1 + 1]; for (int j2 = 0; j2 <= j1; j2++) {
	 * childrenIDs[j2] = buffer.readUnsignedWord(); if (childrenIDs[j2] ==
	 * 65535) childrenIDs[j2] = -1; }
	 * 
	 * } while (true); if (i == -1) { hasActions = anIntArray773 != null &&
	 * (anIntArray776 == null || anIntArray776[0] == 10); if (actions != null)
	 * hasActions = true; } if (aBoolean766) { aBoolean767 = false; isWalkable
	 * = false; } if (anInt760 == -1) anInt760 = aBoolean767 ? 1 : 0; }
	 */

	private void readValues(ByteStreamExt buffer) {
		while (true) {
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
			if (opcode == 1) {
				int modelLength = buffer.readUnsignedByte();
				anIntArray773 = new int[modelLength];
				anIntArray776 = new int[modelLength];
				for (int i2 = 0; i2 < modelLength; i2++) {
					anIntArray773[i2] = buffer.readUnsignedWord();
					anIntArray776[i2] = buffer.readUnsignedByte();
				}
			} else if (opcode == 2)
				name = buffer.readString();
			else if (opcode == 3)
				description = buffer.readString();
			else if (opcode == 5) {
				int length = buffer.readUnsignedByte();
				anIntArray776 = null;
				anIntArray773 = new int[length];
				for (int l1 = 0; l1 < length; l1++)
					anIntArray773[l1] = buffer.readUnsignedWord();
			} else if (opcode == 14)
				anInt744 = buffer.readUnsignedByte();
			else if (opcode == 15)
				anInt761 = buffer.readUnsignedByte();
			else if (opcode == 17) {
				aBoolean767 = false;
				walkType = 0;
			} else if (opcode == 18)
				isWalkable = false;
			else if (opcode == 19) {
				interactive = (buffer.readUnsignedByte() == 1);
			} else if (opcode == 21)
				aBoolean762 = true;
			else if (opcode == 22)
				aBoolean769 = true;
			else if (opcode == 23)
				aBoolean764 = true;
			else if (opcode == 24) {
				anInt781 = buffer.readUnsignedWord();
				if (anInt781 == 65535)
					anInt781 = -1;
			} else if (opcode == 27)
				walkType = 1;
			else if (opcode == 28)
				anInt775 = buffer.readUnsignedByte();
			else if (opcode == 29)
				aByte737 = buffer.readSignedByte();
			else if (opcode == 39)
				aByte742 = buffer.readSignedByte();
			else if (opcode >= 30 && opcode < 39) {
				if (actions == null)
					actions = new String[10];
				actions[opcode - 30] = buffer.readString();
				if (actions[opcode - 30].equalsIgnoreCase("hidden"))
					actions[opcode - 30] = null;
			} else if (opcode == 40) {
				int i1 = buffer.readUnsignedByte();
				originalModelColors = new int[i1];
				modifiedModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = buffer.readUnsignedWord();
					originalModelColors[i2] = buffer.readUnsignedWord();
				}
			} else if (opcode == 41) {
				int length = buffer.readUnsignedByte();
				originalTexture = new int[length];
				modifiedTexture = new int[length];
				for (int textureId = 0; textureId < length; textureId++) {
					originalTexture[textureId] = buffer.readUnsignedWord();
					modifiedTexture[textureId] = buffer.readUnsignedWord();
				}

			} else if (opcode == 61)
				opcode61 = buffer.readUnsignedWord();
			else if (opcode == 62)
				aBoolean751 = true;
			else if (opcode == 64)
				aBoolean779 = false;
			else if (opcode == 65)
				anInt748 = stream.readUnsignedWord();
			else if (opcode == 66)
				anInt772 = stream.readUnsignedWord();
			else if (opcode == 67)
				anInt740 = stream.readUnsignedWord();
			else if (opcode == 68)
				anInt758 = buffer.readUnsignedWord();
			else if (opcode == 69)
				anInt768 = buffer.readUnsignedByte();
			else if (opcode == 70)
				anInt738 = buffer.readSignedWord();
			else if (opcode == 71)
				anInt745 = buffer.readSignedWord();
			else if (opcode == 72)
				anInt783 = buffer.readSignedWord();
			else if (opcode == 73)
				aBoolean736 = true;
			else if (opcode == 74)
				aBoolean766 = true;
			else if (opcode == 75) {
				anInt760 = buffer.readUnsignedByte();
			} else if (opcode == 78) {
				buffer.readUnsignedWord(); // ambient sound id
				buffer.readUnsignedByte();
			} else if (opcode == 79) {
				buffer.readUnsignedWord();
				buffer.readUnsignedWord();
				buffer.readUnsignedByte();
				int len = buffer.readUnsignedByte();

				for (int i = 0; i < len; i++) {
					buffer.readUnsignedWord();
				}
			} else if (opcode == 81)
				buffer.readUnsignedByte();
			else if (opcode == 82)
				anInt746 = buffer.readUnsignedWord();

			if (anInt746 == 0xFFFF) {
				anInt746 = -1;
			} else if (opcode == 89){
				field3621 = false;
		} else if (opcode == 94) {
				opcode150 = stream.readString();
			} else if (opcode == 77 || opcode == 92) {
				varbit = buffer.readUnsignedWord();

			if (varbit == 65535) {
				varbit = -1;
			}

			varp = buffer.readUnsignedWord();

			if (varp == 65535) {
				varp = -1;
			}

			int value = -1;

			if (opcode == 92) {
				value = buffer.readUnsignedWord();

				if (value == 0xFFFF) {
					value = -1;
				}
			}
			int len = buffer.readUnsignedByte();

			morphisms = new int[len + 2];
			for (int i = 0; i <= len; ++i) {
				morphisms[i] = buffer.readUnsignedWord();
				if (morphisms[i] == 0xFFFF) {
					morphisms[i] = -1;
				}
			}
				morphisms[len + 1] = value;
		} else {
			//System.out.println("invalid opcode: " + opcode);
		}
	}
		if (!interactive) {
			hasActions = anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (aBoolean766) {
			aBoolean767 = false;
			isWalkable = false;
		}
		if (anInt760 == -1)
			anInt760 = aBoolean767 ? 1 : 0;
	}

	private ObjectDef() {
		type = -1;
	}

	public boolean hasActions() {
		return hasActions;
	}

	public boolean hasName() {
		return name != null && name.length() > 1;
	}

	public boolean solid() {
		return aBoolean779;
	}

	public int xLength() {
		return anInt744;
	}

	public int yLength() {
		return anInt761;
	}

	public boolean aBoolean767() {
		return aBoolean767;
	}

	private boolean aBoolean766;
	public boolean aBoolean736;
	public String name;
	public int anInt744;
	public int anInt746;
	private int[] originalModelColors;
	private int[] originalTexture;
	private int[] modifiedTexture;
	public int varp;
	public static boolean lowMem;
	public int type;
	public boolean isWalkable;
	public int anInt758;
	public int morphisms[];
	public int anInt761;
	public boolean aBoolean762;
	public boolean aBoolean764;
	public boolean aBoolean767;
	public int anInt768;
	private static int cacheIndex;
	private int[] anIntArray773;
	public int varbit;
	public int anInt775;
	public int anInt738;
	public int anInt740; // Width
	public int anInt745;
	public int anInt783;
	public int anInt772; // Height
	private int[] anIntArray776;
	public int anInt760;
	public String description;
	public boolean hasActions;
	public boolean aBoolean779;
	public byte aByte737;
	public byte aByte742;
	public boolean aBoolean769;
	public int anInt748; // Thickness
	public boolean aBoolean751;
	public int anInt781;
	public static int totalObjects;
	private static ObjectDef[] cache;
	private int[] modifiedModelColors;
	public String actions[];

	    public int walkType = 2;

		public boolean boolean64;

	public boolean isWalkable() {
		// TODO Auto-generated method stub
		return isWalkable;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return type;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
