package com.example.greenpage.myapp;

import com.example.greenpage.model.Header;
import org.springframework.http.ResponseEntity;

public interface CrudInterface<T> {

//    Header<T> create(Header<T> request);
//    ResponseEntity<Header<T>> create(Header<T> request);
    ResponseEntity<?> create(Header<T> request);

    Header<T> read(Integer id);

    Header<T> update(Header<T> request);

    Header<T> delete(Integer id);
}

