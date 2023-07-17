import { getUserActive } from "./user.controller.js";

export const getArticles = async (page) => {
    try {
        const response = await fetch(`/articles/page/${page}`);
        const data = await response.json();
        console.log(data);
        return data;
    } catch (error) {
        console.log(error);
        return [];
    }
}

export const getAllTags = async () => {
    try {
        const response = await fetch("/tags");
        const data = await response.json();
        return data;
    } catch (error) {
        console.log(error);
    }
}

export const getArticlebyId = async ( id ) => {
    try {
        const response = await fetch(`/article/${id}`);
        const data = await response.json();
        return data;
    } catch (e) {
        console.log(e)
        return null;
    }
}

export const getArticlesByTag = async (tag) => {
    try {
        const response = await fetch(`/tag/articles/${tag}`);
        const data = await response.json();
        return data;
        
    } catch (error) {
        console.log(error);
    }
}

export const sendComment = async (comentario) => {
    try {
        const response = await fetch(`/create-comment`, {
            method: "POST",
            body: JSON.stringify(comentario),
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

export const getArticlesByUserId = async () => {

    try {
        const userActive = await getUserActive();

        const response = await fetch(`/user-articles/${userActive.userId}`);
        const data = await response.json();
        return data;    
    } catch (error) {
        console.log(error);
    }
}

export const createArticle = async (article) => {
    try {
        const response = await fetch(`/create-article`, {
            method: "POST",
            body: JSON.stringify(article),
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

export const deleteArticleById = async (id) => {
    try {
        const response = await fetch(`/delete-article/${id}`, {
            method: "DELETE",
            headers: {
                'Content-type': 'application/json'
            }
        });

        if (response.ok) {
            alert("Articulo eliminado correctamente.");
        } else {
            console.log("HTTP request unsuccessful");
        }

        const data = await response.json();
        console.log(data);
    } catch (error) {
        console.log(error);
    }
};

export const updateArticleById = async (id, obj) => {
    try {
        const response = await fetch(`/update-article/${id}`, {
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
