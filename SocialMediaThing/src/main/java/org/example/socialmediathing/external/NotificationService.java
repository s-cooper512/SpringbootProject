package org.example.socialmediathing.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    public void addNotification (NotificationDTO dto) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<NotificationDTO> notificationResponse = restTemplate.postForEntity(
                "http://localhost:8081/notifications",
                dto,
                NotificationDTO.class
        );
    }
}
