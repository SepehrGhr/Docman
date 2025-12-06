package dev.arusha.docman;

import dev.arusha.docman.dto.DocumentRequestDTO;
import dev.arusha.docman.dto.DocumentResponseDTO;
import dev.arusha.docman.model.Document;
import dev.arusha.docman.model.Tag;
import dev.arusha.docman.repository.DocumentRepository;
import dev.arusha.docman.repository.TagRepository;
import dev.arusha.docman.service.impl.DocumentServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    @DisplayName("Create: Should save new tags when none exist")
    void createDocument_AllNewTags() {
        // Given
        DocumentRequestDTO request = new DocumentRequestDTO();
        request.setTitle("Khaje Nasir");
        request.setContent("Khaje Awlie");
        request.setTags(Set.of("Khaje", "KNTU"));

        // Mock: No tags exist in DB
        when(tagRepository.findByNameIn(any())).thenReturn(new ArrayList<>());

        // Mock: Saving new tags returns them with IDs
        when(tagRepository.saveAll(anyList())).thenAnswer(invocation -> {
            List<Tag> tags = invocation.getArgument(0);
            return tags;
        });

        // Mock: Saving document
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> {
            Document doc = invocation.getArgument(0);
            doc.setId(1L);
            doc.setCreatedAt(LocalDateTime.now());
            return doc;
        });

        // When
        DocumentResponseDTO response = documentService.createDocument(request);

        // Then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTags()).containsExactlyInAnyOrder("Khaje", "KNTU");

        verify(tagRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Create: Should NOT save tags if all exist")
    void createDocument_AllExistingTags() {
        // Given
        DocumentRequestDTO request = new DocumentRequestDTO();
        request.setTitle("AP in KNTU");
        request.setContent("AP course in kntu");
        request.setTags(Set.of("Java", "Clean-Code"));

        // Mock: Both tags already exist
        List<Tag> existingTags = List.of(new Tag("Java"), new Tag("Clean-Code"));
        when(tagRepository.findByNameIn(any())).thenReturn(existingTags);

        // Mock: Saving document
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> {
            Document doc = invocation.getArgument(0);
            doc.setId(2L);
            doc.setCreatedAt(LocalDateTime.now());
            return doc;
        });

        // When
        documentService.createDocument(request);

        // Then
        verify(tagRepository, never()).saveAll(anyList());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    @DisplayName("Create: Should only save the ONE missing tag")
    void createDocument_MixedTags() {
        // Given
        DocumentRequestDTO request = new DocumentRequestDTO();
        request.setTitle("Jalebe");
        request.setContent("Nemidoonam");
        request.setTags(Set.of("Bood", "Nabood"));

        // Mock: "Bood" is found, "Nabood" is not
        when(tagRepository.findByNameIn(any())).thenReturn(new ArrayList<>(List.of(new Tag("Bood"))));

        // Mock saveAll
        when(tagRepository.saveAll(anyList())).thenAnswer(i -> i.getArgument(0));
        when(documentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // When
        documentService.createDocument(request);

        // Then
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Tag>> captor = ArgumentCaptor.forClass(List.class);
        verify(tagRepository).saveAll(captor.capture());

        List<Tag> capturedTags = captor.getValue();
        assertThat(capturedTags).hasSize(1);
        assertThat(capturedTags.get(0).getName()).isEqualTo("Nabood");
    }

    @Test
    @DisplayName("Search: Mode 'title' should call repository with specification")
    void search_TitleMode() {
        // Given
        Document doc = new Document();
        doc.setId(1L);
        doc.setTitle("Hands-on ML");
        doc.setTags(new HashSet<>());

        when(documentRepository.findAll(any(Specification.class))).thenReturn(List.of(doc));

        // When
        List<DocumentResponseDTO> results = documentService.searchDocuments("ML", "title");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Hands-on ML");
        verify(documentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Search: Mode 'content' should call repository with specification")
    void search_ContentMode() {
        // Given
        Document doc = new Document();
        doc.setId(2L);
        doc.setContent("Spring is perfect");
        doc.setTags(new HashSet<>());

        when(documentRepository.findAll(any(Specification.class))).thenReturn(List.of(doc));

        // When
        List<DocumentResponseDTO> results = documentService.searchDocuments("Spring", "content");

        // Then
        assertThat(results).hasSize(1);
        verify(documentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Search: Mode 'tag' should call repository with specification")
    void search_TagMode() {
        // Given
        Document doc = new Document();
        doc.setId(3L);
        doc.setTags(Set.of(new Tag("AI")));

        when(documentRepository.findAll(any(Specification.class))).thenReturn(List.of(doc));

        // When
        List<DocumentResponseDTO> results = documentService.searchDocuments("AI", "tag");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTags()).contains("AI");
        verify(documentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Search: Mode 'all' should call repository with specification")
    void search_AllMode() {
        // Given
        Document doc = new Document();
        doc.setId(4L);
        doc.setTitle("Full Text Search");
        doc.setTags(new HashSet<>());

        when(documentRepository.findAll(any(Specification.class))).thenReturn(List.of(doc));

        // When
        List<DocumentResponseDTO> results = documentService.searchDocuments("Search", "all");

        // Then
        assertThat(results).hasSize(1);
        verify(documentRepository, times(1)).findAll(any(Specification.class));
    }
}