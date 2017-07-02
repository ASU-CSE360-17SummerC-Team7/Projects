// filename: CompanionState.java
// purpose: abstraction of the enumerated type CompanionState
package CSE360;

public enum CompanionState {	
	BORED, WORKING, DONE ;

	public static String CompanionStateToString(CompanionState cs) { 
        String s;
		switch (cs) {
			case BORED:    s="BORED"; break; 
			case WORKING:  s="WORKING"; break;
			case DONE:     s="DONE"; break;
			default:       s="BORED"; break;
	    }
		return s;
	}
	
	public static CompanionState StringToCompanionState(String s) { 
		CompanionState cs;
		switch (s) { 
			case "BORED":    cs = CompanionState.BORED; break; 
			case "WORKING":  cs = CompanionState.WORKING; break;
			case "DONE":     cs = CompanionState.DONE; break;
			default:         cs = CompanionState.BORED; break;
	    }
		return cs;
	}
	public static int ToInt(CompanionState cs) { 
		int s=0;
		switch (cs) { 
			case BORED:    s=0; break; 
			case WORKING:  s=1; break;
			case DONE:     s=2; break;
			default:       s=0; break;
	    }
		return s;
	}
	public static CompanionState intToCompanionState(int s) { 
		CompanionState cs;
		switch (s) { 
			case 0:  cs=CompanionState.BORED;    break; 
			case 1:  cs=CompanionState.WORKING;  break;
			case 3:  cs=CompanionState.DONE;     break;
			default: cs=CompanionState.BORED;    break;
	    }
		return cs;
	}
	public static int StringToInt(String s) { 
		CompanionState cs = StringToCompanionState(s);
		return ToInt(cs);
	}

}