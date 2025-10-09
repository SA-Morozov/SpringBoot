package ru.arkhipov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32, message = "UID не может быть длиннее 32 символов")
    private String uid;

    @NotBlank(message = "Operation UID не может быть пустым")
    @Size(max = 32, message = "Operation UID не может быть длиннее 32 символов")
    private String operationUid;

    private Systems systemName; 

    @NotBlank(message = "System Time не может быть пустым")
    private String systemTime;

    private String source;

    @NotNull(message = "Communication ID обязателен")
    @Min(value = 1, message = "Communication ID должен быть больше 0")
    private Integer communicationId;

    private Integer templateId;

    @NotNull(message = "Product Code обязателен")
    @Min(value = 1, message = "Product Code должен быть больше 0")
    private Integer productCode;

    private Integer smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}