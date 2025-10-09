package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Ошибки валидации: ");
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(String.format("[%s: %s]", error.getField(), error.getDefaultMessage()))
            );

            String fullErrorMessage = errorMessage.toString();
            log.error("Обнаружены ошибки валидации: {}", fullErrorMessage);
            throw new ValidationFailedException(fullErrorMessage);
        }
        log.debug("Валидация пройдена без ошибок");
    }
}