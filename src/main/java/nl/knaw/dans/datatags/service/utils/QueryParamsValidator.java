package nl.knaw.dans.datatags.service.utils;


import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import java.util.logging.Logger;

//This has been made a singleton because the schema validator only needs to exist once
//and loads very slowly
@Component
public class QueryParamsValidator {

    private static final Logger logger = Logger.getLogger(QueryParamsValidator.class.getCanonicalName());
    private static QueryParamsValidator dtvSingleton;

    private QueryParamsValidator() {}
    
    static {
        dtvSingleton = new QueryParamsValidator();
    }
    
    public static QueryParamsValidator getInstance() {
        return dtvSingleton;
    }


    public boolean isDataTagValid(String jsonInput) {
        try {
            schema.validate(new JSONObject(jsonInput)); // throws a ValidationException if this object is invalid
        } catch (ValidationException vx) {
            logger.info("Prov schema error : " + vx); //without classLoader is blows up in actual deployment
            return false;
        } catch (Exception ex) {
            logger.info("Prov file error : " + ex);
            return false;
        }

        return true;
    }
    public String getPrettyJsonString(JsonObject jsonObject) {
       return "";
    }


    private static final String provSchema ="{\n" +
            "  \"definitions\": {},\n" +
            "  \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
            "  \"$id\": \"http://example.com/root.json\",\n" +
            "  \"type\": \"object\",\n" +
            "  \"title\": \"The Root Schema\",\n" +
            "  \"required\": [\n" +
            "    \"sourceUrl\",\n" +
            "    \"expiredDateTime\",\n" +
            "    \"apiKey\",\n" +
            "    \"persistentId\",\n" +
            "    \"version\",\n" +
            "    \"fileIds\"\n" +
            "  ],\n" +
            "  \"properties\": {\n" +
            "    \"sourceUrl\": {\n" +
            "      \"$id\": \"#/properties/sourceUrl\",\n" +
            "      \"type\": \"string\",\n" +
            "      \"title\": \"The Sourceurl Schema\",\n" +
            "      \"default\": \"\",\n" +
            "      \"examples\": [\n" +
            "        \"https://localhost\"\n" +
            "      ],\n" +
            "      \"pattern\": \"^(.*)$\"\n" +
            "    },\n" +
            "    \"expiredDateTime\": {\n" +
            "      \"$id\": \"#/properties/expireDateTime\",\n" +
            "      \"type\": \"string\",\n" +
            "      \"title\": \"The Expireddatetime Schema\",\n" +
            "      \"default\": \"\",\n" +
            "      \"examples\": [\n" +
            "        \"2020-02-27 22:21:01\"\n" +
            "      ],\n" +
            "      \"pattern\": \"^(.*)$\"\n" +
            "    },\n" +
            "    \"apiKey\": {\n" +
            "      \"$id\": \"#/properties/apiKey\",\n" +
            "      \"type\": \"string\",\n" +
            "      \"title\": \"The Apikey Schema\",\n" +
            "      \"default\": \"\",\n" +
            "      \"examples\": [\n" +
            "        \"ada3a3a8-dceb-450a-b69a-b543ffbd6188\"\n" +
            "      ],\n" +
            "      \"pattern\": \"^(.*)$\"\n" +
            "    },\n" +
            "    \"persistentId\": {\n" +
            "      \"$id\": \"#/properties/persistentId\",\n" +
            "      \"type\": \"string\",\n" +
            "      \"title\": \"The Persistentid Schema\",\n" +
            "      \"default\": \"\",\n" +
            "      \"examples\": [\n" +
            "        \"doi:10.5072/dans-eko-akmi\"\n" +
            "      ],\n" +
            "      \"pattern\": \"^(.*)$\"\n" +
            "    },\n" +
            "    \"version\": {\n" +
            "      \"$id\": \"#/properties/version\",\n" +
            "      \"type\": \"string\",\n" +
            "      \"title\": \"The Version Schema\",\n" +
            "      \"default\": \"\",\n" +
            "      \"examples\": [\n" +
            "        \"DRAFT\"\n" +
            "      ],\n" +
            "      \"pattern\": \"^(.*)$\"\n" +
            "    },\n" +
            "    \"fileIds\": {\n" +
            "      \"$id\": \"#/properties/fileIds\",\n" +
            "      \"type\": \"array\",\n" +
            "      \"title\": \"The Fileids Schema\",\n" +
            "      \"items\": {\n" +
            "        \"$id\": \"#/properties/fileIds/items\",\n" +
            "        \"type\": \"integer\",\n" +
            "        \"title\": \"The Items Schema\",\n" +
            "        \"default\": 0,\n" +
            "        \"examples\": [\n" +
            "          10,\n" +
            "          12\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static JSONObject rawSchema = null;

    static {
        try {
            rawSchema = new JSONObject(new JSONTokener(provSchema));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final Schema schema = SchemaLoader.load(rawSchema);

}
