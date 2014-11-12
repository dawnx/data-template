package dawn.dt.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dawn.dt.data.DataParser;

public enum BuiltInType implements DataParser {
	_boolean("boolean", "false") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				boolean v = val.equals("1");
				return v;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	},
	_booleanArray("boolean[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				boolean[] vals = new boolean[valArray.length];
				for (int i = 0; i < valArray.length; i++) {
					vals[i] = valArray[i].equals("1");
				}
				return vals;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_int("int", "0") {
		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_intArray("int[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				int[] vals = new int[valArray.length];
				for (int i = 0; i < valArray.length; i++) {
					vals[i] = Integer.parseInt(valArray[i]);
				}
				return vals;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_float("float", "0.0f") {
		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				return Float.parseFloat(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_floatArray("float[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				float[] vals = new float[valArray.length];
				for (int i = 0; i < valArray.length; i++) {
					vals[i] = Float.parseFloat(valArray[i]);
				}
				return vals;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_double("double", "0.0") {
		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				return Double.parseDouble(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_doubleArray("double[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				double[] vals = new double[valArray.length];
				for (int i = 0; i < valArray.length; i++) {
					vals[i] = Double.parseDouble(valArray[i]);
				}
				return vals;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_long("long", "0L") {
		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				return Long.parseLong(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_longArray("long[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				long[] vals = new long[valArray.length];
				for (int i = 0; i < valArray.length; i++) {
					vals[i] = Long.parseLong(valArray[i]);
				}
				return vals;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_string("String", "null") {
		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				return val;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_StringArray("String[]", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				String[] valArray = val.split(",");
				return valArray;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	},
	_date("Date", "null") {

		@Override
		public Object parse(String val, int col, List<String> row) {
			try {
				if (val == null || val.equals("0") || val.equals(""))
					return null;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dateFormat.parse(val);
				return date;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public String getDeclareType() {
			return "java.util.Date";
		}
	};

	String declareType;
	String normalValue;

	private BuiltInType(String n, String v) {
		declareType = n;
		normalValue = v;
	}

	public String getNormalValue() {
		return normalValue;
	}
	
	public String getDeclareType() {
		return declareType;
	}
	
	public String getParserName() {
		return declareType;
	}

	public static BuiltInType parse(String type) {
		for (BuiltInType ft : values())
			if (ft.declareType.equalsIgnoreCase(type))
				return ft;
		return null;
	}
}
