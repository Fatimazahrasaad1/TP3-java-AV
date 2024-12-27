
package DAO;

		import java.util.List;

public interface GenericDAOI<T> {
	void ajouter(T entity);
	List<T> afficher();
	boolean modifier(int id, T entity);  // Méthode modifiée
	void supprimer(int id);
}
