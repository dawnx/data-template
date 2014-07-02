package dawn.dt.template;

import java.util.ArrayList;
import java.util.List;

import dawn.dt.TemplateBuilder;

/**
 * 存储类的定义信息
 * @author huangxiao
 */
public class CodeDescription {

	private List<DTField> filedList = new ArrayList<DTField>();
	private String className;
	public static final String CRLF = "\r\n";

	public void addFiled(String type, String name, String comment) {

		DTField f = new DTField();
		f.type = type;
		f.name = name;
		f.comment = comment;
		filedList.add(f);
	}

	public String toClassString() {
		StringBuilder sb = new StringBuilder();
		// package
		if(TemplateBuilder.config._package != null && TemplateBuilder.config._package != ""){
			sb.append("package ");
			sb.append(TemplateBuilder.config._package);
			sb.append(";");
			sb.append(CRLF);
			sb.append(CRLF);
		}
		
		// class
		sb.append("public class ");
		sb.append(className);
		sb.append(" {");
		sb.append(CRLF);
		sb.append(CRLF);

		// TODO import
		
		for (DTField f : filedList) {

			/** 优先使用内置类型的信息，如果不是内置类型，直接使用文件中的字符信息 */
			BuiltInType ft = BuiltInType.parse(f.type);
			String declareType = f.type;
			String name = f.name;
			String value = "null";
			if (ft != null) {
				declareType = ft.getDeclareType();
				value = ft.getNormalValue();
			}

			sb.append("    public " + declareType + " " + name + " = " + value + ";");

			if (f.comment != null) {
				sb.append("//" + f.comment);
			}
			sb.append(CRLF);
		}

		sb.append("}");

		return sb.toString();
	}

	class DTField {
		String type;
		String name;
		String comment;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
