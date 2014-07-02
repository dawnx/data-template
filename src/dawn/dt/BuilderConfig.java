package dawn.dt;

import dawn.dt.data.IDataReader;

public class BuilderConfig {

	/**
	 * 生成代码时，所使用的包
	 */
	public String _package;

	/**
	 * 数据文件读取器
	 */
	public IDataReader _reader;

	/**
	 * 生成文件的路径
	 */
	public String _dist_path;
}
