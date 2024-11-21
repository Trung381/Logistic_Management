package com.project.logistic_management.dto.request;

public class NotificationDTO {
    private Integer id;
    private String title;
    private String url;
    private Object content;
}

/*
id: 1
title: Có lịch trình số 2 cần phê duyệt
urlApi: /api/schedules/2
content: ScheduleDTO
*/

// => button (url: notification.urlApi)