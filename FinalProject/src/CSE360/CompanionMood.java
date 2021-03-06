// filename: CompanionMood.java
// purpose: abstraction of the enumerated type CompanionMood

package CSE360;
public enum CompanionMood{ 
	ECSTATIC, HAPPY, OKAY, INDIFFERENT, UNHAPPY, GRUMPY;
	
	public static String CompanionMoodToString(CompanionMood cs) { 
        String s;
		switch (cs) {
			case ECSTATIC:    s="ECSTATIC"; break; 
			case HAPPY:  s="HAPPY"; break;
			case OKAY: s="OKAY"; break;
			case INDIFFERENT: s="INDIFFERENT"; break;
			case UNHAPPY:     s="UNHAPPY"; break;
			case GRUMPY:     s="GRUMPY"; break;
			default:       s="OKAY"; break;
	    }
		return s;
	}
	
	public static CompanionMood StringToCompanionMood(String s) { 
		CompanionMood cs;
		switch (s) { 
			case "ECSTATIC":    cs = CompanionMood.ECSTATIC; break; 
			case "HAPPY":  cs = CompanionMood.HAPPY; break;
			case "OKAY": cs = CompanionMood.OKAY; break;
			case "INDIFFERENT": cs = CompanionMood.INDIFFERENT; break;
			case "UNHAPPY":     cs = CompanionMood.UNHAPPY; break;
			case "GRUMPY":     cs = CompanionMood.GRUMPY; break;
			default:         cs = CompanionMood.OKAY; break;
	    }
		Project7Global.DEBUG_MSG(0,"StringToCompanionMood: input => "+s);
		
		return cs;
	}
	public static int ToInt(CompanionMood cs) { 
		int s=2;
		switch (cs) { 
			case ECSTATIC:    s=0; break; 
			case HAPPY:  s=1; break;
			case OKAY: s=2; break;
			case INDIFFERENT: s=3; break;
			case UNHAPPY:     s=4; break;
			case GRUMPY:     s=5; break;
			default:       s=2; break;
	    }
		return s;
	}
	public static CompanionMood intToCompanionMood(int s) { 
		CompanionMood cs;
		switch (s) { 
			case 0:  cs=CompanionMood.ECSTATIC;    break; 
			case 1:  cs=CompanionMood.HAPPY;  break;
			case 2:  cs=CompanionMood.OKAY; break;
			case 3:  cs=CompanionMood.INDIFFERENT; break;
			case 4:  cs=CompanionMood.UNHAPPY;     break;
			case 5:  cs=CompanionMood.GRUMPY;     break;
			default: cs=CompanionMood.INDIFFERENT;    break;
	    }
		return cs;
	}
	
	public static int StringToInt(String s) { 
		CompanionMood cs = StringToCompanionMood(s);
		return ToInt(cs);
	}


};
