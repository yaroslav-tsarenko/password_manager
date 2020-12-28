package com.password.manager.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CredentialsDto {
    private String formattedDate;
    private String username;
    private String serviceName;
    private String password;

    public CredentialsDto() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        this.formattedDate = dateFormat.format(now);
    }

    public CredentialsDto(String username, String serviceName, String password) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        this.formattedDate = dateFormat.format(now);
        this.username = username;
        this.serviceName = serviceName;
        this.password = password;
    }

    public Map<String, Object> getFieldMap() {
        return Map.of("user_name", username, "service_name", serviceName, "password", password,
                "date", formattedDate);
    }

    public static CredentialsDto build(Map<String, Object> fields) {
        CredentialsDto dto = new CredentialsDto();
        if (fields.containsKey("user_name")) dto.setUsername(fields.get("user_name").toString());
        else if (fields.containsKey("user_name:")) dto.setUsername(fields.get("user_name:").toString());
        if (fields.containsKey("service_name")) dto.setServiceName(fields.get("service_name").toString());
        else if (fields.containsKey("service_name:")) dto.setServiceName(fields.get("service_name:").toString());
        if (fields.containsKey("password")) dto.setPassword(fields.get("password").toString());
        else if (fields.containsKey("password:")) dto.setPassword(fields.get("password:").toString());
        if (fields.containsKey("date")) dto.setFormattedDate((String) fields.get("date"));
        return dto;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
