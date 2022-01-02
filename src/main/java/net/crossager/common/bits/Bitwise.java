package net.crossager.common.bits;

public class Bitwise {
    public static final int INT_LENGTH = 31, LONG_LENGTH = 63, SHORT_LENGTH = 15, BYTE_LENGTH = 7;

    /**
     * Get bits
     */
    public static boolean getBit(int index, int val){
        if(index > INT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not get bit " + index + " of " + val);
        try {
            return Integer.toBinaryString(val).charAt(index) == '1';
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public static boolean getBit(int index, byte val){
        if(index > BYTE_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not get bit " + index + " of " + val);
        try {
            return Integer.toBinaryString(val).charAt(index) == '1';
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public static boolean getBit(int index, long val){
        if(index > LONG_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not get bit " + index + " of " + val);
        try {
            return Long.toBinaryString(val).charAt(index) == '1';
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public static boolean getBit(int index, short val){
        if(index > SHORT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not get bit " + index + " of " + val);
        try {
            return Integer.toBinaryString(val).charAt(index) == '1';
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    /**
     * Set bits
     */


    public static int setBit(int index, int i, boolean val){
        if(index > INT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not set bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (val ? '1' : '0') : c;
            b++;
        }
        return Integer.parseInt(bits, 2);
    }

    public static short setBit(int index, short i, boolean val){
        if(index > SHORT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not set bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (val ? '1' : '0') : c;
            b++;
        }
        return Short.parseShort(bits, 2);
    }

    public static long setBit(int index, long i, boolean val){
        if(index > LONG_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not set bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Long.toBinaryString(i).toCharArray()){
            bits += b == index ? (val ? '1' : '0') : c;
            b++;
        }
        return Long.parseLong(bits, 2);
    }

    public static byte setBit(int index, byte i, boolean val){
        if(index > BYTE_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not set bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (val ? '1' : '0') : c;
            b++;
        }
        return (byte) Integer.parseInt(bits, 2);
    }

    /**
     * Flip bits
     */

    public static int flipBit(int index, int i){
        if(index > INT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not flip bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (c == '0' ? '1' : '0') : c;
            b++;
        }
        return Integer.parseInt(bits, 2);
    }

    public static long flipBit(int index, long i){
        if(index > LONG_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not flip bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Long.toBinaryString(i).toCharArray()){
            bits += b == index ? (c == '0' ? '1' : '0') : c;
            b++;
        }
        return Bitwise.parseLong(bits);
    }

    public static short flipBit(int index, short i){
        if(index > SHORT_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not flip bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (c == '0' ? '1' : '0') : c;
            b++;
        }
        return (short) Bitwise.parseInt(bits);
    }

    public static byte flipBit(int index, byte i){
        if(index > BYTE_LENGTH || index < 0) throw new BitIndexOutOfBoundsException("Could not flip bit " + index + " of " + i);
        String bits = "";
        int b = 0;
        for(char c : Integer.toBinaryString(i).toCharArray()){
            bits += b == index ? (c == '0' ? '1' : '0') : c;
            b++;
        }
        return (byte) Bitwise.parseInt(bits);
    }

    /**
     * Get bits
     */

    public static String getBits(int i){
        String s = Integer.toBinaryString(i);
        int amount = INT_LENGTH - s.length() + 1;
        for(; amount > 0; amount--){
            s = "0" + s;
        }
        return s;
    }

    public static String getBits(short i){
        String s = Integer.toBinaryString(i);
        int amount = SHORT_LENGTH - s.length() + 1;
        for(; amount > 0; amount--){
            s = "0" + s;
        }
        return s;
    }

    public static String getBits(byte i){
        String s = Integer.toBinaryString(i);
        int amount = BYTE_LENGTH - s.length() + 1;
        for(; amount > 0; amount--){
            s = "0" + s;
        }
        return s;
    }

    public static String getBits(long i){
        String s = Long.toBinaryString(i);
        int amount = LONG_LENGTH - s.length() + 1;
        for(; amount > 0; amount--){
            s = "0" + s;
        }
        return s;
    }

    public static boolean[] parseString(String s){
        boolean[] bits = new boolean[s.length() - 1];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = s.charAt(i) == '1';
        }
        return bits;
    }

    public static String parseBits(boolean[] bits){
         String s = "";
         for(boolean b : bits){
             s += b ? "1" : "0";
         }
         return s;
    }

    /**
     * Parsing
     */

    public static int parseInt(String bits){
        return parseInt(bits, true);
    }

    public static int parseInt(String bits, boolean withNegative){
        int number = 0;
        int amount = 1;
        if(bits.length() != INT_LENGTH + 1) throw new BitIndexOutOfBoundsException
                ("Length " + bits.length() + " cannot be used to parse bits with length " + (INT_LENGTH + 1));
        for (int i = INT_LENGTH; i >= 0; i--) {
//            if(i == 0 && withNegative){
//                if(bits.toCharArray()[i] == '1'){
//                    number *= -1;
//                    number--;
//                }
//                break;
//            }
            if(bits.charAt(i) == '1'){
                number += amount;
            }
            amount *= 2;
        }
        return number;
    }

    public static long parseLong(String bits){
        return parseLong(bits, true);
    }

    public static long parseLong(String bits, boolean withNegative){
        long number = 0;
        long amount = 2;
        for(int i = LONG_LENGTH; i >= 0; i--){
            if(bits.toCharArray()[i] != '1' && bits.toCharArray()[i] != '0') throw new NumberFormatException("For input string: \"" + bits + "\" under radix 2");
            if(i == 0 && withNegative){
                if(bits.toCharArray()[i] == '1'){
                    number *= -1;
                    number--;
                }
                break;
            }
            if(bits.toCharArray()[i] == '1') number += amount -1;
            amount *= 2;
        }
        return number;
    }
}
