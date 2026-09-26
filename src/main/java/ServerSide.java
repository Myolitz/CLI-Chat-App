/** Class: COSC 2327
 *  LAST EDITED: 9/24/26
 * @author Jesus Cardenas
 * @operating-system Endeavour OS
 */

//Libraries
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerSide
{
    private ArrayList<Socket> socketLists = new ArrayList<>();
    protected int idxUserClient;
    protected int numUsers;

    public void start()
    {
        // Object initializing
        DataInputStream clientReceive;
        DataOutputStream clientSend;
        DataOutputStream srvrLog;

        //Variables
        String clientString = "";



        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port " + port);

            /*
                Will
             */
            socketLists.add(serverSocket.accept());

//            clientReceive = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
//            clientSend = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
//            srvrLog = new DataOutputStream(new BufferedOutputStream(System.out));



            System.out.println("Client Accepted");



            int numClientResp = 0;
            while(numClientResp < 5)
            {
                try
                {
                    clientString = clientReceive.readUTF();
                    clientSend.writeUTF("Message Received: " + clientString + "\n");
                    clientSend.flush();
                    numClientResp++;
                }
                catch (IOException f)
                {
                    System.out.println(f);
                    numClientResp = 5;
                }
            }

            System.out.println("Goodbye from the server side!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(Socket userClient)
    {

    }
}
