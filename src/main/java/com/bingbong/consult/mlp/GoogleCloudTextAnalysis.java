package com.bingbong.consult.mlp;

import com.google.cloud.language.v1.ClassificationCategory;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.ModerateTextResponse;
import com.google.gson.Gson;
import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.*;

public class GoogleCloudTextAnalysis {

    public Map<String, Float> analyze(String text) throws IOException {
        Map<String, Float> map = new HashMap<>();
        List<String> categories = Arrays.asList("Toxic", "Insult", "Profanity", "Derogatory", "Sexual", "Violent");
        // Instantiates a client
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            // The text to analyze
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            ModerateTextResponse moderateTextResponse = language.moderateText(doc);
            for (ClassificationCategory classificationCategory : moderateTextResponse.getModerationCategoriesList()) {
                categories.stream().forEach(category -> {
                    if (classificationCategory.getName().equals(category)) {
                        map.put(category, classificationCategory.getConfidence());
                    }
                });
            }
            for (String s : map.keySet()) {
                System.out.println(s + " : " + map.get(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}


