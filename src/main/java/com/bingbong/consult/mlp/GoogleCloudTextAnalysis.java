package com.bingbong.consult.mlp;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.ModerateTextResponse;

import java.io.IOException;

public class GoogleCloudTextAnalysis {

    public static void analyze(String text) throws IOException {
        // Instantiates a client
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            // The text to analyze
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            ModerateTextResponse moderateTextResponse = language.moderateText(doc);
//            moderateTextResponse.getAllFields().forEach((k, v) -> System.out.println(k + " : " + v));
        }
    }
}
