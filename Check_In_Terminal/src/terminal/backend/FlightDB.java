package terminal.backend;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightDB 
{
	private static String file_path = "flights.csv";
	private String qr_code = "";
	private boolean checked_in = false;
	private String first_name = "";
	private String last_name = "";
	private int num_luggage;
	private int weight_combined;
	private String flight_info = "";
	private Date date = null;
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
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			param = null;
		}
		
		if(param != null)
		{
			record = new FlightDB(param);
			return record;	
		}
		else 
			return null;	
	}
	
	// This constructor is private since it is meant to only be used by the find_record function 
	private FlightDB(String[] values) 
	{
		qr_code = values[0];
		checked_in = bool_from_string(values[1]);
		first_name = values[2];
		last_name = values[3];
		num_luggage = Integer.parseInt(values[4]);
		weight_combined = Integer.parseInt(values[5]);
		flight_info = values[6];
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		try 
		{
			date = formatter.parse(values[7]);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not parse date");
		}
		
		group_id = Integer.parseInt(values[8]);
	}
	
	public FlightDB()
	{
		
	}
	
	public FlightDB(String qr, boolean chkin, String fname, String lname, int numlugg, 
			int weightcomb, String finfo, String dat, int gid)
	{
		qr_code = qr;
		checked_in = chkin;
		first_name = fname;
		last_name = lname;
		num_luggage = numlugg;
		weight_combined = weightcomb;
		flight_info = finfo;
		set_date(dat);
		group_id = gid;
	}
	
	public String to_string()//returns a string which contains a comma separated text line
	{
		String retval = "";
		
		retval += qr_code + "," + String.valueOf(checked_in) + "," + first_name + "," + last_name +
				"," + String.valueOf(num_luggage) + "," + String.valueOf(weight_combined) + "," +
				  flight_info + "," + get_date_string() + "," + String.valueOf(group_id);
		
		return retval;
	}
	
	public void append() throws IOException
	{	
		if(is_valid())
		{
			if(search_file(qr_code) == null) // if the qr_code is not already in the database
			{
				FileWriter fwrite = new FileWriter(file_path,true);
				fwrite.write(to_string());
				fwrite.write("\n");
				fwrite.close();
			}
			else
			{
				System.out.println("QR code already in database");
			}
		}
		else
		{
			System.out.println("Cannot append. Validation failed");
		}
	}
	
	public void update()//saves the 
	{
		if(is_valid())
		{
			String line = null;
			String text = "";
			BufferedReader br = null;
			
			try
			{
				br = new BufferedReader(new FileReader(file_path));
				
				while ((line = br.readLine()) != null)
				{
					if(line.contains(qr_code))
					{
						line = to_string();
					}
					
					text += line + '\n';
				}
				
				br.close();
				
				FileWriter fw = new FileWriter(file_path);
				fw.write(text);
				fw.close();
			}
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("File not found");
			}
			catch(IOException f)
			{
				f.printStackTrace();
				System.out.println("IO error");
			}
		}
		else
		{
			System.out.println("Cannot update. Validation failed");
		}
	}
	
	private boolean is_valid() //checks whether the record is ready to be stored in database
	{
		boolean retval = false;
		
		if(!qr_code.isEmpty())
			if(!first_name.isEmpty())
				if(!last_name.isEmpty())
					if(num_luggage >= 0)
						if(weight_combined >= 0)
							if(!flight_info.isEmpty())
								if(date != null)
									if(group_id >= 0)
										retval = true;
		
		return retval;
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
			
			while(match == false && (line = br.readLine()) != null)
			{
					values = line.split(seperator);
					read_qr_code = values[0];
					if(read_qr_code.equals(qr))
						match = true;
			}
			
			br.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found");
		}
		
		
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
	
	public String get_date_string()
	{
		SimpleDateFormat datestring = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return datestring.format(get_date());
	}
	
	public int get_group_id()
	{
		return group_id;
	}
	
	//==== setters ==============
	
	public void set_qr(String qr)
	{
		qr_code = qr;
	}
	
	public void set_checked_in(boolean val)
	{
		checked_in = val;
	}
	
	public void set_firstname(String fname)
	{
		first_name = fname;
	}
	
	public void set_lastname(String lname)
	{
		last_name = lname;
	}
	
	public void set_num_luggage(int numlugg)
	{
		num_luggage = numlugg;
	}
	
	public void set_weight_combined(int weightcomb)
	{
		weight_combined = weightcomb;
	}
	
	public void set_flight_info(String finfo)
	{
		flight_info = finfo;
	}
	
	public void set_date(Date dat)
	{
		date = dat;
	}
	
	public void set_date(String dat)
	{
		SimpleDateFormat datestring = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		try 
		{
			date = datestring.parse(dat);
		} 
		catch (ParseException e) 
		{
			datestring = new SimpleDateFormat("dd.MM.yyyy");
			
			try
			{
				date = datestring.parse(dat);
			}
			catch (ParseException ee) 
			{
				date = new Date(); //use current date
				System.out.println("Invalid date string");
				//ee.printStackTrace();
			}
			
		}
	}
	
	public void set_group_id(int gid)
	{
		group_id = gid;
	}
	
	
	public static void main(String[] args) throws IOException// Test main
	{
		
		FlightDB rec = FlightDB.find_record("027HZT01a");
		
		if(rec != null)
		{
			System.out.println("Ticket Code: " + rec.get_qr());
			System.out.println("Passenger:" + rec.get_firstname()+" " + rec.get_lastname());
			System.out.println("Checked in: " + rec.is_checked_in());
			System.out.println("Pieces of luggage: " + rec.get_num_luggage());
			System.out.println("Combined luggaage weight: " + rec.get_weight_combined() +"kg");
			System.out.println("Flight Info: " + rec.get_flight_info());
			System.out.println("Date: " + rec.get_date_string());
			System.out.println("Group ID: " + rec.get_group_id());
		}
		else
			System.out.println("No matches.");
		
		rec.set_flight_info("info changed");
		rec.update();
		FlightDB newrec = new FlightDB("ccc000",false, "Daniel", "Schmidt", 1, 10, "Irgendwohin", "12.01.2016",0);
		FlightDB somerec = new FlightDB();
		newrec.append();
		somerec.append();
	}

}
