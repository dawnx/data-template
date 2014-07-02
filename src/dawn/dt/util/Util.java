package dawn.dt.util;

import java.io.File;

import dawn.dt.TemplateBuilder;

public class Util {

	/**
	 * 根据文件名，获取导出类的名字（不包括全名），类名首字母大
	 * @param file
	 * @return
	 */
	public static String getSimpleClassName(File file) {
		String fileName = file.getName();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		String[] words = name.split("_");
		String className = "";

		for (String w : words) {
			String firstChar = w.substring(0, 1).toUpperCase();
			className += firstChar + w.substring(1);
		}

		className = className + "Template";
		return className;
	}

	/**
	 * 根据文件名，获取生成的java的文件的名字
	 * @param file
	 * @return
	 */
	public static String getCreateFilePath(File file) {
		String path = null;
		if (TemplateBuilder.config._dist_path == null) {
			String filePath = file.getPath();
			path = filePath.substring(0, filePath.lastIndexOf('\\') + 1);
		} else {
			path = TemplateBuilder.config._dist_path;
		}
		return path + getSimpleClassName(file) + ".java";
	}
}
