package dawn.dt.data;

import java.util.List;

public interface DataParser {
    Object parse(String val, int col, List<String> row);

    String getDeclareType();
    
    String getParserName();
}
