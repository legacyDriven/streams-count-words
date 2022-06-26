package com.epam.rd.autotasks;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Words {

    public String countWords (List<String> lines){
        List<String> presorted = lines.parallelStream()
                .map(Words::captureValues)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Set<String> unique = presorted
                .parallelStream().distinct().map(String::toLowerCase).collect(Collectors.toSet());

        return unique.parallelStream()
                .map(s -> Entry.constructEntry(s, Words.countFrequency(presorted, s)))
                .filter(entry -> entry.count>9)
                .sorted(entryComparatorCountDesc.thenComparing(Entry::getEntry))
                .map(Entry::makeString)
                .collect(Collectors.joining("\n"));
    }
    private static List<String> captureValues(String input){
        return Pattern.compile("[\\p{L}|\\p{N}]{4,}")
                .matcher(input)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    Comparator<Entry> entryComparatorCountAsc = Comparator.comparing(Entry::getCount);
    Comparator<Entry> entryComparatorCountDesc = entryComparatorCountAsc.reversed();

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

        static Entry constructEntry(String value, long count){
            return new Entry(value, count);
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
