package dev.arusha.docman.adapter.jpa.repository;

import dev.arusha.docman.adapter.jpa.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaDocumentRepository extends JpaRepository<DocumentEntity, Long>, JpaSpecificationExecutor<DocumentEntity> {
}