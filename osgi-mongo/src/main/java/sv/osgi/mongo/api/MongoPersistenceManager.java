package sv.osgi.mongo.api;

public interface MongoPersistenceManager<T extends Object> {

	T persist(T entity);

	T update(T entity);

	T get(Class<T> clazz, final T id);

	long count(Class<T> clazz);

}
