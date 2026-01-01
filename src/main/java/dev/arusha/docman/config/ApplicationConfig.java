package dev.arusha.docman.config;

import dev.arusha.docman.application.service.DocumentApplicationService;
import dev.arusha.docman.domain.port.in.CreateDocumentUseCase;
import dev.arusha.docman.domain.port.in.SearchDocumentsUseCase;
import dev.arusha.docman.domain.port.out.DocumentRepositoryPort;
import dev.arusha.docman.domain.port.out.TagRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    /**
     * Wires up the Core Domain Service.
     * Spring injects the Persistence Adapters (which implement the RepositoryPorts)
     * into this method, and we pass them manually to the Domain Service constructor.
     */
    @Bean
    public DocumentApplicationService documentApplicationService(
            DocumentRepositoryPort documentRepositoryPort,
            TagRepositoryPort tagRepositoryPort
    ) {
        return new DocumentApplicationService(documentRepositoryPort, tagRepositoryPort);
    }

    // Alias beans to make injection into Controller cleaner if you prefer interface-based injection
    // (Spring is smart enough to inject DocumentApplicationService into CreateDocumentUseCase,
    // but explicit aliasing can be helpful for clarity).

    @Bean
    public CreateDocumentUseCase createDocumentUseCase(DocumentApplicationService service) {
        return service;
    }

    @Bean
    public SearchDocumentsUseCase searchDocumentsUseCase(DocumentApplicationService service) {
        return service;
    }
}