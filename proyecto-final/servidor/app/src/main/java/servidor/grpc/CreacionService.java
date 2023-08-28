package servidor.grpc;


import formulariorn.FormularioRn;
import formulariorn.ServicioCreacionGrpc.ServicioCreacionImplBase;
import io.grpc.stub.StreamObserver;
import servidor.entidades.Encuesta;
import servidor.servicios.EncuestaService;
import servidor.entidades.Coordenadas;

public class CreacionService extends ServicioCreacionImplBase {
 
    // @Override
    public void crearFormulario(FormularioRn.CrearFormularioRequest request, StreamObserver<FormularioRn.CrearFormularioResponse> responseObserver) {

        Coordenadas coordenadas = new Coordenadas(request.getFormulario().getCoordenadas().getLatitud(), request.getFormulario().getCoordenadas().getLongitud());

        Encuesta encuesta = new Encuesta(request.getFormulario().getIdEncuesta(), request.getFormulario().getIdEncuestador(), request.getFormulario().getNombre(), request.getFormulario().getSector(), request.getFormulario().getNivel(), coordenadas , request.getFormulario().getFoto());

        try {
            EncuestaService.getInstancia().crearEncuesta(encuesta);
            FormularioRn.CrearFormularioResponse response = FormularioRn.CrearFormularioResponse.newBuilder()
                .setFormulario(request.getFormulario()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
