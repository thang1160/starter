package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.starter.entity.FileUpload;
import com.example.starter.entity.Result;
import com.example.starter.entity.ResultFileUpload;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

public class ResultService extends BaseService {

    public static void update(Result input, List<FileUpload> fileUploads) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Result result = em.find(Result.class, input.getResultId());
            result.setStatus(input.getStatus());
            result.setComment(input.getComment());
            for (FileUpload fileUpload : fileUploads) {
                em.persist(fileUpload);
                ResultFileUpload resultFileUpload = new ResultFileUpload();
                resultFileUpload.setResultId(result.getResultId());
                resultFileUpload.setFileUploadId(fileUpload.getFileUploadId());
                em.persist(resultFileUpload);
            }
            em.merge(result);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static List<Result> findAllByTestRunId(long testRunId) {
        List<Result> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Result> query = em.createQuery("select new com.example.starter.entity.Result(r, c.caseName, s.sectionName) from Result r join r.testCase c join c.section s where r.runId = ?1", Result.class);
            query.setParameter(1, testRunId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static Result findById(Long id) {
        EntityManager em = getEntityManager();

        Result result = null;
        try {
            result = em.find(Result.class, id);
            if (result == null) {
                throw new EntityNotFoundException("Can't find Result for ID " + id);
            }
            Set<FileUpload> fileUploads = result.getResultFileUploads().stream()
                    .map(ResultFileUpload::getFileUpload)
                    .collect(Collectors.toSet());
            result.setFileUploads(fileUploads);
        } finally {
            em.close();
        }
        return result;
    }
}
