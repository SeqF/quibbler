package com.ps.quibbler.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ps
 */
@Slf4j
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper jacksonObjectMapper) {
        JacksonUtil.objectMapper = jacksonObjectMapper;
    }

    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Parse Object to jsonString error:{}", e);
            return null;
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T) json;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Parse String to Object error:{}", e);
            return null;
        }
    }
}
