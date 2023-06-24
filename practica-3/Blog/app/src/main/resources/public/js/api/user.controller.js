export const getUsers = async () => {
  try {
      const response = await fetch('/users');
      const data = await response.json();
      return data;
  } catch (error) {
      console.log(error);
      return [];
  }
}

export const getUserById = async (id) => {
try {
  const response = await fetch(`/user/${id}`)
  const data = await response.json();
  return data

} catch (error) {
  console.log(error);
}
}

export const createUser = async (user) => {

try {
    const response = await fetch(`/create-user`, {
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
    const response = await fetch(`/delete-user/${id}`, {
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

export const updateUserById = async (id, obj) => {
try {
  const response = await fetch(`/update-user/${id}`, {
      method: "PUT",
      headers: {
          'Content-type': 'application/json'
      },
      body: JSON.stringify(obj)
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

const fetchProfileData = async () => {
  try {
    const response = await fetch('/profile');
    const data = await response.text();
    return data;

  } catch (error) {
    console.log(error);
  }
};

export const getUserActive = async () => {
  try {
      const userId = await fetchProfileData();
      const response = await fetch(`/user/${userId}`);
      const data = await response.json();
      return data;

    } catch (error) {
      console.log(error);
    }
}

export const getArticlesByUserId = async (id) => {
  try {
    const userId = await fetchProfileData();
    const response = await fetch(`/user-articles/${userId}`);
    const data = await response.json();
    return data;

  } catch (error) {
    console.log(error);
  }
}

export const LogOut = async () => {
 
  fetch('/logout', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        // Cierre de sesión exitoso, redirigir a la página de inicio de sesión
        window.location.href = '/homeLogOut.html';
      } else {
        // Ocurrió un error al cerrar sesión
        console.log('Error al cerrar sesión');
      }
    })
    .catch(error => {
      console.log('Error al cerrar sesión:', error);
    });
}