package com.tsse.backend.integration;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tsse.entities.Department;

@Component
@CacheConfig(cacheNames={"departments"})
@Transactional
public class DepartmentRestClient {

  @Autowired
  private OAuth2RestTemplate oAuth2RestTemplate;

  @Async
  public CompletableFuture<Department> getDepartment() {
    Department dept = oAuth2RestTemplate.getForObject("http://localhost:8095/api/departments/1", Department.class);
    return CompletableFuture.completedFuture(dept);
  }
}