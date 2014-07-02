package dawn.dt.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvReader implements IDataReader {

	private List<CSVRecord> records;

	private int index = 2;

	public void setFile(File f) {
		try {

			Reader in = new FileReader(f);
			CSVParser csvParser = CSVFormat.EXCEL.parse(in);
			records = csvParser.getRecords();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getComments() {
		if (records.size() < 1)
			return null;
		return readList(records.get(0).iterator());
	}

	@Override
	public List<String> getHeaderFields() {
		if (records.size() < 2)
			return null;
		return readList(records.get(1).iterator());
	}

	private List<String> readList(Iterator<String> ite) {
		List<String> list = new ArrayList<String>();
		while (ite.hasNext()) {
			list.add(ite.next());
		}

		return list;
	}

	@Override
	public void remove() {
		new Exception("not support").printStackTrace();
	}

	@Override
	public List<String> next() {
		if (hasNext())
			return readList(records.get(index++).iterator()); // ++ after
		return null;
	}

	@Override
	public boolean hasNext() {
		return records.size() > this.index;
	}

}
