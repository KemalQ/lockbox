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
    private Long id;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    private String inputText;//для временного хранения текста
    private String inputNumber;//для временного хранения введенного числа

    private boolean textReceived;//флаг наличия текста. Использовать с inputText
    private boolean imageReceived;//флаг наличия фото. Использовать с inputNumber

    @Enumerated
    private WorkFlowState currentState;

    @Enumerated
    private WorkFlowStep workFlowStep;

    @OneToOne
    private BinaryContent binaryContent;//FK

}
