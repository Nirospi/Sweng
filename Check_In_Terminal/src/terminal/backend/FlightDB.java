package terminal.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightDB 
{
	private static String file_path = "flights.csv";
	private boolean successfull;
	private boolean checked_in;
	private String qr_code;
	private String first_name;
	private String last_name;
	private int num_luggage;
	private int weight_combined;
	private String flight_info;
	private Date date;
	private int group_id; //if 0 then no group
	
	//This static function searches the database (CSV file) for a record which contains the qr code specified in the
	//parameter. If such a record is found, it creates a FlightDB instance corresponding to the
	//record. Otherwise it returns null.
	public static FlightDB find_record(String qr)
	{
		FlightDB record = null;
		String[] param = null;
		
		try 
		{
			param = search_file(qr);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(param != null)
		{
			record = new FlightDB(param);
			if(record.successfull == true)
				return record;
			else 
				return null;
		}
		else 
			return null;	
	}
	
	//Constructor is private. Instances of this Class can only be created by using the static method 
	//"find_record(String qr)". 
	private FlightDB(String[] values) 
	{
		successfull = true;
		qr_code = values[0];
		checked_in = bool_from_string(values[1]);
		first_name = values[2];
		last_name = values[3];
		num_luggage = Integer.parseInt(values[4]);
		weight_combined = Integer.parseInt(values[5]);
		flight_info = values[6];
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		try 
		{
			date = formatter.parse(values[7]);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			successfull = false;
		}
		
		group_id = Integer.parseInt(values[8]);
	}
	
	private static String[] search_file(String qr) throws IOException
	{
		boolean match = false;
		BufferedReader br = null;
		String line = "";
		String read_qr_code = "";
		String seperator = ",";
		String[] values = null;
		
		try 
		{
			br = new BufferedReader(new FileReader(file_path));
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while(match == false && (line = br.readLine()) != null)
		{
				values = line.split(seperator);
				read_qr_code = values[0];
				if(read_qr_code.equals(qr))
					match = true;
		}
		
		br.close();
		
		if(match == true)
			return values;
		else
			return null;
	}
	
	private boolean bool_from_string(String s)
	{
		return s.equals("true");	
	}
	
	//==== getters ==================
	
	public boolean is_checked_in()
	{
		return checked_in;
	}
	
	public String get_qr()
	{
		return qr_code;
	}
	
	public String get_firstname()
	{
		return first_name;
	}
	
	public String get_lastname()
	{
		return last_name;
	}
	
	public int get_num_luggage()
	{
		return num_luggage;
	}
	
	public int get_weight_combined()
	{
		return weight_combined;
	}
	
	public String get_flight_info()
	{
		return flight_info;
	}
	
	public Date get_date()
	{
		return date;
	}
	
	public int get_group_id()
	{
		return group_id;
	}
	
	public static void main(String[] args)// Test main
	{
		
		FlightDB rec = FlightDB.find_record("39hga823G");
		
		if(rec != null)
		{
			System.out.println("Ticket Code: " + rec.get_qr());
			System.out.println("Passenger:" + rec.get_firstname()+" " + rec.get_lastname());
			System.out.println("Checked in: " + rec.is_checked_in());
			System.out.println("Pieces of luggage: " + rec.get_num_luggage());
			System.out.println("Combined luggaage weight: " + rec.get_weight_combined() +"kg");
			System.out.println("Flight Info: " + rec.get_flight_info());
			System.out.println("Date: " + rec.get_date().toString());
			System.out.println("Group ID: " + rec.get_group_id());
		}
		else
			System.out.println("No matches.");
		
	}
}
