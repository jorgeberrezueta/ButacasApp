package me.jorgeb.config;

import com.google.gson.*;
import me.jorgeb.util.DateParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Time.class, new TimeTypeAdapter())
                .setPrettyPrinting().create();
    }

    public static class TimeTypeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {
        @Override
        public Time deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return DateParser.parseTime(jsonElement.getAsString());
        }

        @Override
        public JsonElement serialize(Time time, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(DateParser.formatTime(time));
        }
    }

}
