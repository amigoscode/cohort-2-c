package com.amigoscode.chohort2.carRental.userNotification;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@TransactionalService
public class UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
}
