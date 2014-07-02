package dawn.dt;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dawn.dt.data.CsvReader;
import dawn.dt.data.DataParser;
import dawn.dt.template.BuiltInType;

public class DataLoader {

	private static Logger log = LoggerFactory.getLogger(DataLoader.class);

	public static LoaderConfig config;

	public static Map<String, DataParser> pluginMap = new HashMap<String, DataParser>();

	static {
		config = new LoaderConfig();
		config._reader = new CsvReader();
	}

	public static void addPlugin(DataParser ds) {
		pluginMap.put(ds.getParserName(), ds);
	}

	public static DataParser getPlugin(String parserName) {
		DataParser dp = BuiltInType.parse(parserName);
		if (dp == null)
			dp = pluginMap.get(parserName);
		return dp;
	}

	public static <T> List<T> load(Class<T> c, File file) {

		List<T> dataList = new ArrayList<T>();

		config._reader.setFile(file);
		List<String> headers = config._reader.getHeaderFields();

		while (config._reader.hasNext()) {

			List<String> row = config._reader.next();
			T obj = null;
			try {
				obj = c.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0); // XXX
			}

			boolean isNullRow = true;
			for (int i = 0; i < headers.size(); i++) {
				String[] type_name_parser = headers.get(i).split(":");
				if (type_name_parser.length < 2) {
					continue;
				}
				if (i >= row.size())
					break;

				String cell = row.get(i);
				if (cell == null)
					continue;
				if (setValue(obj, type_name_parser, cell, i, row)) {
					isNullRow = false;
				}
			}

			if (!isNullRow) {
				dataList.add(obj);
			}
		}
		return dataList;
	}

	private static boolean setValue(Object obj, String[] type_name_parser, String cellVal, int cellCol, List<String> row) {
		try {

			String parseName = null;
			if (type_name_parser.length == 3)
				parseName = type_name_parser[0];
			else
				parseName = type_name_parser[2];

			DataParser dp = getPlugin(parseName);
			if (dp == null) {
				log.info("DataParser {} not found", type_name_parser[0]);
				return false;
			}

			Field f = obj.getClass().getDeclaredField(type_name_parser[1]);
			Object data = dp.parse(cellVal, cellCol, row);
			f.set(obj, data);
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}
}
