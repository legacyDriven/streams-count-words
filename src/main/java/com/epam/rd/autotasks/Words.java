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
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

        private List<String> preprocess(List<String> words){
        return words.stream()
                .map(s-> s.split(" "))
                .flatMap(Arrays::stream)
                .map(s -> s.replaceAll("\\p{Punct}", ""))
                .filter(s -> s.length()>3)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

//    firing was, he began to look for the general and his staff where they
//    The command of the left flank belonged by seniority to the commander of
//    and colonel looked sternly and significantly at one another like two
//    the time had come to experience the joy of an attack of which he had so
//    word infected the whole crowd with a feeling of panic.
//
//    But the staff officer did not finish what he wanted to say. A cannon
//    Rostóv looked at and listened listlessly to what passed before and

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


//    private List<String> preprocess(List<String> words){
//        return words.stream()
//                .map(s-> s.split(" "))
//                .flatMap(Arrays::stream)
//                .map(s -> s.replaceAll("\\p{Punct}", ""))
//                .filter(s -> s.length()>3)
//                .map(String::toLowerCase)
//                .collect(Collectors.toList());
//    }

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

//                .split(" ").
//        return line.map(String::trim)
//                .map(String::toLowerCase)
//                .map(s -> s.replaceAll("\\p{Punct}", " "))
//                .map(s-> s.replaceAll("[ ]{2,}"," "))
//                .map(s-> s.split(" "))
//                .flatMap(Arrays::stream)
//                .filter(s-> s.length()>3);

//    public String countWords(List<String> lines) {
//        List<String> preprocessed = lines.stream().map(Words::lineProcessor).flatMap(List::stream).collect(Collectors.toList());
//        List<String> uniqueValues = preprocessed.stream().distinct().collect(Collectors.toList());
//
//        return uniqueValues.stream()
//                .map( s-> Entry.constructEntry( s, Words.countFrequency(preprocessed, s)))
//                .filter( s -> s.count > 9)
//                .sorted(entryComparatorCountDesc.thenComparing(Entry::getEntry))
//                .map(Object::toString)
//                .collect(Collectors.joining("\n"));
//    }
//
//    private static List<String> lineProcessor(String line){
//        String temp = line.trim()
//                .toLowerCase(Locale.ROOT)
//                .replaceAll("\\p{Punct}", " ");
//        line = line.replaceAll("[ ]{2,}"," ");
//        return Arrays.stream(line.split(" ")).filter(s-> s.length()>3).collect(Collectors.toList());
//
//    }


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