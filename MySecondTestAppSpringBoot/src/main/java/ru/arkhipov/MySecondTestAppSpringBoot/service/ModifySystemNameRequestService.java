package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Systems;

@Slf4j
@Service
public class ModifySystemNameRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        // Записываем время получения запроса в Сервисе 1
        long service1ReceiveTime = System.currentTimeMillis();
        log.info("Сервис 1 получил запрос в время: {}", service1ReceiveTime);

        // Модифицируем systemName
        request.setSystemName(Systems.ERP);

        // Модифицируем source (если нужно)
        request.setSource("Service 1 Modified");

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        // Отправляем модифицированный запрос в Сервис 2
        log.info("Отправка запроса в Сервис 2...");
        new RestTemplate().exchange(
                "http://localhost:8084/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );

        log.info("Сервис 1 завершил обработку. Время обработки: {} мс",
                System.currentTimeMillis() - service1ReceiveTime);
    }
}