package dawn.dt;

import java.io.File;
import java.util.List;

public class LoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File f = new File("test.csv");
		List<TestTemplate> list  = DataLoader.load(TestTemplate.class, f);
		
		System.out.println("ok");
	}

}
