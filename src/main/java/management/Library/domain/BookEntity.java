package management.Library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "library")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {
    @Id
    private String id;
    private String author;
    private String title;
}
