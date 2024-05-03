package org.example.notificationstuff.service;

import org.example.notificationstuff.model.Notification;
import org.example.notificationstuff.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notification -> {
            if (Objects.equals(notification.getPostOwnerId(), userId)) {
                notifications.add(notification);
            }
        });
        return notifications;
    }

    public List<Notification> getAllNotifications () {
        return notificationRepository.findAll();
    }
}
