package base.dao;

public interface BaseDao<T> {
	T get(String id);

	int insert(T type);

	boolean update(T type);

	void delete(T type);
}
