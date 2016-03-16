package dao;

import java.util.ArrayList;
import java.util.List;
import jdbc.JdbcUtils;
import domin.PictureType;
import service.PictureTypeService;

public class PictureTypeDao implements PictureTypeService {

	@Override
	public List<PictureType> getTypes() {
		// TODO Auto-generated method stub

		List<PictureType> list = new ArrayList<PictureType>();
		List<Object> params = null;
		String sql = "SELECT picturekind.picturetype FROM picturekind";
		list = JdbcUtils.findMoreRefResult(sql, params, PictureType.class);
		return list;

	}

}
