package org.example.notificationstuff.service;

import org.example.notificationstuff.model.Notification;
import org.example.notificationstuff.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findAllById(Collections.singleton(userId));
    }
}
