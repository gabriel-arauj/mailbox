/**
 * Time-of-day server listening to port 6013.
 *
 */
 
import java.net.*;
import java.io.*;

public class DateServer
{
	public static String operacao(String in){
		String[] dado;
		String operador;
		double operando1;
		double operando2;
		try{
			dado = in.split(":");
			operador = dado[0];
			operando1 = Double.parseDouble(dado[1]);
			operando2 = Double.parseDouble(dado[2]);
		}catch(Exception e){
			return null;
		}


		double resp = 0;
		switch (operador) {
			case "+":
			resp = operando1 + operando2;
			break;
			case "-":
			resp = operando1 - operando2;
			break;
			case "*":
			resp = operando1 * operando2;
			break;
			case "/":
			if(operando2 != 0)
				resp = operando1 / operando2;
			break;
			default:
				resp = 0;
		}

		return String.format("%.2f "+ operador + " %.2f = %.2f", operando1, operando2, resp);
	}
	public static void main(String[] args) throws IOException {
		InputStream in = null;
		BufferedReader bin = null;
		Socket client = null;
		ServerSocket sock = null;

		try {
			sock = new ServerSocket(6013);
			// now listen for connections
			while (true) {
				client = sock.accept();
				System.out.println("server = " + sock);
				System.out.println("client = " + client);

				//preparar para receber a mensagem
				in = client.getInputStream();
				bin = new BufferedReader(new InputStreamReader(in));
				// we have a connection
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
				String line;

				line = bin.readLine();
				System.out.println("Mensagem recebida: " + line);
				String resp = operacao(line);
				if (resp == null) {
					pout.println("ERRO de parâmetros");
				}else{
					pout.println(resp);	
				}
				pout.close();
				client.close();			
			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
		finally {
			if (sock != null)
				sock.close();
			if (client != null)
				client.close();
		}
	}
}
