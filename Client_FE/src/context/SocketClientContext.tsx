import { createContext } from "react";
import { SocketClient } from "../webSocket/WsClient";
import { webSocketUrl } from "../ConfigConstats";

interface ISocketClientContext {
  socketClient: SocketClient;
}
const SocketClientContext = createContext<ISocketClientContext>({
  socketClient: new SocketClient(webSocketUrl, ""),
});
export default SocketClientContext;
export type { ISocketClientContext };
