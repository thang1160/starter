package com.example.starter.model;

import java.util.ArrayList;
import java.util.List;

public class PagingResponse<T> {
    private Long length;
    private List<T> list;

    public PagingResponse(Long length, List<T> list) {
        this.length = length;
        this.list = list;
    }

    public PagingResponse() {
        length = 0L;
        list = new ArrayList<>();
    }

    public Long getLength() {
        return this.length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
