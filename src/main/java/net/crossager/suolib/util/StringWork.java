package net.crossager.suolib.util;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringWork {
    public static String fromArray(String[] strs){
        String last = "";
        for (int i = 0; i <  strs.length; i++){
            if (i != 0) last += " ";
            last += strs[i];
        }
        return last;
    }
    public static String toRomanNumerals(int num){
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(num >= values[i]) {
                num -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        return roman.toString();
    }
    public static String rightCapitalization(String str){
        String newString = null;
        char charBefore = 0;
        boolean isFirst = true;
        for (char c :  str.toCharArray()){
            if (isFirst){
                newString += Character.toUpperCase(c);
            } else {
                if (charBefore == ' '){
                    newString += Character.toUpperCase(c);
                } else {
                    newString += Character.toLowerCase(c);
                }
            }
            charBefore = c;
            isFirst = false;
        }
        return newString;
    }
    public static int eval(String infixExpression) {
        Stack<String> stack = new Stack<>();
        String trimmedExp = "(" + infixExpression.replaceAll(" ", "") + ")";
        for (String c : split(trimmedExp)) {
            if (")".equals(c)) {
                if (!"(".equals(stack.peek())) {
                    String result = performOperation(stack.pop(), stack.pop(),
                            stack.pop()) + "";
                    stack.pop();
                    stack.push(result);
                }
            } else {
                stack.push(c);
            }
        }
        return Integer.parseInt(stack.pop());
    }
    private static int performOperation(String operand2, String operator,
                                        String operand1) {
        int op1 = Integer.parseInt(operand1);
        int op2 = Integer.parseInt(operand2);
        switch (operator.toCharArray()[0]) {
            case '+': return op1 + op2;
            case '-': return op1 - op2;
            case '*': return op1 * op2;
            case '/': return op1 / op2;
            default: return 0;
        }
    }
    private static String[] split(String exp) {
        ArrayList<String> parts = new ArrayList<>();
        Pattern pat = Pattern.compile("\\d++|\\+|\\-|\\*|/|\\(|\\)");
        Matcher matcher = pat.matcher(exp);
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        return parts.toArray(new String[0]);
    }
}
