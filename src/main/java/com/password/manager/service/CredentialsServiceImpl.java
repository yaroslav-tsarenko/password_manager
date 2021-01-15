package com.password.manager.service;

import com.password.manager.dto.CredentialsDto;
import com.password.manager.repository.CredentialsRepository;
import com.password.manager.service.exception.ArgumentRequiredException;
import com.password.manager.ts_encrypt.TSEncrypt;
import com.password.manager.util.ResponseFactory;
import com.password.manager.util.StringFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CredentialsServiceImpl implements CredentialService {

    @Override
    public String saveCredentials(CredentialsDto credentials) throws ArgumentRequiredException {
        Map<String, Object> fields = credentials.getFieldMap();
        Map<String, Object> validFields = new HashMap<>();
        fields.forEach((k, v) -> {
            if (fields.get(k) != null) validFields.put(k, v);
            else throw new ArgumentRequiredException(StringFactory.buildString("field:", k, "is empty"));
        });
        String password = (String) validFields.get("password");
        validFields.replace("password", TSEncrypt.doEncryption(password));
        CredentialsRepository.save(ResponseFactory.createCredentialsRecord(CredentialsDto.build(validFields)));
        return ResponseFactory.createResponse("credentials saved");
    }

    @Override
    public String getCredentialsByServiceName(String serviceName) {
        String one = CredentialsRepository.getOneByServiceName(serviceName);
        CredentialsDto dto = getDtoFromOneLine(one);
        StringSelection stringSelection = null;
        if (!dto.hasNull()) stringSelection = new StringSelection(TSEncrypt.doDecryption(dto.getPassword()));
        if (dto.hasNull()) return ResponseFactory.createResponse("service not found");
        dto.setPassword("password saved to clipboard");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        return ResponseFactory.createCredentialResponse(dto);
    }

    private CredentialsDto getDtoFromOneLine(String one) {
        Map<String, Object> map = new HashMap<>();
        String[] words = Arrays.stream(one.split("\\s"))
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        for (int i = 3; i < words.length; i++) {
            map.put(words[i], words[i = i + 1]);
        }
        return CredentialsDto.build(map);
    }
}
