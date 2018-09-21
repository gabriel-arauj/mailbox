/**
 * Time-of-day server listening to port 6013.
 *
 */
 
import java.net.*;
import java.io.*;
import java.util.ArrayList;

class Mensagem
{
	String remetenteID;
	String destinatarioID;
	String mensagem;
	public Mensagem(String remetenteID, String destinatarioID, String mensagem){
		this.remetenteID = remetenteID;
		this.destinatarioID = destinatarioID;
		this.mensagem = mensagem;
	}
	String getMensagem(){
		return this.mensagem;
	}

	String getDestinatarioID(){
		return this.destinatarioID;
	}

	String getRemetenteID(){
		return this.remetenteID;
	}
}

public class DateServer
{
	public static String mailbox(String in){
		String[] dado;
		String remetenteID;
		String destinatarioID;
		String mensagem;
		ArrayList<Mensagem> caixaEntrada = new ArrayList<Mensagem>();

		try{
			dado = in.split(":");
			if (dado[0].equals("enviar")) {
				remetenteID = dado[1];
				destinatarioID = dado[2];
				mensagem = dado[3];
				Mensagem novaMensagem = new Mensagem(remetenteID, destinatarioID, mensagem);
				caixaEntrada.add(novaMensagem);
			}else if (dado[0].equals("receber")) {
				remetenteID = dado[1];
				for (Mensagem i : caixaEntrada) {
					if(i.getRemetenteID() == remetenteID){
						mensagem = i.getMensagem();
						return mensagem;
					}
				}
				return "Sem mensagem";
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
		return null;
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
				String resp = mailbox(line);
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
