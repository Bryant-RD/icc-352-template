package blog.controllers;

import blog.DB.GestionDb;
import blog.encapsulaciones.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ChatController {

    private static GestionDb<Chat> gestionDb = GestionDb.getInstance(Chat.class);

    public static void createChat(Chat chat) {
        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = null;
        
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(chat);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
