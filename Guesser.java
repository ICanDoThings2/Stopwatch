package stopwatchPack;

import java.util.*;
import java.io.*;



public class Guesser 
{
	
	public static boolean checkStrNum ( String isNum )
	{
		for ( int tChar = 0; tChar < isNum.length(); tChar++ )
		{
			if ( isNum.charAt(tChar) != '.' ^ !Character.isDigit( isNum.charAt(tChar) ) )
			{
				return false;
			}
		}
			
		return true;
	}
	
	public static boolean checkStrFloat ( String checkStr )
	{
		
		if ( checkStr.contains(".") && checkStrNum(checkStr) )
		{
			return true;
		}
		
		return false;
	}
	
	public static int casualtyRelevancy(int propInd, String propSetting, ArrayList<Incident> ListOfInc )
	{
		int rating = 0;

		for( Incident ListedInc : ListOfInc)
		{
			if ( ListedInc.valueAtInd(propInd).compareTo(propSetting) == 0 )
			{
				rating += 1;
			}
		}
		
		return rating;
	}
	
	public static int casualtyLvl( Incident fromInc )
	{
		int casScore = 0;
		
		casScore += Integer.parseInt( fromInc.valueAtInd( 13 ) ) * 1;
		casScore += Integer.parseInt( fromInc.valueAtInd( 14 ) ) * 3;
		casScore += Integer.parseInt( fromInc.valueAtInd( 15 ) ) * 2;
		
		return casScore;
	}
	
	public static ArrayList<Incident> compileCasualtyList( ArrayList<Incident> fromList )
	{
		ArrayList<Incident> casualtyCases = new ArrayList<Incident>();
		
		for ( Incident ofCase : fromList )
		{
			if ( casualtyLvl( ofCase ) != 0 )
			{
				casualtyCases.add(ofCase);
			}
		}
		
		return casualtyCases;
	}
	
	public static ArrayList<String> potentialVals( int asInd, ArrayList<Incident> allInc )
	{
		ArrayList<String> possibilities = new ArrayList<String>(); 
		
		for ( Incident tInd : allInc )
		{
			if ( !possibilities.contains( tInd.valueAtInd(asInd) ) )
			{
				possibilities.add(tInd.valueAtInd(asInd));
			}
		}
		
		return possibilities;
		
	}
	
	public static void main( String[] args )
	{

		File reading;
		
		Scanner interpreter;
		
		Incident base;
		
		try 
		{
			reading = new File("C:/pedestrian_crashes.csv"); // In any other case this needs replacing.
			interpreter = new Scanner(reading);
			
			int usedInd = 16; // This was for debugging purposes, apologies.

			ArrayList<Incident> Incidents = new ArrayList<Incident>();
			
			base = new Incident( interpreter.nextLine() );
			
			while ( interpreter.hasNextLine() )
			{
				Incidents.add( new Incident(interpreter.nextLine() ) );
			}
			
			
			
			ArrayList<Incident> notedCases = compileCasualtyList(Incidents); // Gives us the cases where there were casualties.
			System.out.println( notedCases.size() );
			
			
			/*
			 * Now let's see what we can check.
			 */
			
			int curInd = 0; // What property index are we checking?
			int highInd = 0;
			String highestProp = base.valueAtInd(0); // Of possible values, what has had the greatest of casualties?
			int curScore = 0, highScore = 0;
			

			while ( curInd < 24 )
			{
				ArrayList<String> usedValues = potentialVals(curInd, notedCases);
				
				for ( String possProp : usedValues )
				{
					for ( Incident reported : notedCases )
					{
						if ( reported.valueAtInd(curInd).compareTo(possProp) == 0 )
						{
							curScore += casualtyLvl( reported );
						}
					}
					
					if ( curScore > highScore )
					{
						highestProp = possProp;
						highScore = curScore;
						curScore = 0;
						highInd = curInd;
						
						System.out.println("Newest high! At: " + curInd);
						break;
					}
				}
				
				if ( curInd == 12)
				{
					curInd = 16;
					
					continue;
				}
				
				curInd ++;
			}
			
			System.out.println("It seems that when : " + base.valueAtInd(highInd) + " is of " + highestProp + " the casualties are greatest. This is of all values");
			
			ArrayList<String> altValues = potentialVals(19, notedCases);
			
			int mostLightScore = 0;
			String mostLightImpactProp = ""; 
			int curLightScore = 0;
			
			for ( String worst : altValues )
			{
				for ( Incident byLightCase : notedCases )
				{
					if ( byLightCase.valueAtInd(19) == worst )
					{
						curLightScore++;
					}
					
					if ( curLightScore > mostLightScore )
					{
						mostLightScore = curLightScore;
						curLightScore = 0;
						mostLightImpactProp = worst;
						break;
					}
				}
			}
			
			System.out.println("For instance however, for lighting of: " + mostLightImpactProp + " " + "casualties seem more often" );


		}
		catch ( FileNotFoundException FileErr )
		{
			System.out.println("The ship has sunk, captain.");
		}
		
		
		
	}
	
	
	Scanner reader;
	ArrayList<Incident> Deadliest;
	
	
	

	
	
	
	
}
