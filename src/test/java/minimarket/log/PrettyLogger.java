package minimarket.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

public class PrettyLogger implements HttpLoggingInterceptor.Logger {
    @SneakyThrows
    @Override
    public void log(String s) {
        ObjectMapper objectMapper = new ObjectMapper();

        String trimmedString = s.trim();
        if ((trimmedString.startsWith("{") && trimmedString.endsWith("}"))
                || (trimmedString.startsWith("[") && trimmedString.endsWith("]"))) {
            Object object = objectMapper.readValue(s, Object.class);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            Platform.get().log(Platform.INFO, prettyJson, null);
        } else {
            Platform.get().log(Platform.INFO, s, null);
        }
    }
}