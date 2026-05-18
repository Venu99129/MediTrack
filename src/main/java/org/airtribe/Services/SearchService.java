package org.airtribe.Services;

import org.airtribe.Interface.Searchable;

import java.util.List;

public class SearchService {

    public static <T extends Searchable>List<T> search(List<T> list, String key){
        return list.stream().filter(item -> item.match(key)).toList();
    }
}
