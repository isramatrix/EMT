package com.emt.sostenible.data;

public interface OnDataArrived<T> {
    void callback(T data);
}
