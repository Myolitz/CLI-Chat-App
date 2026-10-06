package ClientProfiles;

import ServerHandling.ServerThread;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ClientHandling
{
    @Getter @Setter
    private String name;
    private List<ServerThread> friendList;


    public ClientHandling(String name)
    {
        this.name = name;
        friendList = new ArrayList<>();
    }

    public void createGroup()
    {

    }
}
