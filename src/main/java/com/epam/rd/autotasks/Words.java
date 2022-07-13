package com.epam.rd.autotasks;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Words {

    private static final Pattern pattern = Pattern.compile("[\\p{L}|\\p{N}]{4,}");

    private static final Comparator<Map.Entry<String, Long>> entryComparatorCountDesc =
            Map.Entry.comparingByValue(Comparator.reverseOrder());

    private static final Comparator<Map.Entry<String, Long>> entryByCountDescThenName =
            entryComparatorCountDesc.thenComparing(Map.Entry.comparingByKey());

    public String countWords(List<String> lines) {
        Map<String, Long> preprocessed = Words.preprocess(lines);

        Map<String, Long> result = Words.sortPreprocessed(preprocessed);

        return result.keySet().stream()
                .map(key->key + " - " + result.get(key))
                .collect(Collectors.joining("\n","",""));
    }

    private static Map<String, Long> preprocess(List<String> lines){
        return pattern.matcher(lines.stream()
                        .map(s -> s.split(" "))
                        .flatMap(Arrays::stream).collect(Collectors.joining(" ")))
                .results()
                .map(MatchResult::group)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }

    private static Map<String, Long> sortPreprocessed(Map<String, Long> map){
        return map.entrySet().stream()
                .filter(s -> s.getValue()>9).sorted(entryByCountDescThenName)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,(a, b)->a,
                        LinkedHashMap::new));
    }

    public String countWordsPioter(List<String> lines) {

        String text = lines.toString();
        String[] words = text.split("(?U)\\W+");

        Comparator<Map.Entry<String, Long>> comparator =
                Map.Entry.comparingByValue(Comparator.reverseOrder());
        comparator=comparator.thenComparing(Map.Entry.comparingByKey());

        Map<String,Long> freq = Arrays.stream(words)
                .collect(Collectors.groupingBy
                        (String::toLowerCase, Collectors.counting()));
        System.out.println(freq);

        LinkedHashMap<String,Long> freqSorted = freq.entrySet().stream()
                .filter(x->x.getKey().length()>3&&x.getValue()>9)
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,(a, b)->a,
                        LinkedHashMap::new));

        return freqSorted.keySet().stream().map(key->key+" - "+freqSorted.get(key)).collect(Collectors.joining("\n","",""));
    }


}
