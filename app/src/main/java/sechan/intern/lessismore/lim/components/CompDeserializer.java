package sechan.intern.lessismore.lim.components;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sechan on 2017-08-10.
 */

public class CompDeserializer implements JsonDeserializer<List<Comp>> {
    // For Gson Deserialize -- 그냥하면 Subclass 변환 안됨
    @Override
    public List<Comp> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List <Comp> list = new ArrayList<>();
        JsonArray ja = json.getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement je : ja) {
            switch(je.getAsJsonObject().get("type").getAsString()){
                case "COMP_TEXT":
                    list.add(gson.fromJson(je,CompText.class));
                    break;
                case "COMP_IMAGE":
                    list.add(gson.fromJson(je,CompImage.class));
                    break;
                case "COMP_MAP":
                    list.add(gson.fromJson(je,CompMap.class));
                    break;
            }
        }
        return list;

    }
}
