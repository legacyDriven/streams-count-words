package com.epam.rd.autotasks;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;

public class Words {



    public static void main(String[] args) throws IOException {
        final Path wapPath = Paths.get("src", "test", "resources", "wap.txt");
        final List<String> lines = lines(wapPath, UTF_8).skip(825).collect(Collectors.toList());
        System.out.println(lines.size());
        int count = (int) lines.stream().filter(s-> s.contains("that")).count();
        System.out.println(count);
    }

    public String countWords(List<String> lines) {

        List<String> flatPresortedMapped = preprocess(lines);

        Set<String> finalValues = new HashSet<>(flatPresortedMapped);

        return finalValues.stream()
                .map(s-> Entry.constructEntry(s, Words.countFrequency(flatPresortedMapped, s)))
                .sorted(entryComparatorCountDesc.thenComparing(Entry::getEntry))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

//    public String countWords (List<String> lines){
//
//        List<String> preprocessed = lines.stream()
//                .map( s -> s.replaceAll("\\p{Punct}", " "))
//                .map(String::trim)
//                .map( s -> s.split(" "))
//                .flatMap(Arrays::stream)
//                .filter( s -> s.length()<4)
//                .map(String::toLowerCase)
//                .collect(Collectors.toList());
//
//        List<String> unique = preprocessed.stream().distinct().collect(Collectors.toList());
//
//        return unique.stream()
//                .map( s-> Entry.constructEntry( s, Words.countFrequency(preprocessed, s)))
//                .filter( s -> s.count > 9)
//                .sorted(entryComparatorCountDesc.thenComparing(Entry::getEntry))
//                .map(Object::toString)
//                .collect(Collectors.joining("\n"));
//
//    }

    Comparator<Entry> entryComparatorCountAsc = Comparator.comparing(Entry::getCount);
    Comparator<Entry> entryComparatorCountDesc = entryComparatorCountAsc.reversed();

    private List<String> preprocess(List<String> words){
        return words.stream()
                .map(s-> s.split(" "))
                .flatMap(Arrays::stream)
                .map(s -> s.replaceAll("\\p{Punct}", ""))
                .filter(s -> s.length()>3)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

//    private List<String> preprocess(List<String> words){
//        return words.stream()
//                .map(s-> s.split(" "))
//                .flatMap(Arrays::stream)
//                .map(s -> s.replaceAll("\\p{Punct}", " "))
//                .map(s-> s.split(" "))
//                .flatMap(Arrays::stream)
//                .filter(s -> s.length()>3)
//                .map(String::toLowerCase)
//                .collect(Collectors.toList());
//    }

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

