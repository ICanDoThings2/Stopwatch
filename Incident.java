package stopwatchPack;

import java.util.*;

public class Incident {
	
	private ArrayList<String> properties;
	
	
	
	public String valueAtInd(int asInd)
	{
		if ( properties.size() == 0 )
		{
			return "";
		}
		
		return properties.get(asInd);
	}
	

	public Incident (String asInc)
	{
		properties = new ArrayList<String>();
		
		int lastStop = 0;
		
		for ( int i = 0; i < asInc.length(); i++ )
		{
			
			if ( i == asInc.length() - 1)
			{
				properties.add( asInc.substring(lastStop, asInc.length() - 1).replace("–", "-") );
				
				break;
			}
			
			if ( asInc.charAt(i) == ',' )
			{
				properties.add( asInc.substring(lastStop, i));
				
				
				lastStop = i + 1;
			}
		}

		
	}
	

}
