package com.password.manager.service;

import com.password.manager.dto.CredentialsDto;

import java.io.IOException;

public interface CredentialService {

    /**
     * Saves a new user credentials to credential store
     *
     * @param credentials - user credentials
     * @return String
     */
    String saveCredentials(CredentialsDto credentials) throws IOException;

    /**
     * Give for user his credentials by service name
     *
     * @param serviceName - service name
     * @return String
     */
    String getCredentialsByServiceName(String serviceName);
}
