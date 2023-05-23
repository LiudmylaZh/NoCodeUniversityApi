package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidDeleteUserResponse {
    private String code;
    private String message;
    private String instance;
    private String status;
    private String title;
    private String type;
    private String source;
}
