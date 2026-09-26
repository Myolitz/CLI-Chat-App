package ClientProfiles;

//Libraries:
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

//Packages:

public class UserClient
{
    //Object Initialization
    DataInputStream clientReceive;
    DataOutputStream clientSend;
    DataOutputStream srvrLog;

    //Variable Initialization
    String userName;
    InetAddress userAddress;

    public UserClient(Socket clientSocket)
    {
        try
        {
            clientReceive = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            clientSend = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            srvrLog = new DataOutputStream(new BufferedOutputStream(System.out));

            clientSend.writeUTF("What should you be called?");
            userName = clientReceive.readUTF();
            userAddress = clientSocket.getLocalAddress();

        } catch (IOException e) {
            System.out.println("Couldn't find Socket Stream!");
        }
    }
}
