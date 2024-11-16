package com.rdjava.springboot.service.impl;

import com.rdjava.springboot.service.ExampleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public List<String> getFlattenList() {
        // Create collections of collections
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("cherry", "date", "fig")
        );

        // Use flatMap to flatten the collection of collections into a single list
        List<String> flattenedList = listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return flattenedList;
    }

    @Override
    public List<String> getMapList() {
        // Create two lists of strings
        List<String> list1 = Arrays.asList("apple", "banana");
        List<String> list2 = Arrays.asList("cherry", "date", "fig");

        // Merge the lists using Stream.concat and collect to a single list
        List<String> mergedList = Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());

        // Transform the merged list using map
        List<String> transformedList = mergedList.stream()
                .map(String::toUpperCase) // Example transformation: converting each string to uppercase
                .collect(Collectors.toList());

        return transformedList;
    }
}
