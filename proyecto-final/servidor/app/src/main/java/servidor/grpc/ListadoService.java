package servidor.grpc;

import formulariorn.FormularioRn;
import formulariorn.FormularioRn.*;
import formulariorn.ServicioListadoGrpc.ServicioListadoImplBase;
import io.grpc.stub.StreamObserver;
// import servidor.entidades.Coordenadas;
import servidor.entidades.Encuesta;
import servidor.servicios.EncuestaService;

import java.util.*;
// import java.util.List;

public class ListadoService extends ServicioListadoImplBase {
    

    public void listarFormulario (FormularioRn.ListarFormulariosRequest request, StreamObserver<FormularioRn.ListarFormulariosResponse> responseObserver) {
        try {

            List<Encuesta> listaEncuestas = EncuestaService.getInstancia().listarEncuestas();
            List<FormularioRn.Formulario> listaFormularios = new ArrayList<>();

            if (!listaEncuestas.isEmpty()) {

                for (Encuesta encuesta : listaEncuestas) {
                    Coordenadas coordenadasForm = coordenadasForm = Coordenadas.newBuilder()
                        .setLatitud(encuesta.getCoordenadas().getLatitud())
                        .setLongitud(encuesta.getCoordenadas().getLatitud())
                        .build();
                        
                    Formulario formularioCreado = Formulario.newBuilder()
                    .setIdEncuesta(encuesta.getIdEncuesta())
                    .setIdEncuestador(encuesta.getIdEncuestador())
                    .setNombre(encuesta.getNombre())
                    .setSector(encuesta.getSector())
                    .setNivel(encuesta.getNivel())
                    .setCoordenadas(coordenadasForm)
                    .setFoto("esto es una foto")
                    .build();

                    listaFormularios.add(formularioCreado);
                }

                ListarFormulariosResponse.Builder responseBuilder = ListarFormulariosResponse.newBuilder();
                responseBuilder.addAllFormularios(listaFormularios);

                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    
}