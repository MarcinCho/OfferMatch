import {
  Sidebar,
  Search,
  ConversationList,
  Conversation,
} from "@chatscope/chat-ui-kit-react";
import { useContext, useEffect, useState } from "react";
import SocketClientContext from "../../context/SocketClientContext";
import UserContext from "../../context/UserContext";
import { IUserConnection } from "../../models/IUserConnection";
import backendClient from "../../webSocket/BackendClient";

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
          d.unSeen = 0;
        }
      }
      return [...prev];
    });
  };

  useEffect(() => {
    const loadFriends = async () => {
      const data = await backendClient.getFriends();
      setFriendList(data as unknown as IUserConnection[]);
      const apiResponse = await backendClient.getUnseenMessages();
      if (apiResponse && Array.isArray(apiResponse)) {
        apiResponse.forEach((r) => {
          for (const d of data) {
            if (d.connectionId === r.fromUserId) {
              d.unSeen = r.count;
            }
          }
        });
      }
    };
    loadFriends();

    const subscription = socketClient.subscribe(
      `/topic/${userId}`,

      (message: { body: string }) => {
        const messageBody = JSON.parse(message.body);
        if (
          messageBody.messageType === "CHAT" ||
          messageBody.messageType === "UNSEEN"
        ) {
          const { senderId } = messageBody;
          setFriendList((prev) => {
            for (const d of prev) {
              if (d.connectionId === senderId) {
                d.unSeen += 1;
              }
            }
            return [...prev];
          });
        } else if (messageBody.messageType === "FRIEND_OFFLINE") {
          setFriendList((prev) => {
            for (const d of prev) {
              if (d.connectionId === messageBody.userConnection.connectionId) {
                d.isOnline = false;
                break;
              }
            }
            return [...prev];
          });
        } else if (messageBody.messageType === "FRIEND_ONLINE") {
          setFriendList((prev) => {
            for (const d of prev) {
              if (d.connectionId === messageBody.userConnection.connectionId) {
                d.isOnline = true;
                break;
              }
            }
            return [...prev];
          });
        } else if (messageBody.messageType === "MESSAGE_DELIVERY_UPDATE") {
          setDeliveryStatuses(messageBody.messageDeliveryUpdate);
        }
      },
      "CHAT",
      "UNSEEN",
      "FRIEND_ONLINE",
      "FRIEND_OFFLINE",
      "MESSAGE_DELIVERY_UPDATE"
    );

    return () => {
      if (subscription) {
        subscription.unsubscribe();
      }
    };
  }, [socketClient, userId]);
  console.log(friendList);

  return (
    <div>
      <Sidebar position="left">
        {friendList.length > 0 &&
          friendList.map((friend, idx) => {
            const count =
              friend.unSeen && friend.unSeen > 0 ? `(${friend.unSeen})` : "";
            const displayText = `${friend.isOnline} chat with ${friend.connectionUsername} ${count}`;
            return (
              <div key={idx}>
                <Conversation
                  info={displayText}
                  lastSenderName={"Lilly" + deliveryStatuses}
                  name={selectedFriend?.connectionUsername}
                  onClick={() => {
                    handleSelectedFriend(friend);
                  }}
                ></Conversation>
              </div>
            );
          })}

        <Search placeholder="Search..." />
        <ConversationList>{children}</ConversationList>
      </Sidebar>
    </div>
  );
};
