package com.example.memberservice.common;

public record ResponseEntity<T>(int status, T data, long count) {

}
