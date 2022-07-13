package com.epam.rd.autotasks;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//this impl is shit - 12s for WAP processing in parallel stream, and 120s in stream
public class WordsV1 {

    public String countWords (List<String> lines){
        List<String> presorted = lines.parallelStream()
                .map(WordsV1::captureValues)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Set<String> unique = presorted
                .parallelStream().distinct().map(String::toLowerCase).collect(Collectors.toSet());

        return unique.parallelStream()
                .map(s -> WordsV1.Entry.constructEntry(s, WordsV1.countFrequency(presorted, s)))
                .filter(entry -> entry.count>9)
                .sorted(entryComparatorCountDesc.thenComparing(WordsV1.Entry::getEntry))
                .map(WordsV1.Entry::makeString)
                .collect(Collectors.joining("\n"));
    }

    private static List<String> captureValues(String input){
        return Pattern.compile("[\\p{L}|\\p{N}]{4,}")
                .matcher(input)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    Comparator<WordsV1.Entry> entryComparatorCountAsc = Comparator.comparing(WordsV1.Entry::getCount);
    Comparator<WordsV1.Entry> entryComparatorCountDesc = entryComparatorCountAsc.reversed();

    private static int countFrequency(List<String> data, String entry){
        return (int) data.stream().filter(s -> s.equalsIgnoreCase(entry)).count();
    }

    private static class Entry{
        private final String entry;
        private final long count;

        private Entry(String entry, long count) {
            this.entry = entry;
            this.count = count;
        }

        static WordsV1.Entry constructEntry(String value, long count){
            return new WordsV1.Entry(value, count);
        }

        String getEntry() {
            return entry;
        }

        long getCount() {
            return count;
        }

        String makeString() {
            return entry + " - " + count;

        }
    }
}

/*
// Czesny impl - works good
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
 */