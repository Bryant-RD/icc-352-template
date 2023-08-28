package servidor.grpc;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class gRPCServidor {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8081)
            .addService(new ListadoService())
            .addService(new CreacionService())
            .build();

            server.start();
            System.out.println("\n\n servidor iniciado en e puerto: " + server.getPort() + "\n\n");


    }
}
