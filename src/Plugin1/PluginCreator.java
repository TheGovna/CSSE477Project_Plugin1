package Plugin1;
import java.io.File;

import plugins.IPlugin;

public class PluginCreator {

	
	public IPlugin createPlugin(File name){
		IPlugin temp = null;
		try {
			temp = new Plugin1(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}
