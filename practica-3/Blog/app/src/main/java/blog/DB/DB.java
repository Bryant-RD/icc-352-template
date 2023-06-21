package blog.DB;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blog.encapsulaciones.Article;
import blog.encapsulaciones.Comment;
import blog.encapsulaciones.Tag;
import blog.encapsulaciones.User;

public class DB {

    ArrayList<User> users;
    ArrayList<Comment> comments;
    ArrayList<Tag> tags;
    ArrayList<Article> articles;
    private static DB db = null;
	static Long countArticles = 0L;


	ArrayList<Tag> auxtags = new ArrayList<Tag>();


    public DB() {
        users = new ArrayList<User>();
        articles = new ArrayList<Article>();


    }

    public static DB initDB() {
        if(db == null) {
			db = new DB();
		}
		return db;
    }


	public Article getJustArticleByid(Long id) {

		Article article = null;
		Boolean encontrado = false;
		int i = 0;

        while(!encontrado && i < articles.size()) {
			if(articles.get(i).getId().equals(id)) {
				article = articles.get(i);
				encontrado = true;
			}
			i++;
		}

		return article;
	}

	public User getUserById (String userId) {

		User user = null;
        boolean encontrado = false;
        ArrayList<User> users =  DB.initDB().getUsers();

        int i = 0;
        while(!encontrado && i < users.size()) {
			if(users.get(i).getUserId().equalsIgnoreCase(userId)) {
				user = users.get(i);
				encontrado = true;
			}
            i++;
		}

		return user;

	} 

	public User createUser(User user) {

		User aux = getUserByUserName(user.getUsername());

		if (aux == null) {
			users.add(user);
			return user;
		} else {
			return null;
		}
		
	}
	public User getUserByUserName(String username) {
		User user = null;
		boolean encontrado = false;
		int i = 0;
	
		while (!encontrado && i < users.size()) {
			if (users.get(i).getUsername().equalsIgnoreCase(username)) {
				user = users.get(i);
				encontrado = true;
			}
			i++;
		}
	
		return user;
	}
	
	public ArrayList<Article> getArticlesByAutorId(String userId) {
		ArrayList<Article> articlesByAutor = new ArrayList<>();


		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getAutor().getUserId().equalsIgnoreCase(userId)) {
				articlesByAutor.add(articles.get(i));
			}
		}

		return articlesByAutor;
	}

	

	public User validatUser(String username, String password) {

		User user = getUserByUserName(username);
		if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
			return user;
		} else {
			return null;
		}
	}





	public ArrayList<Tag> removeDuplicateTags(List<Tag> vieja, List<Tag> neuva) {
        ArrayList<Tag> result = new ArrayList<>(vieja);

        for (Tag tag : neuva) {
            boolean isDuplicate = false;
            for (Tag existingTag : vieja) {
                if (tag.getEtiqueta().equals(existingTag.getEtiqueta())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                result.add(tag);
            }
        }

        return result;
    }







    public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}


    public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}


}
