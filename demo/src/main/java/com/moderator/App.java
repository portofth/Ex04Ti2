package com.moderator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class App {

    private static final String ENDPOINT = "https://moderacaotesteti2.cognitiveservices.azure.com/";
    private static final String API_KEY = "2x6sTwCmJLoGjYbpWKnfePSbkco6Qv95Zu4WWJV9CVwXzrMiJqDIJQQJ99BEACHYHv6XJ3w3AAAHACOGX7YG";

    private static String sendToAzure(String text) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"text\": \"" + text.replace("\"", "\\\"") + "\" }";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ENDPOINT + "/contentsafety/text:analyze?api-version=2023-10-01"))
            .header("Ocp-Apim-Subscription-Key", API_KEY)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        for(int i=0;i<10;i++)  // loop para fazer testes 
        { 
            System.out.println("Digite um texto para verificar se vai ser censurado:");
            String input = scanner.nextLine();
        
            String response = sendToAzure(input);
        
            
        
            // Detecta a severidade da imagem de acordo com o Azure
            if (response.matches(".*\"severity\":\\s*[3-7].*")) {
                System.out.println("*** CENSURADO!!!!!! CONTEÚDO INAPROPRIADO ***");
            } else {
                System.out.println("Parabens,você nao foi censurado:");
                System.out.println("A Mensagem " + input);
            }
        }

        }

        
      
    

   
}
