package tts;

public class ttshelper {
    public static TextToSpeech tts = new TextToSpeech();
    public static boolean playtts = true;
    public static String ttsfinal = "tts";

    public static void toggle(boolean value){
        if(value){
            playtts = true;
            tts.speak(ttsfinal,false,playtts);
        } else{
            tts.stopSpeaking();
            playtts = false;
        }

    }

    private static final String[] SUBTWENTY = {"zero", "waan", "tu", "three", "foor", "five", "six", "seeveen", "eight", "naain", "ten",
            "eeleevun", "twelf", "tuurteen", "foor teen", "fifteen", "sixteen", "sevunteen", "eighteen", "naainteen"};

    private static final String[] DECADES = {"zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    /**
     * Convert any value from 0 to 999 inclusive, to a string.
     * @param value The value to convert.
     * @param and whether to use the word 'and' in the output.
     * @return a String representation of the value.
     */
   public static String tripleAsText(int value, boolean and) {
        if (value < 0 || value >= 1000) {
            throw new IllegalArgumentException("Illegal triple-value " + value);
        }

        if (value < SUBTWENTY.length) {
            return SUBTWENTY[value];
        }

        int subhun = value % 100;
        int hun = value / 100;
        StringBuilder sb = new StringBuilder(50);
        if (hun > 0) {
            sb.append(SUBTWENTY[hun]).append(" hundred");
        }
        if (subhun > 0) {
            if (hun > 0) {
                sb.append(and ? " and " : " ");
            }
            if (subhun < SUBTWENTY.length) {
                sb.append(SUBTWENTY[subhun]);
            } else {
                int tens = subhun / 10;
                int units = subhun % 10;
                if (tens > 0) {
                    sb.append(DECADES[tens]);
                }
                if (units > 0) {
                    sb.append(" ").append(SUBTWENTY[units]);
                }
            }
        }

        return sb.toString();
    }


}
