import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Payyans {

	String input_file_name;
	String output_file_name;
	String mapping_file_name;
	String direction;
	Map<String,String> rulesDict = new HashMap<String,String>(); //Substitute for dictionary
	int pdf;
	
	Payyans() throws IOException
	{
		input_file_name = "";
		output_file_name = "";
		mapping_file_name = "";
		pdf = 0;
		direction = "a2u";
		//LoadRules();
		TestLoadRules();
		//TODO : Normalizer instance
	}
	
	void LoadRules() throws IOException
	{
	
	String line = "";
	String text = "";
	int line_number = 0;
	File fileDir = new File("maps/"+mapping_file_name+".map");
	 
	BufferedReader rules_file = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
	while(true)
	{
		line_number++;
		byte[] utf8;
		try{
			
		utf8 = rules_file.readLine().getBytes("UTF-8");
		}catch(NullPointerException e){
			
			break;
		}
		text = new String(utf8, "UTF-8");
		if(text=="")
		{
			break;
		}
		if(text.length()==0) //Null String is read sometimes. :/
		{
			break;
		}
		if(text.charAt(0) == '#')
		{
			continue; //skip comments
		}
		line = text.trim(); //Using StringUtils.strip() will remove unicode whitespace too.
		if(line == "")
		{
			continue;
		}
		if(line.split("=").length!=2)
		{
			System.out.println("Syntax error in ASCII to Unicode Map in line number, " + line_number);
			System.out.println("Line : " + text);
			break;
		}
		String[] parts = line.split("=");
		String lhs = parts[0];
		String rhs = parts[1];
		lhs = lhs.trim();
		rhs = rhs.trim();
		if(direction=="a2u")
		{
			rulesDict.put(lhs, rhs);
		}
		else
		{
			rulesDict.put(rhs, lhs);
		}
	}
	}
	
	void TestLoadRules() throws IOException
	{
		String[] mapping_files = {"ambili","charaka","haritha","indulekha","karthika","manorama","matweb","nandini","panchari","revathi","template","uma","valluvar"};
		for(int i = 0;i<mapping_files.length;i++)
		{
			mapping_file_name = mapping_files[i];
			LoadRules();
			System.out.println(mapping_file_name +" has A mapped to"+ rulesDict.get("A"));
		}
	}
}
