package terminal;

public interface IBackend_Communication 
{

	boolean abbortCheckIn(int Terminalnumber);
	boolean sendQR(int Terminalnumber, int qr);
	boolean sendWeight(int Terminalnumber, int weight);
}