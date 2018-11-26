package json.gson.readerParser;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Reader  Json Class
 * 全部输出Json数据
 */
public class JsonParser_JsonTestDomS {
    public static void main(String[] args) {
        String submitState = "{\n" +
                "    \"htop\": 14,\n" +
                "    \"steps\": [\n" +
                "        {\n" +
                "            \"qid\": \"p1\",\n" +
                "            \"type\": 2,\n" +
                "            \"defid\": \"p1\",\n" +
                "            \"loaded\": false,\n" +
                "            \"updates\": {},\n" +
                "            \"questions\": [\n" +
                "                {\n" +
                "                    \"qid\": \"Q1p\",\n" +
                "                    \"defid\": \"Q1p\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"qid\": \"Q2p\",\n" +
                "                    \"defid\": \"Q2p\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"qid\": \"Q3p\",\n" +
                "                    \"defid\": \"Q3p\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"qid\": \"Q3\",\n" +
                "            \"type\": 204,\n" +
                "            \"defid\": \"Q4\",\n" +
                "            \"loaded\": false,\n" +
                "            \"updates\": {},\n" +
                "            \"questions\": [\n" +
                "                {\n" +
                "                    \"qid\": \"Q6b\",\n" +
                "                    \"defid\": \"Q6b\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"qid\": \"Q7b\",\n" +
                "                    \"defid\": \"Q7b\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"qid\": \"Q8b\",\n" +
                "                    \"defid\": \"Q8b\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"qid\": \"Q9b\",\n" +
                "                    \"defid\": \"Q9b\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"qid\": \"Q5\",\n" +
                "            \"type\": 204,\n" +
                "            \"defid\": \"Q4\",\n" +
                "            \"loaded\": false,\n" +
                "            \"updates\": {},\n" +
                "            \"questions\": [\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String answeredQuestions = "[{\"qid\":\"p1\"},{\"qid\":\"Q3\"},{\"qid\":\"Q5\"}]";
        String jsonStringValue = "[]";

        //创建JsonParser对象
        JsonParser parser = new JsonParser();
        JsonArray jsonAnsweredQuestions = (JsonArray) parser.parse(answeredQuestions);
        JsonArray returnJsonString = (JsonArray) parser.parse(jsonStringValue);

        JsonObject object = (JsonObject) parser.parse(submitState);
        JsonArray stepsArray = object.getAsJsonArray("steps");

        for (int j = 0; j < jsonAnsweredQuestions.size(); j++) {
            String qidAnsweredQuestions = jsonAnsweredQuestions.get(j).getAsJsonObject().get("qid").getAsString();

            System.out.println(qidAnsweredQuestions);
            for (int k = 0; k < stepsArray.size(); k++) {
                String stepsQid = stepsArray.get(k).getAsJsonObject().get("qid").getAsString();

                if (stepsQid.equals(qidAnsweredQuestions)) {
                    JsonArray questionsArray = stepsArray.get(k).getAsJsonObject().getAsJsonArray("questions");
                    if (questionsArray.size() > 0) {
                        for (int i = 0; i < questionsArray.size(); i++) {
                            JsonObject qidList = questionsArray.get(i).getAsJsonObject();
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("qid", qidList.get("qid").getAsString());
                            returnJsonString.add(jsonObject);
                        }
                    } else {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("qid", qidAnsweredQuestions);
                        returnJsonString.add(jsonObject);
                    }
                }

            }
        }
        System.out.println(returnJsonString.toString());
    }

}

