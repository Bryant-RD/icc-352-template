export const URL = "https://app.brtecnology.me:8080"

export const getRegistros = async () => {
    try {
        const response = await fetch(`/registros`);
        const data = await response.json();
        console.log(data);
        return data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

export const getRegistrobyId = async ( id ) => {
    console.log("la buena " + id);
    try {
        const response = await fetch(`/registro/${id}`);
        const data = await response.json();
        return data;
    } catch (e) {
        console.log(e)
        return null;
    }
}


export const createRegistro = async (registro) => {
    try {
        const response = await fetch(`/create-registro`, {
            method: "POST",
            body: JSON.stringify(registro),
            headers: {
                'Content-type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();
            return data;
          } else {
            throw new Error('Error en la solicitud');
          }

        
    } catch (error) {
        console.log(error);
    }
}

export const deleteRegistroById = async (id) => {
    try {
        const response = await fetch(`/delete-registro/${id}`, {
            method: "DELETE",
            headers: {
                'Content-type': 'application/json'
            }
        });

        if (response.ok) {
            alert("REgistro eliminado correctamente.");
        } else {
            console.log("HTTP request unsuccessful");
        }

        const data = await response.json();
        console.log(data);
    } catch (error) {
        console.log(error);
    }
};

export const updateRegistroById = async (id, obj) => {
    try {
        const response = await fetch(`/update-registro/${id}`, {
            method: "PUT",
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(obj)
        });

        if (response.ok) {
            alert("registro editado correctamente.");
        } else {
            console.log("HTTP request unsuccessful");
        }

    } catch (error) {
        console.log(error);
    }
}
