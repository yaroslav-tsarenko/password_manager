package com.password.manager.util;

import com.password.manager.dto.CredentialsDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseFactory {

    private static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static String createResponse(String response) {
        return StringFactory.buildString(
                getDateTime(), "===", "task executed", "===", "\n", "status: ", response);
    }

    public static String createCredentialResponse(CredentialsDto dto) {
        return StringFactory.buildString(
                getDateTime(), "===", "task executed", "===", "\n", "status: success", "\n", "service_name: ",
                dto.getServiceName() + "\n", "user_name:", dto.getUsername() + "\n", "password:", dto.getPassword()) + "\n";
    }

    public static String createCredentialsRecord(CredentialsDto dto) {
        return StringFactory.buildString("date:", dto.getFormattedDate(), "service_name:", dto.getServiceName(),
                "user_name:", dto.getUsername(), "password:", dto.getPassword());
    }
}
