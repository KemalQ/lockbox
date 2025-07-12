package lockBox.entity;


import jakarta.persistence.*;
import lockBox.entity.enums.WorkFlowState;
import lockBox.entity.enums.WorkFlowStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "app_user_session")
public class AppUserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated
    private WorkFlowState currentState;

    @Enumerated
    private WorkFlowStep step;

    @OneToOne
    private BinaryContent binaryContent;//FK

    private String text;

    private String number;

}
