package servidor.grpc;

import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import formulariorn.FormularioRn;
import formulariorn.ServicioCreacionGrpc;
import formulariorn.ServicioListadoGrpc;
import formulariorn.FormularioRn.Formulario;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import servidor.entidades.Usuario;
import servidor.servicios.UsuarioService;

public class gRPCCliente {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();

        ServicioListadoGrpc.ServicioListadoBlockingStub stubListado = ServicioListadoGrpc.newBlockingStub(channel);
        ServicioCreacionGrpc.ServicioCreacionBlockingStub stubCreacion = ServicioCreacionGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        String opcion = "";

        while (continuar) {

            System.out.println("\n\n------------------------------------------------------");
            System.out.println("SERVICIO GRPC");
            System.out.println("\tOpciones: ");
            System.out.println("1. Listado de los formularios publicados por un usuario");
            System.out.println("2. Creacion de formulario");
            System.out.println("3. Salir");

            System.out.println("\nDigita la opcion: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    System.out.println("Listado de los formularios publicados por un usuario");
                    System.out.println("\nDigite el nombre de usuario de los formularios que desea visiualizar: ");
                    String user = scanner.nextLine();
                    Usuario usuario = UsuarioService.getInstancia().getUsuarioPorUsername(user);

                    // Obtener listado de URLs para un usuario
                    FormularioRn.ListarFormulariosRequest requestListado = FormularioRn.ListarFormulariosRequest.newBuilder().setUsernameAgente(usuario.getUserName()).build();
                    FormularioRn.ListarFormulariosResponse responseListado = stubListado.listarFormularios(requestListado);

                    String usuario_request_listado = requestListado.getUsernameAgente();

                    System.out.println("Listado de Formularios para el usuario " + usuario_request_listado + ":");

                    // Creamos una instancia de Gson
                    Gson gson = new Gson();

                    List<Formulario> formularioList = responseListado.getFormulariosList();

                    // // Convertimos la cadena JSON a una lista de objetos Acortador
                    // Type tipoListaForm = new TypeToken<List<FormularioRn.Formulario>>() {
                    // }.getType();
                    // List<FormularioRn.Formulario> formularioList = gson.fromJson(json, tipoListaForm);

                    for (FormularioRn.Formulario form : formularioList) {
                        System.out.println("----------------------------");
                        System.out.println("ID Encuesta: " + form.getIdEncuesta());
                        System.out.println("ID Encuestador: " + form.getIdEncuestador());
                        System.out.println("Nombre: " + form.getNombre());
                        System.out.println("Sector: " + form.getSector());
                        System.out.println("Nivel Escolar: " + form.getNivel());
                        System.out.println("Foto: " + form.getFoto() != null ? "VERDADERO" : "FALSO");
                        System.out.println("----------------------------\n");
                    }

                }
                case "2" -> {
                    System.out.println("\nCrear Formulario");

                    System.out.println("Nombre Completo: ");
                    String name = scanner.nextLine();

                    System.out.println("Sector: ");
                    String sector = scanner.nextLine();

                    System.out.println("Nivel academico:" + "\n" + "-Basico" + "\n" + "-Medio" + "\n" + "-Universitario"
                            + "\n" + "-Posgrado" + "\n" + "-Doctorado");
                    String level = scanner.nextLine();

                    // Crear solicitud para el servicio de creación de URLs
                    // JsonObject jsonRequestCreacion = new JsonObject();
                    // jsonRequestCreacion.addProperty("name", name);
                    // jsonRequestCreacion.addProperty("level", level);
                    // jsonRequestCreacion.addProperty("sector", sector);
                    // jsonRequestCreacion.addProperty("usuario", "invitado");
                    // jsonRequestCreacion.addProperty("imagenBase64", "");
                    // jsonRequestCreacion.addProperty("longitud", "");
                    // jsonRequestCreacion.addProperty("latitud", "");


                    Gson gson2 = new Gson();
                    // String json2 = gson2.toJson(jsonRequestCreacion);
                    FormularioRn.CrearFormularioRequest requestCreacion = FormularioRn.CrearFormularioRequest.newBuilder().setJson(json2).build();

                    // Realizar llamada al servicio de creación de URLs
                    FormularioRn.CrearFormularioResponse responseCreacion = stubCreacion.crearFormulario(requestCreacion);

                    System.out.println("Formulario creado");

                }
                case "3" -> {
                    continuar = false;
                    break;
                }
                default -> System.out.println("Opción inválida, selecciona una opción del 1 al 3");
            }

        }
    }

}