package terminal.backend;

import terminal.IBackend_Communication;

public class Backend implements IBackend_Communication
{
	FlightDB record = null;

	@Override
	public boolean sendQR(int Terminalnumber, String qr) 
	{
		boolean retval = false;
		
		// TODO Auto-generated method stub
		return retval;
	}

	@Override
	public boolean sendWeight(int Terminalnumber, int weight) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
