package base.dao;

public interface BaseDao<T> {
	T get(int id);

	int insert(T type);

	boolean update(T type);

	void delete(T type);
}
