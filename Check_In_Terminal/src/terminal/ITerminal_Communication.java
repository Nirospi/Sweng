package terminal;

public interface ITerminal_Communication 
{
	
	boolean changeGuiMessage(int messageNR);
	
	/**
	 * This function aborts the current checkin process and displays a message, why the process was aborted.
	 * The terminal waits until userconfirmation and then returns to the default screen/message
	 * 
	 * @param message Type of Abort-message
	 * @return true, once default state is reached.
	 */
	boolean abortCheckin(int message); // zum Abbrechen, falls z.B. QR invalid oder Gepaeck ung�ltig, Message gibt informationen
	
	boolean displayFlightInfo(int flight);

}
