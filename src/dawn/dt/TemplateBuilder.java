package dawn.dt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import dawn.dt.data.CsvReader;
import dawn.dt.data.IDataReader;
import dawn.dt.template.CodeDescription;
import dawn.dt.util.Util;

public class TemplateBuilder {

	public static BuilderConfig config;

	static {
		config = new BuilderConfig();
		config._package = "";
		config._reader = new CsvReader();
	}

	public static void main(String... args) throws Exception {
		args = new String[]{"-f:test.csv" , "-p:dawn.dt"}; // TODO del me
		if (args.length == 0) {
			System.err.println("please input file name with args[0]");
			System.exit(0);
		}

		String filePath = null;

		for (String string : args) {
			String[] str = string.split(":");
			if (str.length != 2) {
				System.err.println("arg error:" + string);
				System.exit(0);
			}

			if (str[0].equals("-f")) { // file
				filePath = str[1];
			} else if (str[0].equals("-p")) { // package
				config._package = str[1];
			} else if (str[0].equals("-d")) { // dist_path
				config._dist_path = str[1];
			}
		}

		File file = new File(filePath);
		System.out.println("build start");
		build(file, config._reader);

		System.out.println("-----------------------------");
		System.out.println("build end");
	}

	public static void build(File file, IDataReader reader) throws Exception {
		if (file == null || !file.exists()) {
			System.err.println("file not exist");
			System.exit(0);
		}
		CodeDescription cd = new CodeDescription();
		cd.setClassName(Util.getSimpleClassName(file));
		reader.setFile(file);

		readField(reader, cd);

		write(Util.getCreateFilePath(file), cd);
	}

	private static void readField(IDataReader reader, CodeDescription cd) {
		List<String> comments = reader.getComments();
		List<String> fields = reader.getHeaderFields();

		for (int i = 0; i < fields.size(); i++) {

			String comment = listGet(comments, i);
			String[] type_name = listGet(fields, i).split(":");
			if (type_name.length < 2) {
				continue;
			}
			cd.addFiled(type_name[0], type_name[1], comment);
		}
	}

	private static void write(String filePath, CodeDescription cd) throws UnsupportedEncodingException, IOException {

		FileOutputStream fops = new FileOutputStream(filePath);
		System.out.println(cd.toClassString());
		fops.write(cd.toClassString().getBytes("UTF8"));
		fops.flush();
		fops.close();
	}

	private static String listGet(List<String> list, int idx) {

		if (list == null || list.size() <= idx)
			return "";

		String v = list.get(idx);

		if (v == null)
			return "";

		return v;
	}
}
