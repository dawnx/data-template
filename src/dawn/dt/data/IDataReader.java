package dawn.dt.data;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public interface IDataReader extends Iterator<List<String>> {

	public List<String> getComments();

	public List<String> getHeaderFields();
	
	public List<String> next();
	
	public boolean hasNext();

	public void setFile(File f);
}
