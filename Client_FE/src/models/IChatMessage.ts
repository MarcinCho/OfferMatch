import { IMessageDeliveryUpdate } from "./IMessageDeliveryUpdate";
import { IUserConnection } from "./IUserConnection";
import { MessageDeliveryStatus } from "./MessageDeliveryStatus";
import { MessageTypeInfo } from "./MessageTypeInfo";

export interface IChatMessage {
  id: string;
  content: string;
  type: MessageTypeInfo;
  senderID: string;
  senderUsername: string;
  recieverId: string;
  recieverUsername: string;
  userConnection: IUserConnection;
  messageDeliveryStatus: MessageDeliveryStatus;
  messageDeliveryUpdate: IMessageDeliveryUpdate[];
}
