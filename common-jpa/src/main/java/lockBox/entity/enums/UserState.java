package lockBox.entity.enums;

import jakarta.persistence.*;

import java.util.Date;

public enum UserState {
    BASIC_STATE,
    WAIT_FOR_EMAIL_STATE;
}