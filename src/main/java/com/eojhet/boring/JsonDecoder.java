package com.eojhet.boring;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JsonDecoder {
    String jsonObject;
    JSONParser parser = new JSONParser();
    Object obj;
    JSONObject boringData;

    public JsonDecoder(String jsonObject) {
        this.jsonObject = jsonObject;

        try {
            obj = parser.parse(jsonObject);
            boringData = (JSONObject) obj;

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    public ArrayList<Float> getDepths() {
        ArrayList<Float> depthsArray = new ArrayList<>();
        JSONArray objectArray = (JSONArray) boringData.get("depths");

        for (Object depth : objectArray) {
            depthsArray.add(Float.parseFloat(depth.toString()));
        }

        return depthsArray;
    }

    public ArrayList<String> getTypes() {
        ArrayList<String> typesArray = new ArrayList<>();
        JSONArray objectArray = (JSONArray) boringData.get("types");

        for (Object type : objectArray) {
            typesArray.add(type.toString());
        }

        return typesArray;
    }

    public static void main(String[] args) throws ParseException {
        String jsonObject = "{\"label\":\"MW-1\",\"logBy\":\"Joe Gonzalez\",\"company\":\"Bay Environmental inc.\",\"location\":\"2048 Nags Head Rd\",\"equip\":\"Hand Auger\",\"date\":\"2022-03-17\",\"time\":\"10:30\",\"depths\":[\"1\",\"3\",\"8\",\"12\",\"16\"],\"types\":[\"topSoil\",\"sandyClay\",\"clay\",\"sand\",\"siltySand\"],\"descriptions\":[\"Top soil\",\"Sandy clay\",\"Dark red clay\",\"Beige sand\",\"Light beige silty sand\"]}";

        JsonDecoder decode = new JsonDecoder(jsonObject);
        ArrayList<Float> depths = decode.getDepths();
        ArrayList<String> types = decode.getTypes();

        for (Float depth : depths) {
            System.out.println(depth);
        }

        for (String type : types) {
            System.out.println(type);
        }

    }

}
