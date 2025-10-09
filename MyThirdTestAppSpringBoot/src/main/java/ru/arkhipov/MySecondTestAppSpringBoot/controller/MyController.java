package ru.arkhipov.MySecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Codes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import java.util.Date;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) throws UnsupportedCodeException {

        log.info("Получен запрос: {}", request);

        // Проверка на uid = "123"
        if ("123".equals(request.getUid())) {
            log.warn("Попытка использования запрещенного UID: 123");
            throw new UnsupportedCodeException("UID 123 не поддерживается");
        }

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("Создан первоначальный response: {}", response);

        try {
            log.info("Начало валидации запроса");
            validationService.isValid(bindingResult);
            log.info("Валидация пройдена успешно");

        } catch (ValidationFailedException e) {
            log.error("Ошибка валидации: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response обновлен после ошибки валидации: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response обновлен после непредвиденной ошибки: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Начало модификации response");
        modifyResponseService.modify(response);
        log.info("Response после модификации: {}", response);

        log.info("Успешный ответ отправлен");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UnsupportedCodeException.class)
    public ResponseEntity<Response> handleUnsupportedCode(UnsupportedCodeException e) {
        log.error("Обработка UnsupportedCodeException: {}", e.getMessage());

        Response response = Response.builder()
                .uid("")
                .operationUid("")
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.UNSUPPORTED_EXCEPTION)
                .errorMessage(ErrorMessages.UNSUPPORTED)
                .build();

        log.info("Создан response для UnsupportedCodeException: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}