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
