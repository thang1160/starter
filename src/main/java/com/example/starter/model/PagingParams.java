package com.example.starter.model;

import java.util.Optional;
import io.vertx.core.MultiMap;

public class PagingParams {
    private Integer pageSize;
    private Integer pageIndex;
    private Integer startPosition;

    public PagingParams(MultiMap multiMap) {
        pageSize = Optional.ofNullable(multiMap.get("pageSize")).map(Integer::parseInt).orElse(5);
        pageIndex = Optional.ofNullable(multiMap.get("pageIndex")).map(Integer::parseInt).orElse(0);
        startPosition = pageIndex * pageSize;
    }

    public PagingParams() {}

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getStartPosition() {
        return this.startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }
}
