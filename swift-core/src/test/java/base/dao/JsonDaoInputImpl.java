package base.dao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonDaoInputImpl extends JsonDao implements BaseDao<Object[][]>{

	JsonDaoInputImpl(String filePath) throws FileNotFoundException {
		super(filePath);
	}

	@Override
	public Object[][] get(String id) {
		List<HashMap<String, String>> inputlist = new Gson().fromJson(getReader(), new TypeToken<ArrayList<HashMap<String, String>>>() {
		}.getType());
		
		System.out.println(inputlist);
		
		Object[][] data = new Object[inputlist.size()][1];
		
		for (int i = 0; i < inputlist.size(); i++) {
			data[i][0] = inputlist.get(i);
		}
		return data;
	}

	@Override
	public int insert(Object[][] type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(Object[][] type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(Object[][] type) {
		// TODO Auto-generated method stub
		
	}

}
