package com.example.starter.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class FileUpload {

    public FileUpload(String uploadedFileName, String fileName, Long size, String contentType) {
        this.uploadedFileName = uploadedFileName;
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
    }

    public FileUpload() {}

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileUploadId;

    @Column(nullable = false, length = 50)
    private String uploadedFileName;

    @Column(nullable = false, length = 50)
    private String fileName;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 50)
    private String contentType;

    @JsonIgnore
    @OneToMany(mappedBy = "fileUpload")
    private Set<ResultFileUpload> resultFileUploads;

    public Integer getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(final Integer fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(final String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(final Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public Set<ResultFileUpload> getResultFileUploads() {
        return resultFileUploads;
    }

    public void setResultFileUploads(
            final Set<ResultFileUpload> resultFileUploads) {
        this.resultFileUploads = resultFileUploads;
    }

    @Override
    public String toString() {
        return "FileUpload [fileUploadId=" + fileUploadId + ", uploadedFileName=" + uploadedFileName + ", fileName=" + fileName + ", size=" + size + ", contentType=" + contentType + "]";
    }

}
