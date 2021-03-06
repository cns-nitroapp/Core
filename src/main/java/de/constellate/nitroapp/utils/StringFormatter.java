package de.constellate.nitroapp.utils;

public class StringFormatter {

    public static String capitalizeWord(String str){
        String[] words =str.replace("_", " ").toLowerCase().split("\\s");
        StringBuilder capitalizeWord= new StringBuilder();
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord.append(first.toUpperCase()).append(afterfirst).append(" ");
        }
        return capitalizeWord.toString().trim();
    }

}
