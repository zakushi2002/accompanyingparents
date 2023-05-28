package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileDataRepository extends JpaRepository<FileData, Long>, JpaSpecificationExecutor<FileData> {
}