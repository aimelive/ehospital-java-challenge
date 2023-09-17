package utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseEntity<T> {
    int statusCode;
    String message;
    T data;
}
