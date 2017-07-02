package CSE360;



public final class Project7Global {
	public static boolean DEBUG=true;
	public static int DEBUG_LEVEL=1; // 0 = all messages ... 10 = critical messages
	public static final String[] filePath = {"src/CSE360/inputsTeam7","CSE360/inputsTeam7","inputsTeam7"};
	public static void ERROR_MSG(String msg) {
		System.err.println(msg);
	}
	public static void DEBUG_MSG(int criticality, String msg) {
		if(DEBUG && criticality>=DEBUG_LEVEL) { System.out.println(msg); }
	}

}