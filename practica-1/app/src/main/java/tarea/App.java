/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tarea;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import io.javalin.Javalin;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static int countEtiqueta(String etiqueta, String text) {
        String[] parts = text.split(etiqueta);
        return parts.length - 1;
    }
    
    public static int countMetod(String metodo, String text) {

        int count = 0;
        String[] parts = text.split("<form");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains("method=\"" + metodo + "\"")) {
                count++; 
            }
        }
        return count;

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

            System.out.println("Ingrese una URL: ");
            String urlStr = scanner.nextLine();

            try {
                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(urlStr))
                        .GET()
                        .build();

                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                int statusCode = httpResponse.statusCode();
                String contentType = httpResponse.headers().firstValue("Content-Type").orElse("Desconocido");

                if (statusCode == 200) {
                    String responseBody = httpResponse.body();
                    System.out.println("Contenido:\n" + responseBody);

                    System.out.println("tipo de contenido: " + contentType);

                    if (contentType.contains("html")) {
                        System.out.println("Este documento es un HTML");

                        String[] lines = responseBody.split("\r\n|\r|\n");
                        System.out.println("Cantidad de lineas: " + lines.length);
 
                        System.out.println("Cantidad de imagenes <img />: " + countEtiqueta("<img", responseBody));

                        if (countEtiqueta("<form", responseBody) != 0) {
                            System.out.println("Cantidad de formularios con metodo post: " + countMetod("post", responseBody));
                            System.out.println("Cantidad de formularios con metodo get: " + countMetod("get", responseBody));
                            System.out.println("Inputs con sus types: ");

                            PrintInputTags(responseBody);

                            if (countMetod("post", responseBody) != 0) {
                                String params = "?asignatura=practrica1";
                            
                                Map<String, String> headersMap = new HashMap<>();
                                headersMap.put("Content-Type", "application/x-www-form-urlencoded");
                                headersMap.put("matricula-id", "10143453");
                            
                                HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                                    .uri(URI.create("http://localhost:8080/parametros"))
                                    .POST(HttpRequest.BodyPublishers.ofString(params));
                                headersMap.forEach(requestBuilder::header);
                            
                                HttpRequest httpRequestPost = requestBuilder.build();
                            
                                // Enviar la solicitud POST y obtener la respuesta
                                HttpResponse<String> httpResponsePost = httpClient.send(httpRequestPost, BodyHandlers.ofString());
                                if (httpResponsePost.statusCode() == 200) {
                                    System.out.println("Solicitud enviada correctamente");
                                }
                            }
                                                
                        }
                    }
                } else {
                    // ctx.result("URL inválida. Código de respuesta: " + statusCode);
                    System.out.println(" URL invalida " + statusCode);
                }
            } catch (Exception e) {
                System.out.println("URL inválida. Error: " + e.getMessage());
            } finally {
                scanner.close();
            }

    }

    private static void PrintInputTags(String responseBody) {
        Pattern pattern = Pattern.compile("<input[^>]*>");
        Matcher matcher = pattern.matcher(responseBody);
    
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
