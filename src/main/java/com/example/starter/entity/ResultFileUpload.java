package com.example.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class ResultFileUpload {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultFileUploadId;

    @Column(nullable = false)
    private Integer fileUploadId;

    @Column(nullable = false)
    private Integer resultId;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "fileUploadId", nullable = false, insertable = false, updatable = false)
    private FileUpload fileUpload;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultId", nullable = false, insertable = false, updatable = false)
    private Result result;

    public Integer getResultFileUploadId() {
        return resultFileUploadId;
    }

    public void setResultFileUploadId(final Integer resultFileUploadId) {
        this.resultFileUploadId = resultFileUploadId;
    }

    public Integer getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(final Integer fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(final Integer resultId) {
        this.resultId = resultId;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(final FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(final Result result) {
        this.result = result;
    }

}
