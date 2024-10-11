import {
  Sidebar,
  Search,
  ConversationList,
} from "@chatscope/chat-ui-kit-react";
import { useContext, useState } from "react";
import SocketClientContext from "../../webSocket/context/SocketClientContext";
import UserContext from "../../webSocket/context/UserContext";
import { IUserConnection } from "../../models/IUserConnection";

export const ContactsPanel = ({ children }: { children: React.ReactNode }) => {
  const [friendList, setFriendList] = useState<IUserConnection[]>([]);
  const [selectedFriend, setSelectedFriend] = useState<IUserConnection | null>(
    null
  );
  const { id: userId } = useContext(UserContext);
  const { socketClient } = useContext(SocketClientContext);
  const [deliveryStatuses, setDeliveryStatuses] = useState([]);

  const handleSelectedFriend = (friend: IUserConnection) => {
    setSelectedFriend(friend);
    setFriendList((prev) => {
      for (const d of prev) {
        if (d.connectionId === friend.connectionId) {
          d.seen = true;
        }
      }
      return [...prev];
    });
  };

  return (
    <Sidebar position="left">
      <Search placeholder="Search..." />
      <ConversationList>{children}</ConversationList>
    </Sidebar>
  );
};
