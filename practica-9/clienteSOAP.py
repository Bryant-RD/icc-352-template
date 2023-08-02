from suds.client import Client


url = "http://localhost:7000/ws/EstudianteWebServices?wsdl"
client = Client(url)

def listarEstudiantes():
    lista = client.service.getListaEstudiante()
    print("\nLista de Estudiantes:")
    for estudiante in lista:
        print(estudiante)
    print()

def consultarEstudiante():
    matricula = int(input("\nIngrese la matrícula del estudiante a consultar: "))
    estudiante = client.service.getEstudiante(matricula)
    print("Información del Estudiante:")
    print(estudiante)
    print()

def crearEstudiante():
    matricula = int(input("\nIngrese la matrícula del nuevo estudiante: "))
    nombre = input("Ingrese el nombre del nuevo estudiante: ")
    carrera = input("Ingrese la carrera del nuevo estudiante: ")

    estud2 = client.factory.create("estudiante")
    estud2.matricula = matricula
    estud2.nombre = nombre
    estud2.carrera = carrera

    client.service.crearEstudiante(estud2)
    print("Nuevo estudiante creado exitosamente.")
    print()


def eliminarEstudiante():
    matricula = int(input("\nIngrese la matrícula del estudiante a borrar: "))
    resultadodel = client.service.eliminarEstudiante(matricula)
    if resultadodel == True :
        print("Estudiante eliminado exitosamente.")
    print()


while True:
    print("\nMenú de opciones:")
    print("1. Listar Todos los estudiantes.")
    print("2. Consultar Estudiante.")
    print("3. Crear un nuevo estudiante.")
    print("4. Borrar un estudiante.")
    print("0. Salir.")
    opcion = input("Ingrese el número de opción deseada: ")

    if opcion == "1":
        listarEstudiantes()
    elif opcion == "2":
        consultarEstudiante()
    elif opcion == "3":
        crearEstudiante()
    elif opcion == "4":
        eliminarEstudiante()
    elif opcion == "0":
        print("Cerrando...")
        break
    else:
        print("Opción inválida. Por favor, ingrese una opción válida.")
