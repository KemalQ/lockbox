package lockBox.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.telegram.telegrambots.meta.api.objects.Update;


@Data
@Builder
@AllArgsConstructor//constructor with all args
@NoArgsConstructor//default constructor
@Entity
@Table(name = "raw_data")
@Convert(attributeName = "jsonb", converter = JsonBinaryType.class)//for SpringBoot 3
public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON) // using in Spring Boot 3
    @Column(columnDefinition = "jsonb") // using in Spring Boot 3
    private Update event;
}
