/*
 * Copyright (C) 2016 user
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.astech.lexredupbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class LexRedupler {
    
    static private final List<Character> vowels_ru = Arrays.asList('А', 'О', 'У', 'Я', 'Ё', 'Ю', 'Е', 'Э', 'И', 'Ы');
    static private final List<Character> vowels_en = Arrays.asList('E', 'U', 'I', 'O', 'A');

    static private final List<String> stop_words = Arrays.asList("И", "НА", "ЗА", "У", "ПРО", "ИЗ", "БЕЗ", "А", "НЕ", "НИ");

    protected static int detectLang(String word) {
        Pattern pat = Pattern.compile("[А-Яё]");
        if (pat.matcher(word).matches()) {
            return 0;
        }
        return 1;
    }


    protected static boolean briefWordScan(String word, List<Integer> vowels) {
        //if (detectLang(word)!=0)
        //    return false;
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            if (vowels_ru.contains(word.charAt(i))) {
                vowels.add(i);
            }
            if (vowels_en.contains(word.charAt(i))) {
                vowels.add(i);
            }
        }
//        System.out.println(vowels);
        return (vowels.size() > 0);
    }

    private static String redup(String word) {
        List<Integer> vowels = new ArrayList<>();
        if (!briefWordScan(word, vowels)) {
            return word;
        }
        boolean startsUpper = word.substring(0, 1).equals(word.substring(0, 1).toUpperCase());

        int firstw = vowels.get(0);

        String newword = word.substring(firstw);
        int v = vowels_ru.indexOf(newword.toUpperCase().charAt(0));
        if (v >= 0) {
            // russian
            if (firstw >= 3) {
                newword = (startsUpper ? "Х" : "х") + "уй" + word.substring(firstw - 1);
            } else {
                if (v < 3) {
                    // hard vowel -> the soft analog
                    newword = vowels_ru.get(v + 3).toString().toLowerCase() + newword.substring(1);
                }
                if (startsUpper) {
                    newword = "Ху" + newword.toLowerCase();
                } else {
                    newword = "ху" + newword;
                }
            }
        } else {
            // english
            v = vowels_en.indexOf(newword.toUpperCase().charAt(0));
            if (word.substring(0, 1).equals(word.substring(0, 1).toUpperCase())) {
                newword = "Shm" + newword.toLowerCase();
            } else {
                newword = "shm" + newword;
            }
        }
        return newword;
    }

    public static String processText(String text) {

        String[] words = text.split(" ");
        String answer = "";
        for (int i = 0; i < words.length; i++) {
            if (words.length > 1 && stop_words.contains(words[i].toUpperCase())) {
                answer += words[i] + " ";
            } else {
                answer += redup(words[i]) + " ";
            }
        }

        return answer;
    }


}
