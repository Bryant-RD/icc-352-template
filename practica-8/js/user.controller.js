export const getUsers = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/estudiante/');
        const data = await response.json();
        return data;
    } catch (error) {
        console.log(error);
        return [];
    }
  }
  
  export const getUserById = async (id) => {
  try {
    const response = await fetch(`http://localhost:8080/api/estudiante/${id}`)
    const data = await response.json();
    return data
  
  } catch (error) {
    console.log(error);
  }
  }
  
  export const createUser = async (user) => {
  
  try {
      const response = await fetch(`http://localhost:8080/api/estudiante/`, {
          method: "POST",
          body: JSON.stringify(user),
          headers: {
              'Content-type': 'application/json'
          }
      });
  
      if (response.ok) {
          const data = await response.json();
          console.log(data);
          return data;
        } else {
          throw new Error('Error en la solicitud');
        }
  
      
  } catch (error) {
      console.log(error);
  }
  }
  
  export const deleteUserById = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/estudiante/${id}`, {
          method: "DELETE",
          headers: {
              'Content-type': 'application/json'
          }
      });
  
      if (response.ok) {
          alert("usuario eliminado correctamente.");
      } else {
          console.log("HTTP request unsuccessful");
      }
  
      const data = await response.json();
      console.log(data);
  } catch (error) {
      console.log(error);
  }
  
  }
  
  export const updateUserById = async (obj) => {
    
  try {
    const response = await fetch(`http://localhost:8080/api/estudiante/`, {
        method: "PUT",
        body: JSON.stringify(obj),
        headers: {
            'Content-type': 'application/json'
        }
    });
  
    if (response.ok) {
        alert("Articulo editado correctamente.");
    } else {
        console.log("HTTP request unsuccessful");
    }
  
    const data = await response.json();
    return data
  } catch (error) {
    console.log(error);
  }
  }
  

  