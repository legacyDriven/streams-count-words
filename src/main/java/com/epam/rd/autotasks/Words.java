package com.epam.rd.autotasks;

import java.util.*;
import java.util.stream.Collectors;


public class Words {

    public String countWords(List<String> lines) {

        List<String> flatPresortedMapped = preprocess(lines);

        Set<String> finalValues = new HashSet<>(flatPresortedMapped);

        return finalValues.stream()
                .map(s-> Entry.constructEntry(s, Words.countFrequency(flatPresortedMapped, s)))
                .sorted(entryComparatorCountDesc.thenComparing(Entry::getEntry))
                .filter(entry -> entry.count>9)
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

        private List<String> preprocess(List<String> words){
        return words.stream()
                .map(s-> s.split(" "))
                .flatMap(Arrays::stream)
                .map(s -> s.replaceAll("\\p{IsPunctuation}", " "))  //"\\p{Punct}"   [^A-Za-z]"  "\\p{IsPunctuation}
                .map(s -> s.replaceAll("[ ]{2,}"," "))
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .filter(s -> s.length()>3)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    Comparator<Entry> entryComparatorCountAsc = Comparator.comparing(Entry::getCount);
    Comparator<Entry> entryComparatorCountDesc = entryComparatorCountAsc.reversed();

    private static int countFrequency(List<String> data, String entry){
        return (int) data.stream().filter(s -> s.equals(entry)).count();
    }

    private static class Entry{
        private final String entry;
        private final long count;

        private Entry(String entry, long count) {
            this.entry = entry;
            this.count = count;
        }

        static Entry constructEntry(String value, long count){
            return new Entry(value, count);
        }

        public String getEntry() {
            return entry;
        }

        public long getCount() {
            return count;
        }

        @Override
        public String toString() {
            return entry + " - " + count;

        }
    }
}
