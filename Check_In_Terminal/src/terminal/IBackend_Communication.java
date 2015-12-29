package terminal;

public interface IBackend_Communication 
{
	boolean sendQR(int Terminalnumber, String qr);
	boolean sendWeight(int Terminalnumber, int weight);
}
