import { MessageDeliveryStatus } from "./MessageDeliveryStatus";

export interface IMessageDeliveryUpdate {
  id: string;
  content: string;
  messageDeliveryStatus: MessageDeliveryStatus;
}
