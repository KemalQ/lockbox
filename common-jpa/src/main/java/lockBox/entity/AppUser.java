package lockBox.entity;

import jakarta.persistence.*;
import lockBox.entity.enums.BotState;
import lockBox.entity.enums.UserState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@AllArgsConstructor//constructor with all args
@NoArgsConstructor//default constructor
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserId;

    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Enumerated(EnumType.STRING)
    private BotState botState;
}
